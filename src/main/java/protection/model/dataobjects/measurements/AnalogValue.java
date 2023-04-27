package protection.model.dataobjects.measurements;


import lombok.Getter;
import lombok.Setter;
import protection.model.common.Attribute;

public class AnalogValue {
    @Getter @Setter
    private Attribute<Float> f = new Attribute<>(0f);

}
