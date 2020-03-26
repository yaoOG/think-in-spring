package org.learn.dependency.lookup;

import org.learn.ioc.dependency.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 类型安全的依赖查找示例
 * @author zhuyao
 */
public class TypeSafetyDependencyLookupDemo {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(TypeSafetyDependencyLookupDemo.class);
        applicationContext.refresh();
        // 演示 org.springframework.beans.factory.BeanFactory.getBean(java.lang.Class<T>) 方法的安全性
        displayBeanFactoryGetBean(applicationContext);
        // 演示 org.springframework.beans.factory.ObjectFactory.getObject 方法的安全性
        displayObjectFactoryGetObject(applicationContext);
        // 演示 org.springframework.beans.factory.ObjectProvider.getIfAvailable() 方法的安全性
        displayObjectProviderIfAvailable(applicationContext);
        // 演示 org.springframework.beans.factory.ListableBeanFactory.getBeansOfType(java.lang.Class<T>) 方法的安全性
        displayListableBeanFactoryGetBeansOfType(applicationContext);
        // 演示 org.springframework.beans.factory.ObjectProvider.stream 方法的安全性
        displayObjectProviderStreamOps(applicationContext);
        applicationContext.close();
    }

    private static void displayObjectProviderStreamOps(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
        printBeansExcetion("displayObjectProviderStreamOps", () -> userObjectProvider.forEach(System.out::println));
    }

    private static void displayListableBeanFactoryGetBeansOfType(ListableBeanFactory listableBeanFactory) {
        printBeansExcetion("displayListableBeanFactoryGetBeansOfType", () -> listableBeanFactory.getBeansOfType(User.class));

    }

    private static void displayObjectProviderIfAvailable(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
        printBeansExcetion("displayObjectProviderIfAvailable", () -> userObjectProvider.getIfAvailable());
    }

    private static void displayObjectFactoryGetObject(AnnotationConfigApplicationContext applicationContext) {
        // ObjectProvider is  ObjectFactory
        ObjectFactory<User> userObjectFactory = applicationContext.getBeanProvider(User.class);
        printBeansExcetion("displayBeanFactoryGetBean", () -> userObjectFactory.getObject());
    }

    public static void displayBeanFactoryGetBean(BeanFactory beanFactory) {
        printBeansExcetion("displayBeanFactoryGetBean", () -> beanFactory.getBean(User.class));
    }

    public static void printBeansExcetion(String source, Runnable runnable) {

        System.err.println("===================================");
        System.err.println("Source from :" + source);
        try {
            runnable.run();
        } catch (BeansException exception) {
            exception.printStackTrace();
        }

    }
}
