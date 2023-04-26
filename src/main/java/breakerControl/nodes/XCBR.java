package breakerControl.nodes;

import breakerControl.nodes.breaker.DPC;
import breakerControl.nodes.breaker.SPC;
import protection.model.common.LN;

public class XCBR extends LN {
    private DPC Pos = new DPC();
    private SPC BlkOpn = new SPC();
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

    public DPC getPos() {
        return Pos;
    }

    public void setPos(DPC pos) {
        Pos = pos;
    }

    public SPC getBlkOpn() {
        return BlkOpn;
    }

    public void setBlkOpn(SPC blkOpn) {
        BlkOpn = blkOpn;
    }

    public SPC getBlkCls() {
        return BlkCls;
    }

    public void setBlkCls(SPC blkCls) {
        BlkCls = blkCls;
    }
}
