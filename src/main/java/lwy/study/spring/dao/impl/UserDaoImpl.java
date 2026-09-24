package lwy.study.spring.dao.impl;

import lwy.study.spring.dao.IUserDao;
import lwy.study.spring.pojo.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl implements IUserDao {
    public void save() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = (User) applicationContext.getBean("user");
        System.out.println(user);
        System.out.println("执行UserDaoImpl.save()");
    }
}
