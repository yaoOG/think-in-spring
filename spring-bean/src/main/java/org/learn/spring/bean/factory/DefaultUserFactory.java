package org.learn.spring.bean.factory;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.SQLOutput;

/**
 * 默认 {@link UserFactory} 实现
 * @author zhuyao
 */
public class DefaultUserFactory implements UserFactory, InitializingBean, DisposableBean {
    // 1. 基于 @PostConstruct 注解
    @PostConstruct
    public void init() {
        System.out.println("@PostConstruct : UserFactory 初始化中");
    }

    // 2. 自定义初始化方法
    public void initUserFactory() {
        System.out.println("自定义初始化方法 initUserFactory() : UserFactory 初始化中");
    }

    // 3. 实现 InitializingBean 接口的 afterPropertiesSet() 方法
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("org.springframework.beans.factory.InitializingBean.afterPropertiesSet : UserFactory 初始化中");
    }

    //  1. 基于 @PreDestroy 注解
    @PreDestroy
    public void preDestroy() {
        System.out.println("@PreDestroy : UserFactory 销毁中");
    }

    // 2. 实现 DisposableBean 接口的 destroy() 方法
    @Override
    public void destroy() throws Exception {
        System.out.println("org.springframework.beans.factory.DisposableBean.destroy : UserFactory 销毁中");
    }

    public void doDestroy() {
        System.out.println("自定义销毁方法 : UserFactory 销毁中");
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("当前 DefaultUserFactory 正在被回收");
    }
}
