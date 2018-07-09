package hx.summer.framework.aop.proxy;

import hx.summer.framework.aop.entity.ProxyChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author mingliang(andyming @ aliyun.com)
 * @date 2018-07-08 13:56
 */
public abstract class AspectProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object methodinterceptor(ProxyChain proxyChain) throws Throwable {
        Object object;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getParams();
        begin();
        try {
            if (intercept(cls,method,params)){
                before(cls,method,params);
                object = proxyChain.doProxyChain();
                after(cls,method,params,object);
            }else {
                object = proxyChain.doProxyChain();
            }

        }catch (Exception e){
            LOGGER.error("proxy failure e:{}",e);
            throwable(cls,method,params,e);
            throw e;
        }finally {
            end();
        }

        return object;
    }

    public void begin(){

    }

    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable{
        return true;
    }

    public void before(Class<?> cls, Method method, Object[] params) throws Throwable{

    }

    public void after(Class<?> cls, Method method, Object[] params,Object object) throws Throwable{

    }

    public void throwable(Class<?> cls, Method method, Object[] params,Throwable e){

    }

    public void end(){

    }
}
