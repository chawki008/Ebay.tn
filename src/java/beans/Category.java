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
public class Category implements Serializable{
    
    private int id ;
    private String name;
    private Category parent;
    private List <Category> children ;

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
     * @return the parent
     */
    public Category getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Category parent) {
        this.parent = parent;
    }

    /**
     * @return the children
     */
    public List <Category> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(List <Category> children) {
        this.children = children;
    }
    
    
    
    
    
}
