package payment;

import java.time.LocalDateTime;

public class PaymentInstance implements Comparable<PaymentInstance> {
    final private LocalDateTime paymentTime;
    final private int paymentAmount;

    public PaymentInstance(LocalDateTime paymentTime, int paymentAmount){
        // to do
    }

    public LocalDateTime getPaymentTime(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    public int getPaymentAmount(){
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }

    @Override
    public int compareTo(PaymentInstance other) {
        throw new UnsupportedOperationException("Not implemented yet"); // to do
    }
}
