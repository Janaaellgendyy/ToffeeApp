package toffee.mobile_ui;
import java.util.ArrayList;
import java.util.Scanner;

import toffee.authentication_manager.IAuthentication;
import toffee.category_view_manager.Category;
import toffee.category_view_manager.ICategoryView;
import toffee.category_view_manager.Product;
import toffee.order_manager.CashOnDeliveryPayment;
import toffee.order_manager.IOrderHistory;
import toffee.order_manager.Order;
import toffee.order_manager.OrderStatus;
import toffee.shopping_cart_manager.ICartItem;
import toffee.shopping_cart_manager.IShoppingCart;
import toffee.shopping_cart_manager.ShoppingCart;
import toffee.user_manager.IUser;
import toffee.user_manager.LoggedInUser;

public class MobileGUI {
    private ICategoryView categoryView;
    private IShoppingCart shoppingCart;
    private IOrderHistory orderHistory;
    private IAuthentication authentication;
   


    public MobileGUI(ICategoryView categoryView, IShoppingCart shoppingCart, IOrderHistory orderHistory, IAuthentication authentication) {
    
        this.categoryView = categoryView;
        this.shoppingCart = shoppingCart;
        this.orderHistory = orderHistory;
        this.authentication = authentication;
    }

    public void Menu() {
        
        System.out.println("Please choose one of the following options:");
        System.out.println("1- View Categories");
        if (authentication.isLoggedIn()) {
            System.out.println("2- View Shopping Cart");
            System.out.println("3- View Order History");
            System.out.println("4- View Profile");
            System.out.println("5- Logout");
        } else {
            System.out.println("2- Register");
            System.out.println("3- Login");
        }
        System.out.println("0- Exit");
        System.out.println("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice =  Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                viewCategories();
                break;
            case 2:
                if (authentication.isLoggedIn()) {
                    viewShoppingCart();
                } else {
                    register();
                }
                break;
            case 3:
                if (authentication.isLoggedIn()) {
                    viewOrderHistory();
                } else {
                    login();
                }
                break;
           
            case 4:
                if (authentication.isLoggedIn()) {
                    viewProfile();
                } else {
                    System.out.println("Invalid choice!");
                }
                break;
            case 5:
                if (authentication.isLoggedIn()) {
                    logout();
                } else {
                    System.out.println("Invalid choice!");
                }
                break;
            case 0:
                System.out.println("Thank you for using Toffee App!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }
    }

    public void searchForItem() {

    }
    public void viewCategories() {
        ArrayList<Category> cArrayList = categoryView.viewCategories();
        System.out.println("Please Choose Category:");
        for (int i = 0; i < cArrayList.size(); i++) {
            System.out.println(i+1 + " - " + cArrayList.get(i).getName());
        }
        System.out.println("0 - Back");
        System.out.println("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice =  Integer.parseInt(scanner.nextLine());
        if (choice == 0) {
            Menu();
        } else {
            viewCategoryItems(cArrayList.get(choice-1).getId());
        }  
    }

    
    /** 
     * @param categoryID
     */
    public void viewCategoryItems(int categoryID) {
        ArrayList<Product> pArrayList = categoryView.viewCategoryItems(categoryID);
        System.out.println("Please Choose Item:");
        for (int i = 0; i < pArrayList.size(); i++) {
            System.out.println(i+1 + " - " + pArrayList.get(i).getName());
        }
        System.out.println("0 - Back");
        System.out.println("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice =  Integer.parseInt(scanner.nextLine());
        if (choice == 0) {
            viewCategories();
        } else {
            viewItem(pArrayList.get(choice-1));
        }
    }
    
    /** 
     * @param product
     */
    public void viewItem(Product product) {
        System.out.println("Item Name: " + product.getName());
        System.out.println("Item Price: " + product.getPrice());
        System.out.println("Item Description: " + product.getDescription());
        System.out.println("Item Quantity: " + product.getAvailablieQuantity());
        System.out.println("Item Brand: " + product.getBrand());
        System.out.println("Item Loyalty Points: " + product.getLoyaltyPoints());
        System.out.println("1- Add to Cart");
        System.out.println("0- Back");
        System.out.println("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice =  Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                if (authentication.isLoggedIn()) {
                    
                    addItemsToCart(product);
                }else{
                    System.out.println("Please login first!");
                    Menu();
                }
                break;
            case 0:
                viewCategoryItems(product.getCategoryID());
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }

    }


    
    /** 
     * @param product
     */
    public void addItemsToCart(Product product) {
        System.out.println("Enter Quantity: ");
        Scanner scanner = new Scanner(System.in);
        int quantity =  Integer.parseInt(scanner.nextLine());
        if (quantity > product.getAvailablieQuantity()) {
            System.out.println("Invalid Quantity!");
            addItemsToCart(product);
        } else {
            shoppingCart.AddItem(product, quantity);
            System.out.println("Item added to cart successfully!");
            Menu();
        }
    }
    public void viewShoppingCart() {
        ArrayList<ICartItem> cartItems= shoppingCart.getItems();
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty!");
            Menu();
        }
            
        
        System.out.println("Please Choose Item:");
        for (int i = 0; i < cartItems.size(); i++) {
            System.out.println(i+1 + " - Name: " + cartItems.get(i).getProduct().getName() + "   Quantity: " + cartItems.get(i).getQuantity()+ "   Price: " + cartItems.get(i).getUnitPrice()+ "   Total Price: " + cartItems.get(i).getTotalPrice()) ;
        }
        System.out.println("--------------------------------");
        System.out.println("1- Remove Item");
        System.out.println("2- Update Item Quantity");
        System.out.println("3- Checkout");
        System.out.println("0- Back");
        System.out.println("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice =  Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                System.out.println("Enter Item Number: ");
                int itemNumber =  Integer.parseInt(scanner.nextLine());
                shoppingCart.RemoveItem(cartItems.get(itemNumber-1));
                System.out.println("Item removed successfully!");
                viewShoppingCart();
                break;
            case 2:
                System.out.println("Enter Item Number: ");
                int itemNumber2 =  Integer.parseInt(scanner.nextLine());
                System.out.println("Enter Quantity: ");
                int quantity =  Integer.parseInt(scanner.nextLine());
                if(quantity > cartItems.get(itemNumber2-1).getProduct().getAvailablieQuantity() ){
                    System.out.println("Invalid Quantity!");
                }else{
                    shoppingCart.UpdateItem(cartItems.get(itemNumber2-1), quantity);
                }
                System.out.println("Item updated successfully!");
                viewShoppingCart();
                break;
            case 3:
                checkout();
                break;
            case 0:
                Menu();
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }
    }

    public void checkout() {
        System.out.println("Total Price: " + shoppingCart.calcTotal());
        System.out.println("1- Confirm");
        System.out.println("0- Back");
        System.out.println("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice =  Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                  Order order = shoppingCart.placeOrder(authentication.getUser().getID());
                  
                payOrder(order);
                break;
            case 0:
                viewShoppingCart();
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }
    }

    

    
    /** 
     * @param order
     */
    public void payOrder(Order order) { 
        System.out.println("Please Enter Shipped Address: ");
        Scanner scanner = new Scanner(System.in);
        String address =  scanner.nextLine();
        order.setShippedAdress(address);
        System.out.println("Total Price: " + order.getTotalPrice());
        System.out.println("1- Pay upon delivery");
        System.out.println("2- Pay with Smart Wallet");
        System.out.println("3- Pay using voucher");
        System.out.println("3- Pay using loyalty points");
        
        System.out.println("0- Back");
        System.out.println("Enter your choice: ");
        
        int choice =  Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
            if (order.getTotalPrice() > 2000) {
                System.out.println("The Order is more than 2000 EGP, We Can't Deliver it");
                System.out.println("Please choose another payment method");
                payOrder(order);
            } 
            
            
                System.out.println("There is 20 EGP delivery fees");
                System.out.println("Total Price + 20 EGP: " + (order.getTotalPrice()+20));
                
                System.out.println("1- Confirm");
                System.out.println("0- Back");
                System.out.println("Enter your choice: ");
                choice =  Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                    System.out.println("Please Enter Your Phone Number: ");
                    String phoneNumber =  scanner.nextLine();
                    
                    
                    CashOnDeliveryPayment cashOnDeliveryPayment = new CashOnDeliveryPayment();
                    while(!cashOnDeliveryPayment.validatePhoneNumber(phoneNumber)){
                        System.out.println("Invalid Phone Number!");
                        System.out.println("Please Enter Your Phone Number: ");
                        phoneNumber =  scanner.nextLine();
                    }
                    order.setTotalPrice(order.getTotalPrice()+20);
                    if(order.payOrder(cashOnDeliveryPayment)){
                        
                        order.setStatus(OrderStatus.PROCESSING);
                        System.out.println("Order placed successfully!");
                        cashOnDeliveryPayment.setPaymentMethod("CashOnDelivery");  
                        orderHistory.createOrder(order);
                        order.updateProductsQuantity();
                        int points = 0 ;
                        for (ICartItem cartItem : shoppingCart.getItems()) {
                            points += cartItem.getProduct().getLoyaltyPoints();
                        }
                        authentication.getUser().setLoyaltyPoints(authentication.getUser().getLoyaltyPoints()+points);
                        shoppingCart.clearCart();
                        Menu();
                    }else{
                        System.out.println("Order failed!");
                        Menu();
                    }
                        
                        
                        break;
                        case 0:
                        payOrder(order);
                        break;
                        default:
                        System.out.println("Invalid choice!");
                        break;
                }
                break;
            case 0:
                viewShoppingCart();
                break;
                default:
                System.out.println("Invalid choice!");
                break;
            }
        }
        
