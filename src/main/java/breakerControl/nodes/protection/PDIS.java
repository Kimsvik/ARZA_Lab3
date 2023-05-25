package breakerControl.nodes.protection;

import breakerControl.nodes.breaker.SPC;
import lombok.Data;
import protection.model.common.LN;
import protection.model.dataobjects.measurements.DEL;
import protection.model.dataobjects.protection.*;

/** PDIS
 *  Расстояние */
@Data
public class PDIS extends LN {

    // private ACD Str = new ACD();
    private ACT Op = new ACT(); // Срабатывание
    private ASG x0 = new ASG();
    private ASG y0 = new ASG();
    private SPC Blk = new SPC();
    private DEL Z = new DEL();
    private ASG r0 = new ASG();
    private ASG Zust = new ASG();
    private ING OpDITmms = new ING();

    private double CntAB = 0;
    private double CntBC = 0;
    private double CntCA = 0;



    @Override
    public void process() {

        boolean phsAB = Math.pow(Z.getPhsAB().getCVal().getX().getF().getValue() - x0.getSetMag().getF().getValue(),2) + Math.pow(Z.getPhsAB().getCVal().getY().getF().getValue() - y0.getSetMag().getF().getValue(),2)  <= Math.pow((Zust.getSetMag().getF().getValue()),2);
        /**
        System.out.println(Math.pow(Z.getPhsAB().getCVal().getX().getF().getValue() - x0.getSetMag().getF().getValue(),2) +
                Math.pow(Z.getPhsAB().getCVal().getY().getF().getValue() - y0.getSetMag().getF().getValue(),2));
        System.out.println(Math.pow((Zust.getSetMag().getF().getValue()),2));
        */
        boolean phsBC = Math.pow(Z.getPhsBC().getCVal().getX().getF().getValue() - x0.getSetMag().getF().getValue(),2) + Math.pow(Z.getPhsBC().getCVal().getY().getF().getValue() - y0.getSetMag().getF().getValue(),2)  <= Math.pow((Zust.getSetMag().getF().getValue()),2);

        boolean phsCA = Math.pow(Z.getPhsCA().getCVal().getX().getF().getValue() - x0.getSetMag().getF().getValue(),2) + Math.pow(Z.getPhsCA().getCVal().getY().getF().getValue() - y0.getSetMag().getF().getValue(),2) <= Math.pow((Zust.getSetMag().getF().getValue()),2);


        boolean general = phsAB || phsBC || phsCA;
        if (phsAB) {
            CntAB += 1;
        } else {
            CntAB = 0;
        }
        if (phsBC) {
            CntBC += 1;
        } else{
            CntBC = 0;
        }
        if (phsCA) {
            CntCA += 1;
        } else{
            CntCA = 0;
        }

        //if (!Blk.getStVal().getValue()) { CntAB = CntBC = CntCA = 0; }

        Op.getGeneral().setValue(CntAB > OpDITmms.getSetVal() || CntBC > OpDITmms.getSetVal() || CntCA > OpDITmms.getSetVal() );
        Op.getPhsA().setValue(CntAB > OpDITmms.getSetVal());
        Op.getPhsB().setValue(CntBC > OpDITmms.getSetVal());
        Op.getPhsC().setValue(CntCA > OpDITmms.getSetVal());
    }
}
