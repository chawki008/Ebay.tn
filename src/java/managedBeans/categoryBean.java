/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import beans.Article;
import beans.ArticleAuction;
import beans.Category;
import beans.Transaction;
import beans.User;
import dao.ArticleDao;
import dao.CategoryDao;
import dao.DAOFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.Cookie;
import services.CookieHelper;

/**
 *
 * @author chawki
 */
@ManagedBean(name = "categoryBean")
@SessionScoped
@Dependent
public final class categoryBean {

    private Category category;
    private CategoryDao dao;
    private List <Category> categories;
    private List <Article> articles;
    private List <ArticleAuction> articles_auc;
    private List <Article> articles_imm;
    
    /**
     * Creates a new instance of categoryBean
     */
    public categoryBean() {
        dao = DAOFactory.getInstance().getCategoryDao();
        categories = dao.findAll();
       
        CookieHelper cookieHelper = new CookieHelper();
        Cookie cookie = cookieHelper.getCookie("last_category");
        if(cookie != null)
        category = dao.find(cookie.getValue());             
        else 
            category = categories.get(0);
        setArt_auc_imm();
        
        }
    
    private void setArt_auc_imm(){
        articles_auc = new ArrayList<>();
        articles_imm = new ArrayList<>();
        ArticleDao art_dao = DAOFactory.getInstance().getArticleDao();  
        articles=art_dao.findByCategory(getCategory());
        articles.forEach((article) -> {
            if(article.isAuc()){
                ArticleAuction art_auc = art_dao.find_auc(article.getId());
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if(!date.before(art_auc.getDate_limit())){
            User user = DAOFactory.getInstance().getUserDao().getBiggestBetter(art_auc.getFinal_auction(), art_auc.getId());
            Transaction t = new Transaction();
            t.setArticle(art_auc);
            t.setBuyer(user);
            t.setQuantity(art_auc.getQuantity());
            art_auc.setSent(true);
            DAOFactory.getInstance().getArticleDao().save(art_auc);
            DAOFactory.getInstance().getTransactionDao().create(t);
            }else 
            getArticles_auc().add(art_auc);
            
             }
            else
                getArticles_imm().add(article);
        });
    }
    public String get_category(ValueChangeEvent event){
    FacesContext fc = FacesContext.getCurrentInstance();
       String name = event.getNewValue().toString();
      this.setCategory(getDao().find(name));
        setArt_auc_imm();
       return "show_category";
     
    }
  /*  public String show_category(){
        FacesContext fc = FacesContext.getCurrentInstance();
      Map<String,String> params = 
         fc.getExternalContext().getRequestParameterMap();
        String name = params.get("name");
        category = dao.find(name);
        setArt_auc_imm();
        return "sh";
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
     * @return the dao
     */
    public CategoryDao getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(CategoryDao dao) {
        this.dao = dao;
    }

    /**
     * @return the categories
     */
    public List <Category> getCategories() {
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(List <Category> categories) {
        this.categories = categories;
    }
    
    public void update(String name){
        setCategory(dao.find(name));
        ArticleDao art_dao = DAOFactory.getInstance().getArticleDao();
        setArticles(art_dao.findByCategory(getCategory()));
        CookieHelper cookieHelper = new CookieHelper();
        cookieHelper.setCookie("last_category", name, 1000);
        this.setArt_auc_imm();
    }
    /**
     * @return the articles
     */
    public List <Article> getArticles() {
        return articles;
    }

    /**
     * @param articles the articles to set
     */
    public void setArticles(List <Article> articles) {
        this.articles = articles;
    }

    /**
     * @return the articles_auc
     */
    public List <ArticleAuction> getArticles_auc() {
        return articles_auc;
    }

    /**
     * @param articles_auc the articles_auc to set
     */
    public void setArticles_auc(List <ArticleAuction> articles_auc) {
        this.articles_auc = articles_auc;
    }

    /**
     * @return the articles_imm
     */
    public List <Article> getArticles_imm() {
        return articles_imm;
    }

    /**
     * @param articles_imm the articles_imm to set
     */
    public void setArticles_imm(List <Article> articles_imm) {
        this.articles_imm = articles_imm;
    }
    
}
