package toffee.presistence_manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import toffee.category_view_manager.Product;
import toffee.order_manager.IPayment;
import toffee.order_manager.Order;
import toffee.order_manager.OrderStatus;
import toffee.shopping_cart_manager.CartItem;
import toffee.shopping_cart_manager.ICartItem;

public class OrderDBPresistence {
    private String dataFilePath;

    public OrderDBPresistence() {
        this.dataFilePath = "src\\data\\order.txt";
    }

    
    /** 
     * get all orders
     * @param userId user id
     * @return ArrayList<Order> order list
     * @throws IOException  IO exception
     */
    public ArrayList<Order> getOrdersHistory(int userId) throws IOException {
        ArrayList<Order> orderList = readOrderDataFromFile();
        ArrayList<Order> userOrderList = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getUserId() == userId) {
                userOrderList.add(order);
            }
        }
        return userOrderList;
    }


    
    /** 
     * read order data from file
     * @return ArrayList<Order> return order list
     * @throws IOException if file not found
     */
    private ArrayList<Order> readOrderDataFromFile() throws IOException {
        ArrayList<Order> orders = new ArrayList<>();
        ProductDBPresistence productDBPresistence = new ProductDBPresistence();
       
       BufferedReader reader = new BufferedReader(new FileReader(dataFilePath));
            String line;
            
            while ((line = reader.readLine()) != null) {
               
                ArrayList<ICartItem> cartItemList = new ArrayList<>();
                String[] fields = line.split(",");
                int id = Integer.parseInt(fields[0]);
                int userId = Integer.parseInt(fields[1]);
                String shippedAddress = fields[2];
                double totalPrice = Double.parseDouble(fields[3]);
                int transactionId = Integer.parseInt(fields[4]);
                String paymentMethod = fields[5];
                OrderStatus status = OrderStatus.valueOf(fields[6]);
                String date = fields[7];
                for (int i = 8; i < fields.length; i+=2) {
                    int productId = Integer.parseInt(fields[i]);
                    int quantity = Integer.parseInt(fields[i+1]);
                    Product product = productDBPresistence.getProduct(productId);
                    CartItem cartItem = new CartItem(product, quantity);
                    cartItemList.add(cartItem);
                }

             
           
                Order order = new Order(id, userId, shippedAddress, totalPrice, transactionId, paymentMethod, status, date, cartItemList);
               
                orders.add(order);
            }
        return orders;
    }

    
    /** 
     * add order
     * @param order order to add
     * @throws IOException if file not found
     */
    public void addOrder(Order order) throws IOException {
        ArrayList<Order> orderList = readOrderDataFromFile();
      
        orderList.add(order);
        writeOrderDataToFile(orderList);
    }


   
   /** 
    * write order data to file
    * @param orderList take order list to write
    */
   public void writeOrderDataToFile(ArrayList<Order> orderList){
    String data = "";
    for (Order order : orderList) {
        data += order.getOrderId() + "," + order.getUserId() + "," + order.getShippedAddress() + "," + order.getTotalPrice() + "," + order.getPayment().getTransactionId() + "," + order.getPayment().getPaymentMethod() + "," + order.getStatus() + "," + order.getPayment().getDate();
        for (ICartItem cartItem : order.getItems()) {
            data += "," + cartItem.getProduct().getId() + "," + cartItem.getQuantity();
        }
        data += "\n";
    }
    FileWriter myWriter;
    try {
        myWriter = new FileWriter(dataFilePath);
        myWriter.write(data);
        myWriter.close();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
   }


/** 
 * update order status
 * @param order order to update
 */
public void updateOrderStatus(Order order) {
    ArrayList<Order> orderList;
    try {
        orderList = readOrderDataFromFile();
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderId() == order.getOrderId()) {
                orderList.set(i, order);
            }
        }
        writeOrderDataToFile(orderList);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}


}
