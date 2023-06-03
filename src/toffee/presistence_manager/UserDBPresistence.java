package toffee.presistence_manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import toffee.user_manager.IUser;
import toffee.user_manager.LoggedInUser;
import toffee.user_manager.UserStatus;

public class UserDBPresistence {
    private String dataFilePath;

    public UserDBPresistence() {
        this.dataFilePath = "src\\data\\users.txt";
    }

   
   /** 
    * read user data from file
    * @return ArrayList<IUser> list of users
    * @throws IOException if file not found
    */
   public ArrayList<IUser> readUserDataFromFile() throws IOException {
        ArrayList<IUser> users = new ArrayList<>();

       BufferedReader reader = new BufferedReader(new FileReader(dataFilePath));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                int id = Integer.parseInt(fields[0]);
                String name = fields[1];
                String email = fields[2];
                String password = fields[3];
                String phone = fields[4];
                String address = fields[5];
                String country = fields[6];
                int loyaltyPoints = Integer.parseInt(fields[7]);
                UserStatus status = UserStatus.valueOf(fields[8]);
                
                IUser user = new LoggedInUser(id, name, email, password, phone, address, country, loyaltyPoints, status);
                users.add(user);
            }
        

        return users;
    }

    
    /** 
     * get user by email and password
     * @param email email that user used to login
     * @param password password that user used to login
     * @return IUser user that logged in
     * @throws IOException if file not found
     */
    public IUser getUser(String email , String password) throws IOException {
        ArrayList<IUser> users = readUserDataFromFile();

        for (IUser user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }

        return null;
    }

    
    /** 
     *  register user
     * @param user user that want to register
     * @return boolean true if user registered successfully, false if user already registered
     * @throws IOException if file not found
     */
    public boolean registerUser(IUser user) throws IOException {
        ArrayList<IUser> users = readUserDataFromFile();
        for (IUser u : users) {
            if (u.getEmail().equals(user.getEmail())) {
                return false;
            }
        }
        users.add(user);
        writeUserDataToFile(users);
        return true;
    }

    
    /** 
     * update user
     * @param user user that want to update
     * @throws IOException if file not found
     */
    public void updateUser(IUser user) throws IOException {
        ArrayList<IUser> users = readUserDataFromFile();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getID() == user.getID()) {
                users.set(i, user);
                break;
            }
        }

        writeUserDataToFile(users);
    }

    
    /** 
     * write user data to file
     * @param users list of users
     * @throws IOException if file not found
     */
    private void writeUserDataToFile(ArrayList<IUser> users) throws IOException {
        FileWriter writer = new FileWriter(dataFilePath);

        for (IUser user : users) {
            writer.write(user.getID() + "," + user.getName() + "," + user.getEmail() + "," + user.getPassword() + "," + user.getPhoneNumber() + "," + user.getAddress() + "," + user.getCountry() + "," + user.getLoyaltyPoints() + "," + user.getStatus() + "\n");
        }

        writer.close();
    }

}
