package protection.model.dataobjects.measurements;

import lombok.Data;

@Data
public class DEL {
    private CMV phsAB = new CMV();
    private CMV phsBC = new CMV();
    private CMV phsCA = new CMV();

}