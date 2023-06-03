package toffee.category_view_manager;
import java.io.IOException;
import java.util.ArrayList;

import toffee.presistence_manager.CategoryDBPersistence;
import toffee.presistence_manager.ProductDBPresistence;

public class CategoryView implements ICategoryView{
    ArrayList<Category> categories;

    
    /** 
     *  This method returns all the categories
     * 
     * @return ArrayList<Category>  This returns all the categories
     */
    @Override
    public ArrayList<Category> viewCategories() {
        CategoryDBPersistence categoryDBPersistence = new CategoryDBPersistence();
        try {
            categories = categoryDBPersistence.getAllCategories();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categories;
    }

    
    /** 
     *  This method returns all the products in a category
     * 
     * @param categoryID  This is the category ID
     * @return ArrayList<Product> This returns all the products in a category
     */
    @Override
    public ArrayList<Product> viewCategoryItems(int categoryID) {
       ProductDBPresistence productDBPresistence = new ProductDBPresistence();
       ArrayList<Product> products = new ArrayList<>();
    
        try {
            products = productDBPresistence.getCategoryProducts(categoryID);
        } catch (IOException e) {
           
            e.printStackTrace();
        }
        return products;
    }

    
    /** 
     * This method adds a category
     * 
     * @param id    This is the category ID
     * @param name  This is the category name
     */
    @Override
    public void addCategory(int id, String name) {
        
        throw new UnsupportedOperationException("Unimplemented method 'AddCategory'");
    }

}

