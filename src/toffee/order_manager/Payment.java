package toffee.order_manager;
import java.time.LocalDate;
import java.util.Random;

public class Payment implements IPayment {

    private double amount;
    private int transactionId;
    private String date;
    private String paymentMethod;

    public Payment() {

    }

    public Payment(double amount, String paymentMethod,String date, int transactionId) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.date = date;
        this.transactionId = transactionId;
    }

    @Override
    public void processPayment() {
        Random rand = new Random();
        this.transactionId = rand.nextInt(1000000);
        LocalDate date = LocalDate.now();
        this.date = date.toString();
        System.out.println("Payment processed successfully");
    }

    
    /** 
     * set amount
     * @param amount the amount of the payment
     */
    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }

    
    /** 
     * get amount
     * @return double the amount of the payment 
     */
    @Override
    public double getAmount() {
        return amount;
    }

    
    /** 
     * set transaction id
     * @return int the transaction id of the payment
     */
    @Override
    public int getTransactionId() {
        return transactionId;
    }

    
    /** 
     * get date
     * @return String the date of the payment
     */
    @Override
    public String getDate() {
        return date;
    }

    
    /** 
     * set payment method
     * @param paymentMethod the payment method of the payment
     */
    public void setPaymentMethod(String paymentMethod){
        this.paymentMethod = paymentMethod;
    }

    
    /** 
     * get payment method
     * @return String the payment method of the payment
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

}
