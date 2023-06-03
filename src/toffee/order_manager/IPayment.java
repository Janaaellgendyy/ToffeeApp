package toffee.order_manager;


public interface IPayment {
    void processPayment();
    double getAmount();
    void setAmount(double amount);
    int getTransactionId();
    String getDate();
    String getPaymentMethod();
    void setPaymentMethod(String paymentMethod);
}
