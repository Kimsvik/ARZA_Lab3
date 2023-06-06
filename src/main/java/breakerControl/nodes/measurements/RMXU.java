package breakerControl.nodes.measurements;


import protection.model.common.LN;
import protection.model.dataobjects.measurements.CSD;
import protection.model.dataobjects.measurements.Vector;
import protection.model.dataobjects.measurements.WYE;
import lombok.Data;

import java.util.ArrayList;


@Data
public class RMXU extends LN {
    private ArrayList<WYE> inputA = new ArrayList<>();
    private WYE dif = new WYE();
    private ArrayList<WYE> Difoutput = new ArrayList<>();


    private Vector rstCurrent = new Vector();
    private CSD TmASt = new CSD();
    private WYE RstA = new WYE();
    private WYE TripPoint = new WYE();          //Тормозная характеристика

    private Vector Idifa = new Vector();
    private Vector Idifb = new Vector();
    private Vector Idifc = new Vector();

    private float dif0;
    private float k;
    private float rst0;





    @Override
    public void process() {
        rstCurrent.tovector(0,0);



        Idifa.addition(inputA.get(0).getPhsA().getCVal(), inputA.get(1).getPhsA().getCVal(), inputA.get(2).getPhsA().getCVal(), inputA.get(3).getPhsA().getCVal(), inputA.get(4).getPhsA().getCVal());
        Idifa.tovector(Idifa.getX().getF().getValue(),Idifa.getY().getF().getValue());

        Idifb.addition(inputA.get(0).getPhsB().getCVal(), inputA.get(1).getPhsB().getCVal(), inputA.get(2).getPhsB().getCVal(), inputA.get(3).getPhsB().getCVal(), inputA.get(4).getPhsB().getCVal());
        Idifb.tovector(Idifb.getX().getF().getValue(),Idifb.getY().getF().getValue());

        Idifc.addition(inputA.get(0).getPhsC().getCVal(), inputA.get(1).getPhsC().getCVal(),inputA.get(2).getPhsC().getCVal(), inputA.get(3).getPhsC().getCVal(), inputA.get(4).getPhsC().getCVal());
        Idifc.tovector(Idifc.getX().getF().getValue(),Idifc.getY().getF().getValue());

        dif.getPhsA().setCVal(Idifa);
        dif.getPhsB().setCVal(Idifb);
        dif.getPhsC().setCVal(Idifc);

        Difoutput.add(dif);

        dif0 = TmASt.getCrvPvs().get(1).getYVal().getValue();
        rst0 = TmASt.getCrvPvs().get(1).getXVal().getValue();
        k = (TmASt.getCrvPvs().get(2).getYVal().getValue() - TmASt.getCrvPvs().get(1).getYVal().getValue())/
                (TmASt.getCrvPvs().get(2).getXVal().getValue() - TmASt.getCrvPvs().get(1).getXVal().getValue());

        for (WYE w:inputA){
            if(w.getPhsA().getCVal().getMag().getF().getValue() > rstCurrent.getMag().getF().getValue()){
                rstCurrent.artog(w.getPhsA().getCVal().getX().getF().getValue(),w.getPhsA().getCVal().getY().getF().getValue());
            }
            if(w.getPhsB().getCVal().getMag().getF().getValue() > rstCurrent.getMag().getF().getValue()){
                rstCurrent.artog(w.getPhsB().getCVal().getX().getF().getValue(),w.getPhsB().getCVal().getY().getF().getValue());
            }
            if(w.getPhsC().getCVal().getMag().getF().getValue() > rstCurrent.getMag().getF().getValue()){
                rstCurrent.artog(w.getPhsC().getCVal().getX().getF().getValue(),w.getPhsC().getCVal().getY().getF().getValue());
            }

        }
        RstA.getPhsA().getCVal().artog(rstCurrent.getX().getF().getValue(),rstCurrent.getY().getF().getValue());
        RstA.getPhsB().getCVal().artog(rstCurrent.getX().getF().getValue(),rstCurrent.getY().getF().getValue());
        RstA.getPhsC().getCVal().artog(rstCurrent.getX().getF().getValue(),rstCurrent.getY().getF().getValue());

        if (rstCurrent.getMag().getF().getValue() < rst0) {
            TripPoint.getPhsA().getCVal().tovector(dif0,0);
            TripPoint.getPhsB().getCVal().tovector(dif0,0);
            TripPoint.getPhsC().getCVal().tovector(dif0,0);
        }
        else{
            TripPoint.getPhsA().getCVal().tovector(rstCurrent.getMag().getF().getValue()*k,0);
            TripPoint.getPhsB().getCVal().tovector(rstCurrent.getMag().getF().getValue()*k,0);
            TripPoint.getPhsC().getCVal().tovector(rstCurrent.getMag().getF().getValue()*k,0);
        }
    }
}
