package process.protection.current;

import iec61850.nodes.breaker.CSWI;
import iec61850.nodes.breaker.XCBR;
import iec61850.nodes.custom.LSVC;
import iec61850.nodes.gui.NHMI;
import iec61850.nodes.gui.other.NHMISignal;
import iec61850.nodes.measurements.MMXU;
import iec61850.nodes.protection.PTOC;


public class Main {


    public static void main(String[] args) {

        LSVC lsvc = new LSVC();
        NHMI nhmi = new NHMI();
        MMXU mmxu = new MMXU();
        PTOC ptoc = new PTOC();
        PTOC ptoc2 = new PTOC();
        XCBR xcbr = new XCBR();
        CSWI cswi = new CSWI();



        lsvc.readComtrade("C:\\Users\\Сергей\\Desktop\\МЭИ\\М2 семестр\\Алгоритмы РЗА\\ЛР1\\Опыты\\Опыты\\Начало линии\\PhA80");
        mmxu.setInstIa(lsvc.getSignals().get(0));
        mmxu.setInstIb(lsvc.getSignals().get(1));
        mmxu.setInstIc(lsvc.getSignals().get(2));

        ptoc.setA(mmxu.getA());
        ptoc.getStrVal().getSetMag().getF().setValue(30000F);
        ptoc.getOpDITmms().setSetVal(1000);

        ptoc2.setA(mmxu.getA());
        ptoc2.getStrVal().getSetMag().getF().setValue(1000F);
        ptoc2.getOpDITmms().setSetVal(2000);



        cswi.setOpOpn(ptoc.getOp());

        cswi.getPos().getCtVal().setValue(true);
        cswi.getPos().getStVal().setValue((byte) 2);


        xcbr.setPos(cswi.getPos());


        nhmi.addSignals(
                new NHMISignal("TestSignal1", lsvc.getSignals().get(0).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("TestSignal2", lsvc.getSignals().get(1).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("TestSignal3", lsvc.getSignals().get(2).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("TestSignal 1RMS", mmxu.getA().getPhsA().getcVal().getMag().getF()),
                new NHMISignal("Ustavka1St", ptoc.getStrVal().getSetMag().getF()),
                new NHMISignal("Ustavka2St", ptoc2.getStrVal().getSetMag().getF()));
        nhmi.addSignals(
                new NHMISignal("TestSignal 2RMS", mmxu.getA().getPhsB().getcVal().getMag().getF()),
                new NHMISignal("Ustavka1St", ptoc.getStrVal().getSetMag().getF()),
                new NHMISignal("Ustavka2St", ptoc2.getStrVal().getSetMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("TestSignal 3RMS", mmxu.getA().getPhsC().getcVal().getMag().getF()),
                new NHMISignal("Ustavka1St", ptoc.getStrVal().getSetMag().getF()),
                new NHMISignal("Ustavka2St", ptoc2.getStrVal().getSetMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("1st A", ptoc.getOp().getPhsA())
        );
        nhmi.addSignals(
                new NHMISignal("2st A", ptoc2.getOp().getPhsA())
        );
        nhmi.addSignals(
                new NHMISignal("1st B", ptoc.getOp().getPhsB())
        );
        nhmi.addSignals(
                new NHMISignal("2st B", ptoc2.getOp().getPhsB())
        );
        nhmi.addSignals(
                new NHMISignal("1st C", ptoc.getOp().getPhsC())
        );
        nhmi.addSignals(
                new NHMISignal("2st C", ptoc2.getOp().getPhsC())
        );
        nhmi.addSignals(
                new NHMISignal("Breaker", xcbr.getPos().getStVal())
        );
        while(lsvc.hasNext()) {
            lsvc.process();
            nhmi.process();
            mmxu.process();
            ptoc.process();
            ptoc2.process();
            cswi.process();
            xcbr.process();



        }

    }



}
