package TicketManagement;

import EntityManagement.exceptions.NonexistentEntityException;
import EntityManagement.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Breed
 */
@Stateless
public class TicketEntityJpaController implements Serializable {

    public TicketEntityJpaController() {

    }

    @PersistenceContext(unitName = "TicketPortalPU")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return this.em;
    }

    public void create(TicketEntity ticketEntity) throws RollbackFailureException, Exception {
        try {
            em.persist(ticketEntity);
        } catch (Exception ex) {

        } finally {

        }
    }

    public void edit(TicketEntity ticketEntity) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            ticketEntity = em.merge(ticketEntity);
        } catch (Exception ex) {
            System.out.println("failed to edit");
        } finally {
            System.out.println("edited to: " + ticketEntity);
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            TicketEntity ticketEntity;
            try {
                ticketEntity = em.getReference(TicketEntity.class, id);
                ticketEntity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ticketEntity with id " + id + " no longer exists.", enfe);
            }
            em.remove(ticketEntity);

        } catch (Exception ex) {

        } finally {

        }
    }

    public List<TicketEntity> findTicketEntities() {
        return TicketEntityJpaController.this.findTicketEntities(true, -1, -1);
    }

    public List<TicketEntity> findTicketEntities(int maxResults, int firstResult) {
        return TicketEntityJpaController.this.findTicketEntities(false, maxResults, firstResult);
    }

    private List<TicketEntity> findTicketEntities(boolean all, int maxResults, int firstResult) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TicketEntity.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
        }
    }

    public List<TicketEntity> findTicketsByEvent(String event) {
        String searchCriteria = (event == null) ? "" : event.toLowerCase().trim();
        List<TicketEntity> tickets = findTicketEntities();
        if (tickets == null) {
            tickets = new ArrayList<>();
        }
        List<TicketEntity> searchResults = new ArrayList<>();
        for (TicketEntity ticket : tickets) {
            if (ticket.getEventName() != null && ticket.getEventName().toLowerCase().trim().startsWith(searchCriteria)) {
                if (!ticket.isPaid()) {
                    searchResults.add(ticket);
                }
            }
        }
        return searchResults;
    }

    public List<TicketEntity> findTicketsByUsername(String userName) {
        String searchCriteria = (userName == null) ? "" : userName.toLowerCase().trim();
        List<TicketEntity> tickets = findTicketEntities();
        if (tickets == null) {
            tickets = new ArrayList<>();
        }
        List<TicketEntity> searchResults = new ArrayList<>();
        for (TicketEntity ticket : tickets) {
            System.out.println("Order: " + ticket.getOrderNumber().getUsername());
            if (ticket.getOrderNumber().getUsername() != null && ticket.getOrderNumber().getUsername().toLowerCase().trim().startsWith(searchCriteria)) {
                if (!ticket.isSold()) {
                    searchResults.add(ticket);
                }
            }
        }
        return searchResults;

    }

    public List<TicketEntity> findSoldTicketsByUsername(String userName) {
        String searchCriteria = (userName == null) ? "" : userName.toLowerCase().trim();
        List<TicketEntity> tickets = findTicketEntities();
        if (tickets == null) {
            tickets = new ArrayList<>();
        }
        List<TicketEntity> searchResults = new ArrayList<>();
        for (TicketEntity ticket : tickets) {
            if (ticket.getOrderNumber() == null) {
                break;
            }
            System.out.println("Order: " + ticket.getOrderNumber().getUsername());
            if (ticket.getOrderNumber().getUsername() != null && ticket.getOrderNumber().getUsername().toLowerCase().trim().startsWith(searchCriteria)) {
                if (ticket.isSold()) {
                    searchResults.add(ticket);
                }
            }
        }
        return searchResults;

    }

    public TicketEntity findTicketEntity(Integer id) {
        try {
            return em.find(TicketEntity.class, id);
        } finally {
        }
    }

    public int getTicketEntityCount() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TicketEntity> rt = cq.from(TicketEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
        }
    }

    public Collection<TicketEntity> getChangedTickets() {

        List<TicketEntity> tickets = findTicketEntities();
        if (tickets == null) {
            tickets = new ArrayList<>();
        }
        List<TicketEntity> searchResults = new ArrayList<>();
        for (TicketEntity ticket : tickets) {
            if (ticket.isPriceChanged()) {
                searchResults.add(ticket);
            }
            if (!ticket.isApproved()) {
                searchResults.add(ticket);
            }
        }
        return searchResults;
    }

    public Collection<TicketEntity> findPaidTickets() {

        List<TicketEntity> tickets = findTicketEntities();
        if (tickets == null) {
            tickets = new ArrayList<>();
        }
        List<TicketEntity> searchResults = new ArrayList<>();
        for (TicketEntity ticket : tickets) {
            if (ticket.isPaid()) {
                searchResults.add(ticket);
            }
        }
        return searchResults;
    }

}
