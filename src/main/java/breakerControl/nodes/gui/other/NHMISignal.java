package breakerControl.nodes.gui.other;

import lombok.Getter;
import protection.model.common.Attribute;


public class NHMISignal{
    @Getter
    private final String name;
    @Getter
    private final Attribute<?> dataX, dataY;

    public NHMISignal(String name, Attribute<?> data) { this.name = name; this.dataX = null; this.dataY = data; }
    public NHMISignal(String name, Attribute<?> dataX, Attribute<?> dataY) { this.name = name; this.dataX = dataX; this.dataY = dataY; }

}
