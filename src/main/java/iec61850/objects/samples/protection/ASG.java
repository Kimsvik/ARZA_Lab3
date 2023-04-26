package iec61850.objects.samples.protection;

import iec61850.objects.samples.AnalogValue;

public class ASG {
    private AnalogValue SetMag = new AnalogValue();

    public AnalogValue getSetMag() {
        return SetMag;
    }

    public void setSetMag(AnalogValue setMag) {
        SetMag = setMag;
    }
}