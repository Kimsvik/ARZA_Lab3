    package protection.model.dataobjects.measurements;

    import lombok.Data;

    @Data
    public class WYE {

        private CMV phsA = new CMV();
        private CMV phsB = new CMV();
        private CMV phsC = new CMV();
        private CMV phsNeut = new CMV();


    }
