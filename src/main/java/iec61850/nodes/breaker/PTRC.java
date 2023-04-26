package iec61850.nodes.breaker;

import iec61850.nodes.common.LN;
import iec61850.objects.samples.protection.ACT;

import java.util.ArrayList;

public class PTRC extends LN {

    private ACT Tr = new ACT();
    private ArrayList<ACT> Op = new ArrayList<ACT>();


    @Override
    public void process() {
        for (int i = 0; i < Op.size(); i++) {
            if (Op.get(i).getGeneral().getValue()) {
                Tr.getGeneral().setValue(true);
                break;
            }

        }

    }


    public ACT getTr() {
        return Tr;
    }

    public void setTr(ACT tr) {
        Tr = tr;
    }

    public ArrayList<ACT> getOp() {
        return Op;
    }

    public void setOp(ArrayList<ACT> op) {
        Op = op;
    }
}




