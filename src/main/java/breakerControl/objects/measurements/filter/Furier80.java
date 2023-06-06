package breakerControl.objects.measurements.filter;

import lombok.Data;
import protection.model.dataobjects.measurements.Vector;
import protection.model.dataobjects.measurements.MV;
import java.lang.Math;

import static java.lang.Math.round;

@Data
public class Furier80 extends Filter {

    private int size = 80;
    private int count = 0;
    private final float k = (float) (Math.sqrt(2)/size);

    private float x;
    private float x_prev;
    private float y;
    private float y_prev;
    private final float[] bufferX = new float[size];
    private final float[] bufferY = new float[size];
    private float f;

    @Override
    public void process(MV sav, Vector vector) {
        //f = (float) (180/2/3.1416/(Math.atan(bufferY[size-1]/bufferX[size-1]) - Math.atan(bufferY[size-2]/bufferX[size-2])));
        //System.out.println(f);

        x += k * sav.getInstMag().getF().getValue() * Math.sin((2* Math.PI * 50) *  (0.02/size) * count) - bufferX[count];
        y += k * sav.getInstMag().getF().getValue() * Math.cos((2* Math.PI * 50) * (0.02/size) * count) - bufferY[count];

        bufferX[count] = (float) (k * sav.getInstMag().getF().getValue() * Math.sin((2* Math.PI * 50) * (0.02/size) * count));
        bufferY[count] = (float) (k * sav.getInstMag().getF().getValue() * Math.cos((2* Math.PI * 50) * (0.02/size) * count));
        vector.tovector(x, y);
        if(++count >= size) count = 0;
    }
}
