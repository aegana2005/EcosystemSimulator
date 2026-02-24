package simulator.model;

import java.util.List;
import java.util.function.Predicate;

// Esta interfaz representa o que un animal puede ver del gestor de regiones. En principio puede ver el mapa, pedir comida, y además puede pedir la lista de animales en su campo visual que además satisfacen alguna condición.
public interface AnimalMapView extends MapInfo, FoodSupplier {
  public List<Animal> getAnimalsInRange(Animal e, Predicate<Animal> filter);
}