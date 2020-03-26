package org.learn.dependency.injection;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 基于 API 实现依赖 Constructor 方法注入示例
 * @author zhuyao
 */
public class ApiDependencyConstructorInjectionDemo {
    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        // 生成 UserHoler 的 BeanDefinition
        BeanDefinition beanDefinition = createUserHolderBeanDefinition();
        // 注册 UserHoler 的 BeanDefinition
        applicationContext.registerBeanDefinition("userHolder", beanDefinition);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        String xmlResourcePath = "META-INF/dependency-lookup-context.xml";
        // 加载 XML 资源，解析并且声称 BeanDefinition
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);
        // 启动 Spring 应用上下文
        applicationContext.refresh();
        UserHolder userHolder = applicationContext.getBean(UserHolder.class);
        System.out.println(userHolder);

        //显示的关闭 Spring 应用上下文
        applicationContext.close();
    }

    /**
     * 为 {@link UserHolder} 生成 {@link BeanDefinition}
     * @return BeanDefinition
     */
    private static BeanDefinition createUserHolderBeanDefinition() {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(UserHolder.class);
        beanDefinitionBuilder.addConstructorArgReference("superUser");
        return beanDefinitionBuilder.getBeanDefinition();
    }
}
