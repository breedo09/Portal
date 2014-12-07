package UserManagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Breed
 */
@ManagedBean(name = "userEntry")
@SessionScoped
public class UserEntryBean implements Serializable {

    @EJB
    private UserEntityJpaController userService;
    private UserEntity creatableUser;
    private Collection<UserEntity> createList;

    public UserEntryBean() {

        creatableUser = new UserEntity();
        createList = new ArrayList<>();

    }

    public UserEntity getCreatableUser() {
        return creatableUser;
    }

    public void setCreatableUser(UserEntity creatableUser) {
        this.creatableUser = creatableUser;
    }

    public Collection<UserEntity> getCreateList() {
        return createList;
    }

    public void setCreateList(Collection<UserEntity> createList) {
        this.createList = createList;

    }


    public void addToCreateList() {

        createList.add(creatableUser);
        creatableUser = new UserEntity();

    }

    public String finishCreate() throws Exception {
        UserEntity user;
        Iterator<UserEntity> iterator = createList.iterator();
        while (iterator.hasNext()) {
            user = iterator.next();
            userService.create(user);
            int id = userService.findUserByUsername(user.getUsername()).getId();
            System.out.println("Created User: " + user.getUsername() + " ID: " + id);
            iterator.remove();
        }
        createList = new ArrayList<>();
        return "user_management";
    }
    
    
}
