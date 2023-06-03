package toffee.order_manager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CashOnDeliveryPayment extends Payment{
    private String phoneNumber;
    
    public CashOnDeliveryPayment(){

    }
    public CashOnDeliveryPayment(double amount, String paymentMethod,String date, int transactionId) {
        super(amount, paymentMethod, date, transactionId);
    }




    
    /** 
     * set phone number
     * @param phoneNumber the phone number of the user
     */
    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;
    }

    
    /** 
     * get phone number
     * @return String the phone number of the user
     */
    public String getPhoneNumber() {

        return this.phoneNumber;
    }

    
    /** 
     * validate phone number
     * @param phoneNumber the phone number of the user
     * @return boolean true if the phone number is valid
     */
    public boolean validatePhoneNumber(String phoneNumber) {
        String regex = "^(010|011|012|015)[0-9]{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

   
}
