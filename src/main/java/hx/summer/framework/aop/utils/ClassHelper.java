package hx.summer.framework.aop.utils;

import hx.summer.framework.aop.annotations.Controller;
import hx.summer.framework.aop.annotations.Service;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * @author mingliang(andyming @ aliyun.com)
 * @date 2018-07-08 14:19
 */
public class ClassHelper {

    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = "";
        CLASS_SET = null;
    }

    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    public static Set<Class<?>> getServiceClass(){
        Set<Class<?>> classSet = new HashSet<>();
        CLASS_SET.forEach(v -> {
            if(v.isAnnotationPresent(Service.class))
                classSet.add(v);
        });
        return classSet;
    }

    public static Set<Class<?>> getControllerClass(){
        Set<Class<?>> classSet = new HashSet<>();
        CLASS_SET.forEach(v ->{
            if (v.isAnnotationPresent(Controller.class)){
                classSet.add(v);
            }
        });
        return classSet;
    }

    public static Set<Class<?>> getAllBeanClass(){
        Set<Class<?>> classSet = new HashSet<>();
        classSet.addAll(getServiceClass());
        classSet.addAll(getControllerClass());
        return classSet;
    }

    /**
     * 获取应用包下父类(接口)的子类
     * @param superClass
     * @return
     */
    public static Set<Class<?>> getClassBySuper(Class<?> superClass){
        Set<Class<?>> classSet = new HashSet<>();
        CLASS_SET.forEach(v ->{
            if (superClass.isAssignableFrom(v) && !superClass.equals(v)){
                classSet.add(v);
            }
        });
        return classSet;
    }

    public static Set<Class<?>> getClassByAnnotation(Class<? extends Annotation> cls){
        Set<Class<?>> classSet = new HashSet<>();
        CLASS_SET.forEach(v ->{
            if (v.isAnnotationPresent(cls)){
                classSet.add(v);
            }
        });
        return classSet;
    }
}
