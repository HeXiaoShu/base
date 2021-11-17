package com.util;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @Description 树形结构数据工具类
 * @Author Hexiaoshu
 * @Date 2021/4/17
 * @modify
 */
public class TreeUtil {


    /**
     * 列表型,层次结构
     * @param list
     */
    public static void createTreeTable(List<Test> list){
        TreeMap<Long, List<Test>> collect = list.parallelStream().collect(Collectors.groupingBy(Test::getParentId, TreeMap::new, Collectors.toList()));
        System.out.println(collect);
    }


    /**
     * 有层级关系集合转，树形结构 ,适用用于 菜单树，部门树, 地区树 -->场景
     * example：TreeUtil.createTree(null, all, "id", "parentId", "child", 0L);
     *  null 为全遍历
     * @param t             遍历对象, 当前父级
     * @param list          原集合
     * @param curFiled      当前本级标识字段属性 eg ：id
     * @param parentFiled   当前父级标识字段属性 eg ：parentId
     * @param chidrenFiled  子集合存储标识属性   eg ：child
     * @param topTag        顶级标识字段属性值
     * @param <T>           泛型
     * @return 展开型 list，传入对象的所有下级，不包含当前，如要包含 t.setChild(TreeUtil.createTree(null, all, "id", "parentId", "child", 0L))
     */
    public static <T> List<T> createTree(T t,List<T> list,String curFiled,String parentFiled,String chidrenFiled,Object topTag){
        List<T> children;
        if (t != null) {
            children = list.parallelStream().filter(e -> Objects.equals(getInvoke(parentFiled, e), getInvoke(curFiled, t))).collect(Collectors.toList());
        } else {
            children = list.parallelStream().filter(e -> getInvoke(parentFiled,e) == topTag).collect(Collectors.toList());
        }
        if (children.size() == 0) {
            return null;
        }
        children.forEach(child -> setInvoke(chidrenFiled,child,createTree(child, list, curFiled, parentFiled, chidrenFiled, topTag)));
        return children;
    }


    /**
     * 根据属性名获取属性值 get
     * @param fieldName 属性名称
     * @param o 调用对象
     * @return 返回值
     */
    private static Object getInvoke(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = String.format("get%s%s", firstLetter,fieldName.substring(1));
            Method method = o.getClass().getDeclaredMethod(getter);
            return method.invoke(o);
        } catch (Exception e) {
            System.out.println(fieldName+">>>>方法执行异常");
            return null;
        }
    }

    /**
     * 根据属性名获取属性值 set
     * @param fieldName 属性名称
     * @param o 调用对象
     * @return 返回值
     */
    private static void setInvoke(String fieldName, Object o,List arg) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String setter = String.format("set%s%s", firstLetter,fieldName.substring(1));
            Method method = o.getClass().getDeclaredMethod(setter,List.class);
            method.invoke(o,arg);
        } catch (Exception e) {
            System.out.println(fieldName+">>>>方法执行异常");
        }
    }

}
