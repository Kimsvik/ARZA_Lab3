package protection.model.dataobjects.protection;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.AnalogValue;

public class ASG {
    @Getter @Setter
    private AnalogValue SetMag = new AnalogValue();

}