        public void viewOrderHistory() {
            ArrayList<Order> orders = orderHistory.viewOrderHistory(authentication.getUser().getID());
            if (orders.isEmpty()) {
                System.out.println("You have no orders!");
                Menu();
            }
            System.out.println("Please Choose Order:");
            for (int i = 0; i < orders.size(); i++) {
                System.out.println(i+1 + " - Order ID: " + orders.get(i).getOrderId() + "   Total Price: " + orders.get(i).getTotalPrice() + "   Status: " + orders.get(i).getStatus()) ;
            }
            System.out.println("--------------------------------");
            System.out.println("1- View Order Details");
            System.out.println("0- Back");
            System.out.println("Enter your choice: ");
            Scanner scanner = new Scanner(System.in);
            int choice =  Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Enter Order Number: ");
                    int orderNumber =  Integer.parseInt(scanner.nextLine());
                    viewOrderDetails(orders.get(orderNumber-1));
                    break;
                case 0:
                    Menu();
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }

        
        /** 
         * @param order
         */
        public void viewOrderDetails(Order order){
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Order Date: " + order.getPayment().getDate());
            System.out.println("Order Status: " + order.getStatus());
            System.out.println("Order Total Price: " + order.getTotalPrice());
            System.out.println("Order Shipped Address: " + order.getShippedAddress());
            System.out.println("Order Payment Method: " + order.getPayment().getPaymentMethod());
            System.out.println("Order Items: ");
            for (int i = 0; i < order.getItems().size(); i++) {
                System.out.println(i+1 + " - Item Name: " + order.getItems().get(i).getProduct().getName() + "   Item Price: " + order.getItems().get(i).getUnitPrice() + "   Item Quantity: " + order.getItems().get(i).getQuantity()) ;
            }
            System.out.println("--------------------------------");
            System.out.println("1- Re-Order");
            System.out.println("0- Back");
            System.out.println("Enter your choice: ");
            Scanner scanner = new Scanner(System.in);
            int choice =  Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    ArrayList<ICartItem> itemsList = orderHistory.reOrder(order);
                    shoppingCart.setItems(itemsList);
                    System.out.println("Your Previous Order added to cart successfully!");
                    viewShoppingCart();
                    break;
                case 0:
                    viewOrderHistory();
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
        
    public void register() {
        while(true){
            System.out.println("Please Enter Your Name: ");
            Scanner scanner = new Scanner(System.in);
            String name =  scanner.nextLine();
            System.out.println("Please Enter Your Email: ");
            String email =  scanner.nextLine();
            System.out.println("Please Enter Your Password: ");
            String password =  scanner.nextLine();
            System.out.println("Please Enter Your Phone Number: ");
            String phoneNumber =  scanner.nextLine();
            System.out.println("Please Enter Your Address: ");
            String address =  scanner.nextLine();
            System.out.println("Please Enter Your Country: ");
            String country =  scanner.nextLine();
            if(authentication.validateRegister(email, password, phoneNumber)){
                System.out.println("We have sent you an OTP to your email: " + email+ ", Please check your email");
                int otpSent =  authentication.sendOTP(email);
 
                System.out.println("Please Enter The OTP: ");
                int otp =  Integer.parseInt(scanner.nextLine());
                while(!authentication.validateOTP(otp, otpSent)){
                    System.out.println("Invalid OTP!");
                    System.out.println("Please Enter The OTP: ");
                    otp = Integer.parseInt(scanner.nextLine());
                }
                LoggedInUser user = new LoggedInUser(name, email, password, phoneNumber, address, country);
                if(authentication.register(user)){
                    System.out.println("Registered Successfully!"); 
                }else{
                    System.out.println("Registration Failed! The user registered before");
                }
                break;   
            }else{
                System.out.println("Registration Failed!");
                System.out.println("Do you want to try again? (Y/N)");
                String choice =  scanner.nextLine();
                if(choice.equals("N")){
                    break;
                }
            }
            
        }
        
        Menu();
    }
    public void login() {
        while(true){
            System.out.println("Please Enter Your Email: ");
            Scanner scanner = new Scanner(System.in);
            String email =  scanner.nextLine();
            System.out.println("Please Enter Your Password: ");
            String password =  scanner.nextLine();
            if(authentication.validateLogin(email, password)){

                if(authentication.login(email, password)){
                    System.out.println("Logged in Successfully!"); 
                    break;
                }else{
                    System.out.println("You are not registered!");
                    break;
                }
                
            }else{
                System.out.println("Login Failed!");
                System.out.println("Do you want to try again? (Y/N)");
                String choice =  scanner.nextLine();
                if(choice.equals("N")){
                    break;
                }
            }
            
        }
        
        Menu();
    }
    public void logout() {
        authentication.logout();
        System.out.println("Logged out Successfully!");
        Menu();
    }
    public void viewProfile() {
        System.out.println("Your Profile:");
        System.out.println("ID: " + authentication.getUser().getID());
        System.out.println("Name: " + authentication.getUser().getName());
        System.out.println("Email: " + authentication.getUser().getEmail());
        System.out.println("Phone Number: " + authentication.getUser().getPhoneNumber());
        System.out.println("Address: " + authentication.getUser().getAddress());
        System.out.println("Country: " + authentication.getUser().getCountry());
        System.out.println("Loyalty Points : " + authentication.getUser().getLoyaltyPoints());
        System.out.println("Status: " + authentication.getUser().getStatus());
        System.out.println("--------------------------------");
        System.out.println("0- Back");
        System.out.println("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice =  Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 0:
                Menu();
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }
    }
}
