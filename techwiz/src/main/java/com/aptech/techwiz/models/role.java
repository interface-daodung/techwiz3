/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aptech.techwiz.models;

import com.aptech.techwiz.entities.Role;

public class role {

    private Integer id;
    private String name;

    public role() {
    }

    public role(Role r) {
        this.id = r.getId();
        this.name = r.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
