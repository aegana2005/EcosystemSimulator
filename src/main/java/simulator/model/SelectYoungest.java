package simulator.model;

import java.util.List;

public class SelectYoungest implements SelectionStrategy{

    @Override
    public Animal select(Animal a, List<Animal> as) {
        if(!as.isEmpty()){
            Animal youngAge = as.getFirst(); // Me guardo el pirmer animal de la lista
            for(Animal animal: as){ // Recorro toda la lista de animales
                if(animal.getAge() < youngAge.getAge()){ // En la primnera iteracion comparo dos valores iguales, lfgo no entro al if, pero en las siguientes comparo para ir quedandome con el animal mas joven (el minimo).
                    youngAge = animal;
                }
            }
            return youngAge;
        }
        else{
            return null;
        }
    }
}
