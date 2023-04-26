package protection.model.dataobjects.measurements;

import protection.model.dataobjects.measurements.AnalogValue;

public class MV {
    private AnalogValue instMag = new AnalogValue();


    public AnalogValue getInstMag() {
        return instMag;
    }

    public void setInstMag(AnalogValue instMag) {
        this.instMag = instMag;
    }
}
