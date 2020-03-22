package org.learn.ioc.dependency.lookup;

import org.learn.ioc.dependency.annotation.Supper;
import org.learn.ioc.dependency.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * 依赖查找实例
 * 1.通过名称的方式来查找
 * 2.通过类型来查找
 * @author zhuyao
 */
public class DependencyLookupDemo {
    public static void main(String[] args) {
        //配置 XML 文件
        //启动 Spring 应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("META-INF/dependency-lookup-context.xml");
//        lookupInRealTime(beanFactory);
//        lookupInLzay(beanFactory);
        lookupByType(beanFactory);
        lookupByCollectionType(beanFactory);
        lookupByAnnotation(beanFactory);
    }


    /**
     * 根据注解查找集合对象
     * @param beanFactory
     */
    private static void lookupByAnnotation(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = (Map) listableBeanFactory.getBeansWithAnnotation(Supper.class);
            System.out.println("查找标注 @Super 注解的所有 User 集合对象："+ users);
        }
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

    /**
     * 按照类型查找
     * @param beanFactory
     */
    private static void lookupByType(BeanFactory beanFactory) {
        User user = beanFactory.getBean(User.class);
        System.out.println("实时查找"+user);
    }

    /**
     * 通过名称延迟查找
     * @param beanFactory
     */
    private static void lookupInLzay(BeanFactory beanFactory) {
        ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
        User user = objectFactory.getObject();
        System.out.println("延迟查找"+user);
    }

    /**
     * 通过名称实时查找
     * @param beanFactory
     */
    private static void lookupInRealTime(BeanFactory beanFactory) {
        User user = (User) beanFactory.getBean("user");
        System.out.println("实时查找"+user);
    }
}
