package hx.summer.framework.aop.annotations;

import java.lang.annotation.*;

/**
 * @author mingliang(andyming @ aliyun.com)
 * @date 2018-07-08 13:33
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AspectAop {

    Class<? extends Annotation> value();
}
