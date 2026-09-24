package lwy.study.spring.pojo;

import org.junit.Test;

public class TestCopy {

    @Test
    public void testClone(){
        Teacher teacher = new Teacher(
                1001,
                "张三丰",
                101,
                "男",
                new Address("大庆市龙凤区", "新风路5号")
        );
        //使用Clone() 复制创建新的Teacher 实例
        Teacher teacher1 = teacher.clone();
        System.out.println(teacher);
        teacher1.setSage(30);
        System.out.println(teacher1);
        System.out.println(teacher1 == teacher);
    }
}
