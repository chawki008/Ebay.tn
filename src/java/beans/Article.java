/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author chawki
 */
public class Article implements Serializable{
    
    private int id ;
    private String name;
    private Integer price;
    private Integer quantity;
    private String Condition;
    private boolean shipping ;
    private String brand;
    private String description ; 
    private User owner;
    private Category category;
    private boolean auc;
    private String img_extension;
    private boolean sent ;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the price
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the Condition
     */
    public String getCondition() {
        return Condition;
    }

    /**
     * @param Condition the Condition to set
     */
    public void setCondition(String Condition) {
        this.Condition = Condition;
    }

    /**
     * @return the shipping
     */
    public boolean isShipping() {
        return shipping;
    }

    /**
     * @param shipping the shipping to set
     */
    public void setShipping(boolean shipping) {
        this.shipping = shipping;
    }

    /**
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the categories
     */
    

    /**
     * @return the owner
     */
    public User getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * @return the auc
     */
    public boolean isAuc() {
        return auc;
    }

    /**
     * @param auc the auc to set
     */
    public void setAuc(boolean auc) {
        this.auc = auc;
    }

    /**
     * @return the img_extension
     */
    public String getImg_extension() {
        return img_extension;
    }

    /**
     * @param img_extension the img_extension to set
     */
    public void setImg_extension(String img_extension) {
        this.img_extension = img_extension;
    }

    /**
     * @return the sent
     */
    public boolean isSent() {
        return sent;
    }

    /**
     * @param sent the sent to set
     */
    public void setSent(boolean sent) {
        this.sent = sent;
    }
    
    
    
    
}
