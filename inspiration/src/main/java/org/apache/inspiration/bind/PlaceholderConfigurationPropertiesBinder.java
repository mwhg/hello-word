package org.apache.inspiration.bind;

import java.lang.annotation.Annotation;
import java.text.MessageFormat;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.handler.IgnoreErrorsBindHandler;
import org.springframework.boot.context.properties.bind.handler.IgnoreTopLevelConverterNotFoundBindHandler;
import org.springframework.boot.context.properties.bind.handler.NoUnboundElementsBindHandler;
import org.springframework.boot.context.properties.source.UnboundElementsSourceFilter;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;

/**
 * @see org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor#postProcessBeforeInitialization(Object,
 *      String)
 * @see org.springframework.boot.context.properties.ConfigurationPropertiesBinder
 *
 * @author Oliver XiLei 2018-07-13
 *
 */
public class PlaceholderConfigurationPropertiesBinder {

    private final Binder binder;

    public PlaceholderConfigurationPropertiesBinder(Environment environment) {
        binder = Binder.get(environment);
    }

    public <T> T bind(T bean, Object... prefixPlaceholders)
            throws BeansException {
        PlaceholderConfigurationProperties annotation = AnnotationUtils.findAnnotation(bean.getClass(), PlaceholderConfigurationProperties.class);
        if (annotation != null) {
            ResolvableType type = ResolvableType.forClass(bean.getClass());
            Annotation[] annotations = new Annotation[] { annotation };
            Bindable<?> target = Bindable.of(type).withExistingValue(bean).withAnnotations(annotations);
            BindHandler bindHandler = getBindHandler(annotation);
            String prefix = MessageFormat.format(annotation.prefix(), prefixPlaceholders);
            binder.bind(prefix, target, bindHandler);
        }
        return bean;
    }

    private BindHandler getBindHandler(PlaceholderConfigurationProperties annotation) {
        BindHandler handler = new IgnoreTopLevelConverterNotFoundBindHandler();
        if (annotation != null) {
            if (annotation.ignoreInvalidFields()) {
                handler = new IgnoreErrorsBindHandler(handler);
            }
            if (!annotation.ignoreUnknownFields()) {
                UnboundElementsSourceFilter filter = new UnboundElementsSourceFilter();
                handler = new NoUnboundElementsBindHandler(handler, filter);
            }
        }
        return handler;
    }

}
