package protection.model.dataobjects.measurements;


import protection.model.common.Attribute;
import protection.model.common.Point;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CSD {
    private ArrayList<Point> crvPvs = new ArrayList<>();

    private Attribute<Integer> numPts = new Attribute<>(0);
}
