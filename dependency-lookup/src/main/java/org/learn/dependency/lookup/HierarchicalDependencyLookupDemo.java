package org.learn.dependency.lookup;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 层次性依赖查找
 * @author zhuyao
 */
public class HierarchicalDependencyLookupDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ObjectProviderDemo.class);

        // 1. 获取 HierarchicalBeanFactory  <- ConfigurableBeanFactory  <- ConfigurableListableBeanFactory
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        System.out.println("当前 BeanFactory 的 Parent BeanFactory ：" + beanFactory.getParentBeanFactory());

        // 2.设置 Parent BeanFactory
        HierarchicalBeanFactory parentBeanFactory = createParentBeanFactory();
        beanFactory.setParentBeanFactory(parentBeanFactory);
        System.out.println("当前 BeanFactory 的 Parent BeanFactory ：" + beanFactory.getParentBeanFactory());

        displayContainsLocalBean(beanFactory, "user");
        displayContainsLocalBean(parentBeanFactory, "user");

        displayContainsBean(beanFactory, "user");
        displayContainsBean(parentBeanFactory, "user");

        applicationContext.refresh();
        applicationContext.close();
    }

    public static void displayContainsBean(HierarchicalBeanFactory beanFactory, String beanName) {
        System.out.printf("当前 BeanFactory[%s] 是否包含 bean[name : %s ] : %s\n", beanFactory, beanName, containsBean(beanFactory, beanName));
    }

    public static boolean containsBean(HierarchicalBeanFactory beanFactory, String beanName) {
        BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
        if (parentBeanFactory instanceof HierarchicalBeanFactory) {
            HierarchicalBeanFactory parentHierarchicalBeanFactory = HierarchicalBeanFactory.class.cast(parentBeanFactory);
            if (containsBean(parentHierarchicalBeanFactory, beanName)) {
                return true;
            }
        }
        return beanFactory.containsBean(beanName);
    }

    public static void displayContainsLocalBean(HierarchicalBeanFactory beanFactory, String beanName) {
        System.out.printf("当前 BeanFactory[%s] 是否包含 Local  bean[name : %s ] : %s\n", beanFactory, beanName, beanFactory.containsLocalBean(beanName));
    }

    public static HierarchicalBeanFactory createParentBeanFactory() {
        //第一步：创建BeanFactory容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //XmlBeanDefinitionReader  bean读取器
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        //第二步：加载配置
        String location = "META-INF/dependency-lookup-context.xml";
        reader.loadBeanDefinitions(location);
        return beanFactory;
    }
}
