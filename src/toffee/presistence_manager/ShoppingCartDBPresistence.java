package toffee.presistence_manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import toffee.category_view_manager.Product;
import toffee.shopping_cart_manager.CartItem;
import toffee.shopping_cart_manager.ICartItem;

public class ShoppingCartDBPresistence {
    private String dataFilePath;

    public ShoppingCartDBPresistence() {
        this.dataFilePath = "src\\data\\shoppingcart.txt";
    }

    
    /** 
     * get all products from shopping cart
     * @return ArrayList<ICartItem> list of products
     * @throws IOException if file not found
     */
    public ArrayList<ICartItem> getShoppingCartProducts() throws IOException {
        ArrayList<ICartItem> cartItems = readProductDataFromFile();

        return cartItems;
    }


    
    /** 
     * read product data from file
     * @return ArrayList<ICartItem> list of products
     * @throws IOException if file not found
     */
    private ArrayList<ICartItem> readProductDataFromFile() throws IOException {
        ArrayList<ICartItem> cartItemList = new ArrayList<>();
        ProductDBPresistence productDBPresistence = new ProductDBPresistence();

       BufferedReader reader = new BufferedReader(new FileReader(dataFilePath));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                int id = Integer.parseInt(fields[0]);
                int quantity = Integer.parseInt(fields[1]);
                Product product = productDBPresistence.getProduct(id);
                CartItem cartItem = new CartItem(product, quantity);
                cartItemList.add(cartItem);
            }
        

        return cartItemList;
    }



    
    /** 
     * add product to cart
     * @param cartItem cart item
     * @throws IOException if file not found
     */
    public void addProductToCart(ICartItem cartItem) throws IOException {
        ArrayList<ICartItem> cartItemList = readProductDataFromFile();
        cartItemList.add(cartItem);
        writeProductDataToFile(cartItemList);
    }



    
    /** 
     * remove product from cart
     * @param productId product id
     * @throws IOException if file not found
     */
    public void removeProductFromCart(int productId) throws IOException {
        ArrayList<ICartItem> cartItemList = readProductDataFromFile();
        for (int i = 0; i < cartItemList.size(); i++) {
            if (cartItemList.get(i).getProduct().getId() == productId) {
                cartItemList.remove(i);
                break;
            }
        }
        writeProductDataToFile(cartItemList);
    }

    
    /** 
     * update product quantity
     * @param cartItem cart item
     * @throws IOException if file not found
     */
    public void updateProductQuantity(ICartItem cartItem) throws IOException {
        ArrayList<ICartItem> cartItemList = readProductDataFromFile();
        for (int i = 0; i < cartItemList.size(); i++) {
            if (cartItemList.get(i).getProduct().getId() == cartItem.getProduct().getId()) {
                cartItemList.get(i).setQuantity(cartItem.getQuantity());
                break;
            }
        }
        writeProductDataToFile(cartItemList);
    }

    
    /** 
     * clear cart
     * @throws IOException if file not found
     */
    public void clearCart() throws IOException {
        ArrayList<ICartItem> cartItemList = new ArrayList<>();
        writeProductDataToFile(cartItemList);
    }

    
    /** 
     * write product data to file
     * @param cartItemList list of products
     * @throws IOException if file not found
     */
    public void writeProductDataToFile(ArrayList<ICartItem> cartItemList) throws IOException {
        String data = "";
        for (ICartItem cartItem : cartItemList) {
            data += cartItem.getProduct().getId() + "," + cartItem.getQuantity() + "\n";
        }
        FileWriter myWriter = new FileWriter(dataFilePath);
        myWriter.write(data);
        myWriter.close();
    }

    
}
