package breakerControl.nodes.gui.other;


import lombok.Getter;
import lombok.Setter;

public class NHMIPoint<X, Y> {
    @Getter
    private final X value1;
    @Getter
    private final Y value2;

    public NHMIPoint(X value1, Y value2) { this.value1 = value1; this.value2 = value2; }

}
