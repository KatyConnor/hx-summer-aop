package hx.summer.framework.aop.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author mingliang(andyming @ aliyun.com)
 * @date 2018-07-08 14:13
 */
public final class BeanHelper {

    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getAllBeanClass();
        beanClassSet.forEach(v ->{
            Object obj = null;
            BEAN_MAP.put(v,obj);
        });
    }

    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    public static <T> T getBean (Class<T> cls){
        if (!BEAN_MAP.containsKey(cls)){
            throw new RuntimeException("can not get bean by class:"+ cls);
        }
        return (T) BEAN_MAP.get(cls);
    }

    public static void setBean(Class<?> cls, Object obj){
        BEAN_MAP.put(cls,obj);
    }
}
