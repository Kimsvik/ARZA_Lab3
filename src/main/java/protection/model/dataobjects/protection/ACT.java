package protection.model.dataobjects.protection;

import protection.model.common.Attribute;


public class ACT {

    private Attribute<Boolean> general = new Attribute<>(false);
    private Attribute<Boolean> phsA = new Attribute<>(false);
    private Attribute<Boolean> phsB = new Attribute<>(false);
    private Attribute<Boolean> phsC = new Attribute<>(false);
    private Attribute<Boolean> neut = new Attribute<>(false);

    public Attribute<Boolean> getGeneral() {
        return general;
    }

    public void setGeneral(Attribute<Boolean> general) {
        this.general = general;
    }

    public Attribute<Boolean> getPhsA() {
        return phsA;
    }

    public void setPhsA(Attribute<Boolean> phsA) {
        this.phsA = phsA;
    }

    public Attribute<Boolean> getPhsB() {
        return phsB;
    }

    public void setPhsB(Attribute<Boolean> phsB) {
        this.phsB = phsB;
    }

    public Attribute<Boolean> getPhsC() {
        return phsC;
    }

    public void setPhsC(Attribute<Boolean> phsC) {
        this.phsC = phsC;
    }

    public Attribute<Boolean> getNeut() {
        return neut;
    }

    public void setNeut(Attribute<Boolean> neut) {
        this.neut = neut;
    }
}
