package breakerControl.nodes.breaker;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.Attribute;

@Getter @Setter
public class SPC {
    private Attribute<Boolean> stVal = new Attribute<>( false);
}