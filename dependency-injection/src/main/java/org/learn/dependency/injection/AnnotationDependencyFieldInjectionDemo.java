package org.learn.dependency.injection;

import org.learn.ioc.dependency.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * 基于 Java 注解的依赖 字段注入示例
 * @author zhuyao
 */
public class AnnotationDependencyFieldInjectionDemo {

    @Autowired // @Autowired 会忽略掉静态字段
    private
//    static
            UserHolder userHolder;

    @Resource
    private UserHolder userHolder2;

    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class （配置类）
        applicationContext.register(AnnotationDependencyFieldInjectionDemo.class);
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        String xmlResourcePath = "META-INF/dependency-lookup-context.xml";
        // 加载 XML 资源，解析并且声称 BeanDefinition
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 依赖查找 AnnotationDependencyFieldInjectionDemo
        AnnotationDependencyFieldInjectionDemo bean = applicationContext.getBean(AnnotationDependencyFieldInjectionDemo.class);
        // @Autowired 字段关联
        System.out.println(bean.userHolder);
        System.out.println(bean.userHolder2);
        System.out.println(bean.userHolder == bean.userHolder2);
        //显示的关闭 Spring 应用上下文
        applicationContext.close();
    }

    @Bean
    public UserHolder userHolder(User user) {
        UserHolder userHolder = new UserHolder();
        userHolder.setUser(user);
        return userHolder;

    }
}
