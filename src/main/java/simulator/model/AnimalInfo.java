package simulator.model;

import simulator.misc.Vector2D;

//Esta clase sirve para pasar un objeto animal a aquellas clases que no modifican el estado del animal.
public interface AnimalInfo extends JSONable { // Note that it extends JSONable
	public State getState();
	public Vector2D getPosition();
	public String getGeneticCode();
	public Diet getDiet();
	public double getSpeed();
	public double getSightRange();
	public double getEnergy();
	public double getAge();
	public Vector2D getDestination();
	public boolean isPregnant();
}
