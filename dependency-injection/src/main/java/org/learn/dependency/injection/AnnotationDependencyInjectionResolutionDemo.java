package org.learn.dependency.injection;

import org.learn.dependency.injection.annotation.InjectedUser;
import org.learn.dependency.injection.annotation.MyAutowired;
import org.learn.ioc.dependency.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.*;

import static org.springframework.context.annotation.AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME;

/**
 * 注解驱动的依赖注入处理过程
 * 基于 {@link ObjectProvider}  实现延迟依赖注入
 * @author zhuyao
 */
public class AnnotationDependencyInjectionResolutionDemo {

    @Autowired          // 依赖查找（处理）
    private User user;  // DependencyDescriptor ->
                        // 必须（require=true）
                        // 实时注入（eager=true）
                        // 通过类型（User.class）
                        // 字段名称（"user"）
                        // 是否首要（primary = true）

    @Autowired
    private Map<String,User> users; // user superUser

    @MyAutowired
    private Optional<User> userOptional; // superUser

    @Inject
    private User injectedUser;

    @InjectedUser
    private User myInjectedUser;

/*    @Bean(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)
    public static AutowiredAnnotationBeanPostProcessor beanPostProcessor() {
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        // @Autowired + @Inject + 新注解 @InjectedUser
        Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<>(Arrays.asList(Autowired.class, Inject.class, InjectedUser.class));
        beanPostProcessor.setAutowiredAnnotationTypes(autowiredAnnotationTypes);
        return beanPostProcessor;
    }*/

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE - 3)
    public static AutowiredAnnotationBeanPostProcessor beanPostProcessor() {
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        beanPostProcessor.setAutowiredAnnotationType(InjectedUser.class);
        return beanPostProcessor;
    }

    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class （配置类）
        applicationContext.register(AnnotationDependencyInjectionResolutionDemo.class);
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        String xmlResourcePath = "META-INF/dependency-lookup-context.xml";
        // 加载 XML 资源，解析并且声称 BeanDefinition
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 依赖查找 AnnotationDependencyFieldInjectionDemo
        AnnotationDependencyInjectionResolutionDemo bean =
                applicationContext.getBean(AnnotationDependencyInjectionResolutionDemo.class);

        // 期待输出 superUser
        System.out.println("bean.user =" + bean.user);
        System.out.println("bean.injectedUser =" + bean.injectedUser);

        // 期待输出 user superUser
        System.out.println("bean.users =" + bean.users);

        // 期待输出 superUser
        System.out.println("bean.userOptional =" + bean.userOptional);
        // 期待输出 superUser
        System.out.println("bean.myInjectedUser =" + bean.myInjectedUser);

        //显示的关闭 Spring 应用上下文
        applicationContext.close();
    }
}
