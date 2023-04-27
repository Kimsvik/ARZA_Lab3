package breakerControl.objects.measurements.filter;

import lombok.Data;
import protection.model.dataobjects.measurements.CMV;

/** SEQ (Sequence)
 * Последовательность */
@Data
public class SEQ {
    private CMV c1 = new CMV();
    private CMV c2 = new CMV();
    private CMV c3 = new CMV();

}
