package protection.model.dataobjects.protection;

import protection.model.common.Attribute;


public class ACD extends ACT {

    private Attribute<Direction> dirGeneral = new Attribute<>(Direction.UNKNOWN);
    private Attribute<Direction> dirPhsA = new Attribute<>(Direction.UNKNOWN);
    private Attribute<Direction> dirPhsB = new Attribute<>(Direction.UNKNOWN);
    private Attribute<Direction> dirPhsC = new Attribute<>(Direction.UNKNOWN);
    private Attribute<Direction> dirNeut = new Attribute<>(Direction.UNKNOWN);

    public Attribute<Direction> getDirGeneral() {
        return dirGeneral;
    }

    public void setDirGeneral(Attribute<Direction> dirGeneral) {
        this.dirGeneral = dirGeneral;
    }

    public Attribute<Direction> getDirPhsA() {
        return dirPhsA;
    }

    public void setDirPhsA(Attribute<Direction> dirPhsA) {
        this.dirPhsA = dirPhsA;
    }

    public Attribute<Direction> getDirPhsB() {
        return dirPhsB;
    }

    public void setDirPhsB(Attribute<Direction> dirPhsB) {
        this.dirPhsB = dirPhsB;
    }

    public Attribute<Direction> getDirPhsC() {
        return dirPhsC;
    }

    public void setDirPhsC(Attribute<Direction> dirPhsC) {
        this.dirPhsC = dirPhsC;
    }

    public Attribute<Direction> getDirNeut() {
        return dirNeut;
    }

    public void setDirNeut(Attribute<Direction> dirNeut) {
        this.dirNeut = dirNeut;
    }
}
