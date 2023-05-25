import breakerControl.nodes.CSWI;
import breakerControl.nodes.XCBR;
import breakerControl.nodes.gui.NHMIP;
import breakerControl.nodes.gui.other.NHMIPoint;
import breakerControl.nodes.input.LCOM;
import breakerControl.nodes.input.LSVC;
import breakerControl.nodes.gui.NHMI;
import breakerControl.nodes.gui.other.NHMISignal;
import breakerControl.nodes.measurements.MMXU;
import breakerControl.nodes.protection.PDIS;
import breakerControl.nodes.protection.PTOC;
import breakerControl.nodes.protection.RDIR;
import breakerControl.nodes.protection.RPSB;
import breakerControl.objects.measurements.filter.MSQI;
import breakerControl.nodes.breaker.PTRC;
import protection.model.dataobjects.protection.Direction;

import java.util.ArrayList;
import java.util.List;


public class Main3 {


    public static void main(String[] args) {

        /** LCOM - считывает comtrade-файл */
        LCOM lcom = new LCOM();
        lcom.setFilePath("C:\\Users\\Сергей\\Desktop\\МЭИ\\М2 семестр\\Алгоритмы РЗА\\ЛР2\\Лабораторная работа №2\\Опыты\\", "KZ3");

        /** MMXU - используется для расчета тока, напряжения, мощности в трехфазной системе
         * (МЭК 61850-7-4) */
        MMXU mmxu = new MMXU();

        mmxu.setInstUa(lcom.OUT.get(0));
        mmxu.setInstUb(lcom.OUT.get(1));
        mmxu.setInstUc(lcom.OUT.get(2));
        mmxu.setInstIa(lcom.OUT.get(3));
        mmxu.setInstIb(lcom.OUT.get(4));
        mmxu.setInstIc(lcom.OUT.get(5));

        /** MSQI - используется для определения последовательностей
         * (МЭК 61850-7-4) */
        MSQI msqi = new MSQI();
        msqi.setImbA(mmxu.getA());
        msqi.setImbV(mmxu.getPhV());

        /** RPSB - используется для блокировки колебаний
         * (МЭК 61850-7-4) */
        RPSB rpsb = new RPSB();
        rpsb.setSeqA(msqi.getSeqA());

        /** PDIS
         * (МЭК 61850-7-4) */

        PDIS pdis1 = new PDIS();
        pdis1.setBlk(rpsb.getBlkZn());
        pdis1.setZ(mmxu.getZf());
        pdis1.getY0().getSetMag().getF().setValue(0f);
        pdis1.getX0().getSetMag().getF().setValue(0f);
        pdis1.getZust().getSetMag().getF().setValue(150f);
        pdis1.getOpDITmms().setSetVal(100);
        List<NHMIPoint<Double, Double>> pointsList1 = new ArrayList<>();
        double r1 = pdis1.getZust().getSetMag().getF().getValue();
        System.out.println(r1);
        for(double x1 = -2*r1; x1 <= 2*r1; x1 += 0.05) {
            double y1 = Math.sqrt(Math.pow(r1, 2) - Math.pow(x1, 2));
            pointsList1.add(new NHMIPoint<>(x1, -y1));
            pointsList1.add(new NHMIPoint<>(x1, y1));
        }

        PDIS pdis2 = new PDIS();
        pdis2.setBlk(rpsb.getBlkZn());
        pdis2.setZ(mmxu.getZf());
        pdis2.getY0().getSetMag().getF().setValue(0f);
        pdis2.getX0().getSetMag().getF().setValue(0f);
        pdis2.getZust().getSetMag().getF().setValue(00f);
        pdis2.getOpDITmms().setSetVal(0);
        List<NHMIPoint<Double, Double>> pointsList2 = new ArrayList<>();
        double r2 = pdis2.getZust().getSetMag().getF().getValue();
        System.out.println(r2);
        for(double x2 = -2*r2; x2<= 2*r2; x2 += 0.05) {
            double y2 = Math.sqrt(Math.pow(r2, 2) - Math.pow(x2, 2));
            pointsList2.add(new NHMIPoint<>(x2, -y2));
            pointsList2.add(new NHMIPoint<>(x2, y2));
        }

        PDIS pdis1n = new PDIS();
        pdis1n.setBlk(rpsb.getBlkZn());
        pdis1n.setZ(mmxu.getZf());
        pdis1n.getY0().getSetMag().getF().setValue(150f);
        pdis1n.getX0().getSetMag().getF().setValue(150f);
        pdis1n.getZust().getSetMag().getF().setValue(100f);
        pdis1n.getOpDITmms().setSetVal(0);
        List<NHMIPoint<Double, Double>> pointsList1N = new ArrayList<>();
        double x1N0 = pdis1n.getX0().getSetMag().getF().getValue();
        double y1N0 = pdis1n.getY0().getSetMag().getF().getValue();
        double r1N = pdis1n.getZust().getSetMag().getF().getValue();
        for(double x1N= -2*r1N; x1N<= 2*r1N; x1N += 0.05) {
            double y1N = Math.sqrt(Math.pow(r1N, 2) - Math.pow((x1N-x1N0), 2));
            pointsList1N.add(new NHMIPoint<>(x1N, -y1N+y1N0));
            pointsList1N.add(new NHMIPoint<>(x1N, y1N+y1N0));
        }

        PDIS pdis2n = new PDIS();
        pdis2n.setBlk(rpsb.getBlkZn());
        pdis2n.setZ(mmxu.getZf());
        pdis2n.getY0().getSetMag().getF().setValue(200f);
        pdis2n.getX0().getSetMag().getF().setValue(200f);
        pdis2n.getZust().getSetMag().getF().setValue(100f);
        pdis2n.getOpDITmms().setSetVal(30);
        List<NHMIPoint<Double, Double>> pointsList2N = new ArrayList<>();
        double x2N0 = pdis2n.getX0().getSetMag().getF().getValue();
        double y2N0 = pdis2n.getY0().getSetMag().getF().getValue();
        double r2N = pdis2n.getZust().getSetMag().getF().getValue();
        for(double x2N= -2*r2N; x2N<= 2*r2N; x2N += 0.05) {
            double y2N = Math.sqrt(Math.pow(r2N, 2) - Math.pow((x2N-x2N0), 2));
            pointsList2N.add(new NHMIPoint<>(x2N, -y2N+y2N0));
            pointsList2N.add(new NHMIPoint<>(x2N, y2N+y2N0));
        }

        PDIS pdis3n = new PDIS();
        pdis3n.setBlk(rpsb.getBlkZn());
        pdis3n.setZ(mmxu.getZf());
        pdis3n.getY0().getSetMag().getF().setValue(250f);
        pdis3n.getX0().getSetMag().getF().setValue(250f);
        pdis3n.getZust().getSetMag().getF().setValue(80f);
        pdis3n.getOpDITmms().setSetVal(60);
        List<NHMIPoint<Double, Double>> pointsList3N = new ArrayList<>();
        double x3N0 = pdis3n.getX0().getSetMag().getF().getValue();
        double y3N0 = pdis3n.getY0().getSetMag().getF().getValue();
        double r3N = pdis3n.getZust().getSetMag().getF().getValue();
        for(double x3N= -2*r3N; x3N<= 2*r3N; x3N += 0.05) {
            double y3N = Math.sqrt(Math.pow(r3N, 2) - Math.pow((x3N-x3N0), 2));
            pointsList3N.add(new NHMIPoint<>(x3N, -y3N+y3N0));
            pointsList3N.add(new NHMIPoint<>(x3N, y3N+y3N0));
        }

        /** PTRC - используется для соединения нескольких выходов Operate в один общий Trip
         * (МЭК 61850-7-4) */
        PTRC ptrc = new PTRC();
        ptrc.getOp().add(pdis1.getOp());
        ptrc.getOp().add(pdis2.getOp());
        ptrc.getOp().add(pdis1n.getOp());
        ptrc.getOp().add(pdis2n.getOp());
        ptrc.getOp().add(pdis3n.getOp());

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
        NHMI nhmi = new NHMI();
        NHMIP nhmip = new NHMIP();
        NHMIP nhmipn = new NHMIP();


        nhmi.addSignals(
                new NHMISignal("Авар сост", rpsb.getAvarSost().getMag().getF())
        );

        nhmi.addSignals(
                new NHMISignal("Блокировка", rpsb.getBlkZn().getStVal())
        );

        nhmi.addSignals(
                new NHMISignal("Ток ОП", msqi.getSeqA().getC2().getCVal().getMag().getF())
        );

//        nhmi.addSignals(
//                new NHMISignal("Uпп", msqi.getSeqV().getC1().getcVal().getMag().getF())
//        );
//        nhmi.addSignals(
//                new NHMISignal("Uоп", msqi.getSeqV().getC2().getcVal().getMag().getF())
//        );
//        nhmi.addSignals(
//                new NHMISignal("Uнп", msqi.getSeqV().getC2().getcVal().getMag().getF())
//        );
        nhmi.addSignals(
                new NHMISignal("Ток фазы А", lcom.OUT.get(5).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("Ток Фазы Б", lcom.OUT.get(4).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("Ток фазы С", lcom.OUT.get(3).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("ZАB", mmxu.getZab().getMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("ZБC", mmxu.getZbc().getMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("ZСA", mmxu.getZca().getMag().getF())
        );

        nhmi.addSignals(
                new NHMISignal("Выключатель", xcbr.getPos().getStVal())
        );

        nhmi.addSignals(
                new NHMISignal("PDIS1", pdis1.getOp().getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("PDIS2", pdis2.getOp().getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("PDIS1N", pdis1n.getOp().getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("PDIS2N", pdis2n.getOp().getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("PDIS3N", pdis3n.getOp().getGeneral())
        );

        nhmip.drawCharacteristic("1 ненаправленная", pointsList1);
        nhmip.drawCharacteristic("2 ненаправленная", pointsList2);
        nhmip.addSignals(
                new NHMISignal("ZAB", mmxu.getZab().getX().getF(), mmxu.getZab().getY().getF()),
                new NHMISignal("ZBC", mmxu.getZbc().getX().getF(), mmxu.getZbc().getY().getF()),
                new NHMISignal("ZCA", mmxu.getZca().getX().getF(), mmxu.getZca().getY().getF())
        );
        nhmipn.drawCharacteristic("1 направленная", pointsList1N);
        nhmipn.drawCharacteristic("2 направленная", pointsList2N);
        nhmipn.drawCharacteristic("3 направленная", pointsList3N);
        nhmipn.addSignals(
                new NHMISignal("ZAB", mmxu.getZab().getX().getF(), mmxu.getZab().getY().getF()),
                new NHMISignal("ZBC", mmxu.getZbc().getX().getF(), mmxu.getZbc().getY().getF()),
                new NHMISignal("ZCA", mmxu.getZca().getX().getF(), mmxu.getZca().getY().getF())
        );


        while(lcom.hasNext()) {
            lcom.process();
            mmxu.process();
            msqi.process();

            rpsb.process();

            pdis1.process();
            pdis2.process();
            pdis1n.process();
            pdis2n.process();
            pdis3n.process();

            ptrc.process();

            cswi.process();
            xcbr.process();

            nhmi.process();
            nhmipn.process();
            nhmip.process();
        }
    }
}
