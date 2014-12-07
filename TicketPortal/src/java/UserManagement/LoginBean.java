package UserManagement;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Breed
 */
@ManagedBean(name = "LoginBean")
@SessionScoped
public class LoginBean implements Serializable{

    @EJB
    private UserEntityJpaController userService;
    private String username;
    private String password;

    public LoginBean() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        
        
        if ("Brian".equalsIgnoreCase(getUsername()) && "opp".equals(getPassword())) {
            return "Dashboard";
        }
        if ("user".equalsIgnoreCase(getUsername()) && "test".equals(getPassword())) {
            return "user_management";
        }
        if ("ticket".equalsIgnoreCase(getUsername()) && "test".equals(getPassword())) {
            return "ticket_management";
        }
        if (userService.userPermitted(username, password)) {
            return "Client_Dashboard";
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("breed", new FacesMessage("Invalid UserName or Password"));
            return "index";
        }
    }
}
