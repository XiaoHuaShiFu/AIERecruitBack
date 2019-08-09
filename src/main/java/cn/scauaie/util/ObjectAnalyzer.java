package cn.scauaie.util;

import org.springframework.http.HttpStatus;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * 描述: 用反射的通用toString方法
 *
 * @from Core Java Volume
 * @email 827032783@qq.com
 * @create 2019-08-08 21:31
 */
public class ObjectAnalyzer {
    //为了避免循环引用而导致的无限递归
    private ArrayList<Object> visited = new ArrayList<>();

    /**
     * 将任意对象toString
     *
     * @param object object
     * @return String
     */
    public String toString(Object object) {
        if (object == null) return "null";
        if (visited.contains(object)) return "...";

        visited.add(object);
        Class<?> c1 = object.getClass();
        if (c1 == String.class) return (String) object;
        //是否为数组
        if (c1.isArray()) {
            StringBuilder r = new StringBuilder(c1.getComponentType() + "[]{");
            for (int i = 0; i < Array.getLength(object); i++) {
                if (i > 0) {
                    r.append(",");
                }
                Object val = Array.get(object, i);
                //Class.isPrimitive 判断是否为原始类型（boolean char byte short int long float double )
                if (c1.getComponentType().isPrimitive()) {
                    r.append(val);
                } else {
                    r.append(toString(val));
                }
            }
            return r.toString() + "}";
        }

        StringBuilder r = new StringBuilder(c1.getName());

        do {
            r.append("[");
            //获取所有的域（属性）
            Field[] fields = c1.getDeclaredFields();
            //设置对象数组可访问 true表示屏蔽Java语言的访问检查，使得私有属性也可被查询和设置
            AccessibleObject.setAccessible(fields, true);

            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    if (!r.toString().endsWith("[")) r.append(",");
                    r.append(field.getName()).append("=");
                    try {
                        Class<?> type = field.getType();
                        Object val = field.get(object);
                        //是否是原始类型
                        if (type.isPrimitive()) {
                            r.append(val);
                        } else {
                            r.append(toString(val));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            r.append("]");
            c1 = c1.getSuperclass();
        } while (c1 != null);
        return r.toString();
    }
}
