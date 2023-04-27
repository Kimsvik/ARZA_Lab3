package protection.model.dataobjects.protection;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.AnalogValue;

/** ASG (Analogue setting)
 * Задание значения аналогового сигнала */
public class ASG {
    @Getter @Setter
    private AnalogValue SetMag = new AnalogValue();

}