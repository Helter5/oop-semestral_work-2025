package payment;

public class PaymentInstance {
    private LocalDateTime paymentTime;
    private int paymentAmount;

    public PaymentInstance(LocalDateTime paymentTime, int paymentAmount){

    };

    public LocalDateTime getPaymentTime();

    public int getPaymentAmount();
}
