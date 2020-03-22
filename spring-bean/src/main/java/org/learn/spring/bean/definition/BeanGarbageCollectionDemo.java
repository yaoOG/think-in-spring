package org.learn.spring.bean.definition;

import org.learn.spring.bean.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Bean 垃圾回收实例
 * @author zhuyao
 */
public class BeanGarbageCollectionDemo {

    public static void main(String[] args) throws InterruptedException {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 装配
        applicationContext.register(BeanInitializationDemo.class);
        applicationContext.refresh();
        applicationContext.close();
        Thread.sleep(5000L);
        // 强制 GC
        System.gc();
        Thread.sleep(5000L);
    }
}
