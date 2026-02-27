package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Simulator;

public class Controller {

    private Simulator sim;

    // Unica constructora que recibe el simulador
    public Controller(Simulator sim){
        if(sim != null){
            this.sim = sim;
        }
    }

    public void loadData(JSONObject data){
        if(data.has("regions")){
            JSONArray rows = data.getJSONArray("row");
            JSONArray cols = data.getJSONArray("col");
            JSONObject regDescrip = data.getJSONObject("spec");
            for(int i = rows.getInt(0); i <= rows.getInt(1); i++){
                for(int j = cols.getInt(0); j <= cols.getInt(1); j++){
                    sim.setRegion(i, j, regDescrip);
                }
            }

        }
    }
}
