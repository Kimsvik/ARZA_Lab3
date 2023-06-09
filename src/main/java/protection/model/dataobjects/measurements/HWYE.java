package protection.model.dataobjects.measurements;

import protection.model.dataobjects.measurements.AnalogValue;
import protection.model.common.Attribute;
import lombok.Data;

import java.util.ArrayList;

@Data
public class HWYE {
    private ArrayList<Vector> phsAHar = new ArrayList<>();
    private ArrayList<Vector> phsBHar = new ArrayList<>();
    private ArrayList<Vector> phsCHar = new ArrayList<>();
    private ArrayList<Vector> neutHar = new ArrayList<>();
    private Attribute<Integer> numHar = new Attribute<>(5);

    public HWYE(){

        for(int i=0;i<=numHar.getValue();i++){
            phsAHar.add(new Vector());
            phsBHar.add(new Vector());
            phsCHar.add(new Vector());
            neutHar.add(new Vector());
        }
    }

}
