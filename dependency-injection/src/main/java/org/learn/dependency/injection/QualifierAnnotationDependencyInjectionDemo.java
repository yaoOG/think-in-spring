package org.learn.dependency.injection;

import org.learn.dependency.injection.annotation.UserGroup;
import org.learn.ioc.dependency.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Collection;

/**
 * 基于 {@link org.springframework.beans.factory.annotation.Qualifier}  注解依赖注入示例
 * @author zhuyao
 */
public class QualifierAnnotationDependencyInjectionDemo {

    @Autowired
    private User user; // superUser

    @Autowired
    @Qualifier("user") // 指定 Bean 名称或 ID
    private User nameUser;

    // 整体应用上下文存在 4 个 User 类型的 Bean
    // superUser
    // user
    // user1 -> @Qualifier
    // user2 -> @Qualifier

    @Autowired
    private Collection<User> allUsers; // 2 Beans = user + superUser

    @Autowired
    @Qualifier
    private Collection<User> qualifiedUsers; // 2 Beans = user1 + user2

    @Autowired
    @UserGroup
    private Collection<User> groupedUsers; // 2 Beans = user3 + user4

    @Bean
    @Qualifier //进行逻辑分组
    public User user1() {
        return createUser(7L);
    }

    @Bean
    @Qualifier
    public User user2() {
        return createUser(8L);
    }

    @Bean
    @UserGroup
    public User user3() {
        return createUser(9L);
    }

    @Bean
    @UserGroup
    public User user4() {
        return createUser(10L);
    }

    private static User createUser(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class （配置类）
        applicationContext.register(QualifierAnnotationDependencyInjectionDemo.class);
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        String xmlResourcePath = "META-INF/dependency-lookup-context.xml";
        // 加载 XML 资源，解析并且声称 BeanDefinition
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 依赖查找 AnnotationDependencyFieldInjectionDemo
        QualifierAnnotationDependencyInjectionDemo bean =
                applicationContext.getBean(QualifierAnnotationDependencyInjectionDemo.class);

        // 期待输出 superUser
        System.out.println("bean.user =" + bean.user);
        // 期待输出 User
        System.out.println("bean.nameUser =" + bean.nameUser);

        // 期待输出 2 Beans = user + superUser
        System.out.println("bean.allUsers =" + bean.allUsers);
        // 期待输出 2 Beans = user1 + user2
        System.out.println("bean.qualifiedUsers =" + bean.qualifiedUsers);
        // 期待输出 2 Beans = user3 + user4
        System.out.println("bean.groupedUsers =" + bean.groupedUsers);
        //显示的关闭 Spring 应用上下文
        applicationContext.close();
    }
}
