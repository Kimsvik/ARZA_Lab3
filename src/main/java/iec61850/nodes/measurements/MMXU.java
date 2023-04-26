package iec61850.nodes.measurements;

import iec61850.nodes.common.LN;
import iec61850.objects.measurements.Vector;
import iec61850.objects.measurements.filter.Filter;
import iec61850.objects.measurements.filter.Furier;
import iec61850.objects.measurements.WYE;
import iec61850.objects.samples.SAV;


public class MMXU extends LN {

    private SAV instIa = new SAV();
    private SAV instIb = new SAV();
    private SAV instIc = new SAV();

    private SAV instUa = new SAV();
    private SAV instUb = new SAV();
    private SAV instUc = new SAV();


    private WYE A = new WYE();
    private WYE PhV = new WYE();
    private WYE W = new WYE();

    private WYE N = new WYE();
    private Vector Na = new Vector();
    private Vector Nb = new Vector();
    private Vector Nc = new Vector();

    private Filter UaF = new Furier();
    private Filter UbF = new Furier();
    private Filter UcF = new Furier();

    private Filter iaF = new Furier();
    private Filter ibF = new Furier();
    private Filter icF = new Furier();


    @Override
    public void process() {

        UaF.process(instUa, PhV.getPhsA().getcVal());
        UbF.process(instUb, PhV.getPhsB().getcVal());
        UcF.process(instUc, PhV.getPhsC().getcVal());
        iaF.process(instIa, A.getPhsA().getcVal());
        ibF.process(instIb, A.getPhsB().getcVal());
        icF.process(instIc, A.getPhsC().getcVal());




        Na.Napravlenie(A.getPhsA().getcVal(), PhV.getPhsA().getcVal());
        Nb.Napravlenie(A.getPhsB().getcVal(), PhV.getPhsB().getcVal());
        Nc.Napravlenie(A.getPhsC().getcVal(), PhV.getPhsC().getcVal());

        N.getPhsA().setcVal(Na);
        N.getPhsB().setcVal(Nb);
        N.getPhsC().setcVal(Nc);


    }

    public WYE getN() {
        return N;
    }

    public void setN(WYE n) {
        N = n;
    }

    public Vector getNa() {
        return Na;
    }

    public void setNa(Vector na) {
        Na = na;
    }

    public Vector getNb() {
        return Nb;
    }

    public void setNb(Vector nb) {
        Nb = nb;
    }

    public Vector getNc() {
        return Nc;
    }

    public void setNc(Vector nc) {
        Nc = nc;
    }

    public Filter getUaF() {
        return UaF;
    }

    public void setUaF(Filter uaF) {
        UaF = uaF;
    }

    public Filter getUbF() {
        return UbF;
    }

    public void setUbF(Filter ubF) {
        UbF = ubF;
    }

    public Filter getUcF() {
        return UcF;
    }

    public void setUcF(Filter ucF) {
        UcF = ucF;
    }

    public SAV getInstIa() {
        return instIa;
    }

    public SAV getInstIb() {
        return instIb;
    }

    public SAV getInstIc() {
        return instIc;
    }

    public SAV getInstUa() {
        return instUa;
    }

    public SAV getInstUb() {
        return instUb;
    }

    public SAV getInstUc() {
        return instUc;
    }

    public Filter getIaF() {
        return iaF;
    }

    public Filter getIbF() {
        return ibF;
    }

    public Filter getIcF() {
        return icF;
    }

    public void setInstIa(SAV instIa) {this.instIa = instIa;    }

    public void setInstIb(SAV instIb) {
        this.instIb = instIb;
    }

    public void setInstIc(SAV instIc) {
        this.instIc = instIc;
    }

    public void setInstUa(SAV instUa) {
        this.instUa = instUa;
    }

    public void setInstUb(SAV instUb) {
        this.instUb = instUb;
    }

    public void setInstUc(SAV instUc) {
        this.instUc = instUc;
    }

    public void setA(WYE a) {
        A = a;
    }

    public WYE getPhV() {
        return PhV;
    }

    public void setPhV(WYE phV) {
        PhV = phV;
    }

    public WYE getW() {
        return W;
    }

    public void setW(WYE w) {
        W = w;
    }

    public void setIaF(Filter iaF) {
        this.iaF = iaF;
    }

    public void setIbF(Filter ibF) {
        this.ibF = ibF;
    }

    public void setIcF(Filter icF) {
        this.icF = icF;
    }

    public WYE getA() {
        return A;
    }
}
