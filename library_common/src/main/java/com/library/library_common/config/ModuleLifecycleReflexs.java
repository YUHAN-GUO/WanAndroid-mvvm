package com.library.library_common.config;

/**
 * 应用模块: common
 * <p>
 * 类描述: 组件生命周期初始化类的类目管理者,在这里注册需要初始化的组件,通过反射动态调用各个组件的初始化方法
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-02-25
 */
public class ModuleLifecycleReflexs {
    /**
     * 基础库
     */
    private static final String BaseModuleInit =
            "com.library.library_base.base.BaseModuleInit";

    /**
     * 网络库
     */
    private static final String NetworkInit =
            "com.library.library_network.NetworkModuleInit";

    /**
     * Common初始化
     */
    private static final String CommonInit =
            "com.library.library_common.CommonModuleInit";

    public static String[] initModuleNames = {BaseModuleInit, NetworkInit, CommonInit};
}
