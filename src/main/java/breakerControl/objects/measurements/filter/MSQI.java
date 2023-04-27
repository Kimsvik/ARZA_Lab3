package breakerControl.objects.measurements.filter;

import lombok.Data;
import protection.model.common.LN;
import protection.model.dataobjects.measurements.Vector;
import protection.model.dataobjects.measurements.WYE;

@Data
public class MSQI extends LN {


    private SEQ SeqA = new SEQ();
    private SEQ SeqV = new SEQ();

    private WYE ImbA = new WYE();
    private WYE ImbV = new WYE();



    private Vector turnBplus120 = new Vector();
    private Vector turnBminus120 = new Vector();
    private Vector turnCplus120 = new Vector();
    private Vector turnCminus120 = new Vector();
    private Vector Aneizm = new Vector();
    private Vector Bneizm = new Vector();
    private Vector Cneizm = new Vector();




    @Override
    public void process() {
        setting(ImbA, SeqA);
        setting(ImbV, SeqV);
    }
    private void setting(WYE wye, SEQ seq){

        turnBplus120.naXandY(wye.getPhsB().getCVal().getMag().getF().getValue(),
                wye.getPhsB().getCVal().getAng().getF().getValue() + 120);
        turnBminus120.naXandY(wye.getPhsB().getCVal().getMag().getF().getValue(),
                wye.getPhsB().getCVal().getAng().getF().getValue() - 120);
        turnCplus120.naXandY(wye.getPhsC().getCVal().getMag().getF().getValue(),
                wye.getPhsC().getCVal().getAng().getF().getValue() + 120);
        turnCminus120.naXandY(wye.getPhsC().getCVal().getMag().getF().getValue(),
                wye.getPhsC().getCVal().getAng().getF().getValue() - 120);

        Aneizm.naXandY(wye.getPhsA().getCVal().getMag().getF().getValue(),
                wye.getPhsA().getCVal().getAng().getF().getValue());
        Bneizm.naXandY(wye.getPhsB().getCVal().getMag().getF().getValue(),
                wye.getPhsB().getCVal().getAng().getF().getValue());
        Cneizm.naXandY(wye.getPhsC().getCVal().getMag().getF().getValue(),
                wye.getPhsC().getCVal().getAng().getF().getValue());

        seq.getC1().getCVal().tovector(((Aneizm.getX().getF().getValue() + turnBplus120.getX().getF().getValue() + turnCminus120.getX().getF().getValue())/3),
                ((Aneizm.getY().getF().getValue() + Bneizm.getY().getF().getValue()  + turnCminus120.getY().getF().getValue())/3));
        seq.getC2().getCVal().tovector(((Aneizm.getX().getF().getValue() + Bneizm.getX().getF().getValue() + Cneizm.getX().getF().getValue())/3),
                ((Aneizm.getY().getF().getValue() + Bneizm.getY().getF().getValue()  + Cneizm.getY().getF().getValue())/3));
        seq.getC3().getCVal().tovector(((Aneizm.getX().getF().getValue() + Bneizm.getX().getF().getValue() + Cneizm.getX().getF().getValue())/3),
                ((Aneizm.getY().getF().getValue() + Bneizm.getY().getF().getValue()  + Cneizm.getY().getF().getValue())/3));

    }

}
