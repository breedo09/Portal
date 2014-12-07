package OrderManagement;


import EntityManagement.exceptions.*;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Breed
 */
@Stateless
public class OrderEntityJpaController {

    public OrderEntityJpaController() {

    }

    @PersistenceContext(unitName = "TicketPortalPU")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return this.em;
    }
    
    public void create(OrderEntity orderEntity) throws RollbackFailureException, Exception {
        try {
            em.persist(orderEntity);
        } catch (Exception ex) {

        } finally {

        }
    }

    public void edit(OrderEntity orderEntity) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            orderEntity = em.merge(orderEntity);
        } catch (Exception ex) {

        } finally {
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            em = getEntityManager();
            OrderEntity orderEntity;
            try {
                orderEntity = em.getReference(OrderEntity.class, id);
                orderEntity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderEntity with id " + id + " no longer exists.", enfe);
            }
            em.remove(orderEntity);

        } catch (Exception ex) {

        } finally {

        }
    }

    public List<OrderEntity> findorderEntities() {
        return OrderEntityJpaController.this.findorderEntities(true, -1, -1);
    }

    public List<OrderEntity> findorderEntities(int maxResults, int firstResult) {
        return OrderEntityJpaController.this.findorderEntities(false, maxResults, firstResult);
    }

    private List<OrderEntity> findorderEntities(boolean all, int maxResults, int firstResult) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrderEntity.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
        }
    }

    public OrderEntity findOrderEntity(Integer id) {
        try {
            return em.find(OrderEntity.class, id);
        } finally {
        }
    }
    
        public int getOrderEntityCount() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrderEntity> rt = cq.from(OrderEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
        }
    }

}
