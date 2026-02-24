package simulator.model;

public enum Diet {
    HERVIVORO, CARNIVORO;

    public Diet conviertoAenum(String dieta){
        Diet enumerado = null;
        switch(dieta){
            case "HERVIVORO":
                enumerado = HERVIVORO;
                break;
            case "CARNIVORO":
                enumerado = CARNIVORO;
                break;
            default:
                break;
        }
        return enumerado;    
    }
}
