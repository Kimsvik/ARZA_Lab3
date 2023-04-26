package breakerControl.objects.measurements.filter;

import protection.model.dataobjects.measurements.Vector;
import breakerControl.objects.samples.SAV;


public class Furier extends Filter {
    private int size = 80;
    private int count = 0;
    private float k = (float) (Math.sqrt(2)/size);
    private float x;
    private float y;
    private final float[] bufx = new float[80];
    private final float[] bufy = new float[80];
    @Override
    public void process(SAV sav, Vector vector){
        x += k*sav.getInstMag().getF().getValue() *Math.sin((2*Math.PI *50)*(0.02/size)*count)-bufx[count];
        y += k*sav.getInstMag().getF().getValue() *Math.sin((2*Math.PI *50)*(0.02/size)*count)-bufy[count];
        bufx[count] = (float) (k*sav.getInstMag().getF().getValue() * Math.sin((2*Math.PI *50)*(0.02/size)*count));
        bufy[count] = (float) (k*sav.getInstMag().getF().getValue() * Math.sin((2*Math.PI *50)*(0.02/size)*count));
        vector.tovector(x,y);

        if(++count >= size) count =0;





    }
}
