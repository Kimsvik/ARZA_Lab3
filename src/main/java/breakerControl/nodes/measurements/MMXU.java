package breakerControl.nodes.measurements;

import lombok.Data;
import protection.model.common.LN;
import protection.model.dataobjects.measurements.DEL;
import protection.model.dataobjects.measurements.Vector;
import breakerControl.objects.measurements.filter.Filter;
import breakerControl.objects.measurements.filter.Furier;
import protection.model.dataobjects.measurements.WYE;
import protection.model.dataobjects.measurements.MV;

/** MMXU
 *  Измерения */
@Data
public class MMXU extends LN {
    private MV TotW = new MV(); // Суммарная активная мощность (суммарная P)
    private MV TotVAr = new MV(); // Суммарная реактивная мощность (суммарная Q)
    private MV TotVA = new MV(); // Суммарная фиксируемая мощность (суммарная S)
    private MV TotPF = new MV(); // Средний коэффициент мощности (суммарный коэффициент мощности PF)
    private MV instIa = new MV();
    private MV instIb = new MV();
    private MV instIc = new MV();
    private MV instUa = new MV();
    private MV instUb = new MV();
    private MV instUc = new MV();
    private WYE A = new WYE(); // Фазные токи (IL1, IL2, IL3)
    private WYE PhV = new WYE(); // Фазные напряжения (VL1ER, ...)
    private WYE W = new WYE(); // Активная мощность фазы

    private WYE Z = new WYE(); // Полное сопротивление фазы
    private DEL Zf = new DEL();
    private WYE N = new WYE();
    private Vector Na = new Vector();
    private Vector Nb = new Vector();
    private Vector Nc = new Vector();

    private Furier UaF = new Furier();
    private Furier UbF = new Furier();
    private Furier UcF = new Furier();

    private Furier iaF = new Furier();
    private Furier ibF = new Furier();
    private Furier icF = new Furier();
    private Vector zA = new Vector();
    private Vector zB = new Vector();
    private Vector zC = new Vector();
    private Vector Iab = new Vector();
    private Vector Ibc = new Vector();
    private Vector Ica = new Vector();
    private Vector Uab = new Vector();
    private Vector Ubc = new Vector();
    private Vector Uca = new Vector();
    private float cosFiAB;
    private float cosFiBC;
    private float cosFiCA;
    private float sinFiAB;
    private float sinFiBC;
    private float sinFiCA;
    private Vector Zab = new Vector();
    private Vector Zbc = new Vector();
    private Vector Zca = new Vector();
    private int f = 50;
    private int counter;
    private WYE prev = new WYE();


    @Override
    public void process() {

        UaF.process(instUa, PhV.getPhsA().getCVal());
        UbF.process(instUb, PhV.getPhsB().getCVal());
        UcF.process(instUc, PhV.getPhsC().getCVal());
        iaF.process(instIa, A.getPhsA().getCVal());
        ibF.process(instIb, A.getPhsB().getCVal());
        icF.process(instIc, A.getPhsC().getCVal());

        Na.Zvector(A.getPhsA().getCVal(), PhV.getPhsA().getCVal());
        Nb.Zvector(A.getPhsB().getCVal(), PhV.getPhsB().getCVal());
        Nc.Zvector(A.getPhsC().getCVal(), PhV.getPhsC().getCVal());

        N.getPhsA().setCVal(Na);
        N.getPhsB().setCVal(Nb);
        N.getPhsC().setCVal(Nc);

        Uab.tovector((PhV.getPhsA().getCVal().getX().getF().getValue() - PhV.getPhsB().getCVal().getX().getF().getValue()),
                (PhV.getPhsA().getCVal().getY().getF().getValue() - PhV.getPhsB().getCVal().getY().getF().getValue()));
        Ubc.tovector((PhV.getPhsB().getCVal().getX().getF().getValue() - PhV.getPhsC().getCVal().getX().getF().getValue()),
                (PhV.getPhsB().getCVal().getY().getF().getValue() - PhV.getPhsC().getCVal().getY().getF().getValue()));
        Uca.tovector((PhV.getPhsC().getCVal().getX().getF().getValue() - PhV.getPhsA().getCVal().getX().getF().getValue()),
                (PhV.getPhsC().getCVal().getY().getF().getValue() - PhV.getPhsA().getCVal().getY().getF().getValue()));

        Iab.tovector((A.getPhsA().getCVal().getX().getF().getValue() - A.getPhsB().getCVal().getX().getF().getValue()),
                (A.getPhsA().getCVal().getY().getF().getValue() - A.getPhsB().getCVal().getY().getF().getValue()));
        Ibc.tovector((A.getPhsB().getCVal().getX().getF().getValue() - A.getPhsC().getCVal().getX().getF().getValue()),
                (A.getPhsB().getCVal().getY().getF().getValue() - A.getPhsC().getCVal().getY().getF().getValue()));
        Ica.tovector((A.getPhsC().getCVal().getX().getF().getValue() - A.getPhsA().getCVal().getX().getF().getValue()),
                (A.getPhsC().getCVal().getY().getF().getValue() - A.getPhsA().getCVal().getY().getF().getValue()));


        Zab.Zvector(Iab, Uab);
        Zbc.Zvector(Ibc, Ubc);
        Zca.Zvector(Ica, Uca);

        Zab.artog(Zab.getMag().getF().getValue(), Zab.getAng().getF().getValue());
        Zbc.artog(Zbc.getMag().getF().getValue(), Zbc.getAng().getF().getValue());
        Zca.artog(Zca.getMag().getF().getValue(), Zca.getAng().getF().getValue());

        Zf.getPhsAB().setCVal(Zab);
        Zf.getPhsBC().setCVal(Zbc);
        Zf.getPhsCA().setCVal(Zca);

        float angAB = (float) Math.toRadians(Uab.getAng().getF().getValue() - Iab.getAng().getF().getValue());
        float angBC = (float) Math.toRadians(Ubc.getAng().getF().getValue() - Ibc.getAng().getF().getValue());
        float angCA = (float) Math.toRadians(Uca.getAng().getF().getValue() - Ica.getAng().getF().getValue());

        cosFiAB= (float) Math.cos(angAB);
        cosFiBC= (float) Math.cos(angBC);
        cosFiCA= (float) Math.cos(angCA);

        sinFiAB= (float) Math.sin(angAB);
        sinFiBC= (float) Math.sin(angBC);
        sinFiCA= (float) Math.sin(angCA);

        zA.Zvector(A.getPhsA().getCVal(), PhV.getPhsA().getCVal());
        zB.Zvector(A.getPhsB().getCVal(), PhV.getPhsB().getCVal());
        zC.Zvector(A.getPhsC().getCVal(), PhV.getPhsC().getCVal());

        Z.getPhsA().setCVal(zA);
        Z.getPhsB().setCVal(zB);
        Z.getPhsC().setCVal(zC);
    }

}
