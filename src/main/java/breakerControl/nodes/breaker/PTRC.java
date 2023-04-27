package breakerControl.nodes.breaker;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.LN;
import protection.model.dataobjects.protection.ACT;

import java.util.ArrayList;

public class PTRC extends LN {

    @Getter @Setter
    private ACT Tr = new ACT();
    @Getter @Setter
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
}




