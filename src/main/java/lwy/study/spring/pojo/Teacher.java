package lwy.study.spring.pojo;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
public class Teacher extends Object implements Cloneable {

    @Value("1004")
    private Integer sid;
    @Value("谢逊")
    private String sname;
    @Value("38")
    private Integer sage;
    @Value("男")
    private String sgender;
    @Autowired
    @Qualifier("addr")
    //引用类型的属性，形成实例时，需要进行依赖注入（DI）
    private Address schoolAddress;
    //实现原型就是实现的深拷贝和浅拷贝
    //步骤是: 1.实现Cloneable接口 2.并重写Object的clone（）方法

    @Override
    protected Teacher clone() {
        try {
            //clone() 给他生成硬性的副本
            Teacher teacher = (Teacher) super.clone();
            //对引用类型设置地址进行更新
            teacher.schoolAddress = this.schoolAddress.clone();
            return teacher;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
