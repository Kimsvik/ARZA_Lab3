package breakerControl.nodes.protection;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.LN;
import protection.model.dataobjects.measurements.WYE;
import protection.model.dataobjects.protection.ACD;
import protection.model.dataobjects.protection.Direction;

public class RDIR extends LN {

    @Getter @Setter
    private ACD Dir = new ACD();
    @Getter @Setter
    private WYE N = new WYE();
    @Override
    public void process() {
        if(N.getPhsA().getCVal().getAng().getF().getValue() < -20 && N.getPhsA().getCVal().getAng().getF().getValue() > -180){
            Dir.getDirPhsA().setValue(Direction.BACKWARD);
        }
        if(N.getPhsB().getCVal().getAng().getF().getValue() < -20 && N.getPhsB().getCVal().getAng().getF().getValue() > -180){
            Dir.getDirPhsB().setValue(Direction.BACKWARD);
        }
        if(N.getPhsC().getCVal().getAng().getF().getValue() < -20 && N.getPhsC().getCVal().getAng().getF().getValue() > -180){
            Dir.getDirPhsC().setValue(Direction.BACKWARD);
        }
    }

}
