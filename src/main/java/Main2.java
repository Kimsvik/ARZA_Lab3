import breakerControl.nodes.CSWI;
import breakerControl.nodes.XCBR;
import breakerControl.nodes.input.LCOM;
import breakerControl.nodes.input.LSVC;
import breakerControl.nodes.gui.NHMI;
import breakerControl.nodes.gui.other.NHMISignal;
import breakerControl.nodes.measurements.MMXU;
import breakerControl.nodes.protection.PTOC;
import breakerControl.nodes.protection.RDIR;
import breakerControl.objects.measurements.filter.MSQI;
import breakerControl.nodes.breaker.PTRC;
import protection.model.dataobjects.protection.Direction;


public class Main2 {


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

        /** PDIR - используется для задания направленности защиты
         * (МЭК 61850-7-4) */
        RDIR rdir = new RDIR();
        rdir.setN(mmxu.getN());

        /** PTOC - используется для моделирования направленной максимальной токовой защиты с выдержкой времени
         * (МЭК 61850-7-4) */
        PTOC ptocnotdir1 = new PTOC();
        ptocnotdir1.getA().setPhsA(msqi.getSeqA().getC3());
        ptocnotdir1.getA().setPhsB(msqi.getSeqA().getC3());
        ptocnotdir1.getA().setPhsC(msqi.getSeqA().getC3());
        ptocnotdir1.getStrVal().getSetMag().getF().setValue(500F);
        ptocnotdir1.getOpDITmms().setSetVal(0);

        PTOC ptocnotdir2 = new PTOC();
        ptocnotdir2.getA().setPhsA(msqi.getSeqA().getC3());
        ptocnotdir2.getA().setPhsB(msqi.getSeqA().getC3());
        ptocnotdir2.getA().setPhsC(msqi.getSeqA().getC3());
        ptocnotdir2.getStrVal().getSetMag().getF().setValue(300F);
        ptocnotdir2.getOpDITmms().setSetVal(500);

        PTOC ptocdir1 = new PTOC();
        ptocdir1.getA().setPhsA(msqi.getSeqA().getC3());
        ptocdir1.getA().setPhsB(msqi.getSeqA().getC3());
        ptocdir1.getA().setPhsC(msqi.getSeqA().getC3());
        ptocdir1.getStrVal().getSetMag().getF().setValue(500F);
        ptocdir1.getOpDITmms().setSetVal(0);
        ptocdir1.getDirMod().getStVal().setValue(Direction.FORWARD);
        ptocdir1.setDir(rdir.getDir());

        PTOC ptocdir2 = new PTOC();
        ptocdir2.getA().setPhsA(msqi.getSeqA().getC3());
        ptocdir2.getA().setPhsB(msqi.getSeqA().getC3());
        ptocdir2.getA().setPhsC(msqi.getSeqA().getC3());
        ptocdir2.getStrVal().getSetMag().getF().setValue(300F);
        ptocdir2.getOpDITmms().setSetVal(500);
        ptocdir2.getDirMod().getStVal().setValue(Direction.FORWARD);
        ptocdir2.setDir(rdir.getDir());

        PTOC ptocdir3 = new PTOC();
        ptocdir3.getA().setPhsA(msqi.getSeqA().getC3());
        ptocdir3.getA().setPhsB(msqi.getSeqA().getC3());
        ptocdir3.getA().setPhsC(msqi.getSeqA().getC3());
        ptocdir3.getStrVal().getSetMag().getF().setValue(200F);
        ptocdir3.getOpDITmms().setSetVal(1000);
        ptocdir3.getDirMod().getStVal().setValue(Direction.FORWARD);
        ptocdir3.setDir(rdir.getDir());

        /** PTRC - используется для соединения нескольких выходов Operate в один общий Trip
         * (МЭК 61850-7-4) */
        PTRC ptrc = new PTRC();
        ptrc.getOp().add(ptocdir1.getOp());
        ptrc.getOp().add(ptocdir2.getOp());
        ptrc.getOp().add(ptocdir3.getOp());
        ptrc.getOp().add(ptocnotdir1.getOp());
        ptrc.getOp().add(ptocnotdir2.getOp());

        /** CSWI - выдаёт команды на управление выключателем */
        CSWI cswi = new CSWI();
        cswi.getPos().getCtVal().setValue(true);
        cswi.getPos().getStVal().setValue((byte) 2);
        cswi.setOpOpn(ptrc.getTr());
        cswi.setOpCls(ptrc.getTr());

        /** XCBR - положение выключателя */
        XCBR xcbr = new XCBR();
        xcbr.setPos(cswi.getPos());

        /** Ускорение защиты
         *  true    ON
         *  false   OFF */
        boolean dir1 = false;
        boolean dir2 = false;
        boolean dir3 = false;
        boolean notdir1 = false;
        boolean notdir2 = false;

        if (dir1) {
            ptocdir1.getOpDITmms().setSetVal(0);
        }
        if (dir2) {
            ptocdir2.getOpDITmms().setSetVal(0);
        }
        if (dir3) {
            ptocdir3.getOpDITmms().setSetVal(0);
        }
        if (notdir1) {
            ptocnotdir1.getOpDITmms().setSetVal(0);
        }
        if (notdir2) {
            ptocnotdir2.getOpDITmms().setSetVal(0);
        }

        /** NHMI - используется для вывода графиков */
        NHMI nhmi = new NHMI();
        nhmi.addSignals(
                new NHMISignal("U1", msqi.getSeqV().getC1().getCVal().getMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("U2", msqi.getSeqV().getC2().getCVal().getMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("U0", msqi.getSeqV().getC2().getCVal().getMag().getF())
        );



        nhmi.addSignals(
                new NHMISignal("Ia", lcom.OUT.get(3).getInstMag().getF())
        );
        nhmi.addSignals(
                    new NHMISignal("Ib", lcom.OUT.get(4).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("Ic", lcom.OUT.get(5).getInstMag().getF())
        );

        nhmi.addSignals(
            new NHMISignal("I1", msqi.getSeqA().getC1().getCVal().getMag().getF())
        );
        nhmi.addSignals(
            new NHMISignal("I2", msqi.getSeqA().getC2().getCVal().getMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("I0", msqi.getSeqA().getC3().getCVal().getMag().getF()),
                new NHMISignal("Iср", ptocdir1.getStrVal().getSetMag().getF()),
                new NHMISignal(" ", ptocdir2.getStrVal().getSetMag().getF()),
                new NHMISignal(" ", ptocdir3.getStrVal().getSetMag().getF()),
                new NHMISignal(" ", ptocnotdir1.getStrVal().getSetMag().getF()),
                new NHMISignal(" ", ptocnotdir2.getStrVal().getSetMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("Напр 1", ptocdir1.getOp().getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("Напр 2", ptocdir2.getOp().getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("Напр 3", ptocdir3.getOp().getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("Ненапр 1", ptocnotdir1.getOp().getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("Ненапр 2", ptocnotdir2.getOp().getGeneral())
        );
        nhmi.addSignals(
                new NHMISignal("Выключатель", xcbr.getPos().getStVal())
        );

        while(lcom.hasNext()) {
            lcom.process();
            ptrc.process();
            nhmi.process();
            mmxu.process();
            ptocnotdir1.process();
            ptocnotdir2.process();
            ptocdir1.process();
            ptocdir2.process();
            ptocdir3.process();
            cswi.process();
            xcbr.process();
            msqi.process();
            rdir.process();
            }

                //System.out.println(MSQI.getSeqA().getC1().getCVal().getMag());

    }
}
