package protection.model.dataobjects.protection;

import lombok.Data;
import protection.model.common.Attribute;

@Data
public class ACD extends ACT {

    private Attribute<Direction> dirGeneral = new Attribute<>(Direction.UNKNOWN);
    private Attribute<Direction> dirPhsA = new Attribute<>(Direction.UNKNOWN);
    private Attribute<Direction> dirPhsB = new Attribute<>(Direction.UNKNOWN);
    private Attribute<Direction> dirPhsC = new Attribute<>(Direction.UNKNOWN);
    private Attribute<Direction> dirNeut = new Attribute<>(Direction.UNKNOWN);

}
