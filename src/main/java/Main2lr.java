import breakerControl.nodes.CSWI;
import breakerControl.nodes.XCBR;
import breakerControl.nodes.input.LSVC;
import breakerControl.nodes.gui.NHMI;
import breakerControl.nodes.gui.other.NHMISignal;
import breakerControl.nodes.measurements.MMXU;
import breakerControl.nodes.protection.PTOC;
import breakerControl.nodes.protection.RDIR;
import breakerControl.objects.measurements.filter.MSQI;
import breakerControl.nodes.breaker.PTRC;
import protection.model.dataobjects.protection.Direction;


public class Main2lr {


    public static void main(String[] args) {

        /** LSVC - считывает comtrade-файл */
        LSVC lsvc = new LSVC();
        lsvc.readComtrade("C:\\Users\\Сергей\\Desktop\\МЭИ\\М2 семестр\\Алгоритмы РЗА\\ЛР2\\Лабораторная работа №2\\Опыты\\KZ4");

        /** MMXU - используется для расчета тока, напряжения, мощности в трехфазной системе
         * (МЭК 61850-7-4) */
        MMXU mmxu = new MMXU();
        mmxu.setInstUa(lsvc.getSignals().get(0));
        mmxu.setInstUb(lsvc.getSignals().get(1));
        mmxu.setInstUc(lsvc.getSignals().get(2));
        mmxu.setInstIa(lsvc.getSignals().get(3));
        mmxu.setInstIb(lsvc.getSignals().get(4));
        mmxu.setInstIc(lsvc.getSignals().get(5));

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

        /** 1 - ON
         *  0 - OFF */
        int dir1 = 0;
        int dir2 = 1;
        int notdir1 = 1;
        int notdir2 = 1;

        if (dir1 == 0) {
            ptocdir1.getStrVal().getSetMag().getF().setValue(1000F);
        }
        if (dir2 == 0) {
            ptocdir2.getStrVal().getSetMag().getF().setValue(1000F);
        }
        if (notdir1 == 0) {
            ptocnotdir1.getStrVal().getSetMag().getF().setValue(1000F);
        }
        if (notdir2 == 0) {
            ptocnotdir2.getStrVal().getSetMag().getF().setValue(1000F);
        }

        if (dir1 == 0 || dir2 == 0 || notdir1 == 0 || notdir2 == 0) {
            ptocdir2.getOpDITmms().setSetVal(0);
            ptocnotdir2.getOpDITmms().setSetVal(0);
        }

        /** NHMI - используется для вывода графиков */
        NHMI nhmi = new NHMI();
        nhmi.addSignals(
                new NHMISignal("Uпп", msqi.getSeqV().getC1().getcVal().getMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("Uоп", msqi.getSeqV().getC2().getcVal().getMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("Uнп", msqi.getSeqV().getC2().getcVal().getMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("Ток фазы А", lsvc.getSignals().get(3).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("Ток Фазы Б", lsvc.getSignals().get(4).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("Ток фазы С", lsvc.getSignals().get(5).getInstMag().getF())
        );
        nhmi.addSignals(
            new NHMISignal("Ток ПП", msqi.getSeqA().getC1().getcVal().getMag().getF())
        );
        nhmi.addSignals(
            new NHMISignal("Ток ОП", msqi.getSeqA().getC2().getcVal().getMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("Ток НП", msqi.getSeqA().getC3().getcVal().getMag().getF()),
                new NHMISignal("Уст.напр.1", ptocdir1.getStrVal().getSetMag().getF()),
                new NHMISignal("Уст.напр.2", ptocdir2.getStrVal().getSetMag().getF()),
                new NHMISignal("Уст.напр.3", ptocdir3.getStrVal().getSetMag().getF()),
                new NHMISignal("Уст.ненапр.1", ptocnotdir1.getStrVal().getSetMag().getF()),
                new NHMISignal("Уст.ненапр.2", ptocnotdir2.getStrVal().getSetMag().getF())
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

        while(lsvc.hasNext()) {
            lsvc.process();
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

            //System.out.println(MSQI.getSeqA().getC1().getcVal().getMag());
        }

    }



}
