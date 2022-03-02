package com.iqiny.silly.demo.common.sc;

import java.util.Collection;

public abstract class MyAssert {

    public static void notNull(Object obj) {
        notNull(obj, "参数不可为空");
    }

    public static void notNull(Object obj, String msg) {
        if (obj == null) {
            throw new MyException(msg);
        }
    }

    public static void notEmpty(String col) {
        notEmpty(col, "参数不可为空");
    }

    public static void notEmpty(String col, String msg) {
        if (col == null || col.isEmpty()) {
            throw new MyException(msg);
        }
    }


    public static void empty(String col, String msg) {
        if (col != null && !col.isEmpty()) {
            throw new MyException(msg);
        }
    }



    public static void notEmpty(Collection col) {
        notEmpty(col, "参数不可为空");
    }

    public static void notEmpty(Collection col, String msg) {
        if (col == null || col.isEmpty()) {
            throw new MyException(msg);
        }
    }

    public static void isTrue(boolean flag) {
        isTrue(flag, "应为true");
    }

    public static void isTrue(boolean flag, String msg) {
        if (!flag) {
            throw new MyException(msg);
        }
    }

    public static void isFalse(boolean flag, String msg) {
        if (flag) {
            throw new MyException(msg);
        }
    }
}
