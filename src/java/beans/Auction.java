/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Date;

/**
 *
 * @author chawki
 */
public class Auction {
    private int id ;
    private int amount ; 
    private Date date;
    private User buyer;
    private ArticleAuction article;

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
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the buyer
     */
    public User getBuyer() {
        return buyer;
    }

    /**
     * @param buyer the buyer to set
     */
    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    
    

    /**
     * @param article the article to set
     */
    public void setArticle(ArticleAuction article) {
        this.article = article;
    }

    /**
     * @return the article
     */
    public ArticleAuction getArticle() {
        return article;
    }
    
}
