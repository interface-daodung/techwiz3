/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author inter
 */
public class BaseController {
    public EntityManagerFactory factory;
    public EntityManager manager;
    
    public BaseController() {
        factory = Persistence.createEntityManagerFactory("com.aptech_techwiz_jar_0.0.1-SNAPSHOTPU");
        manager = factory.createEntityManager();
    }
}
