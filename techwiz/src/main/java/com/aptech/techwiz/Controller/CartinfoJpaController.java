/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aptech.techwiz.Controller;

import com.aptech.techwiz.Controller.exceptions.NonexistentEntityException;
import com.aptech.techwiz.entities.Cartinfo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.aptech.techwiz.entities.Product;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author inter
 */
public class CartinfoJpaController implements Serializable {

    public CartinfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cartinfo cartinfo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product productId = cartinfo.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getId());
                cartinfo.setProductId(productId);
            }
            em.persist(cartinfo);
            if (productId != null) {
                productId.getCartinfoCollection().add(cartinfo);
                productId = em.merge(productId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cartinfo cartinfo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cartinfo persistentCartinfo = em.find(Cartinfo.class, cartinfo.getId());
            Product productIdOld = persistentCartinfo.getProductId();
            Product productIdNew = cartinfo.getProductId();
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getId());
                cartinfo.setProductId(productIdNew);
            }
            cartinfo = em.merge(cartinfo);
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getCartinfoCollection().remove(cartinfo);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getCartinfoCollection().add(cartinfo);
                productIdNew = em.merge(productIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cartinfo.getId();
                if (findCartinfo(id) == null) {
                    throw new NonexistentEntityException("The cartinfo with id " + id + " no longer exists.");
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
            Cartinfo cartinfo;
            try {
                cartinfo = em.getReference(Cartinfo.class, id);
                cartinfo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cartinfo with id " + id + " no longer exists.", enfe);
            }
            Product productId = cartinfo.getProductId();
            if (productId != null) {
                productId.getCartinfoCollection().remove(cartinfo);
                productId = em.merge(productId);
            }
            em.remove(cartinfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cartinfo> findCartinfoEntities() {
        return findCartinfoEntities(true, -1, -1);
    }

    public List<Cartinfo> findCartinfoEntities(int maxResults, int firstResult) {
        return findCartinfoEntities(false, maxResults, firstResult);
    }

    private List<Cartinfo> findCartinfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cartinfo.class));
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

    public Cartinfo findCartinfo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cartinfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCartinfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cartinfo> rt = cq.from(Cartinfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
