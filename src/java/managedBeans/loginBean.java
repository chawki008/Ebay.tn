/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import beans.User;
import dao.DAOFactory;
import dao.UserDaoImpl;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import services.SessionUtils;

/**
 *
 * @author chawki
 */
@ManagedBean(name = "loginBean")
@SessionScoped
@Dependent

public class loginBean {
    
    private User user;
    private String email;
    private String password;
    private UserDaoImpl userdao;
    /**
     * Creates a new instance of loginBean
     */
    public loginBean() {
        this.userdao = (UserDaoImpl) DAOFactory.getInstance().getUserDao();
        
    }
    
    public String verify(){
         setUser(getUserdao().get(getEmail()));
        
        if(getUser() != null){
        String password = getUser().getPassword();
        if(password.equals(getPassword()))
        {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("user", getUser());
            return "index";
        }
        }
        return "subscription";
    }
    public String logout(){
    HttpSession session = SessionUtils.getSession();
    session.setAttribute("user",null);
    session.invalidate();
    return "index";
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the userdao
     */
    public UserDaoImpl getUserdao() {
        return userdao;
    }

    /**
     * @param userdao the userdao to set
     */
    public void setUserdao(UserDaoImpl userdao) {
        this.userdao = userdao;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
    public boolean isLoggedIn(){
         HttpSession ses = SessionUtils.getSession();
        if (ses.getAttribute("user") != null)
        return true;
        return false;
    
    }
    
}
