package protection.model.dataobjects.protection;

import protection.model.common.Attribute;

public class ENG {
    private Attribute<Direction> stVal = new Attribute<>(Direction.UNKNOWN);

    public Attribute<Direction> getStVal() {
        return stVal;
    }

    public void setStVal(Attribute<Direction> stVal) {
        this.stVal = stVal;
    }
}
