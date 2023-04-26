package process.protection.current;

import iec61850.nodes.breaker.CSWI;
import iec61850.nodes.breaker.XCBR;
import iec61850.nodes.custom.LSVC;
import iec61850.nodes.gui.NHMI;
import iec61850.nodes.gui.other.NHMISignal;
import iec61850.nodes.measurements.MMXU;
import iec61850.nodes.protection.PTOC;
import iec61850.objects.Additional.RDIR;
import iec61850.objects.measurements.filter.MSQI;
import iec61850.nodes.breaker.PTRC;
import iec61850.objects.samples.protection.dir.Direction;


public class Main2lr {


    public static void main(String[] args) {

        LSVC lsvc = new LSVC();
        PTRC ptrc = new PTRC();
        NHMI nhmi = new NHMI();
        MMXU mmxu = new MMXU();
        PTOC ptocnotdir1 = new PTOC();
        PTOC ptocnotdir2 = new PTOC();
        PTOC ptocdir1 = new PTOC();
        PTOC ptocdir2 = new PTOC();
        XCBR xcbr = new XCBR();
        CSWI cswi = new CSWI();
        MSQI msqi = new MSQI();
        RDIR rdir = new RDIR();


        lsvc.readComtrade("C:\\Users\\Сергей\\Desktop\\МЭИ\\М2 семестр\\Алгоритмы РЗА\\ЛР2\\Лабораторная работа №2\\Опыты\\KZ4");
        mmxu.setInstUa(lsvc.getSignals().get(0));
        mmxu.setInstUb(lsvc.getSignals().get(1));
        mmxu.setInstUc(lsvc.getSignals().get(2));
        mmxu.setInstIa(lsvc.getSignals().get(3));
        mmxu.setInstIb(lsvc.getSignals().get(4));
        mmxu.setInstIc(lsvc.getSignals().get(5));
        msqi.setImbA(mmxu.getA());
        msqi.setImbV(mmxu.getPhV());
        rdir.setN(mmxu.getN());


        //Направленные
        ptocdir1.getA().setPhsA(msqi.getSeqA().getC3());
        ptocdir1.getA().setPhsB(msqi.getSeqA().getC3());
        ptocdir1.getA().setPhsC(msqi.getSeqA().getC3());
        ptocdir1.getStrVal().getSetMag().getF().setValue(500F);
        ptocdir1.getOpDITmms().setSetVal(0);
        ptocdir1.getDirMod().getStVal().setValue(Direction.FORWARD);
        ptocdir1.setDir(rdir.getDir());

        ptocdir2.getA().setPhsA(msqi.getSeqA().getC3());
        ptocdir2.getA().setPhsB(msqi.getSeqA().getC3());
        ptocdir2.getA().setPhsC(msqi.getSeqA().getC3());
        ptocdir2.getStrVal().getSetMag().getF().setValue(300F);
        ptocdir2.getOpDITmms().setSetVal(500);
        ptocdir2.getDirMod().getStVal().setValue(Direction.FORWARD);
        ptocdir2.setDir(rdir.getDir());

        //Ненаправленные
        ptocnotdir1.getA().setPhsA(msqi.getSeqA().getC3());
        ptocnotdir1.getA().setPhsB(msqi.getSeqA().getC3());
        ptocnotdir1.getA().setPhsC(msqi.getSeqA().getC3());
        ptocnotdir1.getStrVal().getSetMag().getF().setValue(500F);
        ptocnotdir1.getOpDITmms().setSetVal(0);

        ptocnotdir2.getA().setPhsA(msqi.getSeqA().getC3());
        ptocnotdir2.getA().setPhsB(msqi.getSeqA().getC3());
        ptocnotdir2.getA().setPhsC(msqi.getSeqA().getC3());
        ptocnotdir2.getStrVal().getSetMag().getF().setValue(300F);
        ptocnotdir2.getOpDITmms().setSetVal(500);


        cswi.getPos().getCtVal().setValue(true);
        cswi.getPos().getStVal().setValue((byte) 2);

        ptrc.getOp().add(ptocdir1.getOp());
        ptrc.getOp().add(ptocdir2.getOp());
        ptrc.getOp().add(ptocnotdir1.getOp());
        ptrc.getOp().add(ptocnotdir2.getOp());

        cswi.setOpOpn(ptrc.getTr());
        cswi.setOpCls(ptrc.getTr());
        xcbr.setPos(cswi.getPos());
        //1 rabotaet. 0 ne rabotaet
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
            cswi.process();
            xcbr.process();
            msqi.process();
            rdir.process();

            //System.out.println(MSQI.getSeqA().getC1().getcVal().getMag());
        }

    }



}
