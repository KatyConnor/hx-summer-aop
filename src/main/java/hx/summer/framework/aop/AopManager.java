package hx.summer.framework.aop;

import hx.summer.framework.aop.annotations.AspectAop;
import hx.summer.framework.aop.proxy.Proxy;
import hx.summer.framework.aop.proxy.ProxyFactory;
import hx.summer.framework.aop.utils.BeanHelper;
import hx.summer.framework.aop.utils.ClassHelper;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author mingliang(andyming @ aliyun.com)
 * @date 2018-07-08 14:55
 */
public final class AopManager {

    private static final Set<Class<? extends Annotation>> annotationSet = new HashSet<>();

    static {
        try {
            Map<Class<?>,Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>,List<Proxy>> targetMap = createTargetMap(proxyMap);
            targetMap.forEach((k,v) ->{
                Object proxy = ProxyFactory.createProxy(k,v);
                BeanHelper.setBean(k,proxy);
            });
        }catch (Exception e){

        }
    }

    /**
     * 获取注解 AspectAop 中切点类集合
     * @param aspectProxy
     * @return
     * @throws Exception
     */
    private static Set<Class<?>> createTargetClass(AspectAop aspectProxy) throws Exception{
        Set<Class<?>> classSet = new HashSet<>();
        Class<? extends Annotation> annotation = aspectProxy.value();
        if (annotationSet.contains(annotation)){
            throw new RuntimeException(annotation+ "annotation had ");
        }

        annotationSet.add(annotation);

        if (null != annotation && !annotation.equals(AspectAop.class)){
            classSet.addAll(ClassHelper.getClassByAnnotation(annotation));
        }
        return classSet;
    }

    /**
     * 切面
     * @return
     * @throws Exception
     */
    private static Map<Class<?>,Set<Class<?>>> createProxyMap() throws Exception{
        Map<Class<?>,Set<Class<?>>> proxyMap = new HashMap<>();
        Set<Class<?>> classSet = ClassHelper.getClassBySuper(hx.summer.framework.aop.proxy.AspectProxy.class);
        classSet.forEach(v ->{
            if (v.isAnnotationPresent(AspectAop.class)){
                AspectAop aspectProxy = v.getAnnotation(AspectAop.class);
                Set<Class<?>> targetClassSet = null;
                try {
                    targetClassSet = createTargetClass(aspectProxy);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                proxyMap.put(v,targetClassSet);
            }
        });
        return proxyMap;
    }

    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        proxyMap.forEach( (k,v) ->{
            v.forEach(value ->{
                try {
                    Proxy proxy = (Proxy) value.newInstance();
                    if (targetMap.containsKey(value)){
                        targetMap.get(value).add(proxy);
                    }else {
                        List<Proxy> proxyList = new ArrayList<>();
                        proxyList.add(proxy);
                        targetMap.put(value,proxyList);
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        });
        return targetMap;
    }
}
