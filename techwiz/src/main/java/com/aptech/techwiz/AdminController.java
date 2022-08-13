/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aptech.techwiz;

import com.aptech.techwiz.Controller.RoleJpaController;
import com.aptech.techwiz.Controller.UserJpaController;
import com.aptech.techwiz.entities.Role;
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
@RequestMapping("/user")
public class AdminController extends BaseController {

    @PostMapping("/register")
    public String register(@WebParam(name = "fullname") String fullname,
            @WebParam(name = "email") String email,
            @WebParam(name = "password") String password
    ) {

        User user = new User();
        user.setFullname(fullname);
        user.setEmail(email);
        user.setPassword(getSecurityMD5(password));
        user.setDeleted(0);
        user.setStatus(1);
        user.setCreatedAt(new Date());
        RoleJpaController roleJpaController = new RoleJpaController(factory);

        List<Role> findRoleEntities = roleJpaController.findRoleEntities();

        user.setRoleId(findRoleEntities.get(1));

        UserJpaController userJpaController = new UserJpaController(factory);
        if (userJpaController.findUserByEmail(email) == null) {
            userJpaController.create(user);
        } else {
            return "redirect:../admin/register.html?msg=1";
        }
        return "redirect:../admin/login.html";
    }

    @PostMapping("/login")
    public String login(@WebParam(name = "email") String email,
            @WebParam(name = "password") String password
    ) {
                System.out.println(password+" "+email+" ");
        System.out.println(getSecurityMD5(password));

        UserJpaController userJpaController = new UserJpaController(factory);
        User Email = userJpaController.findUserByEmail(email);
        if (Email != null) {
            if (Email.getPassword().equals(getSecurityMD5(password))) {
                if (Email.getRoleId().getId()==1) {
                    return "redirect:../admin/index.html";
                } else{
                    return "redirect:../index.html";
                }
            } else {
                return "redirect:../admin/login.html?msg=2";
            }
        } else {
            return "redirect:../admin/login.html?msg=1";
        }
    }
    
    public static String getSecurityMD5(String pwd) {
        return MD5(MD5(pwd) + "PRIVATE_KEY");
    }

    private static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

}
