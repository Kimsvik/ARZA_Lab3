package breakerControl.objects.samples;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.AnalogValue;

public class MV {
    @Getter @Setter
    private AnalogValue instMag = new AnalogValue();
}
