package breakerControl.nodes.protection;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import protection.model.common.LN;
import protection.model.dataobjects.measurements.WYE;
import protection.model.dataobjects.protection.ACD;
import protection.model.dataobjects.protection.Direction;

/** RDIR
 *  Элемент направленной защиты */

@Data
public class RDIR extends LN {

    private ACD Dir = new ACD(); // Направление
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
