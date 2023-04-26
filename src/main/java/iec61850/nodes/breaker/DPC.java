package iec61850.nodes.breaker;

import iec61850.objects.samples.Attribute;

public class DPC {
    private Attribute<Byte> stVal = new Attribute<>((byte) 0);
    private Attribute<Boolean> ctVal = new Attribute<>(false);

    public Attribute<Byte> getStVal() {
        return stVal;
    }

    public void setStVal(Attribute<Byte> stVal) {
        this.stVal = stVal;
    }

    public Attribute<Boolean> getCtVal() {
        return ctVal;
    }

    public void setCtVal(Attribute<Boolean> ctVal) {
        this.ctVal = ctVal;
    }
}