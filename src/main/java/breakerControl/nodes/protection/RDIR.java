package breakerControl.nodes.protection;

import protection.model.common.LN;
import protection.model.dataobjects.measurements.WYE;
import protection.model.dataobjects.protection.ACD;
import protection.model.dataobjects.protection.Direction;

public class RDIR extends LN {

    private ACD Dir = new ACD();
    private WYE W = new WYE();
    private WYE N = new WYE();
    @Override
    public void process() {
        if(N.getPhsA().getcVal().getAng().getF().getValue() < -10 && N.getPhsA().getcVal().getAng().getF().getValue() > -220){
            Dir.getDirPhsA().setValue(Direction.BACKWARD);
        }
        if(N.getPhsB().getcVal().getAng().getF().getValue() < -10 && N.getPhsB().getcVal().getAng().getF().getValue() > -220){
            Dir.getDirPhsB().setValue(Direction.BACKWARD);
        }
        if(N.getPhsC().getcVal().getAng().getF().getValue() < -10 && N.getPhsC().getcVal().getAng().getF().getValue() > -220){
            Dir.getDirPhsC().setValue(Direction.BACKWARD);
        }
    }

    public WYE getN() {
        return N;
    }

    public void setN(WYE n) {
        N = n;
    }

    public ACD getDir() {
        return Dir;
    }

    public void setDir(ACD dir) {
        Dir = dir;
    }

    public WYE getW() {
        return W;
    }

    public void setW(WYE w) {
        W = w;
    }
}
