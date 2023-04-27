package protection.model.dataobjects.protection;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.Attribute;

public class ENG {
    @Getter @Setter
    private Attribute<Direction> stVal = new Attribute<>(Direction.UNKNOWN);
}
