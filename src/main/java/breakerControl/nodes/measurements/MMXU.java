package breakerControl.nodes.measurements;

import lombok.Data;
import protection.model.common.LN;
import protection.model.dataobjects.measurements.Vector;
import breakerControl.objects.measurements.filter.Filter;
import breakerControl.objects.measurements.filter.Furier;
import protection.model.dataobjects.measurements.WYE;
import breakerControl.objects.samples.MV;

@Data
public class MMXU extends LN {

    private MV instIa = new MV();
    private MV instIb = new MV();
    private MV instIc = new MV();

    private MV instUa = new MV();
    private MV instUb = new MV();
    private MV instUc = new MV();

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

        UaF.process(instUa, PhV.getPhsA().getCVal());
        UbF.process(instUb, PhV.getPhsB().getCVal());
        UcF.process(instUc, PhV.getPhsC().getCVal());
        iaF.process(instIa, A.getPhsA().getCVal());
        ibF.process(instIb, A.getPhsB().getCVal());
        icF.process(instIc, A.getPhsC().getCVal());




        Na.Napravlenie(A.getPhsA().getCVal(), PhV.getPhsA().getCVal());
        Nb.Napravlenie(A.getPhsB().getCVal(), PhV.getPhsB().getCVal());
        Nc.Napravlenie(A.getPhsC().getCVal(), PhV.getPhsC().getCVal());

        N.getPhsA().setCVal(Na);
        N.getPhsB().setCVal(Nb);
        N.getPhsC().setCVal(Nc);


    }

}
