package org.learn.spring.bean.definition;

import org.learn.ioc.dependency.domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultBeanNameGenerator;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.Bean;

/**
 * {@link org.springframework.beans.factory.config.BeanDefinition} 构建示例
 * @author zhuyao
 */
public class BeanDefinitionCreationDemo {
    public static void main(String[] args) {
        // 1.通过 BeanDefinationBuilder 构建
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        // 通过属性设置
        beanDefinitionBuilder.addPropertyValue("id", 1)
                .addPropertyValue("name", "Tom");
        // 获取 BeanDefinition 实例
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        //BeanDefinition 并非 Bean 的最终状态，可以自定义修改


        // 2.通过AbstractBeanDefinition 以及派生类
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        // 设置 Bean 类型
        genericBeanDefinition.setBeanClass(User.class);
        // 通过 MutablePropertyValues 批量操作属性
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("id", 1)
                .add("name", "Tom");
        genericBeanDefinition.setPropertyValues(propertyValues);

    }
}
