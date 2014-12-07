package UserManagement;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-12-06T14:22:18")
@StaticMetamodel(UserEntity.class)
public class UserEntity_ { 

    public static volatile SingularAttribute<UserEntity, Integer> id;
    public static volatile SingularAttribute<UserEntity, String> lastName;
    public static volatile SingularAttribute<UserEntity, String> username;
    public static volatile SingularAttribute<UserEntity, String> paymentStatus;
    public static volatile SingularAttribute<UserEntity, Double> amountDue;
    public static volatile SingularAttribute<UserEntity, String> firstName;
    public static volatile SingularAttribute<UserEntity, String> password;

}