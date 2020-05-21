package assignment8;

import java.io.Serializable;
import java.math.BigDecimal;

public class AddRequest implements Serializable {

    private BigDecimal num1;
    private BigDecimal num2;

    public AddRequest(BigDecimal num1, BigDecimal num2) {
        this.num1 = num1;
        this.num2 = num2;
    }

    public BigDecimal getNum1() {
        return num1;
    }

    public void setNum1(BigDecimal num1) {
        this.num1 = num1;
    }

    public BigDecimal getNum2() {
        return num2;
    }

    public void setNum2(BigDecimal num2) {
        this.num2 = num2;
    }

}
