package toffee.authentication_manager;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import toffee.presistence_manager.UserDBPresistence;
import toffee.user_manager.IUser;
import toffee.user_manager.LoggedInUser;

public class Authentication implements IAuthentication{
    private boolean isLoggedIn;
    private IUser user;

    public Authentication() {
        this.isLoggedIn = false;
        user = new LoggedInUser();
    }
    
    /** 
     * login user with email and password
     * 
     * @param email the email the user entered
     * @param password the password the user entered
     * @return boolean true if the user is logged in, false otherwise
     */
    public boolean login(String email, String password) {
        UserDBPresistence userDBPresistence = new UserDBPresistence();
        try {
            user = userDBPresistence.getUser(email, password);
            if (user == null) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        this.isLoggedIn = true;
        return true;
    }
    
    /** 
     *  logout the user
     * 
     * @return boolean true if the user is logged out, false otherwise
     */
    public boolean logout() {
        this.isLoggedIn = false;
        this.user = new LoggedInUser();
        return true;
    }
    
    /** 
     *  register the user
     * 
     * @param user the user to be registered
     * @return boolean true if the user is registered, false otherwise
     */
    public boolean register(IUser user) {
        UserDBPresistence userDBPresistence = new UserDBPresistence();
        try {
            boolean data  = userDBPresistence.registerUser(user);
            if (!data) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false; 
        }
        this.user = user;
        this.isLoggedIn = true;
        return true;
    }
    
    /** 
     *  reset the user password
     * 
     * @param email the email of the user
     * @return boolean true if the password is reset, false otherwise
     */
    public boolean resetPassword(String email) {
        
        return false;
    }
    
    /**
     *  check if user inputs are valid
     * 
     * @param email the email of the user
     * @param password the password of the user
     * @param phone    the phone of the user
     * @return boolean  true if the inputs are valid, false otherwise
     */
    @Override
    public boolean validateRegister(String email, String password,  String phone) {
        if (!validateEmail(email)) {
            System.out.println("Invalid email");
            return false;
        }else if (!validatePassword(password)) {
            System.out.println("1- Password must contain at least one digit [0-9].");
            System.out.println("2- Password must contain at least one lowercase Latin character [a-z].");
            System.out.println("3- Password must contain at least one uppercase Latin character [A-Z].");
            System.out.println("4- Password must contain at least one special character like ! @ # & ( ).");
            System.out.println("5- Password must contain a length of at least 8 characters and a maximum of 20 characters.");
            return false;
        }else if (!validatePhone(phone)) {
            System.out.println("Invalid phone");
            return false;
        }
        return true;
    }

    
    /** 
     *  check if the email is valid
     * 
     * @param email the email of the user
     * @return boolean true if the email is valid, false otherwise
     */
    private boolean validateEmail(String email) {
        String regex = "^(.+)@(.+)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    
    /** 
     *  check if the password is valid
     * 
     * @param password the password of the user
     * @return boolean true if the password is valid, false otherwise
     */
    private boolean validatePassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }


    
    /** 
     *  check if the phone is valid
     * 
     * @param phone the phone of the user
     * @return boolean true if the phone is valid, false otherwise
     */
    private boolean validatePhone(String phone) {
        String regex = "^(010|011|012|015)[0-9]{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    
    /** 
     *  check if the user is logged in
     * 
     * @return boolean true if the user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return this.isLoggedIn;
    }
    
    /** 
     *  vlidate the login of the user
     * 
     * @param email the email of the user
     * @param password  the password of the user
     * @return boolean true if the login is valid, false otherwise
     */
    @Override
    public boolean validateLogin(String email, String password) {
        if (!validateEmail(email)) {
            System.out.println("Invalid email");
            return false;
        }else if (!validatePassword(password)) {
            System.out.println("1- Password must contain at least one digit [0-9].");
            System.out.println("2- Password must contain at least one lowercase Latin character [a-z].");
            System.out.println("3- Password must contain at least one uppercase Latin character [A-Z].");
            System.out.println("4- Password must contain at least one special character like ! @ # & ( ).");
            System.out.println("5-  Password must contain a length of at least 8 characters and a maximum of 20 characters.");
            return false;
        }
        return true;
    }
    
    /** 
     *  get the user
     * 
     * @return IUser the user
     */
    @Override
    public IUser getUser() {
       return this.user;
    }
    
    /** 
     *  set the user
     * 
     * @param user  the user
     */
    @Override
    public void setUser(IUser user) {
        this.user = user;
    }
    
    /** 
     *  validate the OTP
     * 
     * @param otp  the otp the user entered
     * @param otpSent  the otp sent to the user
     * @return boolean  true if the otp is valid, false otherwise
     */
    @Override
    public boolean validateOTP(int otp,int otpSent) {
        if(otp == otpSent){
            return true;
        }
        return false;
    }
    
    /**
     * send the OTP to the user 
     * 
     * @param email the email of the user
     * @return int the otp sent to the user
     */
    @Override
    public int sendOTP(String email) {
       Random rand = new Random();
        int otp = rand.nextInt(10000);
        String to = email;
        String from = "amhk11827@gmail.com";
        String host = "smtp.gmail.com";
    

        Properties properties = System.getProperties();  
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, "xhiykotowtrrijyn");

            }

        });
       
        // session.setDebug(true);

        try{  
            MimeMessage message = new MimeMessage(session);  
       
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
            message.setSubject("Email Verification");       
            message.setFrom(new InternetAddress(from));  
            message.setText("Thank you for registering with our service. To ensure the security of your account, we require you to verify your email address by entering the following OTP: "+otp+"\n\n\n Best Regards,\n Toffee team");  

            Transport.send(message);  
            System.out.println("OTP sent successfully....");  
    
        }catch (MessagingException mex) {mex.printStackTrace();}  
   
         return otp;
    }

}
