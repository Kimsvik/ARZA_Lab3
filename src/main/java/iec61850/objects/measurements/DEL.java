package iec61850.objects.measurements;

public class DEL {
    private CMV phsAB = new CMV();
    private CMV phsBC = new CMV();
    private CMV phsCA = new CMV();

    public CMV getPhsAB() {
        return phsAB;
    }

    public void setPhsAB(CMV phsAB) {
        this.phsAB = phsAB;
    }

    public CMV getPhsBC() {
        return phsBC;
    }

    public void setPhsBC(CMV phsBC) {
        this.phsBC = phsBC;
    }

    public CMV getPhsCA() {
        return phsCA;
    }

    public void setPhsCA(CMV phsCA) {
        this.phsCA = phsCA;
    }
}