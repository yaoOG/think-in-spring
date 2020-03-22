package org.learn.spring.bean.definition;

import org.learn.spring.bean.factory.DefaultUserFactory;
import org.learn.spring.bean.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Bean 初始化 Demo
 * @author zhuyao
 */
@Configuration //Configuration class
public class BeanInitializationDemo {

    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 装配
        applicationContext.register(BeanInitializationDemo.class);
        applicationContext.refresh();
        // 非延迟加载在 Spring 应用上下文启动前，被初始化
        System.out.println("Spring 应用上下文已经启动...");
        // 依赖查找 UserFactory
        UserFactory userFactory = applicationContext.getBean(UserFactory.class);
        System.out.println(userFactory);
        System.out.println("Spring 应用上下文准备关闭...");
        applicationContext.close();
        System.out.println("Spring 应用上下文已经关闭...");
    }

    @Bean(initMethod = "initUserFactory", destroyMethod = "doDestroy")
    @Lazy(value = false)
    public UserFactory userFactory() {
        return new DefaultUserFactory();
    }

}
