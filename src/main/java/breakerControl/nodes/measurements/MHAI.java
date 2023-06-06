package breakerControl.nodes.measurements;

import protection.model.common.LN;
import breakerControl.objects.measurements.filter.Filter;
import breakerControl.objects.measurements.filter.Furier20;
import protection.model.dataobjects.measurements.HWYE;
import protection.model.dataobjects.measurements.MV;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/** MHAI
 *  Гармоники и интергармоники */
@Getter @Setter
public class MHAI extends LN {

    private HWYE HA = new HWYE(); // Последовательность тока гармоник или интергармоник
    private MV instIa = new MV();
    private MV instIb = new MV();
    private MV instIc = new MV();
    private ArrayList<Filter> iaF = new ArrayList<>();
    private ArrayList<Filter> ibF = new ArrayList<>();
    private ArrayList<Filter> icF = new ArrayList<>();


    @Override
    public void process() {
        for (int i=0;i<=HA.getNumHar().getValue();i++){
            iaF.add(new Furier20(i));
            ibF.add(new Furier20(i));
            icF.add(new Furier20(i));

            iaF.get(i).process(instIa,HA.getPhsAHar().get(i));
            ibF.get(i).process(instIb,HA.getPhsBHar().get(i));
            icF.get(i).process(instIc,HA.getPhsCHar().get(i));

        }
    }
}
