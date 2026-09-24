package lwy.study.spring.pojo;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAC {
    ApplicationContext applicationContext;

    @Before
    public void init() {
        applicationContext = new ClassPathXmlApplicationContext("spring-beans.xml");
    }

    @Test
    public void testProtoType() {
        Teacher teacher = applicationContext.getBean("teacher", Teacher.class);
        Teacher teacher1 = applicationContext.getBean("teacher", Teacher.class);
        System.out.println(teacher1);
        System.out.println(teacher);
        System.out.println(teacher == teacher1);
        teacher.setSage(50);
        teacher.getSchoolAddress().setCity("哈尔滨");
        System.out.println(teacher1);
        System.out.println(teacher);
    }
}
