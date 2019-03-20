package cnvd.weakpwdscan;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by carl on 2017/6/20.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleDefine {

    /**
     * 模块编号.
     */
    long moduleId();

    /**
     * 模块名称，页面的菜单中就显示这个名字.
     */
    String moduleName() default "";


    /**
     * 模块导航地址，前端页面的导航使用这个字段
     */
    String moduleMapping() default "";

    /**
     * 模块组ID，此模块属于哪个模块组
     */
    long moduleGroupId();

    /**
     * 模块导航地址栏的图标
     */
    String moduleIconCls() default "";
}
