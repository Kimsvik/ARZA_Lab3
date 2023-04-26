package breakerControl.nodes.protection;

import protection.model.common.LN;
import protection.model.dataobjects.measurements.WYE;
import protection.model.dataobjects.protection.Direction;
import protection.model.dataobjects.protection.*;


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

        boolean phsA = A.getPhsA().getcVal().getMag().getF().getValue() > StrVal.getSetMag().getF().getValue() ;
        boolean phsB = A.getPhsB().getcVal().getMag().getF().getValue() > StrVal.getSetMag().getF().getValue() ;
        boolean phsC = A.getPhsC().getcVal().getMag().getF().getValue() > StrVal.getSetMag().getF().getValue();


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

    public ENG getDirMod() {
        return DirMod;
    }

    public void setDirMod(ENG dirMod) {
        DirMod = dirMod;
    }

    public ACD getDir() {
        return Dir;
    }

    public void setDir(ACD dir) {
        Dir = dir;
    }

    public WYE getA() {
        return A;
    }

    public void setA(WYE a) {
        A = a;
    }

    public ACT getOp() {
        return Op;
    }

    public void setOp(ACT op) {
        Op = op;
    }

    public ACD getStr() {
        return Str;
    }

    public void setStr(ACD str) {
        Str = str;
    }

    public ASG getStrVal() {
        return StrVal;
    }

    public void setStrVal(ASG strVal) {
        StrVal = strVal;
    }

    public ING getOpDITmms() {
        return OpDITmms;
    }

    public void setOpDITmms(ING opDITmms) {
        OpDITmms = opDITmms;
    }

    public double getCntA() {
        return CntA;
    }

    public void setCntA(double cntA) {
        CntA = cntA;
    }

    public double getCntB() {
        return CntB;
    }

    public void setCntB(double cntB) {
        CntB = cntB;
    }

    public double getCntC() {
        return CntC;
    }

    public void setCntC(double cntC) {
        CntC = cntC;
    }
}
