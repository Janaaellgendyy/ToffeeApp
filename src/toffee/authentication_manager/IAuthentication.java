package toffee.authentication_manager;

import toffee.user_manager.IUser;

public interface IAuthentication {
    public boolean login(String email, String password);
    public boolean logout();
    public boolean register(IUser user);
    public boolean resetPassword(String email);
    public boolean validateRegister(String email, String password,  String phone);
    public boolean validateLogin(String email, String password);
    public boolean isLoggedIn();
    public IUser getUser();
    public void setUser(IUser user);
    public boolean validateOTP(int otp,int otpSent);
    public int sendOTP(String email);
}
