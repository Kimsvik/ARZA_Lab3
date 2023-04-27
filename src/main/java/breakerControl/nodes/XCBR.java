package breakerControl.nodes;

import breakerControl.nodes.breaker.DPC;
import breakerControl.nodes.breaker.SPC;
import lombok.Getter;
import lombok.Setter;
import protection.model.common.LN;

public class XCBR extends LN {
    @Getter @Setter
    private DPC Pos = new DPC();
    @Getter @Setter
    private SPC BlkOpn = new SPC();
    @Getter @Setter
    private SPC BlkCls = new SPC();

    @Override
    public void process() {
        if(!Pos.getCtVal().getValue() && Pos.getStVal().getValue() == 2){
            Pos.getStVal().setValue((byte) 1);
        }
        if(Pos.getCtVal().getValue() && Pos.getStVal().getValue() == 1){
            Pos.getStVal().setValue((byte) 2);
        }
    }

}
