package breakerControl.nodes;

import breakerControl.nodes.breaker.DPC;
import lombok.Data;
import protection.model.common.LN;
import protection.model.dataobjects.protection.ACT;

@Data
public class CSWI extends LN {
    private ACT OpOpn = new ACT();
    private ACT OpCls = new ACT();
    private ACT OpOpn2 = new ACT();
    private ACT OpCls2 = new ACT();
    private DPC Pos = new DPC();
    private DPC PosA = new DPC();
    private DPC PosB = new DPC();
    private DPC PosC = new DPC();

    @Override
    public void process() {
        if (OpOpn.getGeneral().getValue() & Pos.getStVal().getValue() == 2 ){
            Pos.getCtVal().setValue(false);
        }
    }






}
