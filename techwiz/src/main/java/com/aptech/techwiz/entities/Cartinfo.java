/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aptech.techwiz.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author inter
 */
@Entity
@Table(name = "cartinfo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cartinfo.findAll", query = "SELECT c FROM Cartinfo c"),
    @NamedQuery(name = "Cartinfo.findById", query = "SELECT c FROM Cartinfo c WHERE c.id = :id"),
    @NamedQuery(name = "Cartinfo.findByOrderDate", query = "SELECT c FROM Cartinfo c WHERE c.orderDate = :orderDate"),
    @NamedQuery(name = "Cartinfo.findByAmount", query = "SELECT c FROM Cartinfo c WHERE c.amount = :amount")})
public class Cartinfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "orderDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    @Column(name = "amount")
    private Integer amount;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User userId;
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne
    private Product productId;

    public Cartinfo() {
    }

    public Cartinfo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cartinfo)) {
            return false;
        }
        Cartinfo other = (Cartinfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aptech.techwiz.entities.Cartinfo[ id=" + id + " ]";
    }
    
}
