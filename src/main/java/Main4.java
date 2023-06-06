import breakerControl.nodes.CSWI;
import breakerControl.nodes.XCBR;
import breakerControl.nodes.breaker.PTRC;
import breakerControl.nodes.gui.NHMI;
import breakerControl.nodes.gui.NHMIP;
import breakerControl.nodes.gui.other.NHMIPoint;
import breakerControl.nodes.gui.other.NHMISignal;
import breakerControl.nodes.input.LCOM;
import breakerControl.nodes.measurements.MHAI;
import breakerControl.nodes.measurements.MMXU;
import breakerControl.nodes.measurements.RMXU;
import breakerControl.nodes.protection.*;
import breakerControl.objects.measurements.filter.MSQI;
import protection.model.common.Point;
import protection.model.dataobjects.protection.Direction;

import java.util.ArrayList;
import java.util.List;


public class Main4 {


    public static void main(String[] args) {

        /** LCOM - считывает comtrade-файл */
        LCOM lcom = new LCOM();
        lcom.setFilePath("C:\\Users\\Сергей\\Desktop\\МЭИ\\М2 семестр\\Алгоритмы РЗА\\ЛР4\\Опыты\\DPB\\5 sections\\", "KzB");
        //lcom.setFilePath("C:\\Users\\Сергей\\Desktop\\МЭИ\\М2 семестр\\Алгоритмы РЗА\\ЛР4\\Опыты\\DPB\\5 sections\\", "Vkl");

        /** MMXU - используется для расчета тока, напряжения, мощности в трехфазной системе
         * (МЭК 61850-7-4) */
        MMXU mmxu1 = new MMXU();
        mmxu1.setInstIa(lcom.OUT.get(0));
        mmxu1.setInstIb(lcom.OUT.get(1));
        mmxu1.setInstIc(lcom.OUT.get(2));

        MMXU mmxu2 = new MMXU();
        mmxu2.setInstIa(lcom.OUT.get(3));
        mmxu2.setInstIb(lcom.OUT.get(4));
        mmxu2.setInstIc(lcom.OUT.get(5));

        MMXU mmxu3 = new MMXU();
        mmxu3.setInstIa(lcom.OUT.get(6));
        mmxu3.setInstIb(lcom.OUT.get(7));
        mmxu3.setInstIc(lcom.OUT.get(8));

        MMXU mmxu4 = new MMXU();
        mmxu4.setInstIa(lcom.OUT.get(9));
        mmxu4.setInstIb(lcom.OUT.get(10));
        mmxu4.setInstIc(lcom.OUT.get(11));

        MMXU mmxu5 = new MMXU();
        mmxu5.setInstIa(lcom.OUT.get(12));
        mmxu5.setInstIb(lcom.OUT.get(13));
        mmxu5.setInstIc(lcom.OUT.get(14));
        
        
        /** MHAI - */
        MHAI mhai1 = new MHAI();
        mhai1.setInstIa(lcom.OUT.get(0));
        mhai1.setInstIb(lcom.OUT.get(1));
        mhai1.setInstIc(lcom.OUT.get(2));

        MHAI mhai2 = new MHAI();
        mhai2.setInstIa(lcom.OUT.get(3));
        mhai2.setInstIb(lcom.OUT.get(4));
        mhai2.setInstIc(lcom.OUT.get(5));

        MHAI mhai3 = new MHAI();
        mhai3.setInstIa(lcom.OUT.get(6));
        mhai3.setInstIb(lcom.OUT.get(7));
        mhai3.setInstIc(lcom.OUT.get(8));

        MHAI mhai4 = new MHAI();
        mhai4.setInstIa(lcom.OUT.get(9));
        mhai4.setInstIb(lcom.OUT.get(10));
        mhai4.setInstIc(lcom.OUT.get(11));

        MHAI mhai5 = new MHAI();
        mhai5.setInstIa(lcom.OUT.get(12));
        mhai5.setInstIb(lcom.OUT.get(13));
        mhai5.setInstIc(lcom.OUT.get(14));

        /** */
        RMXU rmxu = new RMXU();
        rmxu.getInputA().add(mmxu1.getA());
        rmxu.getInputA().add(mmxu2.getA());
        rmxu.getInputA().add(mmxu3.getA());
        rmxu.getInputA().add(mmxu4.getA());
        rmxu.getInputA().add(mmxu5.getA());
        rmxu.getTmASt().getCrvPvs().add(new Point(0f,3000f));
        rmxu.getTmASt().getCrvPvs().add(new Point(300f,3000f));
        rmxu.getTmASt().getCrvPvs().add(new Point(850f,4500f));
        rmxu.process();

        /***/
        PDIF pdif = new PDIF();
        pdif.getInputHarm().add(mhai1.getHA());
        pdif.getInputHarm().add(mhai2.getHA());
        pdif.getInputHarm().add(mhai3.getHA());
        pdif.getInputHarm().add(mhai4.getHA());
        pdif.getInputHarm().add(mhai5.getHA());
        pdif.setDifAClc(rmxu.getDifoutput().get(0));
        pdif.setTripPoint(rmxu.getTripPoint());
        pdif.getStrVal().getSetMag().getF().setValue(4000f);
        pdif.getMinOpTmms().setSetVal(10);
        pdif.getStrHarm().getSetMag().getF().setValue(0.01f);

        /** PTOC - используется для моделирования направленной максимальной токовой защиты с выдержкой времени
         * (МЭК 61850-7-4) */
        PTOC ptoc = new PTOC();
        ptoc.setA(mmxu1.getA());
        ptoc.getStrVal().getSetMag().getF().setValue(5000F);
        ptoc.getOpDITmms().setSetVal(1);

        /** PTRC - используется для соединения нескольких выходов Operate в один общий Trip
         * (МЭК 61850-7-4) */
        PTRC ptrc = new PTRC();
        ptrc.getOp().add(pdif.getOp());
        ptrc.getOp().add(ptoc.getOp());

        /** CSWI - выдаёт команды на управление выключателем */
        CSWI cswi = new CSWI();
        cswi.getPos().getCtVal().setValue(true);
        cswi.getPos().getStVal().setValue((byte) 2);
        cswi.setOpOpn(ptrc.getTr());
        cswi.setOpCls(ptrc.getTr());

        /** XCBR - положение выключателя */
        XCBR xcbr = new XCBR();
        xcbr.setPos(cswi.getPos());

        /** NHMI - используется для вывода графиков */
        NHMI nhmi1 = new NHMI();
        NHMI nhmi2 = new NHMI();
        NHMI nhmi3 = new NHMI();
        NHMI nhmi4 = new NHMI();
        NHMI nhmi5 = new NHMI();
        NHMI nhmidif = new NHMI();

        nhmi1.addSignals(
                new NHMISignal("Ia1", lcom.OUT.get(0).getInstMag().getF())
        );
        nhmi1.addSignals(
                new NHMISignal("Ib1", lcom.OUT.get(1).getInstMag().getF())
        );
        nhmi1.addSignals(
                new NHMISignal("Ic1", lcom.OUT.get(2).getInstMag().getF())
        );


        nhmi1.addSignals(
                new NHMISignal("Ia1 (1)", mhai1.getHA().getPhsAHar().get(1).getMag().getF())
        );
        nhmi1.addSignals(
                new NHMISignal("Ib1 (1)", mhai1.getHA().getPhsBHar().get(1).getMag().getF())
        );
        nhmi1.addSignals(
                new NHMISignal("Ic1 (1)", mhai1.getHA().getPhsCHar().get(1).getMag().getF())
        );

        nhmi1.addSignals(
                new NHMISignal("Ia1 (5)", mhai1.getHA().getPhsAHar().get(5).getMag().getF())
        );
        nhmi1.addSignals(
                new NHMISignal("Ib1 (5)", mhai1.getHA().getPhsBHar().get(5).getMag().getF())
        );
        nhmi1.addSignals(
                new NHMISignal("Ic1 (5)", mhai1.getHA().getPhsCHar().get(5).getMag().getF())
        );
        nhmi1.addSignals(
                new NHMISignal("Ic1 (5)", mhai1.getHA().getPhsCHar().get(5).getMag().getF())
        );

        nhmi2.addSignals(
                new NHMISignal("Ia2", lcom.OUT.get(3).getInstMag().getF())
        );
        nhmi2.addSignals(
                new NHMISignal("Ib2", lcom.OUT.get(4).getInstMag().getF())
        );
        nhmi2.addSignals(
                new NHMISignal("Ic2", lcom.OUT.get(5).getInstMag().getF())
        );


        nhmi2.addSignals(
                new NHMISignal("Ia2 (1)", mhai2.getHA().getPhsAHar().get(1).getMag().getF())
        );
        nhmi2.addSignals(
                new NHMISignal("Ib2 (1)", mhai2.getHA().getPhsBHar().get(1).getMag().getF())
        );
        nhmi2.addSignals(
                new NHMISignal("Ic2 (1)", mhai2.getHA().getPhsCHar().get(1).getMag().getF())
        );

        nhmi2.addSignals(
                new NHMISignal("Ia2 (5)", mhai2.getHA().getPhsAHar().get(5).getMag().getF())
        );
        nhmi2.addSignals(
                new NHMISignal("Ib2 (5)", mhai2.getHA().getPhsBHar().get(5).getMag().getF())
        );
        nhmi2.addSignals(
                new NHMISignal("Ic2 (5)", mhai2.getHA().getPhsCHar().get(5).getMag().getF())
        );


        nhmi3.addSignals(
                new NHMISignal("Ia3", lcom.OUT.get(6).getInstMag().getF())
        );
        nhmi3.addSignals(
                new NHMISignal("Ib3", lcom.OUT.get(7).getInstMag().getF())
        );
        nhmi3.addSignals(
                new NHMISignal("Ic3", lcom.OUT.get(8).getInstMag().getF())
        );


        nhmi3.addSignals(
                new NHMISignal("Ia3 (1)", mhai3.getHA().getPhsAHar().get(1).getMag().getF())
        );
        nhmi3.addSignals(
                new NHMISignal("Ib3 (1)", mhai3.getHA().getPhsBHar().get(1).getMag().getF())
        );
        nhmi3.addSignals(
                new NHMISignal("Ic3 (1)", mhai3.getHA().getPhsCHar().get(1).getMag().getF())
        );

        nhmi3.addSignals(
                new NHMISignal("Ia3 (5)", mhai3.getHA().getPhsAHar().get(5).getMag().getF())
        );
        nhmi3.addSignals(
                new NHMISignal("Ib3 (5)", mhai3.getHA().getPhsBHar().get(5).getMag().getF())
        );
        nhmi3.addSignals(
                new NHMISignal("Ic3 (5)", mhai3.getHA().getPhsCHar().get(5).getMag().getF())
        );


        nhmi4.addSignals(
                new NHMISignal("Ia4", lcom.OUT.get(9).getInstMag().getF())
        );
        nhmi4.addSignals(
                new NHMISignal("Ib4", lcom.OUT.get(10).getInstMag().getF())
        );
        nhmi4.addSignals(
                new NHMISignal("Ic4", lcom.OUT.get(11).getInstMag().getF())
        );


        nhmi4.addSignals(
                new NHMISignal("Ia4 (1)", mhai4.getHA().getPhsAHar().get(1).getMag().getF())
        );
        nhmi4.addSignals(
                new NHMISignal("Ib4 (1)", mhai4.getHA().getPhsBHar().get(1).getMag().getF())
        );
        nhmi4.addSignals(
                new NHMISignal("Ic4 (1)", mhai4.getHA().getPhsCHar().get(1).getMag().getF())
        );

        nhmi4.addSignals(
                new NHMISignal("Ia4 (5)", mhai4.getHA().getPhsAHar().get(5).getMag().getF())
        );
        nhmi4.addSignals(
                new NHMISignal("Ib4 (5)", mhai4.getHA().getPhsBHar().get(5).getMag().getF())
        );
        nhmi4.addSignals(
                new NHMISignal("Ic4 (5)", mhai4.getHA().getPhsCHar().get(5).getMag().getF())
        );


        nhmi5.addSignals(
                new NHMISignal("Ia5", lcom.OUT.get(11).getInstMag().getF())
        );
        nhmi5.addSignals(
                new NHMISignal("Ib5", lcom.OUT.get(12).getInstMag().getF())
        );
        nhmi5.addSignals(
                new NHMISignal("Ic5", lcom.OUT.get(13).getInstMag().getF())
        );


        nhmi5.addSignals(
                new NHMISignal("Ia5 (1)", mhai5.getHA().getPhsAHar().get(1).getMag().getF())
        );
        nhmi5.addSignals(
                new NHMISignal("Ib5 (1)", mhai5.getHA().getPhsBHar().get(1).getMag().getF())
        );
        nhmi5.addSignals(
                new NHMISignal("Ic5 (1)", mhai5.getHA().getPhsCHar().get(1).getMag().getF())
        );

        nhmi5.addSignals(
                new NHMISignal("Ia5 (5)", mhai5.getHA().getPhsAHar().get(5).getMag().getF())
        );
        nhmi5.addSignals(
                new NHMISignal("Ib5 (5)", mhai5.getHA().getPhsBHar().get(5).getMag().getF())
        );
        nhmi5.addSignals(
                new NHMISignal("Ic5 (5)", mhai5.getHA().getPhsCHar().get(5).getMag().getF())
        );

//////////////////////////////////////////////////////////////////


        nhmidif.addSignals(
                new NHMISignal("IaDIF",rmxu.getIdifa().getMag().getF()),
                new NHMISignal("It",rmxu.getTripPoint().getPhsA().getCVal().getMag().getF())
        );

        nhmidif.addSignals(
                new NHMISignal("IbDIF",rmxu.getIdifb().getMag().getF()),
                new NHMISignal("It",rmxu.getTripPoint().getPhsA().getCVal().getMag().getF())
        );

        nhmidif.addSignals(
                new NHMISignal("IcDIF",rmxu.getIdifc().getMag().getF()),
                new NHMISignal("It",rmxu.getTripPoint().getPhsA().getCVal().getMag().getF())
        );

        nhmidif.addSignals(
                new NHMISignal("Op",pdif.getOp().getGeneral())
        );
        nhmidif.addSignals(
                new NHMISignal("Block",pdif.getBlockA())
        );
        nhmidif.addSignals(
                new NHMISignal("Block",pdif.getBlockB())
        );
        nhmidif.addSignals(
                new NHMISignal("Block",pdif.getBlockC())
        );
        nhmidif.addSignals(
                new NHMISignal("Trip",ptrc.getTr().getGeneral())
        );
        nhmidif.addSignals(
                new NHMISignal("Breaker",xcbr.getPos().getStVal())
        );

        NHMIP nhmip = new NHMIP();
        List<NHMIPoint<Double, Double>> pointsList1 = new ArrayList<>();
        double x0 = 0;
        double y0 = 3000;
        double x1 = 300;
        double y1 = 3000;
        double x2 = 850;
        double y2 = 4000;
        for (double i = x0; i <= x1; i += 10) {
            pointsList1.add(new NHMIPoint<>(i, y0));
        }
        for (double i = x1; i <= 10000; i += 10) {
            pointsList1.add(new NHMIPoint<>(i, y1+(i-x1)*(y2-y1)/(x2-x1)));
        }
        nhmip.drawCharacteristic("1 ненаправленная", pointsList1);
        nhmip.addSignals(
                new NHMISignal("Iab", rmxu.getIdifa().getMag().getF(), rmxu.getTripPoint().getPhsA().getCVal().getX().getF()),
                new NHMISignal("Ibc", rmxu.getIdifb().getMag().getF(), rmxu.getTripPoint().getPhsB().getCVal().getX().getF()),
                new NHMISignal("Ica", rmxu.getIdifc().getMag().getF(), rmxu.getTripPoint().getPhsC().getCVal().getX().getF())
        );

        while(lcom.hasNext()) {
            lcom.process();

            mmxu1.process();
            mmxu2.process();
            mmxu3.process();
            mmxu4.process();
            mmxu5.process();

            mhai1.process();
            mhai2.process();
            mhai3.process();
            mhai4.process();
            mhai5.process();

            rmxu.process();

            pdif.process();

            ptrc.process();

            nhmi1.process();
            nhmi2.process();
            nhmi3.process();
            nhmi4.process();
            nhmi5.process();
            nhmidif.process();
            nhmip.process();

            cswi.process();
            xcbr.process();

        }

        //System.out.println(MSQI.getSeqA().getC1().getCVal().getMag());

    }
}
