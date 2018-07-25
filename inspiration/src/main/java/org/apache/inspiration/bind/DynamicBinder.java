package org.apache.inspiration.bind;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author Oliver XiLei 2018-07-16
 *
 */
@Component
public class DynamicBinder implements ApplicationContextAware {

    private PlaceholderConfigurationPropertiesBinder placeholderConfigurationPropertiesBinder;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        placeholderConfigurationPropertiesBinder = new PlaceholderConfigurationPropertiesBinder(applicationContext.getEnvironment());
    }

    public <T> T getBean(Class<T> clazz, Object... prefixPlaceholders) {
        T bean = applicationContext.getBean(clazz);
        placeholderConfigurationPropertiesBinder.bind(bean, prefixPlaceholders);
        return bean;
    }

    public <T> void coverNullProperties(T source, T target) {
        BeanUtil.coverNullProperties(source, target);
    }

}
