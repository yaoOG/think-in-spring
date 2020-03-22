package org.learn.ioc.dependency.container;

import org.learn.ioc.dependency.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 注解能力的ApplicationContext作为IoC容器
 * @author zhuyao
 */
public class AnnotationApplicationContextAsIocContainerDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //将当前类 AnnotationApplicationContextAsIocContainerDemo 作为配置类 (Configuration Class)
        applicationContext.register(AnnotationApplicationContextAsIocContainerDemo.class);
        //启动应用上下文
        //org.springframework.context.support.AbstractApplicationContext.refresh 方法创建beanfactory，加入一些我们内建的bean对象或者依赖，内建的非bean依赖等
        applicationContext.refresh();
        //依赖查找集合对象
        lookupByCollectionType(applicationContext);
        //关闭应用上下文
        applicationContext.close();
    }

    /**
     * 通过Java注解的方式定义一个Bean
     * @return
     */
    @Bean
    public User user() {
        User user = new User();
        user.setId(1L);
        user.setName("Tome");
        return user;
    }

    /**
     * 按照类型查找集合对象
     * @param beanFactory
     */
    private static void lookupByCollectionType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("查找到的所有 User 集合对象："+ users);
        }
    }
}
