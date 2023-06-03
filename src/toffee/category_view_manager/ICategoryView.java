package toffee.category_view_manager;
import java.util.ArrayList;

public interface ICategoryView {
    ArrayList<Category> viewCategories();
    ArrayList<Product> viewCategoryItems(int categoryID);
    void addCategory(int id,String name);
}
