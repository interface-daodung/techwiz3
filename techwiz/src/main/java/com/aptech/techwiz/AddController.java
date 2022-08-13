/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aptech.techwiz;

import static com.aptech.techwiz.AdminController.getSecurityMD5;
import com.aptech.techwiz.Controller.CategoryJpaController;
import com.aptech.techwiz.Controller.RoleJpaController;
import com.aptech.techwiz.Controller.StatusJpaController;
import com.aptech.techwiz.Controller.UserJpaController;
import com.aptech.techwiz.entities.Category;
import com.aptech.techwiz.entities.Role;
import com.aptech.techwiz.entities.Status;
import com.aptech.techwiz.entities.User;
import controller.BaseController;
import java.util.Date;
import java.util.List;
import javax.jws.WebParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author inter
 */
@Controller
@RequestMapping("/admin")
public class AddController extends BaseController {

    @PostMapping("/role")
    public String role(@WebParam(name = "name") String name) {

        Role role = new Role();
        role.setName(name);
        RoleJpaController roleJpaController = new RoleJpaController(factory);

        roleJpaController.create(role);

        return "redirect:/role_table.html";
    }

    @PostMapping("/category")
    public String category(@WebParam(name = "name") String name) {

        Category category = new Category();
        category.setName(name);
        CategoryJpaController categoryJpaController = new CategoryJpaController(factory);

        categoryJpaController.create(category);

        return "redirect:/category_table.html";
    }

    @PostMapping("/status")
    public String status(@WebParam(name = "statusName") String statusName) {

        Status status = new Status();
        status.setStatusName(statusName);
        StatusJpaController statusJpaController = new StatusJpaController(factory);

        statusJpaController.create(status);

        return "redirect:/product_status.html";
    }
}
