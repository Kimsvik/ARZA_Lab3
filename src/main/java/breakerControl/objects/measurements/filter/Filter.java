package breakerControl.objects.measurements.filter;

import protection.model.dataobjects.measurements.Vector;
import protection.model.dataobjects.measurements.MV;


public abstract class Filter {

    public abstract void process(MV mv, Vector vector);

}
