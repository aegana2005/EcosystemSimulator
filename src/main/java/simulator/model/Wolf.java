package simulator.model;

import simulator.misc.Vector2D;

public class Wolf extends Animal {

    private Animal huntTarget; // Referencia a animal que quiere cazar en algun momento de la simulacion.
    private SelectionStrategy huntingStrategy;

    public Wolf(SelectionStrategy mateStrategy, SelectionStrategy huntingStrategy,  Vector2D pos){
        super("Wolf", Diet.CARNIVORO, 50.0, 60.0, mateStrategy, pos);
        this.huntingStrategy = huntingStrategy;
        this.huntTarget = null;
    }

    // Constructora para que nazca una bebe de tipo Wolf.
    protected Wolf(Wolf p1, Animal p2){
        super(p1, p2);
        this.huntingStrategy = p1.huntingStrategy;
        this.huntTarget = null;
    } 

    @Override
    public void update(double dt) {
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