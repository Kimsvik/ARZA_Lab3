package protection.model.dataobjects.measurements;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.AnalogValue;

/** MV (Measured Value)
 * Измеряемые значения */
public class MV {
    @Getter @Setter
    private AnalogValue instMag = new AnalogValue();
}
