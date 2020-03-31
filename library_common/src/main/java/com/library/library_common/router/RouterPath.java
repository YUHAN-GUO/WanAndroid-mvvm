package com.library.library_common.router;

/**
 * Creat by GuoYh
 * com.library.library_common.router
 * 2020/3/19
 */
public class RouterPath {
    /**
     * 首页组件
     */
    public static class Home {
        private static final String HOME = "/module_home";

        /**
         * 首页
         */
        public static final String PAGER_HOME = HOME + "/home";

    }

    /**
     * 我的组件
     */
    public static class User {
        private static final String User = "/user";

        /**
         * 首页
         */
        public static final String PAGER_USER = User + "/User";

    }
}
