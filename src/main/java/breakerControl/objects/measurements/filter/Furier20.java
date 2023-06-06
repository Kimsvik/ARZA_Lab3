package breakerControl.objects.measurements.filter;

import lombok.Data;
import protection.model.dataobjects.measurements.MV;
import protection.model.dataobjects.measurements.Vector;

@Data
public class Furier20 extends Filter {

    private int size = 20;
    private int count = 0;
    private final float k = (float) (Math.sqrt(2)/size);

    private float x;
    private float y;
    private final float[] bufferX = new float[size];
    private final float[] bufferY = new float[size];
    private int Fz;

    public Furier20(Integer F){
        this.Fz = 50*F;
    }
    @Override
    public void process(MV sav, Vector vector) {

        x += k * sav.getInstMag().getF().getValue() * Math.sin((2* Math.PI * Fz) *  (0.02/size) * count) - bufferX[count];
        y += k * sav.getInstMag().getF().getValue() * Math.cos((2* Math.PI * Fz) * (0.02/size) * count) - bufferY[count];
        bufferX[count] = (float) (k * sav.getInstMag().getF().getValue() * Math.sin((2* Math.PI * Fz) * (0.02/size) * count));
        bufferY[count] = (float) (k * sav.getInstMag().getF().getValue() * Math.cos((2* Math.PI * Fz) * (0.02/size) * count));
        vector.tovector(x, y);

        if(++count >= size) count = 0;
    }
}
