package breakerControl.nodes.breaker;

import protection.model.common.Attribute;

public class SPC {
    private Attribute<Boolean> stVal = new Attribute<>( false);

    public Attribute<Boolean> getStVal() {
        return stVal;
    }

    public void setStVal(Attribute<Boolean> stVal) {
        this.stVal = stVal;
    }
}