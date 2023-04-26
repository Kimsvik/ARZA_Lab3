package iec61850.objects.measurements;

import iec61850.objects.samples.AnalogValue;

public class Vector {

    private AnalogValue mag = new AnalogValue();
    private AnalogValue ang = new AnalogValue();
    private AnalogValue rad = new AnalogValue();
    private AnalogValue x = new AnalogValue();
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
        mag.getF().setValue(voltage.getMag().getF().getValue()/current.getMag().getF().getValue());
    }

    public AnalogValue getMag() {
        return mag;
    }

    public void setMag(AnalogValue mag) {
        this.mag = mag;
    }

    public AnalogValue getAng() {
        return ang;
    }

    public void setAng(AnalogValue ang) {
        this.ang = ang;
    }

    public AnalogValue getRad() {
        return rad;
    }

    public void setRad(AnalogValue rad) {
        this.rad = rad;
    }

    public AnalogValue getX() {
        return x;
    }

    public void setX(AnalogValue x) {
        this.x = x;
    }

    public AnalogValue getY() {
        return y;
    }

    public void setY(AnalogValue y) {
        this.y = y;
    }
}