package toffee.category_view_manager;


public class Category {
    private int id;
    private String name;
   

    public Category(int id, String name){
        this.id = id;
        this.name = name;
    }


    
    /** 
     * set  id
     * 
     * @param id  the id of the category
     */
    public void setId(int id){
        this.id = id;
    }
    
    /** 
     *  get id
     * 
     * @return int the id of the category
     */
    public int getId(){
        return id;
    }
    
    /** 
     * set the category name
     * 
     * @param name the name of the category
     */
    public void setName(String name){
        this.name = name;
    }
    
    /** 
     * get the category name
     * 
     * @return String the name of the category
     */
    public String getName(){
        return name;
    }
    
    
}
