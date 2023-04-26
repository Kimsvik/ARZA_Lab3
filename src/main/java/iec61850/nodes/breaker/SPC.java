package iec61850.nodes.breaker;

import iec61850.objects.samples.Attribute;

public class SPC {
    private Attribute<Boolean> stVal = new Attribute<>( false);

    public Attribute<Boolean> getStVal() {
        return stVal;
    }

    public void setStVal(Attribute<Boolean> stVal) {
        this.stVal = stVal;
    }
}