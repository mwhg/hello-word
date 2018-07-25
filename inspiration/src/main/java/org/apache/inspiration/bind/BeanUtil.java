package org.apache.inspiration.bind;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.util.ReflectionUtils;

public class BeanUtil {

    /**
     * copy source field value to set target field if target field is null
     *
     * ignore non null fields
     * 
     * @param source
     * @param target
     */
    public static <T> void coverNullProperties(T source, T target) {
        String[] ignoreProperties = getNonNullFields(target);
        BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    /**
     * ignore static field
     *
     * ignore non null fields
     *
     * contain super class fields
     *
     * @param bean
     * @return
     */
    public static String[] getNonNullFields(Object bean) {
        List<String> result = new ArrayList<>();
        Class<?> clazz = bean.getClass();
        do {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                try {
                    if (!Modifier.isStatic(field.getModifiers())) {
                        ReflectionUtils.makeAccessible(field);
                        if (field.get(bean) != null) {
                            result.add(field.getName());
                        }
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new InvalidPropertyException(clazz, field.getName(), e.getMessage(), e);
                }
            }
        } while ((clazz = clazz.getSuperclass()) != null);
        return result.toArray(new String[result.size()]);
    }

}
