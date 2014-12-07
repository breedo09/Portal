package TicketManagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Breed
 */
@ManagedBean(name = "ticketManagement")
@SessionScoped
public class TicketManagedBean implements Serializable {

    @Inject
    private TicketEntityJpaController ticketService;
    private String searchPhrase;
    private Collection<TicketEntity> searchTicketsResults;
    private Collection<TicketEntity> priceChangeList;
    private Collection<TicketEntity> paidTickets;
    private TicketEntity selectedTicket;

    public TicketEntity getSelectedTicket() {
        if (selectedTicket == null) {
            selectedTicket = new TicketEntity();
        }
        return selectedTicket;
    }

    public TicketManagedBean() {
        selectedTicket = new TicketEntity();
    }

    public Collection<TicketEntity> getPaidTickets() {
        paidTickets = ticketService.findPaidTickets();
        if(paidTickets == null)paidTickets = new ArrayList();
        return paidTickets;
    }

    public void setPaidTickets(Collection<TicketEntity> paidTickets) {
        this.paidTickets = paidTickets;
    }
    
    

    public void setSelectedTicket(TicketEntity tick) {
        System.out.println("setting selected ticket: " + tick);
        this.selectedTicket = tick;
    }

    public Collection<TicketEntity> getSearchTicketsResults() {
        searchTicketsResults = ticketService.findTicketsByEvent("");
        if(searchTicketsResults == null)searchTicketsResults = new ArrayList();
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

    public String deleteSelectedTicket() throws Exception {
        System.out.println("Deleted: " + selectedTicket.getId());
        ticketService.destroy(selectedTicket.getId());
        return searchTicket();
    }

    public String searchTicket() {
        String eventName = (this.searchPhrase == null) ? "" : this.searchPhrase.trim();
        try {

            this.searchTicketsResults = ticketService.findTicketsByEvent(eventName);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            searchTicketsResults = new ArrayList();
            return "ticket_management";
        }
        System.out.println(searchTicketsResults);
        return "ticket_management";
    }

//    public String updateTicket() throws Exception{
//        ticketService.edit(this.selectedTicket);
//        return "tempTicketEntry";
//    }
    public void onTicketSelect() {
    }

    public void onTicketUnselect(UnselectEvent event) {
    }

    public Collection<TicketEntity> getPriceChangeList() {
        priceChangeList = ticketService.getChangedTickets();
        if(priceChangeList == null)priceChangeList = new ArrayList();
        return priceChangeList;
    }

    public void setPriceChangeList(Collection<TicketEntity> priceChangeList) {
        this.priceChangeList = priceChangeList;
    }

    public String approveTicket() throws Exception {
        if (selectedTicket.isPriceChanged()) {
            selectedTicket.setCurrentPrice(selectedTicket.getNewPrice());
            selectedTicket.setNewPrice((float) 0);
            selectedTicket.setPriceChanged(false);
        }
        if (!selectedTicket.isApproved()) {
            selectedTicket.setApproved(true);
        }
        ticketService.edit(selectedTicket);

        return "Dashboard";
    }

    public String soldTicket() throws Exception {
        selectedTicket.setSold(true);
        ticketService.edit(selectedTicket);
        return searchTicket();
    }

    public String paidTicket() throws Exception {
        selectedTicket.setPaid(true);
        ticketService.edit(selectedTicket);
        return searchTicket();
    }
}
