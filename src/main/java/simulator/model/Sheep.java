package simulator.model;

import simulator.misc.Utils;
import simulator.misc.Vector2D;

public class Sheep extends Animal {

    private Animal dangerSource;
    private SelectionStrategy dangerStrategy;

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

    @Override
    public void update(double dt) {
        RegionManager rm = this.regionMngr.;
        if(!this.state.equals(State.DEAD)){
            this.setState(this.state);
            rm.getfood(this, dt);

            if((this.getEnergy() == 0.0) || (this.getAge() > 8.0)){
                this.state = State.DEAD;
            }
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void setNormalStateAction() {
        advanceAnimal();
        changeState();
    }

    private void advanceAnimal() {
        if (pos.distanceTo(dest) < 8)
            randomDestination();

        this.move(speed * dt * Math.exp((energy - 100.0) * 0.007));
        age += dt;
        this.energy = aux(this.energy - 20.0 * dt, 0.0, 100.0);
        this.desire = aux(this.desire + 40.0 * dt, 0.0, 100.0);

    }

    private static double aux(double v, double min, double max) {
        return Math.max(min, Math.min(max, v)); // si supera un numero por arriba o por abajo se queda con los valores
                                                // que yo quiero
    }

    private void randomDestination() {
        double x = Utils.RAND.nextDouble(regionMngr.getWidth() - 1);
        double y = Utils.RAND.nextDouble(regionMngr.getHeight() - 1);
        this.dest = new Vector2D(x, y);
    }

    private void changeState() {
        if (dangerSource == null) {
            // terminar esto
            // dangerSource = dangerStrategy.select(,);
        }
        if (dangerSource != null) {
            this.state = State.DANGER;
        } else if (this.desire > 65)
            this.state = State.MATE;
    }

    @Override
    protected void setMateStateAction() {

    }

    @Override
    protected void setHungerStateAction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void setDangerStateAction() {
        if (this.dangerSource != null && this.dangerSource.getState().equals(State.DEAD)) {
            this.dangerSource = null;
        }
        if (this.dangerSource == null) {
            this.advanceAnimal();
        }
    }

    @Override
    protected void setDeadStateAction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
