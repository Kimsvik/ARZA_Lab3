package breakerControl.objects.measurements.filter;

import protection.model.dataobjects.measurements.Vector;
import breakerControl.objects.samples.MV;


public abstract class Filter {

    public abstract void process(MV sav, Vector vector);

}
