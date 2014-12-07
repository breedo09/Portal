package TicketManagement;

import java.io.Serializable;
import OrderManagement.OrderEntity;
import OrderManagement.OrderEntityJpaController;
import UserManagement.UserEntity;
import UserManagement.UserEntityJpaController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Breed
 */
@ManagedBean(name = "ticketEntry")
@SessionScoped
public class TicketEntryBean implements Serializable {

    @Inject
    private TicketEntityJpaController ticketService;
    @Inject
    private OrderEntityJpaController orderService;
    @Inject
    private UserEntityJpaController userService;
    private Collection<TicketEntity> createTicketsList;
    private Collection<UserEntity> userList;
    private TicketEntity createableTicket;
    private String User;

    public TicketEntryBean() {

        createableTicket = new TicketEntity();
        createTicketsList = new ArrayList<>();

    }

    public Collection<UserEntity> getUserList() {
        userList = userService.findUserEntities();
        return userList;
    }

    public void setUserList(Collection<UserEntity> userList) {
        this.userList = userList;
    }
    
    

    public String getUser() {
        return User;
    }

    public void setUser(String User) {
        this.User = User;
    }

    public TicketEntity getCreateableTicket() {
        return createableTicket;
    }

    public void setCreateableTicket(TicketEntity tick) {
        this.createableTicket = tick;
    }

    public Collection<TicketEntity> getCreateTicketsList() {
        return createTicketsList;
    }

    public void setCreateTicketsList(Collection<TicketEntity> createTicketsList) {
        this.createTicketsList = createTicketsList;
    }

    public void onTicketSelect() {
    }

    public void addToCreateList() {
        System.out.println("added to create list");

        createTicketsList.add(createableTicket);
        createableTicket = new TicketEntity();
    }

    public String finishCreate() throws Exception {

        TicketEntity tick;
        Iterator<TicketEntity> iterator = createTicketsList.iterator();
        while (iterator.hasNext()) {
            tick = iterator.next();
            OrderEntity orderEntity = new OrderEntity(orderService.getOrderEntityCount() + 1, User);
            orderService.create(orderEntity);
            orderEntity = orderService.findOrderEntity(orderEntity.getId());
            tick.setOrderNumber(orderEntity);
            tick.setApproved(true);
            tick.setPaid(false);
            tick.setSold(false);
            ticketService.create(tick);
            int id = ticketService.getTicketEntityCount();
            System.out.println("Created Ticket: " + tick.getEventName() + " Count: " + id);
            iterator.remove();
        }
        createTicketsList = new ArrayList();
        return "ticket_management";
    }

}
