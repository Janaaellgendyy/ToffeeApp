package toffee.category_view_manager;
public class Product {
    private int id;
    private String name;
    private String brand;
    private double price;
    private String image;
    private String description;
    private int avaliablequantity;
    private int loyaltyPoints;
    private int categoryID;

    
    /** 
     *  set id
     * 
     * @param id the id of the product
     */
    public void setId(int id){
        this.id = id;
    }
    
    /** 
     *  get id
     * 
     * @return int  the id of the product
     */
    public int getId(){
        return id;
    }
    
    /** 
     *  set name
     * @param name the name of the product
     */
    public void setName(String name){
        this.name = name;
    }
    
    /** 
     * get name
     * @return String   the name of the product
     */
    public String getName(){
        return name;
    }
    
    /** 
     * set price
     * @param price the price of the product
     */
    public void setPrice(int price){
        this.price = price;
    }
    
    /** 
     * get price
     * @return double  the price of the product
     */
    public double getPrice(){
        return price;
    }
    
    /** 
     * set brand
     * @param brand the brand of the product
     */
    public void setBrand(String brand){
        this.brand = brand;
    }
    
    /** 
     * get brand
     * @return String  the brand of the product
     */
    public String getBrand(){
        return brand;
    }
    
    /** 
     * set image
     * @param image the image of the product
     */
    public void setImage(String image){
        this.image = image;
    }
    
    /** 
     * get image
     * @return String the image of the product
     */
    public String getImage(){
        return image;
    }
    
    /** 
     * set description
     * @param description the description of the product
     */
    public void setDescription(String description){
        this.description = description;
    }
    
    /** 
     * get description
     * @return String the description of the product
     */
    public String getDescription(){
        return description;
    }
    
    /** 
     * set avaliable quantity
     * @param avaliablequantity the avaliable quantity of the product
     */
    public void setAvailablieQuantity(int avaliablequantity){
        this.avaliablequantity = avaliablequantity;
    }
    
    /** 
     * get avaliable quantity
     * @return int the avaliable quantity of the product
     */
    public int getAvailablieQuantity(){
        return avaliablequantity;
    }
    
    /** 
     * set loyalty points
     * @param loyaltyPoints the loyalty points of the product
     */
    public void setLoyaltyPoints(int loyaltyPoints){
        this.loyaltyPoints= loyaltyPoints;
    }
    
    /** 
     * get loyalty points
     * @return int the loyalty points of the product
     */
    public int getLoyaltyPoints(){
        return loyaltyPoints;
    }
    
    /** 
     * set category id
     * @param categoryID    the category id of the product
     */
    public void setCategoryID(int categoryID){
        this.categoryID = categoryID;
    }
    
    /** 
     * get category id
     * @return int the category id of the product
     */
    public int getCategoryID(){
        return categoryID;
    }
    
    public Product(int id, String name, String brand, double price, String image, String desc, int avaliablequantity, int category_id, int loyaltyPoints){
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.image = image;
        this.description = desc;
        this.avaliablequantity = avaliablequantity;
        this.categoryID = category_id;
        this.loyaltyPoints = loyaltyPoints;
    }

    
    
}
