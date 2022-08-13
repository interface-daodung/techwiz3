/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aptech.techwiz.models;

import com.aptech.techwiz.entities.Product;
import java.util.Date;

/**
 *
 * @author inter
 */
public class product {

    private Integer id;

    private String imgName;

    private String size;

    private String title;

    private Double price;

    private String description;

    private Date createdAt;

    private Date updateAt;

    private Integer deleted;

    private Integer categoryId;

    private Integer statusId;

    public product() {
    }

    public product(Product p) {
        this.id = p.getId();
        this.imgName = p.getImgName();
        this.size = p.getSize();
        this.title = p.getTitle();
        this.price = p.getPrice();
        this.description = p.getDescription();
        this.createdAt = p.getCreatedAt();
        this.updateAt = p.getUpdateAt();
        this.deleted = p.getDeleted();
        this.categoryId = p.getCategoryId().getId();
        this.statusId = p.getStatusId().getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "com.aptech.techwiz.entities.Product[ id=" + id + " ]";
    }

}
