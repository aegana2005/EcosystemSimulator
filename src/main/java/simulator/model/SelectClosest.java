package simulator.model;

import java.util.List;

public class SelectClosest implements SelectionStrategy {

    @Override
    public Animal select(Animal a, List<Animal> as) {
        if (!as.isEmpty()) {
            Animal closestAnimal = as.getFirst(); // Me guardo el pirmero de la lista.
            double distance = -1;
            double minDistance = a.pos.distanceTo(closestAnimal.getPosition());
            for (Animal i : as) {
                distance = a.pos.distanceTo(i.getPosition());
                if (distance < minDistance) { // Si la distanciade el animal a, a el animal de
                                              // la lista en ese momento es menor a la menor
                                              // distancia que tengo, actualizo.
                    minDistance = distance;
                    closestAnimal = i; // Actualizo el animal mas cercano
                }
            }
            return closestAnimal;
        } else {
            return null;
        }
    }
}
