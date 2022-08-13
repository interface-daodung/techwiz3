/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aptech.techwiz.models;

import com.aptech.techwiz.entities.User;
import java.util.Date;

public class user {

    private Integer id;

    private String fullname;

    private String password;

    private String phoneNumber;

    private String email;

    private String address;

    private Integer status;

    private Date createdAt;

    private Date updateAt;

    private Integer deleted;

    private Integer roleId;

    public user() {
    }

    public user(User u) {
        this.id = u.getId();
        this.fullname = u.getFullname();
        this.password = u.getPassword();
        this.phoneNumber = u.getPhoneNumber();
        this.email = u.getEmail();
        this.address = u.getAddress();
        this.status = u.getStatus();
        this.createdAt = u.getCreatedAt();
        this.updateAt = u.getUpdateAt();
        this.deleted = u.getDeleted();
        this.roleId = u.getRoleId().getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
    public String toString() {
        return "com.aptech.techwiz.entities.User[ id=" + id + " ]";
    }

}
