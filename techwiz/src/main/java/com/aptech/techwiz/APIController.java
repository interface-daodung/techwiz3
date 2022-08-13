package com.aptech.techwiz;

import com.aptech.techwiz.entities.Cartinfo;
import com.aptech.techwiz.entities.Category;
import com.aptech.techwiz.entities.Comment;
import com.aptech.techwiz.entities.Contact;
import com.aptech.techwiz.entities.Orderdetail;
import com.aptech.techwiz.entities.Orders;
import com.aptech.techwiz.entities.Product;
import com.aptech.techwiz.entities.Role;
import com.aptech.techwiz.entities.Status;
import com.aptech.techwiz.entities.User;
import com.aptech.techwiz.models.product;
import com.aptech.techwiz.models.user;
import controller.BaseController;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIController extends BaseController{
    
    @GetMapping("/User")
    public List<user> User() {
        Query q = manager.createNamedQuery("User.findAll", User.class);
        List<User> dataList = q.getResultList();
        List<user> userList = new ArrayList<>();
        for (User u : dataList) {
            userList.add(new user(u));
        }
        return userList;
    }
    @GetMapping("/Status")
    public List<Status> Status() {
        Query q = manager.createNamedQuery("Status.findAll", Status.class);
        List<Status> dataList = q.getResultList();
        return dataList;
    }
    @GetMapping("/Role")
    public List<Role> Role() {
        Query q = manager.createNamedQuery("Role.findAll", Role.class);
        List<Role> dataList = q.getResultList();
        return dataList;
    }
    @GetMapping("/Product")
    public List<product> Product() {
        Query q = manager.createNamedQuery("Product.findAll", Product.class);
        List<Product> dataList = q.getResultList();
        List<product> productList = new ArrayList<>();
        for (Product u : dataList) {
           productList.add(new product(u));
        }
        return productList;
    }
    @GetMapping("/Orders")
    public List<Orders> Order1() {
         Query q = manager.createNamedQuery("Orders.findAll", Orders.class);
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        List<Orders> dataList = q.getResultList();
                System.out.println("dsadasdasds");

        return dataList;
    }
     @GetMapping("/Orderdetail")
    public List<Orderdetail> Orderdetail() {
        Query q = manager.createNamedQuery("Orderdetail.findAll", Orderdetail.class);
        List<Orderdetail> dataList = q.getResultList();
        return dataList;
    }
    @GetMapping("/Contact")
    public List<Contact> Contact() {
        Query q = manager.createNamedQuery("Contact.findAll", Contact.class);
        List<Contact> dataList = q.getResultList();
        return dataList;
    }
    @GetMapping("/Comment")
    public List<Comment> Comment() {
        Query q = manager.createNamedQuery("Comment.findAll", Comment.class);
        List<Comment> dataList = q.getResultList();
        return dataList;
    }
    @GetMapping("/Category")
    public List<Category> Category() {
        Query q = manager.createNamedQuery("Category.findAll", Category.class);
        List<Category> dataList = q.getResultList();
        return dataList;
    }
    @GetMapping("/Cartinfo")
    public List<Cartinfo> Cartinfo() {
        Query q = manager.createNamedQuery("Cartinfo.findAll", Cartinfo.class);
        List<Cartinfo> dataList = q.getResultList();
        return dataList;
    }
}
