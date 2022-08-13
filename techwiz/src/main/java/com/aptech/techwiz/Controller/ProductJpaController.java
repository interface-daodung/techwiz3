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
import com.aptech.techwiz.entities.Category;
import com.aptech.techwiz.entities.Cartinfo;
import java.util.ArrayList;
import java.util.Collection;
import com.aptech.techwiz.entities.Orderdetail;
import com.aptech.techwiz.entities.Product;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author inter
 */
public class ProductJpaController implements Serializable {

    public ProductJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Product product) {
        if (product.getCartinfoCollection() == null) {
            product.setCartinfoCollection(new ArrayList<Cartinfo>());
        }
        if (product.getOrderdetailCollection() == null) {
            product.setOrderdetailCollection(new ArrayList<Orderdetail>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Category categoryId = product.getCategoryId();
            if (categoryId != null) {
                categoryId = em.getReference(categoryId.getClass(), categoryId.getId());
                product.setCategoryId(categoryId);
            }
            Collection<Cartinfo> attachedCartinfoCollection = new ArrayList<Cartinfo>();
            for (Cartinfo cartinfoCollectionCartinfoToAttach : product.getCartinfoCollection()) {
                cartinfoCollectionCartinfoToAttach = em.getReference(cartinfoCollectionCartinfoToAttach.getClass(), cartinfoCollectionCartinfoToAttach.getId());
                attachedCartinfoCollection.add(cartinfoCollectionCartinfoToAttach);
            }
            product.setCartinfoCollection(attachedCartinfoCollection);
            Collection<Orderdetail> attachedOrderdetailCollection = new ArrayList<Orderdetail>();
            for (Orderdetail orderdetailCollectionOrderdetailToAttach : product.getOrderdetailCollection()) {
                orderdetailCollectionOrderdetailToAttach = em.getReference(orderdetailCollectionOrderdetailToAttach.getClass(), orderdetailCollectionOrderdetailToAttach.getId());
                attachedOrderdetailCollection.add(orderdetailCollectionOrderdetailToAttach);
            }
            product.setOrderdetailCollection(attachedOrderdetailCollection);
            em.persist(product);
            if (categoryId != null) {
                categoryId.getProductCollection().add(product);
                categoryId = em.merge(categoryId);
            }
            for (Cartinfo cartinfoCollectionCartinfo : product.getCartinfoCollection()) {
                Product oldProductIdOfCartinfoCollectionCartinfo = cartinfoCollectionCartinfo.getProductId();
                cartinfoCollectionCartinfo.setProductId(product);
                cartinfoCollectionCartinfo = em.merge(cartinfoCollectionCartinfo);
                if (oldProductIdOfCartinfoCollectionCartinfo != null) {
                    oldProductIdOfCartinfoCollectionCartinfo.getCartinfoCollection().remove(cartinfoCollectionCartinfo);
                    oldProductIdOfCartinfoCollectionCartinfo = em.merge(oldProductIdOfCartinfoCollectionCartinfo);
                }
            }
            for (Orderdetail orderdetailCollectionOrderdetail : product.getOrderdetailCollection()) {
                Product oldProductIdOfOrderdetailCollectionOrderdetail = orderdetailCollectionOrderdetail.getProductId();
                orderdetailCollectionOrderdetail.setProductId(product);
                orderdetailCollectionOrderdetail = em.merge(orderdetailCollectionOrderdetail);
                if (oldProductIdOfOrderdetailCollectionOrderdetail != null) {
                    oldProductIdOfOrderdetailCollectionOrderdetail.getOrderdetailCollection().remove(orderdetailCollectionOrderdetail);
                    oldProductIdOfOrderdetailCollectionOrderdetail = em.merge(oldProductIdOfOrderdetailCollectionOrderdetail);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Product product) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product persistentProduct = em.find(Product.class, product.getId());
            Category categoryIdOld = persistentProduct.getCategoryId();
            Category categoryIdNew = product.getCategoryId();
            Collection<Cartinfo> cartinfoCollectionOld = persistentProduct.getCartinfoCollection();
            Collection<Cartinfo> cartinfoCollectionNew = product.getCartinfoCollection();
            Collection<Orderdetail> orderdetailCollectionOld = persistentProduct.getOrderdetailCollection();
            Collection<Orderdetail> orderdetailCollectionNew = product.getOrderdetailCollection();
            if (categoryIdNew != null) {
                categoryIdNew = em.getReference(categoryIdNew.getClass(), categoryIdNew.getId());
                product.setCategoryId(categoryIdNew);
            }
            Collection<Cartinfo> attachedCartinfoCollectionNew = new ArrayList<Cartinfo>();
            for (Cartinfo cartinfoCollectionNewCartinfoToAttach : cartinfoCollectionNew) {
                cartinfoCollectionNewCartinfoToAttach = em.getReference(cartinfoCollectionNewCartinfoToAttach.getClass(), cartinfoCollectionNewCartinfoToAttach.getId());
                attachedCartinfoCollectionNew.add(cartinfoCollectionNewCartinfoToAttach);
            }
            cartinfoCollectionNew = attachedCartinfoCollectionNew;
            product.setCartinfoCollection(cartinfoCollectionNew);
            Collection<Orderdetail> attachedOrderdetailCollectionNew = new ArrayList<Orderdetail>();
            for (Orderdetail orderdetailCollectionNewOrderdetailToAttach : orderdetailCollectionNew) {
                orderdetailCollectionNewOrderdetailToAttach = em.getReference(orderdetailCollectionNewOrderdetailToAttach.getClass(), orderdetailCollectionNewOrderdetailToAttach.getId());
                attachedOrderdetailCollectionNew.add(orderdetailCollectionNewOrderdetailToAttach);
            }
            orderdetailCollectionNew = attachedOrderdetailCollectionNew;
            product.setOrderdetailCollection(orderdetailCollectionNew);
            product = em.merge(product);
            if (categoryIdOld != null && !categoryIdOld.equals(categoryIdNew)) {
                categoryIdOld.getProductCollection().remove(product);
                categoryIdOld = em.merge(categoryIdOld);
            }
            if (categoryIdNew != null && !categoryIdNew.equals(categoryIdOld)) {
                categoryIdNew.getProductCollection().add(product);
                categoryIdNew = em.merge(categoryIdNew);
            }
            for (Cartinfo cartinfoCollectionOldCartinfo : cartinfoCollectionOld) {
                if (!cartinfoCollectionNew.contains(cartinfoCollectionOldCartinfo)) {
                    cartinfoCollectionOldCartinfo.setProductId(null);
                    cartinfoCollectionOldCartinfo = em.merge(cartinfoCollectionOldCartinfo);
                }
            }
            for (Cartinfo cartinfoCollectionNewCartinfo : cartinfoCollectionNew) {
                if (!cartinfoCollectionOld.contains(cartinfoCollectionNewCartinfo)) {
                    Product oldProductIdOfCartinfoCollectionNewCartinfo = cartinfoCollectionNewCartinfo.getProductId();
                    cartinfoCollectionNewCartinfo.setProductId(product);
                    cartinfoCollectionNewCartinfo = em.merge(cartinfoCollectionNewCartinfo);
                    if (oldProductIdOfCartinfoCollectionNewCartinfo != null && !oldProductIdOfCartinfoCollectionNewCartinfo.equals(product)) {
                        oldProductIdOfCartinfoCollectionNewCartinfo.getCartinfoCollection().remove(cartinfoCollectionNewCartinfo);
                        oldProductIdOfCartinfoCollectionNewCartinfo = em.merge(oldProductIdOfCartinfoCollectionNewCartinfo);
                    }
                }
            }
            for (Orderdetail orderdetailCollectionOldOrderdetail : orderdetailCollectionOld) {
                if (!orderdetailCollectionNew.contains(orderdetailCollectionOldOrderdetail)) {
                    orderdetailCollectionOldOrderdetail.setProductId(null);
                    orderdetailCollectionOldOrderdetail = em.merge(orderdetailCollectionOldOrderdetail);
                }
            }
            for (Orderdetail orderdetailCollectionNewOrderdetail : orderdetailCollectionNew) {
                if (!orderdetailCollectionOld.contains(orderdetailCollectionNewOrderdetail)) {
                    Product oldProductIdOfOrderdetailCollectionNewOrderdetail = orderdetailCollectionNewOrderdetail.getProductId();
                    orderdetailCollectionNewOrderdetail.setProductId(product);
                    orderdetailCollectionNewOrderdetail = em.merge(orderdetailCollectionNewOrderdetail);
                    if (oldProductIdOfOrderdetailCollectionNewOrderdetail != null && !oldProductIdOfOrderdetailCollectionNewOrderdetail.equals(product)) {
                        oldProductIdOfOrderdetailCollectionNewOrderdetail.getOrderdetailCollection().remove(orderdetailCollectionNewOrderdetail);
                        oldProductIdOfOrderdetailCollectionNewOrderdetail = em.merge(oldProductIdOfOrderdetailCollectionNewOrderdetail);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = product.getId();
                if (findProduct(id) == null) {
                    throw new NonexistentEntityException("The product with id " + id + " no longer exists.");
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
            Product product;
            try {
                product = em.getReference(Product.class, id);
                product.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
            }
            Category categoryId = product.getCategoryId();
            if (categoryId != null) {
                categoryId.getProductCollection().remove(product);
                categoryId = em.merge(categoryId);
            }
            Collection<Cartinfo> cartinfoCollection = product.getCartinfoCollection();
            for (Cartinfo cartinfoCollectionCartinfo : cartinfoCollection) {
                cartinfoCollectionCartinfo.setProductId(null);
                cartinfoCollectionCartinfo = em.merge(cartinfoCollectionCartinfo);
            }
            Collection<Orderdetail> orderdetailCollection = product.getOrderdetailCollection();
            for (Orderdetail orderdetailCollectionOrderdetail : orderdetailCollection) {
                orderdetailCollectionOrderdetail.setProductId(null);
                orderdetailCollectionOrderdetail = em.merge(orderdetailCollectionOrderdetail);
            }
            em.remove(product);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Product> findProductEntities() {
        return findProductEntities(true, -1, -1);
    }

    public List<Product> findProductEntities(int maxResults, int firstResult) {
        return findProductEntities(false, maxResults, firstResult);
    }

    private List<Product> findProductEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Product.class));
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

    public Product findProduct(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Product> rt = cq.from(Product.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
