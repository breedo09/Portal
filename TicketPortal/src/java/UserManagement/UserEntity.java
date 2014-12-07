package UserManagement;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Breed
 */
@Entity
@Table(name="USER_ENTITY")
@XmlRootElement
public class UserEntity implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USERID")
    private Integer id;
    @NotNull(message="Username cannot be null.")
    @Column(name="USERNAME")
    private String username;
    @NotNull(message="First name cannot be null.")
    @Column(name = "FIRST_NAME")
    private String firstName;
    @NotNull(message="Last name cannot be null.")
    @Column(name = "LAST_NAME")
    private String lastName;
    @NotNull(message="Password cannot be null.")
    @Column(name = "PASSWORD")
    private String password;
    @Column(name="AMOUNT_DUE")
    private double amountDue;
    @Column(name="PAYMENT_STATUS")
    private String paymentStatus;
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
        
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public double getAmountDue(){
        return this.amountDue;
    }

    public String getAmountDueAsString() {
        return String.valueOf(amountDue);
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserEntity)) {
            return false;
        }
        UserEntity other = (UserEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public int compareTo(Object t) {
        UserEntity other = (UserEntity) t;
        String thisName = this.firstName + this.lastName;
        String otherName = other.getFirstName() + other.getLastName();
        return thisName.compareTo(otherName);
    }
    
}
