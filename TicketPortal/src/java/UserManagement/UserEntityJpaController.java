package UserManagement;


import EntityManagement.exceptions.*;
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
public class UserEntityJpaController implements Serializable {

    public UserEntityJpaController() {

    }

    @PersistenceContext(unitName = "TicketPortalPU")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return this.em;
    }

    public void create(UserEntity userEntity) throws RollbackFailureException, Exception {
        try {
            em.persist(userEntity);
        } catch (Exception ex) {

        } finally {

        }
    }

    public void edit(UserEntity userEntity) throws NonexistentEntityException, RollbackFailureException, Exception {
        System.out.println("Editing:" + userEntity);
        try {
            userEntity = em.merge(userEntity);
        } catch (Exception ex) {
            System.out.println("Exception Caught");
            System.out.println(ex.getMessage());
        } finally {
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            UserEntity userEntity;
            try {
                userEntity = em.getReference(UserEntity.class, id);
                userEntity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userEntity with id " + id + " no longer exists.", enfe);
            }
            em.remove(userEntity);

        } catch (Exception ex) {

        } finally {

        }
    }

    public List<UserEntity> findUserEntities() {
        return UserEntityJpaController.this.findUserEntities(true, -1, -1);
    }

    public List<UserEntity> findUserEntities(int maxResults, int firstResult) {
        return UserEntityJpaController.this.findUserEntities(false, maxResults, firstResult);
    }

    private List<UserEntity> findUserEntities(boolean all, int maxResults, int firstResult) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserEntity.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
        }
    }

    public UserEntity findUserEntity(Integer id) {
        try {
            return em.find(UserEntity.class, id);
        } finally {
        }
    }

    public UserEntity findUserByUsername(String userName) {
        String searchCriteria = (userName == null) ? "" : userName.toLowerCase().trim();
        Collection<UserEntity> users = findUserEntities();
        if (users == null) {
            users = new ArrayList<>();
        }
        List<UserEntity> searchResults = new ArrayList<>();
        for (UserEntity user : users) {
            if (user.getUsername() != null && user.getUsername().toLowerCase().trim().startsWith(searchCriteria)) {
                searchResults.add(user);
            }
        }
        if (searchResults.isEmpty()) {
            return null;
        }
        return searchResults.get(0);

    }

    public int getUserEntityCount() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserEntity> rt = cq.from(UserEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
        }
    }

    public boolean userPermitted(String username, String password) {

        UserEntity current = new UserEntity();
        current.setUsername(username);
        current.setPassword(password);
        if (username == null || username.isEmpty()) {
            return false;
        }
        if (password == null || password.isEmpty()) {
            return false;
        }
        String searchCriteria = username.toLowerCase().trim();
        Collection<UserEntity> users = findUserEntities();
        for (UserEntity user : users) {
            if (user.getUsername() != null && user.getUsername().toLowerCase().trim().startsWith(searchCriteria)) {
                if (user.getPassword().matches(password)) {
                    return true;
                }
            }
        }

        return false;
    }

}
