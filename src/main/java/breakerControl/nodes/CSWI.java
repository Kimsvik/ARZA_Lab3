package breakerControl.nodes;

import breakerControl.nodes.breaker.DPC;
import protection.model.common.LN;
import protection.model.dataobjects.protection.ACT;


public class CSWI extends LN {
private ACT OpOpn = new ACT();
private ACT OpCls = new ACT();
private ACT OpOpn2 = new ACT();
private ACT OpCls2 = new ACT();
private DPC Pos = new DPC();
private DPC PosA = new DPC();
private DPC PosB = new DPC();
private DPC PosC = new DPC();

    public ACT getOpOpn() {
        return OpOpn;
    }

    public void setOpOpn(ACT opOpn) {
        OpOpn = opOpn;
    }

    public ACT getOpOpn2() {
        return OpOpn2;
    }

    public void setOpOpn2(ACT opOpn2) {
        OpOpn2 = opOpn2;
    }

    public ACT getOpCls2() {
        return OpCls2;
    }

    public void setOpCls2(ACT opCls2) {
        OpCls2 = opCls2;
    }

    public ACT getOpCls() {
        return OpCls;
    }

    public void setOpCls(ACT opCls) {
        OpCls = opCls;
    }

    public DPC getPos() {
        return Pos;
    }

    public void setPos(DPC pos) {
        Pos = pos;
    }

    public DPC getPosA() {
        return PosA;
    }

    public void setPosA(DPC posA) {
        PosA = posA;
    }

    public DPC getPosB() {
        return PosB;
    }

    public void setPosB(DPC posB) {
        PosB = posB;
    }

    public DPC getPosC() {
        return PosC;
    }

    public void setPosC(DPC posC) {
        PosC = posC;
    }

    @Override
    public void process() {
        if (OpOpn.getGeneral().getValue() & Pos.getStVal().getValue() == 2 ){
            Pos.getCtVal().setValue(false);
        }
    }






}
