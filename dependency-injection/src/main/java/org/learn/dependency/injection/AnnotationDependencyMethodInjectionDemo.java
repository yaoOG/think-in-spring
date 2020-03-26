package org.learn.dependency.injection;

import org.learn.ioc.dependency.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * 基于 Java 注解的依赖方法注入示例
 * @author zhuyao
 */
public class AnnotationDependencyMethodInjectionDemo {

    private UserHolder userHolder;
    private UserHolder userHolder2;

    @Autowired
    public void initUserHolder(UserHolder userHolder) {
        this.userHolder = userHolder;
    }
    @Resource
    public void initUserHolder2(UserHolder userHolder2) {
        this.userHolder2 = userHolder2;
    }
    @Bean
    public UserHolder userHolder(User user) {
        return new UserHolder(user);
    }
    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class （配置类）
        applicationContext.register(AnnotationDependencyMethodInjectionDemo.class);
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        String xmlResourcePath = "META-INF/dependency-lookup-context.xml";
        // 加载 XML 资源，解析并且声称 BeanDefinition
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 依赖查找 AnnotationDependencyFieldInjectionDemo
        AnnotationDependencyMethodInjectionDemo bean = applicationContext.getBean(AnnotationDependencyMethodInjectionDemo.class);
        // @Autowired 字段关联
        System.out.println(bean.userHolder);
        System.out.println(bean.userHolder2);
        System.out.println(bean.userHolder == bean.userHolder2);
        //显示的关闭 Spring 应用上下文
        applicationContext.close();
    }
}
