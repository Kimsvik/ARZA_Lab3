package protection.model.dataobjects.protection;

import lombok.Data;
import protection.model.common.Attribute;

@Data
public class ACT {

    private Attribute<Boolean> general = new Attribute<>(false);
    private Attribute<Boolean> phsA = new Attribute<>(false);
    private Attribute<Boolean> phsB = new Attribute<>(false);
    private Attribute<Boolean> phsC = new Attribute<>(false);
    private Attribute<Boolean> neut = new Attribute<>(false);

}
