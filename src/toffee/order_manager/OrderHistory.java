package toffee.order_manager;
import java.io.IOException;
import java.util.ArrayList;


import toffee.presistence_manager.OrderDBPresistence;

import toffee.shopping_cart_manager.ICartItem;

public class OrderHistory implements IOrderHistory {
    private ArrayList<Order> orderHistory = new ArrayList<Order>();
    
    /** 
     *  create order and add it to order history
     * @param order  order to be created
     */
    @Override
    public void createOrder(Order order) {
        orderHistory.add(order);
        OrderDBPresistence orderDBPresistence = new OrderDBPresistence();
        try {
            orderDBPresistence.addOrder(order);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            new java.util.Timer().schedule( 
        new java.util.TimerTask() {
            @Override
            public void run() {
                order.setStatus(OrderStatus.DELIVERED);
                orderDBPresistence.updateOrderStatus(order);
            }
        }, 
        20000 
        );
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }

  

    
    /** 
     * reOrder items from order
     * @param order order to be reordered
     * @return ArrayList<ICartItem> items to be reordered
     */
    @Override
    public ArrayList<ICartItem> reOrder(Order order) {
        ArrayList<ICartItem> items = order.getItems();
        int outOfStock = 0;
        for (ICartItem iCartItem : items) {
            if (iCartItem.getProduct().getAvailablieQuantity() < iCartItem.getQuantity()) {
               if (iCartItem.getProduct().getAvailablieQuantity() > 0) {
                   iCartItem.setQuantity(iCartItem.getProduct().getAvailablieQuantity());
                
               }else{
                    items.remove(iCartItem);
                    outOfStock++;
               }
            } 
        }
        if (outOfStock > 0) {
            System.out.println("Sorry, "+ outOfStock + " item(s) are out of stock");
        
        }
        return items;
    }

    
    /** 
     * view order history for specific user
     * @param userID user id to view his order history
     * @return ArrayList<Order> order history for specific user
     */
    @Override
    public ArrayList<Order> viewOrderHistory(int userID) {
        OrderDBPresistence orderDBPresistence = new OrderDBPresistence();
        try {
            orderHistory = orderDBPresistence.getOrdersHistory(userID);
        } catch (IOException e) {
            e.printStackTrace();
        }
       return orderHistory;
    }

    
}
