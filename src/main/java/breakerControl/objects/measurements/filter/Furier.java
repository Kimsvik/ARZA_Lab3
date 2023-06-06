package breakerControl.objects.measurements.filter;

import protection.model.dataobjects.measurements.Vector;
import protection.model.dataobjects.measurements.MV;
import lombok.Getter;
import lombok.Setter;

import static org.apache.commons.io.filefilter.FileFilterUtils.and;

@Getter @Setter
public class Furier extends Filter {

    private int size = 20;
    private int count = 0;
    private final float k = (float) (Math.sqrt(2)/size);

    private float x;
    private float y;
    private final float[] bufferX = new float[size];
    private final float[] bufferY = new float[size];

    private float prevInst;
    private int Fz;
    private float F;
    private int counter;


    @Override
    public void process(MV sav, Vector vector) {

        if (prevInst < 0 & sav.getInstMag().getF().getValue() > 0) {
            counter += 1;
            F = (float) (1000/counter);
            counter = 0;
            System.out.println(F);
        } else {
            counter += 1;
        }

        x += k * sav.getInstMag().getF().getValue() * Math.sin((2* Math.PI * F) *  (0.02/size) * count) - bufferX[count];
        y += k * sav.getInstMag().getF().getValue() * Math.cos((2* Math.PI * F) * (0.02/size) * count) - bufferY[count];

        bufferX[count] = (float) (k * sav.getInstMag().getF().getValue() * Math.sin((2* Math.PI * F) * (0.02/size) * count));
        bufferY[count] = (float) (k * sav.getInstMag().getF().getValue() * Math.cos((2* Math.PI * F) * (0.02/size) * count));
        vector.tovector(x, y);

        prevInst = sav.getInstMag().getF().getValue();

        if(++count >= size) count = 0;
    }
}
