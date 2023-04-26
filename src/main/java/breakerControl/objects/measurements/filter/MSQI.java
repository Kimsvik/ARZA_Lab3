package breakerControl.objects.measurements.filter;

import protection.model.common.LN;
import protection.model.dataobjects.measurements.Vector;
import protection.model.dataobjects.measurements.WYE;

public class MSQI extends LN {


    private SEQ SeqA = new SEQ();
    private SEQ SeqV = new SEQ();

    private WYE ImbA = new WYE();
    private WYE ImbV = new WYE();



    private Vector turnBplus120 = new Vector();
    private Vector turnBminus120 = new Vector();
    private Vector turnCplus120 = new Vector();
    private Vector turnCminus120 = new Vector();
    private Vector Aneizm = new Vector();
    private Vector Bneizm = new Vector();
    private Vector Cneizm = new Vector();




    @Override
    public void process() {
        setting(ImbA, SeqA);
        setting(ImbV, SeqV);
    }
    private void setting(WYE wye, SEQ seq){


        turnBplus120.naXandY(wye.getPhsB().getcVal().getMag().getF().getValue(),
                wye.getPhsB().getcVal().getAng().getF().getValue() + 120);
        turnBminus120.naXandY(wye.getPhsB().getcVal().getMag().getF().getValue(),
                wye.getPhsB().getcVal().getAng().getF().getValue() - 120);
        turnCplus120.naXandY(wye.getPhsC().getcVal().getMag().getF().getValue(),
                wye.getPhsC().getcVal().getAng().getF().getValue() + 120);
        turnCminus120.naXandY(wye.getPhsC().getcVal().getMag().getF().getValue(),
                wye.getPhsC().getcVal().getAng().getF().getValue() - 120);

        Aneizm.naXandY(wye.getPhsA().getcVal().getMag().getF().getValue(),
                wye.getPhsA().getcVal().getAng().getF().getValue());
        Bneizm.naXandY(wye.getPhsB().getcVal().getMag().getF().getValue(),
                wye.getPhsB().getcVal().getAng().getF().getValue());
        Cneizm.naXandY(wye.getPhsC().getcVal().getMag().getF().getValue(),
                wye.getPhsC().getcVal().getAng().getF().getValue());

        seq.getC3().getcVal().tovector(((Aneizm.getX().getF().getValue()+ Bneizm.getX().getF().getValue() + Cneizm.getX().getF().getValue())/3),
                ((Aneizm.getY().getF().getValue()  + Bneizm.getY().getF().getValue()  + Cneizm.getY().getF().getValue())/3));
        seq.getC1().getcVal().tovector(((Aneizm.getX().getF().getValue() + turnBplus120.getX().getF().getValue() + turnCminus120.getX().getF().getValue())/3),
                ((Aneizm.getY().getF().getValue() + Bneizm.getY().getF().getValue()  + turnCminus120.getY().getF().getValue())/3));
        seq.getC2().getcVal().tovector(((Aneizm.getX().getF().getValue()+ Bneizm.getX().getF().getValue() + Cneizm.getX().getF().getValue())/3),
                ((Aneizm.getY().getF().getValue()  + Bneizm.getY().getF().getValue()  + Cneizm.getY().getF().getValue())/3));





    }

    public SEQ getSeqA() {
        return SeqA;
    }

    public void setSeqA(SEQ seqA) {
        SeqA = seqA;
    }

    public SEQ getSeqV() {
        return SeqV;
    }

    public void setSeqV(SEQ seqV) {
        SeqV = seqV;
    }

    public WYE getImbA() {
        return ImbA;
    }

    public void setImbA(WYE imbA) {
        ImbA = imbA;
    }

    public WYE getImbV() {
        return ImbV;
    }

    public void setImbV(WYE imbV) {
        ImbV = imbV;
    }

    public Vector getturnBplus120() {
        return turnBplus120;
    }

    public void setturnBplus120(Vector turnBplus120) {
        this.turnBplus120 = turnBplus120;
    }

    public Vector getturnBminus120() {
        return turnBminus120;
    }

    public void setturnBminus120(Vector turnBminus120) {
        this.turnBminus120 = turnBminus120;
    }

    public Vector getturnCplus120() {
        return turnCplus120;
    }

    public void setturnCplus120(Vector turnCplus120) {
        this.turnCplus120 = turnCplus120;
    }

    public Vector getturnCminus120() {
        return turnCminus120;
    }

    public void setturnCminus120(Vector turnCminus120) {
        this.turnCminus120 = turnCminus120;
    }

    public Vector getAneizm() {
        return Aneizm;
    }

    public void setAneizm(Vector aneizm) {
        Aneizm = aneizm;
    }

    public Vector getBneizm() {
        return Bneizm;
    }

    public void setBneizm(Vector bneizm) {
        Bneizm = bneizm;
    }

    public Vector getCneizm() {
        return Cneizm;
    }

    public void setCneizm(Vector cneizm) {
        Cneizm = cneizm;
    }
}
