package simulator.model;

import simulator.misc.Vector2D;

public class Sheep extends Animal{

    private Animal dangerSource;
    private SelectionStrategy dangerStrategy;

    // Primera constructora
    public Sheep(SelectionStrategy mateStrategy, SelectionStrategy dangerStrategy,  Vector2D pos){
        super("Sheep", Diet.HERVIVORO, 40.0, 35.0, mateStrategy, pos);
        this.dangerStrategy = dangerStrategy;
        this.dangerSource = null;
    }

    // Constructora para el nacimineto del bebe tipo sheep
    protected Sheep(Sheep p1, Animal p2){
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void setMateStateAction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void setHungerStateAction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void setDangerStateAction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void setDeadStateAction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
