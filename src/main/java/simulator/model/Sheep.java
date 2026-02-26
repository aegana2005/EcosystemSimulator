package simulator.model;

import java.util.List;

import simulator.misc.Utils;
import simulator.misc.Vector2D;

public class Sheep extends Animal {
    private Animal dangerSource;
    private SelectionStrategy dangerStrategy;
    private double dt;

    // Primera constructora
    public Sheep(SelectionStrategy mateStrategy, SelectionStrategy dangerStrategy, Vector2D pos) {
        super("Sheep", Diet.HERVIVORO, 40.0, 35.0, mateStrategy, pos);
        this.dangerStrategy = dangerStrategy;
        this.dangerSource = null;
    }

    // Constructora para el nacimineto del bebe tipo sheep
    protected Sheep(Sheep p1, Animal p2) {
        super(p1, p2);
        this.dangerStrategy = p1.dangerStrategy;
        this.dangerSource = null;
    }

    // Para comprobar si la posicion actual de la obeja esta dentro de las dimensiones de la matriz.
    private boolean correctPos(){
        int col = (int) Math.floor(this.getPosition().getX() / regionMngr.getRegionWidth());
        int row = (int) Math.floor(this.getPosition().getY() / regionMngr.getRegionHeight());
        return col >= 0 && col < regionMngr.getCols() && row >= 0 && row < regionMngr.getRows();
    }

    @Override
    public void update(double dt) {
        this.dt = dt;
        if (!this.state.equals(State.DEAD)) {
            this.setState(this.state);
            if(!this.correctPos()){
                double x = pos.getX();
                double y = pos.getY();
                double width = regionMngr.getWidth();
                double height = regionMngr.getHeight();

                while (x >= width) x = (x - width);
                while (x < 0) x = (x + width);
                while (y >= height) y = (y - height);
                while (y < 0) y = (y + height);
                this.state = State.NORMAL;
            }
            if ((this.getEnergy() == 0.0) || (this.getAge() > 8.0)) {
                this.state = State.DEAD;
            }
            if(!this.getState().equals(State.DEAD)){
                double comida;
                comida = this.regionMngr.getfood(this, dt);
                this.energy = Math.max(0.0, Math.min(100.0, comida));
            }
        }
    }

    ///////////////////////////////////////////////////// FUNCIONES AUXILIARES

    private List<Animal> getDangerousAnimals() {
        return regionMngr.getAnimalsInRange(this,
                a -> a.getState() != State.DEAD && a.getDiet() == Diet.CARNIVORO);
    }

    private List<Animal> getMateAnimals() {
        return regionMngr.getAnimalsInRange(this,
                a -> a.getState() != State.DEAD && a.getGeneticCode().equals(getGeneticCode()));
    }

    // Metodo que elige una posicion aleatoria cuando en el estado normal cuando la
    // distancia entre pos y dest es menor a 8.
    private void randomDestination() {
        double x = Utils.RAND.nextDouble(regionMngr.getWidth() - 1);
        double y = Utils.RAND.nextDouble(regionMngr.getHeight() - 1);
        dest = new Vector2D(x, y);
    }

    ////////////////////////////// NORMAL
    @Override
    protected void setNormalStateAction() {
        advanceAnimalNormal();
        changeStateNormal();
    }

    private void changeStateNormal() {
        if (this.dangerSource == null) {
            this.dangerSource = this.dangerStrategy.select(this, getDangerousAnimals());
        }
        if (this.dangerSource != null) {
            this.state = State.DANGER;
        } else if (this.desire > 65)
            this.state = State.MATE;
    }

    private void advanceAnimalNormal() {
        if (pos.distanceTo(dest) < 8)
            randomDestination();
        this.move(speed * dt * Math.exp((energy - 100.0) * 0.007));
        age += dt;
        energy = Utils.constrainValueInRange(energy - 20.0 * dt, 0.0, 100.0);
        desire = Utils.constrainValueInRange(desire + 40.0 * dt, 0.0, 100.0);

    }

    ////////////////////////////// MATE
    @Override
    protected void setMateStateAction() {
        // Cuidado con este if
        if ((mateTarget != null && (mateTarget.getState().equals(State.DEAD))
                || getPosition().distanceTo(mateTarget.getPosition()) >= this.getSightRange())) {
            mateTarget = null;
        }
        if (this.mateTarget == null) {
            mateTarget = mateStrategy.select(this, getMateAnimals());
            if (mateTarget == null)
                advanceAnimalNormal();
        } else {
            advanceAnimalMate();
        }
        if (this.dangerSource == null) {
            this.dangerSource = this.dangerStrategy.select(this, getDangerousAnimals());
        }
        if (this.dangerSource != null) {
            this.state = State.DANGER;
        } else if (this.dangerSource == null && desire < 65) {
            this.state = State.NORMAL;
        }

    }

    private void advanceAnimalMate() {
        this.dest = mateTarget.getPosition();
        move(2.0 * speed * dt * Math.exp((this.energy - 100.0) * 0.007));
        age += dt;
        energy -= 20.0 * 1.2 * dt;
        if (this.getPosition().distanceTo(mateTarget.getPosition()) < 8) {
            this.setDesire(0.0);
            mateTarget.setDesire(0);
            if (!isPregnant() && Utils.RAND.nextDouble() < 0.9)
                this.setBaby(new Sheep(this, mateTarget));
            mateTarget = null;
        }
    }
    ////////////////////////////// HUNGER

    @Override
    protected void setHungerStateAction() {
    }

    ////////////////////////////// DANGER

    @Override
    protected void setDangerStateAction() {
        if (this.dangerSource != null && this.dangerSource.getState().equals(State.DEAD)) {
            this.dangerSource = null;
        }
        if (this.dangerSource == null) {
            this.advanceAnimalNormal();
        } else {
            this.advanceAnimalDanger();
            this.ChangeStateDanger();
        }
    }

    // Metodo para hacer el paso tres para el estado DANGER.
    private void ChangeStateDanger() {
        if (this.dangerSource == null
                || (this.getPosition().distanceTo(this.dangerSource.getPosition())) >= this.getSightRange()) {
            this.dangerSource = this.dangerStrategy.select(this, getDangerousAnimals());
            // Esto lo que hace es que busca un nuevo animal que se considere peligroso y lo
            // elige segun la estrategia
        }
        if (this.dangerSource == null) {
            if (this.desire < 65.0) {
                this.state = State.NORMAL;
            } else {
                this.state = State.MATE;
            }
        }
    }

    private void advanceAnimalDanger() {
        double form = 2.0 * speed * dt * Math.exp((energy - 100.0) * 0.007);
        this.setDest(this.getPosition().plus(pos.minus(dangerSource.getPosition()).direction()));
        this.move(form);
        this.age += dt;
        energy = Utils.constrainValueInRange(energy - 20.0 * 1.2 * dt, 0.0, 100.0);
        desire = Utils.constrainValueInRange(desire + 40.0 * dt, 0.0, 100.0);
    }

    //////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////// DEAD
    @Override
    protected void setDeadStateAction() {
    }
}
