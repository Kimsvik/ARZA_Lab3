package protection.model.dataobjects.measurements;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class Vector {
    @Getter @Setter
    private AnalogValue mag = new AnalogValue();
    @Getter @Setter
    private AnalogValue ang = new AnalogValue();
    @Getter @Setter
    private AnalogValue rad = new AnalogValue();
    @Getter @Setter
    private AnalogValue x = new AnalogValue();
    @Getter @Setter
    private AnalogValue y = new AnalogValue();

    public void artog(float ampl, float ugl) {
        mag.getF().setValue(ampl);
        ang.getF().setValue(ugl);
        rad.getF().setValue((float) Math.toRadians(ang.getF().getValue()));
        x.getF().setValue((float) (ampl * Math.cos(rad.getF().getValue())));
        y.getF().setValue((float) (ampl * Math.sin(rad.getF().getValue())));
    }

    public void tovector(float poX, float poY){
        x.getF().setValue(poX);
        y.getF().setValue(poY);
        rad.getF().setValue((float) Math.atan2( poY ,poX));
        mag.getF().setValue((float) Math.sqrt(Math.pow(poX,2) + Math.pow(poY,2)));
        ang.getF().setValue((float) Math.toDegrees(rad.getF().getValue()));
    }

    public void Zvector(Vector current, Vector voltage){
        ang.getF().setValue(voltage.getAng().getF().getValue() - current.getAng().getF().getValue());
        mag.getF().setValue(voltage.getMag().getF().getValue()/current.getMag().getF().getValue());
    }

    public void addition(Vector a, Vector b, Vector c, Vector d, Vector e){
        a.artog(a.getMag().getF().getValue(),a.getAng().getF().getValue());
        b.artog(b.getMag().getF().getValue(),b.getAng().getF().getValue());
        c.artog(c.getMag().getF().getValue(),c.getAng().getF().getValue());

        d.artog(d.getMag().getF().getValue(),d.getAng().getF().getValue());
        e.artog(e.getMag().getF().getValue(),e.getAng().getF().getValue());

        x.getF().setValue(a.getX().getF().getValue() + b.getX().getF().getValue() + c.getX().getF().getValue() + d.getX().getF().getValue() + e.getX().getF().getValue());
        y.getF().setValue(a.getY().getF().getValue() + b.getY().getF().getValue() + c.getY().getF().getValue() + d.getX().getF().getValue() + e.getX().getF().getValue());
    }
}