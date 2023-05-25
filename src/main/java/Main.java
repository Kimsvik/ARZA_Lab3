import breakerControl.nodes.CSWI;
import breakerControl.nodes.XCBR;
import protection.model.common.LN;
import breakerControl.nodes.input.LCOM;
import breakerControl.nodes.input.LSVC;
import breakerControl.nodes.gui.NHMI;
import breakerControl.nodes.gui.other.NHMISignal;
import breakerControl.nodes.measurements.MMXU;
import breakerControl.nodes.protection.PTOC;

import java.util.ArrayList;
import java.util.List;


public class Main {

    private final static List<LN> lnList = new ArrayList<>();

    public static void main(String[] args) {

        /** LCOM - считывает comtrade-файл */
        LCOM lcom = new LCOM();
        lcom.setFilePath(
                "C:\\Users\\Сергей\\Desktop\\МЭИ\\М2 семестр\\Алгоритмы РЗА\\ЛР1\\Опыты\\Опыты\\Конец линии\\",
                "PhB80");

        /** MMXU - используется для расчета тока, напряжения, мощности в трехфазной системе
         * (МЭК 61850-7-4) */
        MMXU mmxu = new MMXU();

        mmxu.setInstIa(lcom.OUT.get(0));
        mmxu.setInstIb(lcom.OUT.get(1));
        mmxu.setInstIc(lcom.OUT.get(2));

        /** PTOC - используется для моделирования направленной максимальной токовой защиты с выдержкой времени
         * (МЭК 61850-7-4) */
        PTOC ptoc = new PTOC();
        ptoc.setA(mmxu.getA());
        ptoc.getStrVal().getSetMag().getF().setValue(3000F);
        ptoc.getOpDITmms().setSetVal(1000);

        PTOC ptoc2 = new PTOC();
        ptoc2.setA(mmxu.getA());
        ptoc2.getStrVal().getSetMag().getF().setValue(1000F);
        ptoc2.getOpDITmms().setSetVal(2000);

        /** CSWI - выдаёт команды на управление выключателем */
        CSWI cswi = new CSWI();
        cswi.setOpOpn(ptoc.getOp());

        cswi.getPos().getCtVal().setValue(true);
        cswi.getPos().getStVal().setValue((byte) 2);

        /** XCBR - положение выключателя */
        XCBR xcbr = new XCBR();
        xcbr.setPos(cswi.getPos());

        /** NHMI - используется для вывода графиков */
        NHMI nhmi = new NHMI();
        nhmi.addSignals(
                new NHMISignal("TestSignal1", lcom.OUT.get(0).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("TestSignal2", lcom.OUT.get(1).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("TestSignal3", lcom.OUT.get(2).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("TestSignal 1RMS", mmxu.getA().getPhsA().getCVal().getMag().getF()),
                new NHMISignal("Ustavka1St", ptoc.getStrVal().getSetMag().getF()),
                new NHMISignal("Ustavka2St", ptoc2.getStrVal().getSetMag().getF()));
        nhmi.addSignals(
                new NHMISignal("TestSignal 2RMS", mmxu.getA().getPhsB().getCVal().getMag().getF()),
                new NHMISignal("Ustavka1St", ptoc.getStrVal().getSetMag().getF()),
                new NHMISignal("Ustavka2St", ptoc2.getStrVal().getSetMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("TestSignal 3RMS", mmxu.getA().getPhsC().getCVal().getMag().getF()),
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

        while(lcom.hasNext()) {
            lcom.process();
            nhmi.process();
            mmxu.process();
            ptoc.process();
            ptoc2.process();
            cswi.process();
            xcbr.process();



        }

    }



}
