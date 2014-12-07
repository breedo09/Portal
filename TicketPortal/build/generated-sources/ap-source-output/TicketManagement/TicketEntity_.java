package TicketManagement;

import OrderManagement.OrderEntity;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-12-06T14:22:18")
@StaticMetamodel(TicketEntity.class)
public class TicketEntity_ { 

    public static volatile SingularAttribute<TicketEntity, Date> eventDate;
    public static volatile SingularAttribute<TicketEntity, Float> newPrice;
    public static volatile SingularAttribute<TicketEntity, Boolean> priceChanged;
    public static volatile SingularAttribute<TicketEntity, Float> currentPrice;
    public static volatile SingularAttribute<TicketEntity, String> eventRow;
    public static volatile SingularAttribute<TicketEntity, Float> cost;
    public static volatile SingularAttribute<TicketEntity, String> section;
    public static volatile SingularAttribute<TicketEntity, Integer> id;
    public static volatile SingularAttribute<TicketEntity, Boolean> approved;
    public static volatile SingularAttribute<TicketEntity, Boolean> paid;
    public static volatile SingularAttribute<TicketEntity, String> splits;
    public static volatile SingularAttribute<TicketEntity, OrderEntity> orderNumber;
    public static volatile SingularAttribute<TicketEntity, Integer> quantity;
    public static volatile SingularAttribute<TicketEntity, String> seats;
    public static volatile SingularAttribute<TicketEntity, String> eventName;
    public static volatile SingularAttribute<TicketEntity, Boolean> sold;

}