package breakerControl.nodes.protection;

import lombok.Data;
import protection.model.common.LN;
import protection.model.dataobjects.measurements.WYE;
import protection.model.dataobjects.protection.Direction;
import protection.model.dataobjects.protection.*;

@Data
public class PTOC extends LN {

    private WYE A = new WYE();
    private ACT Op = new ACT();
    private ACD Str = new ACD();
    private ASG StrVal = new ASG();
    private ING OpDITmms = new ING();
    private double CntA = 0;
    private double CntB = 0;
    private double CntC = 0;
    private ENG DirMod = new ENG();
    private ACD Dir = new ACD();


    @Override
    public void process() {

        boolean phsA = A.getPhsA().getCVal().getMag().getF().getValue() > StrVal.getSetMag().getF().getValue() ;
        boolean phsB = A.getPhsB().getCVal().getMag().getF().getValue() > StrVal.getSetMag().getF().getValue() ;
        boolean phsC = A.getPhsC().getCVal().getMag().getF().getValue() > StrVal.getSetMag().getF().getValue();


        boolean general = phsA || phsB || phsC;
        if (phsA) {
            CntA = 1;
        }
        else{
            CntA =0;
        }
        if (phsB) {
            CntB += 1;
        }
        else{
            CntB =0;
        }
        if (phsC) {
            CntC += 1;
        }
        else{
            CntC =0;
        }

        if (DirMod.getStVal().getValue() == Direction.FORWARD){
            if (Dir.getDirPhsA().getValue() == Direction.BACKWARD) CntA = 0;
            if (Dir.getDirPhsB().getValue() == Direction.BACKWARD) CntB = 0;
            if (Dir.getDirPhsC().getValue() == Direction.BACKWARD) CntC = 0;
        }

        Op.getGeneral().setValue(CntA > OpDITmms.getSetVal() || CntB > OpDITmms.getSetVal() || CntC > OpDITmms.getSetVal() );
        Op.getPhsA().setValue(phsA);
        Op.getPhsB().setValue(phsB);
        Op.getPhsC().setValue(phsC);
    }

}
