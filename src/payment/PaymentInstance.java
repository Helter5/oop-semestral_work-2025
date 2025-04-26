package payment;

import java.time.LocalDateTime;

public class PaymentInstance implements Comparable<PaymentInstance> {
    final private LocalDateTime paymentTime;
    final private int paymentAmount;

    public PaymentInstance(LocalDateTime paymentTime, int paymentAmount){
        if (paymentTime == null) {
            throw new IllegalArgumentException("Payment time can't be null");
        }

        if (paymentAmount < 0) {
            throw new IllegalArgumentException("Payment amount has to be positive value");
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

    @Override
    public int compareTo(PaymentInstance other) {
        return this.paymentTime.compareTo(other.paymentTime);
    }
}
