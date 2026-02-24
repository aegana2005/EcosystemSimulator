package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class Simulator implements JSONable {
    
    private double dt;
    private List<Animal> listaAnimales;
    private RegionManager rm;

    public Simulator(int cols, int rows, int width, int height, Factory<Animal> animalsFactory, Factory<Region> regionsFactory){
        this.dt = 0.0;
        this.listaAnimales = new ArrayList();
        this.rm = new RegionManager(cols, rows, width, height);
    }

    // Estructura JSON del simulador
    public JSONObject asJSON(){
        JSONObject o = new JSONObject();
        o.put("time", this.dt);
        o.put("state", this.rm.asJSON());
        return o;
    }

    private void setRegion(int row, int col, Region r){
        this.rm.setRegion(row, col, r);
    }

    public void setRegion(int row, int col, JSONObject rJson){
        // TODO Se hace con la factoria de regiones, donde le paso este json.
    }

    private void addAnimal(Animal a){
        this.listaAnimales.add(a);
        this.rm.registerAnimal(a);
    }

}
