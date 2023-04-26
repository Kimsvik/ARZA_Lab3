package breakerControl.objects.measurements.filter;

import protection.model.dataobjects.measurements.Vector;
import breakerControl.objects.samples.SAV;


public abstract class Filter {

    public abstract void process(SAV sav, Vector vector);

}
