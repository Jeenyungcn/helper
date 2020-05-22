package com.seeyon.oversea.utils;

import com.seeyon.oversea.db.GenericBean;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class BeanHelper
{
    /**
     * 将Map转为JavaBean
     * @param map Map对象
     * @param classz JavaBean的字节码对象
     * @return 返回Object对象
     */
    public static <T> T toBean(Map<String,Object> map, Class<T> classz)
    {
        if(map==null|map.size()<1)
        {
            return null;
        }
        T bean = null;

        try
        {
            bean = classz.newInstance();

            List<Method> setMethodList = new ArrayList<Method>();

            Set<String> keySet = map.keySet();

            Method[] methodArray = classz.getDeclaredMethods();

            for(Method method:methodArray)
            {
                String methodName = method.getName();
                if(methodName.startsWith("set"))
                {
                    for(String key:keySet)
                    {
                        if(methodName.replaceFirst("set","").equalsIgnoreCase(key))
                        {
                            method.invoke(bean, map.get(key));
                            keySet.remove(key);
                        }
                    }
                }

                if(keySet.size()==0)
                {
                    break;
                }
            }
        } catch (InstantiationException e)
        {
                e.printStackTrace();
        } catch (IllegalAccessException e)
        {
                e.printStackTrace();
        } catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }

        return bean;
    }

    /**
     * 将Map转为JavaBean
     * @param mapList Map对象
     * @param classz JavaBean的字节码对象
     * @return 返回Object的List集合
     */
    public static <T> List<T> toBeanList(List<Map<String,Object>> mapList, Class<T> classz)
    {
        if(mapList==null||mapList.size()==0)
        {
            return null;
        }

        List<T> beanList = new ArrayList<T>();

        for(Map<String,Object> row:mapList)
        {
            beanList.add(toBean(row, classz));
        }

        return beanList;
    }

    /**
     * 将JavaBean转为Map
     * @param obj JavaBean对象
     * @return Map
     */
    public static Map<String,Object> toMap(Object obj)
    {
        if(obj==null)
        {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        Class c= obj.getClass();

        Method[] methods = c.getDeclaredMethods();

        for(Method method:methods)
        {
            String methodName = method.getName();
            if(!methodName.startsWith("get"))
            {
                continue;
            }
            String key = methodName.replaceFirst("get", "");
            Object value = null;
            try
            {
                value = method.invoke(obj);
            } catch (IllegalAccessException e)
            {
                e.printStackTrace();
            } catch (InvocationTargetException e)
            {
                e.printStackTrace();
            }

            map.put(key, value);
        }

        return map;
    }


    public static List<Map<String,Object>> toMapList(List<Object> objects)
    {
        if(objects==null||objects.size()<1)
        {
            return null;
        }

        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        for(Object obj:objects)
        {
            Map<String,Object> map = toMap(obj);
            mapList.add(map);
        }

        return mapList;
    }
}
