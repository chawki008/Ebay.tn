/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import beans.Article;
import beans.ArticleAuction;
import beans.Transaction;
import beans.User;
import dao.DAOFactory;
import dao.TransactionDao;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.jms.Session;
import javax.servlet.http.HttpSession;
import services.SessionUtils;

/**
 *
 * @author chawki
 */
@ManagedBean(name = "transactionBean")
@SessionScoped
@Dependent
public class transactionBean {

    private Transaction transaction ;
    private List<Transaction> transactions; 
    private TransactionDao dao ;
    /**
     * Creates a new instance of transactionBean
     */
    public transactionBean() {
        dao = DAOFactory.getInstance().getTransactionDao();
        transaction = new Transaction();
        HttpSession ses = SessionUtils.getSession();
        if (ses != null && ses.getAttribute("user") != null)
            this.get_transacs();
        
    }

    /**
     * @return the transaction
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * @param transaction the transaction to set
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    

    /**
     * @return the dao
     */
    public TransactionDao getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(TransactionDao dao) {
        this.dao = dao;
    }
    
    public String add_trans(){
        HttpSession ses = SessionUtils.getSession();
        if (ses == null || ses.getAttribute("user") == null){
            return "login";
        }
        User buyer = SessionUtils.getUser();
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = 
        fc.getExternalContext().getRequestParameterMap();
        String article_name =  params.get("article"); 
        Article article = DAOFactory.getInstance().getArticleDao().find(article_name);
        transaction.setArticle(article);
        transaction.setBuyer(buyer);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if(transaction.getQuantity() <= article.getQuantity()){
            article.setQuantity(article.getQuantity()-transaction.getQuantity());
            getDao().create(transaction);}
        else {
            facesContext.addMessage("form:q", new FacesMessage(" Quantity unavailable"));   
        return null;
            }
        
        if(article.getQuantity() == 0)
            article.setSent(true);
        DAOFactory.getInstance().getArticleDao().save_art(article);
        
        return "index" ;
    }
    
    public String get_trans(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = 
        fc.getExternalContext().getRequestParameterMap();
        String name =  params.get("name"); 
        int id = Integer.parseInt(name);
        setTransaction(getDao().find(id));
       
        return "index";
    }

    /**
     * @return the transactions
     */
    public List<Transaction> getTransactions() {
        transactions = dao.findAll(SessionUtils.getUser().getId());
        return transactions;
    }
    public String  get_transacs(){
        this.getTransactions();
        return "show_transactions";
    }
    /**
     * @param transactions the transactions to set
     */
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    
    
    
}
