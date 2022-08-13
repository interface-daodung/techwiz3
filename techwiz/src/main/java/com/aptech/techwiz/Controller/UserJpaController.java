/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aptech.techwiz.Controller;

import com.aptech.techwiz.Controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.aptech.techwiz.entities.Role;
import com.aptech.techwiz.entities.Orders;
import com.aptech.techwiz.entities.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author inter
 */
public class UserJpaController implements Serializable {

    public UserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) {
        if (user.getOrdersCollection() == null) {
            user.setOrdersCollection(new ArrayList<Orders>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Role roleId = user.getRoleId();
            if (roleId != null) {
                roleId = em.getReference(roleId.getClass(), roleId.getId());
                user.setRoleId(roleId);
            }
            Collection<Orders> attachedOrdersCollection = new ArrayList<Orders>();
            for (Orders ordersCollectionOrdersToAttach : user.getOrdersCollection()) {
                ordersCollectionOrdersToAttach = em.getReference(ordersCollectionOrdersToAttach.getClass(), ordersCollectionOrdersToAttach.getId());
                attachedOrdersCollection.add(ordersCollectionOrdersToAttach);
            }
            user.setOrdersCollection(attachedOrdersCollection);
            em.persist(user);
            if (roleId != null) {
                roleId.getUserCollection().add(user);
                roleId = em.merge(roleId);
            }
            for (Orders ordersCollectionOrders : user.getOrdersCollection()) {
                User oldUserIdOfOrdersCollectionOrders = ordersCollectionOrders.getUserId();
                ordersCollectionOrders.setUserId(user);
                ordersCollectionOrders = em.merge(ordersCollectionOrders);
                if (oldUserIdOfOrdersCollectionOrders != null) {
                    oldUserIdOfOrdersCollectionOrders.getOrdersCollection().remove(ordersCollectionOrders);
                    oldUserIdOfOrdersCollectionOrders = em.merge(oldUserIdOfOrdersCollectionOrders);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getId());
            Role roleIdOld = persistentUser.getRoleId();
            Role roleIdNew = user.getRoleId();
            Collection<Orders> ordersCollectionOld = persistentUser.getOrdersCollection();
            Collection<Orders> ordersCollectionNew = user.getOrdersCollection();
            if (roleIdNew != null) {
                roleIdNew = em.getReference(roleIdNew.getClass(), roleIdNew.getId());
                user.setRoleId(roleIdNew);
            }
            Collection<Orders> attachedOrdersCollectionNew = new ArrayList<Orders>();
            for (Orders ordersCollectionNewOrdersToAttach : ordersCollectionNew) {
                ordersCollectionNewOrdersToAttach = em.getReference(ordersCollectionNewOrdersToAttach.getClass(), ordersCollectionNewOrdersToAttach.getId());
                attachedOrdersCollectionNew.add(ordersCollectionNewOrdersToAttach);
            }
            ordersCollectionNew = attachedOrdersCollectionNew;
            user.setOrdersCollection(ordersCollectionNew);
            user = em.merge(user);
            if (roleIdOld != null && !roleIdOld.equals(roleIdNew)) {
                roleIdOld.getUserCollection().remove(user);
                roleIdOld = em.merge(roleIdOld);
            }
            if (roleIdNew != null && !roleIdNew.equals(roleIdOld)) {
                roleIdNew.getUserCollection().add(user);
                roleIdNew = em.merge(roleIdNew);
            }
            for (Orders ordersCollectionOldOrders : ordersCollectionOld) {
                if (!ordersCollectionNew.contains(ordersCollectionOldOrders)) {
                    ordersCollectionOldOrders.setUserId(null);
                    ordersCollectionOldOrders = em.merge(ordersCollectionOldOrders);
                }
            }
            for (Orders ordersCollectionNewOrders : ordersCollectionNew) {
                if (!ordersCollectionOld.contains(ordersCollectionNewOrders)) {
                    User oldUserIdOfOrdersCollectionNewOrders = ordersCollectionNewOrders.getUserId();
                    ordersCollectionNewOrders.setUserId(user);
                    ordersCollectionNewOrders = em.merge(ordersCollectionNewOrders);
                    if (oldUserIdOfOrdersCollectionNewOrders != null && !oldUserIdOfOrdersCollectionNewOrders.equals(user)) {
                        oldUserIdOfOrdersCollectionNewOrders.getOrdersCollection().remove(ordersCollectionNewOrders);
                        oldUserIdOfOrdersCollectionNewOrders = em.merge(oldUserIdOfOrdersCollectionNewOrders);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = user.getId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            Role roleId = user.getRoleId();
            if (roleId != null) {
                roleId.getUserCollection().remove(user);
                roleId = em.merge(roleId);
            }
            Collection<Orders> ordersCollection = user.getOrdersCollection();
            for (Orders ordersCollectionOrders : ordersCollection) {
                ordersCollectionOrders.setUserId(null);
                ordersCollectionOrders = em.merge(ordersCollectionOrders);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public User findUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public User findUserByEmail(String email) {
        EntityManager em = getEntityManager();
        try {
            Query createNamedQuery = em.createNamedQuery("User.findByEmail", User.class);
            createNamedQuery.setParameter("email", email);
            List<User> resultList = createNamedQuery.getResultList();
            if (resultList.isEmpty()) {
                return null;
            }
            return resultList.get(0);
        } finally {
            em.close();
        }
    }
    
}
