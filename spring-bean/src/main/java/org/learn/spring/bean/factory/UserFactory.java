package org.learn.spring.bean.factory;

import org.learn.ioc.dependency.domain.User;

/**
 * {@link User} 工厂类
 * @author zhuyao
 */
public interface UserFactory {

    default User createUser(){
        return User.createUser();
    }
}
