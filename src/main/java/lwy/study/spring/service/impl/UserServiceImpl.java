package lwy.study.spring.service.impl;

import lwy.study.spring.dao.IUserDao;
import lwy.study.spring.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;

    @Override
    public void save() {
        userDao.save();
        System.out.println("执行UserServiceImpl.save()");
    }
}
