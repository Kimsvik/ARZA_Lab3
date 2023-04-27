package breakerControl.nodes.breaker;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.Attribute;

@Getter @Setter
public class DPC {
    private Attribute<Byte> stVal = new Attribute<>((byte) 0);
    private Attribute<Boolean> ctVal = new Attribute<>(false);
}