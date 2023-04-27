package protection.model.common;


import lombok.Getter;
import lombok.Setter;

public class Attribute<T> {
    @Getter @Setter
    private T value;

    public Attribute(T value) {  this.value = value;  }
}
