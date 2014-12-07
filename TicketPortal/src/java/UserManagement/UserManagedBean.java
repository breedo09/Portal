package UserManagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Breed
 */
@ManagedBean(name = "userManagement")
@SessionScoped
public class UserManagedBean implements Serializable {

    @EJB
    private UserEntityJpaController userService;

    private String username;
    private String searchUser;
    private Collection<UserEntity> searchUsersResults;
    private UserEntity selectedUser;
    private UserEntity updatedUser;
    private String amountDue = "";
    private String paymentStatus;

    public UserManagedBean() {
        selectedUser = new UserEntity();
    }

    public String getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(String input) {
        try {
            Double.parseDouble(input);
        } catch (Exception e) {
            input = "";
        }
        
        this.amountDue = input;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public UserEntity getUpdatedUser() {
        return selectedUser;
    }

    public void setUpdatedUser(UserEntity updatedUser) {
        this.updatedUser = updatedUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;

    }

    public UserEntity getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(UserEntity user) {
        System.out.println("setting selected user");
        updatedUser = user;
        this.selectedUser = user;
    }

    public Collection<UserEntity> getSearchUsersResults() {
        searchUsersResults = userService.findUserEntities();
        return searchUsersResults;
    }

    public void setSearchUsersResults(Collection<UserEntity> searchUsersResults) {
        this.searchUsersResults = searchUsersResults;
    }

    public String getSearchUser() {
        return searchUser;
    }

    public void setSearchUser(String searchUser) {
        this.searchUser = searchUser;
    }

    public String searchUser() {

        System.out.println("Searching Users for: " + searchUser);
        String searchUsername = (searchUser == null) ? "" : searchUser.toLowerCase().trim();
        if (searchUsername.isEmpty()) {
            this.searchUsersResults = userService.findUserEntities();
            return "user_management";
        }
        this.searchUsersResults = userService.findUserEntities();
        return "user_management";
    }

    public String updateUser() throws Exception {
        userService.edit(this.selectedUser);
        return "user_management";
    }

    public String deleteSelectedUser() throws Exception {
        System.out.println("Deleted: " + selectedUser);
        userService.destroy(selectedUser.getId());

        return searchUser();
    }

    public void onUserSelect(SelectEvent ev) {
        UserEntity current = (UserEntity) ev.getObject();
        this.setUpdatedUser(current);
        this.setSelectedUser(current);
    }

    public void onUserUnselect(UnselectEvent event) {
        System.out.println("unslecting.....");
    }

    public String updateSelectedUser() throws Exception {
        System.out.println("updating");
        if (amountDue != null && !amountDue.isEmpty() && selectedUser != null) {
            selectedUser.setAmountDue(Double.parseDouble(amountDue));
        }
        if (paymentStatus != null && !paymentStatus.isEmpty() && selectedUser != null) {
            selectedUser.setPaymentStatus(paymentStatus);
        }
        userService.edit(selectedUser);
        amountDue = "";
        paymentStatus = "";
        return searchUser();
    }
}
