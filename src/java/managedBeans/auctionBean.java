/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import beans.Article;
import beans.ArticleAuction;
import beans.Auction;
import beans.User;
import dao.ArticleDao;
import dao.AuctionDao;
import dao.DAOFactory;
import java.util.Map;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import services.SessionUtils;

/**
 *
 * @author chawki
 */
@ManagedBean(name = "auctionBean")
@Dependent
@SessionScoped
public class auctionBean {

    private Auction auction;
    private AuctionDao dao;
    
    /**
     * Creates a new instance of auctionBean
     */
    public auctionBean() {
        dao = DAOFactory.getInstance().getAuctionDao();
        auction = new Auction();
    }

    /**
     * @return the auction
     */
    public Auction getAuction() {
        return auction;
    }

    /**
     * @param auction the auction to set
     */
    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    /**
     * @return the dao
     */
    public AuctionDao getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(AuctionDao dao) {
        this.dao = dao;
    }

    /**
     *
     * @return
     */
    public String add_auction(){
        HttpSession ses = SessionUtils.getSession();
        if (ses == null || ses.getAttribute("user") == null){
            return "login";
        }
        User buyer = SessionUtils.getUser();
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = 
        fc.getExternalContext().getRequestParameterMap();
        String article_name =  params.get("article"); 
        ArticleDao art_dao = DAOFactory.getInstance().getArticleDao();
        ArticleAuction article = art_dao.find_auc(article_name);
        auction.setArticle(article);
        auction.setBuyer(buyer);
        dao.create(auction);
        if(article.getFinal_auction()< auction.getAmount()){
            article.setFinal_auction(auction.getAmount());
            art_dao.save(article);
        }
        return "show_article_auc" ;
    }
}
