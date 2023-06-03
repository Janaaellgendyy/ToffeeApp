package toffee.presistence_manager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import toffee.category_view_manager.Product;

public class ProductDBPresistence {
    private String dataFilePath;

    public ProductDBPresistence() {
        this.dataFilePath = "src\\data\\products.txt";
    }

    
    /** 
     * get all products from file for a category
     * @param categoryID takes category id
     * @return ArrayList<Product> list of products
     * @throws IOException if file not found
     */
    public ArrayList<Product> getCategoryProducts(int categoryID) throws IOException {
        ArrayList<Product> productList = readProductDataFromFile();
        ArrayList<Product> categoryProducts = new ArrayList<>();

        for (Product product : productList) {
            if (product.getCategoryID() == categoryID) {
                categoryProducts.add(product);
            }
        }

        return categoryProducts;
    }

    
    /**
     * get product by id 
     * @param productId takes product id
     * @return Product product
     * @throws IOException if file not found
     */
    public Product getProduct(int productId) throws IOException {
        ArrayList<Product> productList = readProductDataFromFile();

        for (Product product : productList) {
            if (product.getId() == productId) {
                return product;
            }
        }

        return null;
    }

    
    /** 
     * read product data from file
     * @return ArrayList<Product> list of products
     * @throws IOException if file not found
     */
    public ArrayList<Product> readProductDataFromFile() throws IOException {
        ArrayList<Product> productList = new ArrayList<>();

       BufferedReader reader = new BufferedReader(new FileReader(dataFilePath));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                int id = Integer.parseInt(fields[0]);
                String name = fields[1];
                double price = Double.parseDouble(fields[2]);
                int categoryId = Integer.parseInt(fields[3]);
                String brand = fields[4];
                String image = fields[5];
                String description = fields[6];
                int avaliablequantity = Integer.parseInt(fields[7]);
                int loyaltyPoints = Integer.parseInt(fields[8]);
                Product product = new Product(id, name, brand, price, image, description, avaliablequantity, categoryId, loyaltyPoints);
                productList.add(product);
            }
        

        return productList;
    }

    // public void updateProduct(Product product) throws IOException {
    //     ArrayList<Product> productList = readProductDataFromFile();

    //     for (Product productItem : productList) {
    //         if (productItem.getId() == product.getId()) {
    //             productItem.setName(product.getName());
    //             productItem.setPrice(product.getPrice());
    //             productItem.setBrand(product.getBrand());
    //             productItem.setImage(product.getImage());
    //             productItem.setDescription(product.getDescription());
    //             productItem.setAvaliablequantity(product.getAvaliablequantity());
    //             productItem.setLoyaltyPoints(product.getLoyaltyPoints());
    //             break;
    //         }
    //     }

    //     writeProductDataToFile(productList);
    // }

    // public void addProduct(Product product) throws IOException {
    //     ArrayList<Product> productList = readProductDataFromFile();
    //     productList.add(product);
    //     writeProductDataToFile(productList);
    // }

    // public void deleteProduct(int productId) throws IOException {
    //     ArrayList<Product> productList = readProductDataFromFile();

    //     for (Product productItem : productList) {
    //         if (productItem.getId() == productId) {
    //             productList.remove(productItem);
    //             break;
    //         }
    //     }

    //     writeProductDataToFile(productList);
    // }

    public void writeProductDataToFile(ArrayList<Product> productList) throws IOException {
        FileWriter writer = new FileWriter(dataFilePath);
        for (Product product : productList) {
            writer.write(product.getId() + "," + product.getName() + "," + product.getPrice() + "," + product.getCategoryID() + "," + product.getBrand() + "," + product.getImage() + "," + product.getDescription() + "," + product.getAvailablieQuantity() + "," + product.getLoyaltyPoints() + "\n");
        }
        writer.close();
    }
}