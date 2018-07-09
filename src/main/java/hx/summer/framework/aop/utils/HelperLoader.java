package hx.summer.framework.aop.utils;

import hx.summer.framework.aop.AopManager;

/**
 * @author mingliang(andyming @ aliyun.com)
 * @date 2018-07-08 16:55
 */
public final class HelperLoader {

    public static void init(){
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopManager.class
        };
        for (int i = 0; i < classList.length; i++) {
            //初始化加载
        }
    }
}
