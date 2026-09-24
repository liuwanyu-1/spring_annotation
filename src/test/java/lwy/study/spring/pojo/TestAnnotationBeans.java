package lwy.study.spring.pojo;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAnnotationBeans {
    ApplicationContext applicationContext;

    @Before
    public void init() {
        applicationContext = new ClassPathXmlApplicationContext("annotation_beans.xml");
    }

    @Test
    public void testComponent() {
        Address address = applicationContext.getBean("addr", Address.class);
        System.out.println(address);
    }

    @Test
    public void testAutoWire() {
        Teacher teacher = applicationContext.getBean("teacher", Teacher.class);
        System.out.println(teacher);
    }
}
