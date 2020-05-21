package assignment8;

import java.io.Serializable;
import java.math.BigDecimal;

public class AddResponse implements Serializable {

    private BigDecimal sum;

    public AddResponse(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

}
