package lwy.study.spring.controller;

import lwy.study.spring.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller("userController")
public class UserController {
    @Autowired
    private IUserService userService;

    public void save() {
        this.userService.save();
        System.out.println("执行UserController.save()");
    }
}
