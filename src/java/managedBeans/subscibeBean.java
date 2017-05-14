/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import beans.User;
import dao.DAOFactory;
import dao.UserDaoImpl;
import java.sql.Timestamp;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import services.SessionUtils;

/**
 *
 * @author chawki
 */
@ManagedBean(name="subscribeBean")
@Dependent
public class subscibeBean {

    
    private User user ;
    private UserDaoImpl userdao ; 
    
    /**
     * Creates a new instance of UserBean
     */
    public subscibeBean() {
        user = new User();
        this.userdao = (UserDaoImpl) DAOFactory.getInstance().getUserDao();
        
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
    
    public String subscribe(){
    
    userdao.creer(user);
    HttpSession session = SessionUtils.getSession();
    session.setAttribute("user", getUser());
            
    return "index";
    }
}
