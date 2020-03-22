package org.learn.spring.bean.definition;

import org.learn.ioc.dependency.domain.User;
import org.learn.spring.bean.factory.DefaultUserFactory;
import org.learn.spring.bean.factory.UserFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.ServiceLoader;

import static java.util.ServiceLoader.load;

/**
 * 特殊的 Bean 实例化示例
 * @author zhuyao
 */
public class SpecialBeanInstantiationDemo {

    public static void main(String[] args) {

      /*  BeanFactory beanFactory = new ClassPathXmlApplicationContext("META-INF/special-bean-instantiation-context.xml");
        ServiceLoader<UserFactory> serviceLoader = beanFactory.getBean("userFactoryServiceLoader", ServiceLoader.class);
        dicplayServiceLoader(serviceLoader);*/

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/special-bean-instantiation-context.xml");
        //通过 ApplicationContext 获取    AutowireCapableBeanFactory
        AutowireCapableBeanFactory capableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        //创建 UserFactory 对象，通过 AutowireCapableBeanFactory
        UserFactory userFactory = capableBeanFactory.createBean(DefaultUserFactory.class);
        System.out.println(userFactory.createUser());

    }

    public static void demoServiceLoader() {
        ServiceLoader<UserFactory> serviceLoader = load(UserFactory.class, Thread.currentThread().getContextClassLoader());
        dicplayServiceLoader(serviceLoader);
    }

    public static void dicplayServiceLoader(ServiceLoader<UserFactory> serviceLoader) {
        Iterator<UserFactory> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            UserFactory userFactory = iterator.next();
            System.out.println(userFactory.createUser());
        }
    }


}
