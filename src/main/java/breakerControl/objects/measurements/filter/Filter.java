package breakerControl.objects.measurements.filter;

import lombok.Data;
import protection.model.dataobjects.measurements.Vector;
import protection.model.dataobjects.measurements.MV;

@Data
public abstract class Filter {

    public abstract void process(MV mv, Vector vector);

}
