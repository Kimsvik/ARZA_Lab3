package iec61850.objects.measurements.filter;

import iec61850.nodes.common.LN;
import iec61850.objects.measurements.Vector;
import iec61850.objects.measurements.WYE;

public class MSQI extends LN {


    private SEQ SeqA = new SEQ();
    private SEQ SeqV = new SEQ();

    private WYE ImbA = new WYE();
    private WYE ImbV = new WYE();



    private Vector povorotBplus120 = new Vector();
    private Vector povorotBminus120 = new Vector();
    private Vector povorotCplus120 = new Vector();
    private Vector povorotCminus120 = new Vector();
    private Vector Aneizm = new Vector();
    private Vector Bneizm = new Vector();
    private Vector Cneizm = new Vector();




    @Override
    public void process() {
        preobrazovaniya(ImbA, SeqA);
        preobrazovaniya(ImbV, SeqV);
    }
    private void preobrazovaniya(WYE prishli, SEQ yshli){


        povorotBplus120.naXandY(prishli.getPhsB().getcVal().getMag().getF().getValue(),
                prishli.getPhsB().getcVal().getAng().getF().getValue() + 120);
        povorotBminus120.naXandY(prishli.getPhsB().getcVal().getMag().getF().getValue(),
                prishli.getPhsB().getcVal().getAng().getF().getValue() - 120);
        povorotCplus120.naXandY(prishli.getPhsC().getcVal().getMag().getF().getValue(),
                prishli.getPhsC().getcVal().getAng().getF().getValue() + 120);
        povorotCminus120.naXandY(prishli.getPhsC().getcVal().getMag().getF().getValue(),
                prishli.getPhsC().getcVal().getAng().getF().getValue() - 120);

        Aneizm.naXandY(prishli.getPhsA().getcVal().getMag().getF().getValue(),
                prishli.getPhsA().getcVal().getAng().getF().getValue());
        Bneizm.naXandY(prishli.getPhsB().getcVal().getMag().getF().getValue(),
                prishli.getPhsB().getcVal().getAng().getF().getValue());
        Cneizm.naXandY(prishli.getPhsC().getcVal().getMag().getF().getValue(),
                prishli.getPhsC().getcVal().getAng().getF().getValue());

        yshli.getC3().getcVal().tovector(((Aneizm.getX().getF().getValue()+ Bneizm.getX().getF().getValue() + Cneizm.getX().getF().getValue())/3),
                ((Aneizm.getY().getF().getValue()  + Bneizm.getY().getF().getValue()  + Cneizm.getY().getF().getValue())/3));
        yshli.getC1().getcVal().tovector(((Aneizm.getX().getF().getValue() + povorotBplus120.getX().getF().getValue() + povorotCminus120.getX().getF().getValue())/3),
                ((Aneizm.getY().getF().getValue() + Bneizm.getY().getF().getValue()  + povorotCminus120.getY().getF().getValue())/3));
        yshli.getC2().getcVal().tovector(((Aneizm.getX().getF().getValue()+ Bneizm.getX().getF().getValue() + Cneizm.getX().getF().getValue())/3),
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

    public Vector getPovorotBplus120() {
        return povorotBplus120;
    }

    public void setPovorotBplus120(Vector povorotBplus120) {
        this.povorotBplus120 = povorotBplus120;
    }

    public Vector getPovorotBminus120() {
        return povorotBminus120;
    }

    public void setPovorotBminus120(Vector povorotBminus120) {
        this.povorotBminus120 = povorotBminus120;
    }

    public Vector getPovorotCplus120() {
        return povorotCplus120;
    }

    public void setPovorotCplus120(Vector povorotCplus120) {
        this.povorotCplus120 = povorotCplus120;
    }

    public Vector getPovorotCminus120() {
        return povorotCminus120;
    }

    public void setPovorotCminus120(Vector povorotCminus120) {
        this.povorotCminus120 = povorotCminus120;
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
