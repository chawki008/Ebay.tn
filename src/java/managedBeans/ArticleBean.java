/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import beans.Article;
import beans.ArticleAuction;
import beans.Category;
import beans.User;
import dao.ArticleDao;
import dao.DAOFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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
import javax.servlet.http.Part;
import services.SessionUtils;

/**
 *
 * @author chawki
 */
@ManagedBean(name = "articleBean")
@SessionScoped
@Dependent
public final class ArticleBean {
    
    private ArticleAuction article_auc;
    private Article article;
    private ArticleDao dao;
    private Part image ;
    private List <String> art_images;
    private List <String> art_auc_images;
    private List<Article> articles;
    private List<Article> articles_imm;
    private List<ArticleAuction> articles_auc;
    /**
     * Creates a new instance of ArticleBean
     */
    public ArticleBean() {
        
        this.dao = DAOFactory.getInstance().getArticleDao();
        article = new Article();
        article_auc = new ArticleAuction();
        Category cat = new Category();
        cat.setId(1);
        article.setCategory(cat);
        article_auc.setCategory(cat);
        articles = dao.findAll();
        articles_auc = new ArrayList<>();
        articles_imm = new ArrayList<>();
        get_all_auc();
        
    }
    
    public void get_images(){
        setArt_images(new ArrayList<>());
        setArt_auc_images(new ArrayList<>());
      int i = 0 ;
      for(i=0;i<10;i++){
          if (getArticles().get(i).isAuc())
              getArt_images().add(getArticles().get(i).getImg_extension());
          else
              getArt_auc_images().add(getArticles().get(i).getImg_extension());
          
      }
    
    }
    
    public void get_all_auc() {
        for (Article article:getArticles()){
            if (article.isAuc())
                getArticles_auc().add(getDao().find_auc(article.getId()));
            else
                getArticles_imm().add(article);
         }
    
    }
    public void set_cat(ValueChangeEvent e){
        
       Category cat = DAOFactory.getInstance().getCategoryDao().find(e.getNewValue().toString());
       getArticle().setCategory(cat);
        getArticle_auc().setCategory(cat);
    
    }
    public void doUpload(int t){
        try{
            InputStream in = getImage().getInputStream();
            File f = new File("/home/chawki/NetBeansProjects/Online_shopping/web/resources/img_upload/"+getImage().getSubmittedFileName());
            if(t==0)
            getArticle().setImg_extension("resources/img_upload/"+getImage().getSubmittedFileName());
            else
                getArticle_auc().setImg_extension("resources/img_upload/"+getImage().getSubmittedFileName());
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);
            byte[] buffer = new byte[1024];
            int length;
            while((length=in.read(buffer))>0){
                out.write(buffer, 0, length);
            }
            out.close();
            in.close();
         
        }catch(Exception e){
        
        }
    }
    
    public String addArticle(){
        User owner = SessionUtils.getUser();
        getArticle().setOwner(owner);
        doUpload(0);
        getDao().create(getArticle());
        return "index";
    }

    /**
     * @return the article
     */
    public Article getArticle() {
        return article;
    }

    /**
     * @param article the article to set
     */
    public void setArticle(Article article) {
        this.article = article;
    }

    /**
     * @return the dao
     */
    public ArticleDao getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(ArticleDao dao) {
        this.dao = dao;
    }
    public String get_article(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = 
         fc.getExternalContext().getRequestParameterMap();
      String name =  params.get("name"); 
      this.setArticle(getDao().find(name));
        return "show_article";
    }
    
    public String add_article_auc(){
        User owner = SessionUtils.getUser();
        getArticle_auc().setOwner(owner);
        doUpload(1);
        getArticle_auc().setSent(false);
        getDao().create_auc(getArticle_auc());
        return "index";
    }
    
    public String get_article_auc(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = 
         fc.getExternalContext().getRequestParameterMap();
      String name =  params.get("name"); 
      this.setArticle_auc( getDao().find_auc(name));
        return "show_article_auc";
    
   
    }

    /**
     * @return the article_auc
     */
    public ArticleAuction getArticle_auc() {
        return article_auc;
    }

    /**
     * @param article_auc the article_auc to set
     */
    public void setArticle_auc(ArticleAuction article_auc) {
        this.article_auc = article_auc;
    }
    
    public  List <Article> getArticleByCat(Category category){
        return  getDao().findByCategory(category);
    }
    public String show_article(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = 
         fc.getExternalContext().getRequestParameterMap();
        String name = params.get("art_name");
        setArticle(getDao().find(name));
        if (article.isAuc()){
            article_auc = dao.find_auc(article.getName());
            return "show_article_auc";
        }
            return "show_article";
    }
    public void update(){
            article_auc = dao.find_auc(article_auc.getName());
            
    }
    public String show_article_auc(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = 
         fc.getExternalContext().getRequestParameterMap();
        String name = params.get("name");
        setArticle_auc(getDao().find_auc(name));
        return "show_article_auc";
    }

    /**
     * @return the image
     */
    public Part getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(Part image) {
        this.image = image;
    }

    /**
     * @return the images
     */
    public List <String> getImages() {
        return getArt_images();
    }

    /**
     * @param images the images to set
     */
    public void setImages(List <String> images) {
        this.setArt_images(images);
    }

    /**
     * @return the articles
     */
    public List<Article> getArticles() {
        return articles;
    }

    /**
     * @param articles the articles to set
     */
    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    /**
     * @return the articles_auc
     */
    public List<ArticleAuction> getArticles_auc() {
        return articles_auc;
    }

    /**
     * @param articles_auc the articles_auc to set
     */
    public void setArticles_auc(List<ArticleAuction> articles_auc) {
        this.articles_auc = articles_auc;
    }

    /**
     * @return the art_images
     */
    public List <String> getArt_images() {
        return art_images;
    }

    /**
     * @param art_images the art_images to set
     */
    public void setArt_images(List <String> art_images) {
        this.art_images = art_images;
    }

    /**
     * @return the art_auc_images
     */
    public List <String> getArt_auc_images() {
        return art_auc_images;
    }

    /**
     * @param art_auc_images the art_auc_images to set
     */
    public void setArt_auc_images(List <String> art_auc_images) {
        this.art_auc_images = art_auc_images;
    }

    /**
     * @return the articles_imm
     */
    public List<Article> getArticles_imm() {
        return articles_imm;
    }

    /**
     * @param articles_imm the articles_imm to set
     */
    public void setArticles_imm(List<Article> articles_imm) {
        this.articles_imm = articles_imm;
    }
}
