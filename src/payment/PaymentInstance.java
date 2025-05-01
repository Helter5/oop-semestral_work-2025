package payment;

import java.time.LocalDateTime;
import java.util.Objects;

public class PaymentInstance implements Comparable<PaymentInstance> {
    final private LocalDateTime paymentTime;
    final private int paymentAmount;

    public PaymentInstance(LocalDateTime paymentTime, int paymentAmount){
        if (paymentTime == null) {
            throw new IllegalArgumentException();
        }

        if (paymentAmount <= 0) {
            throw new IllegalArgumentException();
        }

        this.paymentTime = paymentTime;
        this.paymentAmount = paymentAmount;
    }

    public LocalDateTime getPaymentTime(){
        return paymentTime;
    }

    public int getPaymentAmount(){
        return paymentAmount;
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PaymentInstance that)) return false;
        return paymentAmount == that.paymentAmount && Objects.equals(paymentTime, that.paymentTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentTime, paymentAmount);
    }

     */


    @Override
    public int compareTo(PaymentInstance other) {
        return this.paymentTime.compareTo(other.paymentTime);
    }
}
