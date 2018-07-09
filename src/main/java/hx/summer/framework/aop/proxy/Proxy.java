package hx.summer.framework.aop.proxy;

import hx.summer.framework.aop.entity.ProxyChain;

/**
 * @author mingliang(andyming @ aliyun.com)
 * @date 2018-07-08 13:36
 */
public interface Proxy {

    Object methodinterceptor(ProxyChain proxyChain) throws Throwable;
}
