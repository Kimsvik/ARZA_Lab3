package iec61850.objects.samples.protection;

import iec61850.objects.samples.Attribute;
import iec61850.objects.samples.protection.dir.Direction;

public class ENG {
    private Attribute<Direction> stVal = new Attribute<>(Direction.UNKNOWN);

    public Attribute<Direction> getStVal() {
        return stVal;
    }

    public void setStVal(Attribute<Direction> stVal) {
        this.stVal = stVal;
    }
}
