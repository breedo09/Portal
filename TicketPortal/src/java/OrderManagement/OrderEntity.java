package OrderManagement;

import UserManagement.UserEntity;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Breed
 */
@Entity
@Table(name = "ORDER_ENTITY")
@XmlRootElement
public class OrderEntity implements Serializable {

    @Id
    @NotNull(message = "ID cannot be null")
    @Column(name = "ID")
    private Integer id;

    @Column(name="USERNAME")
    private String username;

//    public UserEntity getUserID() {
//        return userID;
//    }
//
//    public void setUserID(UserEntity userID) {
//        this.userID = userID;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
    public OrderEntity() {

    }

    public OrderEntity(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer ID) {
        this.id = ID;
    }

    @Override
    public String toString() {
        return id + ": " + this.getUsername();
    }
}
