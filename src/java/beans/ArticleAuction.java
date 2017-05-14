/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Date;

/**
 *
 * @author chawki
 */
public class ArticleAuction extends Article {
    
    private Date date_limit;
    private int final_auction;

    /**
     * @return the date_limit
     */
    public Date getDate_limit() {
        return date_limit;
    }

    /**
     * @param date_limit the date_limit to set
     */
    public void setDate_limit(Date date_limit) {
        this.date_limit = date_limit;
    }

    /**
     * @return the final_auction
     */
    public int getFinal_auction() {
        return final_auction;
    }

    /**
     * @param final_auction the final_auction to set
     */
    public void setFinal_auction(int final_auction) {
        this.final_auction = final_auction;
    }
    
    
}
