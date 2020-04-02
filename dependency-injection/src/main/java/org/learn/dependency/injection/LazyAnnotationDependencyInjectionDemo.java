package org.learn.dependency.injection;

import org.learn.dependency.injection.annotation.UserGroup;
import org.learn.ioc.dependency.domain.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Set;

/**
 * 基于 {@link org.springframework.beans.factory.ObjectProvider}  实现延迟依赖注入
 * @author zhuyao
 */
public class LazyAnnotationDependencyInjectionDemo {

    @Autowired
    private User user; // 实时注入

    @Autowired
    private ObjectProvider<User> userObjectProvider; // 延迟注入

    @Autowired
    private ObjectFactory<Set<User>> userObjectFactory;

    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class （配置类）
        applicationContext.register(LazyAnnotationDependencyInjectionDemo.class);
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        String xmlResourcePath = "META-INF/dependency-lookup-context.xml";
        // 加载 XML 资源，解析并且声称 BeanDefinition
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 依赖查找 AnnotationDependencyFieldInjectionDemo
        LazyAnnotationDependencyInjectionDemo bean =
                applicationContext.getBean(LazyAnnotationDependencyInjectionDemo.class);

        // 期待输出 superUser
        System.out.println("bean.user =" + bean.user);
        // 期待输出 superUser
        System.out.println("bean.userObjectProvider =" + bean.userObjectProvider.getObject()); // 继承 ObjectFactory
        // 期待输出 superUser and user
        System.out.println("bean.userObjectFactory =" + bean.userObjectFactory.getObject()); //  ObjectFactory
        // 实时注入
        bean.userObjectProvider.forEach(System.out::println);
        //显示的关闭 Spring 应用上下文
        applicationContext.close();
    }
}
