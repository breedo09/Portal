package TicketManagement;

import EntityManagement.exceptions.RollbackFailureException;
import OrderManagement.OrderEntity;
import OrderManagement.OrderEntityJpaController;
import UserManagement.UserEntityJpaController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author Breed
 */
@ManagedBean(name = "clientTicketBean")
@SessionScoped
public class ClientTicketBean implements Serializable {

    @Inject
    private TicketEntityJpaController ticketService;
    @Inject
    private UserEntityJpaController userService;
    @Inject
    private OrderEntityJpaController orderService;
    private String searchPhrase;
    private Collection<TicketEntity> searchTicketsResults;
    private TicketEntity selectedTicket;
    private String price;
    private String username;
    private String password;
    private Collection<TicketEntity> ticketsSold;
    private Collection<TicketEntity> createTicketsList;
    private TicketEntity createableTicket;

    public TicketEntity getSelectedTicket() {
        if (selectedTicket == null) {
            selectedTicket = new TicketEntity();
        }
        return selectedTicket;
    }

    public ClientTicketBean() {
        createableTicket = new TicketEntity();
        createTicketsList = new ArrayList<>();
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

    public void addToCreateList() {
        System.out.println("added to create list");

        createTicketsList.add(createableTicket);
        createableTicket = new TicketEntity();
    }

    public String finishCreate() throws Exception {

        TicketEntity tick;
        Iterator<TicketEntity> iterator = createTicketsList.iterator();
        OrderEntity orderEntity = new OrderEntity(orderService.getOrderEntityCount() + 1, username);
        orderService.create(orderEntity);
        orderEntity = orderService.findOrderEntity(orderEntity.getId());
        while (iterator.hasNext()) {
            tick = iterator.next();
            tick.setOrderNumber(orderEntity);
            tick.setApproved(false);
            tick.setPaid(false);
            tick.setSold(false);
            ticketService.create(tick);
            int id = ticketService.getTicketEntityCount();
            System.out.println("Created Ticket: " + tick.getEventName() + " Count: " + id);
            iterator.remove();
        }
        createTicketsList = new ArrayList();
        return "Client_ticketEntry";
    }

    public Collection<TicketEntity> getTicketsSold() {
        ticketsSold = ticketService.findSoldTicketsByUsername(username);
        return ticketsSold;
    }

    public void setTicketsSold(Collection<TicketEntity> ticketsSold) {
        this.ticketsSold = ticketService.findSoldTicketsByUsername(username);
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setSelectedTicket(TicketEntity tick) {
        this.selectedTicket = tick;
    }

    public Collection<TicketEntity> getSearchTicketsResults() {
        searchTicketsResults = ticketService.findTicketsByUsername(username);
        return searchTicketsResults;
    }

    public void setSearchTicketsResults(Collection<TicketEntity> searchTicketsResults) {
        this.searchTicketsResults = searchTicketsResults;
    }

    public String getSearchPhrase() {
        return searchPhrase;
    }

    public void setSearchPhrase(String searchPhrase) {
        this.searchPhrase = searchPhrase;
    }

    public String searchTicket() {
        try {
            System.out.println("username: " + username);
            searchTicketsResults = ticketService.findTicketsByUsername(username);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
        return "Client_Dashboard";
    }

    public String changePrice() throws RollbackFailureException, Exception {
        if (price == null || price.isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("breed", new FacesMessage("Invalid Price: " + "'" + price + "'" + ", New Price must be a number with or without a decimal."));
            return "Client_Dashboard";
        }
        Float fltPrice = new Float(price);
        System.out.println("Attempting to edit");
        selectedTicket.setNewPrice(fltPrice);
        selectedTicket.setPriceChanged(true);
        ticketService.edit(selectedTicket);
        price = "";
        return "Client_Dashboard";
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
            context.addMessage("username", new FacesMessage("Invalid UserName or Password"));
            return "index";
        }
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
}
