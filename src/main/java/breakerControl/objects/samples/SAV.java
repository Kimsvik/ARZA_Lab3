package breakerControl.objects.samples;

import protection.model.dataobjects.measurements.AnalogValue;

public class SAV {

    private AnalogValue instMag = new AnalogValue();


    public AnalogValue getInstMag() {
        return instMag;
    }

    public void setInstMag(AnalogValue instMag) {
        this.instMag = instMag;
    }
}
