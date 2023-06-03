package toffee.order_manager;
import java.io.IOException;
import java.util.ArrayList;

import toffee.category_view_manager.Product;
import toffee.presistence_manager.ProductDBPresistence;
import toffee.shopping_cart_manager.ICartItem;

public class Order {
    private int orderId;
    private int userId;
    private String shippedAddress;
    private double totalPrice;
    private IPayment payment;
    private OrderStatus status;
    
    private ArrayList<ICartItem> items;

    public Order(int orderId,int userId ,double totalPrice, ArrayList<ICartItem> items) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.items = items;
        this.userId = userId;
    }

    public Order(int orderId,int userId ,String shippedAddress , double totalPrice, int transactionId, String paymentMethod, OrderStatus status, String date, ArrayList<ICartItem> items) {
        this.orderId = orderId;
        this.userId = userId;
        this.shippedAddress = shippedAddress;
        this.totalPrice = totalPrice;
        
        if(paymentMethod.equals("CashOnDelivery")){
            this.payment = new CashOnDeliveryPayment(totalPrice, paymentMethod, date, transactionId);

        }

        
        
        this.items = items;
        
        this.status = status;
    }

    
    /** 
     * pay order using payment method
     * @param payment payment method
     * @return boolean true if payment is done successfully
     */
    public boolean payOrder(IPayment payment){
        this.payment = payment;
        this.payment.processPayment();
        // save order in database
        return true;
    }

    
    /**
     * set order id 
     * @param orderId order id
     */
    public void setorderId(int orderId) {
        this.orderId = orderId;
    }

    
    /** 
     * get order id
     * @return int order id
     */
    public int getOrderId() {
        return this.orderId;
    }

    
    /** 
     * set shipped address
     * @param shippedAddress shipped address
     */
    public void setShippedAdress(String shippedAddress) {
        this.shippedAddress = shippedAddress;
    }

    
    /** 
     * get shipped address
     * @return String shipped address
     */
    public String getShippedAddress() {
        return this.shippedAddress;
    }

    
    /** 
     * set payment method
     * @param payment payment method
     */
    public void setPayment(IPayment payment) {
        this.payment = payment;
    }

    
    /** 
     * get payment method
     * @return IPayment payment method
     */
    public IPayment getPayment() {
        return this.payment;
    }

    
    /** 
     * set total price
     * @param totalPrice total price
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    
    /** 
     * get total price
     * @return double total price
     */
    public double getTotalPrice() {
        return this.totalPrice;
    }

    
    /** 
     * set order status
     * @param status order status
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    
    /** 
     * get order status
     * @return OrderStatus order status
     */
    public OrderStatus getStatus() {
        return this.status;
    }

    
    /** 
     * set user id
     * @param userId user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    
    /** 
     * get user id
     * @return int user id
     */
    public int getUserId() {
        return this.userId;
    }

    
    /** 
     * set items
     * @param items items
     */
    public void setItems(ArrayList<ICartItem> items) {
        this.items = items;
    }

    
    /** 
     * get items
     * @return ArrayList<ICartItem> items
     */
    public ArrayList<ICartItem> getItems() {
        return this.items;
    }

    public void updateProductsQuantity() {
        ProductDBPresistence productDBPresistence = new ProductDBPresistence();
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            products = productDBPresistence.readProductDataFromFile();
        } catch (IOException e) {
           
            e.printStackTrace();
        }
        for (ICartItem cartItem : items) {
            for (Product product : products) {
                if (cartItem.getProduct().getId() == product.getId()) {
                    product.setAvailablieQuantity(product.getAvailablieQuantity() - cartItem.getQuantity());
                }
            }
        }
        try {
            productDBPresistence.writeProductDataToFile(products);
        } catch (IOException e) {
           
            e.printStackTrace();
        }

    }
    

}
