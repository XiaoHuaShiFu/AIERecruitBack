package cn.scauaie.util;

import javax.validation.constraints.NotNull;

/**
 * 描述: 布尔工具类
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-15 21:12
 */
public final class BooleanUtils {

    public static boolean isBoolean(@NotNull String s) {
        return s.equals("true") || s.equals("false");
    }

}
