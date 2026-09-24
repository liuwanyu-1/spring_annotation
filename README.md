# spring_annotation —— Spring 注解开发课堂跟写（2026-09-24）

今天全天课堂内容的完整跟写工程。上半场用 Cloneable 手工克隆讲透 prototype 的原理，下半场进入注解开发。

## 一、手工克隆与 prototype 原理（上午）

### 1. Cloneable 接口与 clone() 重写

- `Cloneable` 是**标记接口**（里面什么方法都没有），实现它才允许被克隆
- 重写 `Object.clone()`：权限从 `protected` 保持 protected，返回值**协变**成 `Teacher`（省得调用方强转）
- `super.clone()` 抛受检异常 `CloneNotSupportedException`，课上的处理是 try-catch 后包成 `RuntimeException` 抛出

```java
@Override
protected Teacher clone() {
    try {
        Teacher teacher = (Teacher) super.clone();
        // 深拷贝：引用类型属性要再克隆一层
        teacher.schoolAddress = this.schoolAddress.clone();
        return teacher;
    } catch (CloneNotSupportedException e) {
        throw new RuntimeException(e);
    }
}
```

### 2. 浅拷贝 vs 深拷贝

- **浅拷贝**：只克隆对象本身，里面的引用类型属性（如 Address）还指向同一个对象
- **深拷贝**：引用类型属性再 clone 一层，两个对象彻底独立
- 测试：`Teacher` 里 `sage` 改成 50、`schoolAddress` 的 city 改成"哈尔滨"，原对象不受影响 → 深拷贝生效

### 3. Spring 的 prototype 作用域

- XML 里 `<bean scope="prototype">`：每次 `getBean()` 都返回**新实例**，`==` 比较为 false
- 注意坑：bean 设了 prototype，但它 ref 引用的 Address 也得是 prototype，否则两个 Teacher 共享同一个 Address，改一个另一个跟着变
- `TestAC.testProtoType()` 验证：两次 getBean、打印、修改、再打印

## 二、注解开发（下午）

### 1. 核心注解

| 注解 | 位置 | 作用 |
|---|---|---|
| `@Component` | 实体类 | 声明为组件，交给容器管理；可显式命名 `@Component("addr")`，不写名字则默认类名首字母小写 |
| `@Value("...")` | 字段 | 注入基本值（String/Integer/Double 都能自动转） |
| `@Autowired` | 字段 | 按类型自动装配 |
| `@Qualifier("xxx")` | 配合 @Autowired | 按名字指定注入哪个 bean |
| `@Scope(value = "singleton")` | 实体类 | 指定作用域 |
| `@Repository("xxx")` | DAO 实现类 | 分层注解，本质也是组件 |
| `@Service("xxx")` | Service 实现类 | 分层注解 |
| `@Controller("xxx")` | 控制器 | 分层注解 |

### 2. 组件扫描：不写扫描，注解不生效

```xml
<context:component-scan base-package="lwy.study.spring"/>
```

两个配置文件两种扫法：
- `applicationContext.xml`：扫全包（分层链用）
- `annotation_beans.xml`：只扫 pojo（关联注入演示用），里面还保留了一个 XML 定义的 bean 作对照

### 3. 两个测试点

**TestAnnotation（main 方法）** —— User 四件套分层链：

```
User(name=张无忌, age=20)
执行UserDaoImpl.save()
执行UserServiceImpl.save()
执行UserController.save()
```

链路：容器取 `userController` → @Autowired 注入 service → service 注入 dao → dao 起容器取 user 打印。

**TestAnnotationBeans（JUnit 2 用例）** —— Teacher↔Address 关联：

- `testComponent`：取 `addr`（@Component 命名的 Address）
- `testAutoWire`：取 `teacher`，其 `schoolAddress` 通过 `@Autowired + @Qualifier("addr")` 注入

```
Teacher(sid=1004, sname=谢逊, sage=38, sgender=男, schoolAddress=Address(city=南京市鼓楼区, street=中山北路8号))
Address(city=南京市鼓楼区, street=中山北路8号)
```

### 4. 踩坑记录

- **板书笔误**：老师把 property 名写成 `sgenden`（少个 r），照抄会报 `NotWritablePropertyException`，正确是 `sgender`
- IDEA 红线误报（Spring Inspection 对注解 bean 的误判）不用管，`mvn test` 绿就是真绿
- 外部改了文件记得 IDEA 里 File → Reload All from Disk，不然旧缓冲保存会覆盖磁盘改动

## 三、运行方式

```bash
mvn test -Dtest=TestAnnotationBeans   # JUnit 2 用例
mvn test -Dtest=TestCopy,TestAC       # 克隆/prototype 3 用例
# TestAnnotation 是 main 方法，在 IDEA 里直接运行
```
