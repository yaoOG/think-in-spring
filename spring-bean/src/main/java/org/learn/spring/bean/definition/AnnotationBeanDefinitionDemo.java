package org.learn.spring.bean.definition;

import org.learn.ioc.dependency.container.AnnotationApplicationContextAsIocContainerDemo;
import org.learn.ioc.dependency.domain.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.genericBeanDefinition;

/**
 * 注解 BeanDefinition 示例
 * @author zhuyao
 */
@Import(AnnotationBeanDefinitionDemo.Config.class)  // 3. 通过 @Import来进行导入
public class AnnotationBeanDefinitionDemo {
    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 注册 Configuration Class （配置类）
        applicationContext.register(AnnotationBeanDefinitionDemo.class);

        // 按照 BeanDefinition 注册 API 实现
        // 1. 命名 Bean 的注册方式
        registerBeanDefinition(applicationContext,"tom");
        // 2. 非命名 Bean 的注册方式
        registerBeanDefinition(applicationContext);
        // 启动 Spring 应用上下文
        applicationContext.refresh();

        // 按照类型依赖查找
        System.out.println("Config 类型的所有 Beans ：" + applicationContext.getBeansOfType(Config.class));
        System.out.println("User 类型的所有 Beans ：" + applicationContext.getBeansOfType(User.class));

        //显示的关闭 Spring 应用上下文
        applicationContext.close();
    }

    /**
     * 命名 Bean 的注册方式
     *
     * @param registry
     * @param beanName
     */
    public static void registerBeanDefinition(BeanDefinitionRegistry registry, String beanName) {
        BeanDefinitionBuilder beanDefinitionBuilder = genericBeanDefinition(User.class);
        beanDefinitionBuilder
                .addPropertyValue("id", 1L)
                .addPropertyValue("name", "daniel");
        // 判断如果 beanName 参数存在是
        if (StringUtils.hasText(beanName)) {
            //注册 BeanDefinition
            registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
        } else {
            // 非命名 Bean 注册方式
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(), registry);
        }

    }

    public static void registerBeanDefinition(BeanDefinitionRegistry registry) {
        registerBeanDefinition(registry, null);
    }

    // 2. 通过 @Component 方式
    @Component//定义当前类作为 Spring Bean（组件）
    public static class Config {
        /**
         * 通过Java注解的方式定义一个Bean
         * @return
         */
        @Bean(name = {"user", "daniel-user"})// 1. 通过 @Bean 方式定义
        public User user() {
            User user = new User();
            user.setId(1L);
            user.setName("Tom");
            return user;
        }
    }


}
