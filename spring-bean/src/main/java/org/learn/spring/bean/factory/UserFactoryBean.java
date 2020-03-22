package org.learn.spring.bean.factory;

import org.learn.ioc.dependency.domain.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * {@link org.learn.ioc.dependency.domain.User }Bean 的 {@link org.springframework.beans.factory.FactoryBean} 实现
 * @author zhuyao
 */
public class UserFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return User.createUser();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
