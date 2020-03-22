package org.learn.spring.bean.definition;

import org.learn.spring.bean.factory.DefaultUserFactory;
import org.learn.spring.bean.factory.UserFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 单体 Bean 注册示例
 * 外部单体对象: 这个对象的生命周期并不由 spring 来管理，但是也可以被它托管
 * @author zhuyao
 */
public class SingletonBeanRegistrationDemo {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 创建一个 UserFactory 外部对象
        UserFactory userFactory = new DefaultUserFactory();
        SingletonBeanRegistry singletonBeanRegistry = applicationContext.getBeanFactory();

        // 注册外部单例对象
        singletonBeanRegistry.registerSingleton("userFactory", userFactory);
        applicationContext.refresh();

        // 通过依赖查找的方式来获取 UserFactory 对象
        UserFactory userFactoryByLookUp = applicationContext.getBean("userFactory", UserFactory.class);
        System.out.println(userFactory == userFactoryByLookUp);

        applicationContext.close();
    }
}
