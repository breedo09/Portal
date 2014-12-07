package TicketManagement;

import OrderManagement.OrderEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Breed
 */
@Entity
@Table(name = "TICKET_ENTITY")
@XmlRootElement
public class TicketEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 50)
    @Column(name = "EVENT_NAME", length = 50)
    private String eventName;
    @Column(name = "EVENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date eventDate;
    @Size(max = 15)
    @Column(name = "SECTION")
    private String section;
    @Size(max = 3)
    @Column(name = "EVENT_ROW", length = 3)
    private String eventRow;
    @Size(max = 10)
    @Column(name = "SEATS")
    private String seats;
    @Column(name = "QUANTITY")
    private int quantity;
    @Column(name = "SPLITS", length = 3)
    private String splits;
    @Basic
    @Column(name = "CURRENT_PRICE", precision = 3, length = 10)
    private float currentPrice;
    @Basic
    @Column(name = "NEW_PRICE", precision = 3, length = 10)
    private float newPrice;
    @Column(name = "COST", precision = 3, length = 10)
    private float cost;
    @Column(name = "PRICE_CHANGED")
    private boolean priceChanged;
    @Column(name = "SOLD")
    private boolean sold;
    @Column(name = "PAID")
    private boolean paid;
    @Column(name = "APPROVED")
    private boolean approved;    
    @ManyToOne(targetEntity = OrderEntity.class, cascade = CascadeType.DETACH)
    private OrderEntity orderNumber;

    public TicketEntity() {
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
        
    public float getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(float newPrice) {
        this.newPrice = newPrice;
    }
        
    public void setOrderNumber(OrderEntity orderNumber) {
        this.orderNumber = orderNumber;
    }

    public OrderEntity getOrderNumber() {
        return orderNumber;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public TicketEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String event) {
        this.eventName = event;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventRow() {
        return eventRow;
    }

    public void setEventRow(String eventRow) {
        this.eventRow = eventRow;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSplits() {
        return splits;
    }

    public void setSplits(String splits) {
        this.splits = splits;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public boolean isPriceChanged() {
        return priceChanged;
    }

    public void setPriceChanged(boolean priceChanged) {
        this.priceChanged = priceChanged;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof TicketEntity)) {
            return false;
        }
        TicketEntity other = (TicketEntity) object;
        if (!this.id.equals(other.getId())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String tic1 = "Ticket Entity  [ id= " + this.getId() + " Event: " + this.getEventName() + " OrderNum: " + this.getOrderNumber() + " ]";
//        String tic2 = 
        return tic1;
    }

}
