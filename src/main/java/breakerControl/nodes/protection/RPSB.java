package breakerControl.nodes.protection;

import breakerControl.nodes.breaker.SPC;
import protection.model.dataobjects.measurements.Vector;
import breakerControl.objects.measurements.filter.SEQ;

/** RPSB
 *  Обнаружение колебаний мощности/блокировка */
public class RPSB {
    private SPC BlkZn = new SPC(); // Блокировка коррелированной зоны PDIS
    private SEQ SeqA = new SEQ();


    private Vector AvarSost = new Vector();
    private int size = 80;
    private float[] Buffer = new float[size];
    private int counter = 0;
    private float difference = 0;


    private void RaschetAvarSost(SEQ Obrposled, Vector dif) {
        difference = Obrposled.getC2().getCVal().getMag().getF().getValue() - Buffer[counter];
        dif.getMag().getF().setValue(difference);
        Buffer[counter] = Obrposled.getC2().getCVal().getMag().getF().getValue();
        if(++counter >= size) counter = 0;

    }

    public void process() {
        RaschetAvarSost(SeqA, AvarSost);
        if(Math.abs(AvarSost.getMag().getF().getValue()) > 1000) BlkZn.getStVal().setValue(true);

    }



    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public float[] getBuffer() {
        return Buffer;
    }

    public void setBuffer(float[] buffer) {
        Buffer = buffer;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public float getDifference() {
        return difference;
    }

    public void setDifference(float difference) {
        this.difference = difference;
    }

    public Vector getAvarSost() {
        return AvarSost;
    }

    public void setAvarSost(Vector avarSost) {
        AvarSost = avarSost;
    }

    public SPC getBlkZn() {
        return BlkZn;
    }

    public void setBlkZn(SPC blkZn) {
        BlkZn = blkZn;
    }

    public SEQ getSeqA() {
        return SeqA;
    }

    public void setSeqA(SEQ seqA) {
        SeqA = seqA;
    }
}
