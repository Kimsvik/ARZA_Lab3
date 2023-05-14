package protection.model.dataobjects.measurements;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class Vector {
    @Getter @Setter
    private AnalogValue mag = new AnalogValue();
    @Getter
    private AnalogValue ang = new AnalogValue();
    @Getter @Setter
    private AnalogValue rad = new AnalogValue();
    @Getter @Setter
    private AnalogValue x = new AnalogValue();
    @Getter @Setter
    private AnalogValue y = new AnalogValue();



    public void naXandY(float modul, float ugol) {
        mag.getF().setValue(modul);
        ang.getF().setValue(ugol);
        rad.getF().setValue((float) Math.toRadians(ang.getF().getValue()));
        x.getF().setValue((float) (modul * Math.cos(rad.getF().getValue())));
        y.getF().setValue((float) (modul * Math.sin(rad.getF().getValue())));
    }
    public void tovector(float poX, float poY){
        x.getF().setValue(poX);
        y.getF().setValue(poY);
        rad.getF().setValue((float) Math.atan2( poY ,poX));
        mag.getF().setValue((float) Math.sqrt(Math.pow(poX,2) + Math.pow(poY,2)));
        ang.getF().setValue((float) Math.toDegrees(rad.getF().getValue()));
    }
    public void Napravlenie(Vector current, Vector voltage){

        ang.getF().setValue(voltage.getAng().getF().getValue() - current.getAng().getF().getValue());
        this.setAng(ang);
        mag.getF().setValue(voltage.getMag().getF().getValue()/current.getMag().getF().getValue());
    }

    public void setAng(AnalogValue ang) {
        if (ang.getF().getValue() > 180) {
            ang.getF().setValue(ang.getF().getValue() - 360);
        } else if (ang.getF().getValue() < -180) {
            ang.getF().setValue(ang.getF().getValue() + 360);
        }
        this.ang = ang;
    }

}