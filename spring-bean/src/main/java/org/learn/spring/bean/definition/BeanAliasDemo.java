package org.learn.spring.bean.definition;

import org.learn.ioc.dependency.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bean 别名示例
 * @author zhuyao
 */
public class BeanAliasDemo {
    public static void main(String[] args) {
        //配置 XML 文件
        //启动 Spring 应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("META-INF/bean-definition-context.xml");
        // 通过别名 daniel-user 获取曾用名 user 的 bean
        User user = beanFactory.getBean("user", User.class);
        User daniel = beanFactory.getBean("daniel-user", User.class);
        System.out.println("daniel-user 是否与 user Bean 相同 : " + (daniel == user));
    }
}
