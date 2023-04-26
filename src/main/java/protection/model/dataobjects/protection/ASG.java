package protection.model.dataobjects.protection;

import protection.model.dataobjects.measurements.AnalogValue;

public class ASG {
    private AnalogValue SetMag = new AnalogValue();

    public AnalogValue getSetMag() {
        return SetMag;
    }

    public void setSetMag(AnalogValue setMag) {
        SetMag = setMag;
    }
}