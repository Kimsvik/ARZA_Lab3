package protection.model.dataobjects.measurements;


import protection.model.common.Attribute;

public class AnalogValue {

    private Attribute<Float> f = new Attribute<>(0f);

    public Attribute<Float> getF() {
        return f;
    }

    public void setF(Attribute<Float> f) {
        this.f = f;
    }
}
