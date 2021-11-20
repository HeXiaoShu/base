package com.tk;

import com.constant.DateOrder;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Hexiaoshu
 * @Date 2021/4/13
 * @modify
 */
@Transactional(rollbackFor = Exception.class)
public interface TkService <T> {

    /**
     * 根据id查询单条
     * @param id id
     * @return T
     */
    T getById(Long id);

    /**
     * 根据参数查询单条数据
     * @param t entity
     * @return T entity
     */
    T getOneByParam(T t);

    /**
     * 参数精确查询列表
     * @param t entity
     * @param dateOrder 日期降序
     * @return list
     */
    List<T> getListEqual(T t,DateOrder dateOrder);

    /**
     * 参数精确查询列表
     * @param paraMap 参数列表，k-> 数据库字段
     * @param dateOrder 日期降序
     * @return list
     */
    List<T> getListLike(Map<String, Object> paraMap, DateOrder dateOrder, Class<T> c);

    /**
     * 参数精确查询列表-分页
     * @param t entity
     * @param dateOrder 日期降序
     * @return list
     */
    PageInfo<T> getListEqualPage(T t,DateOrder dateOrder,Integer curPage, Integer pageSize);

    /**
     * 参数精确查询列表-分页
     * @param paraMap 参数列表，k-> 数据库字段
     * @param dateOrder 日期降序
     * @return list
     */
    PageInfo<T> getListLikePage(Map<String, Object> paraMap,DateOrder dateOrder,Integer curPage, Integer pageSize,Class<T> c);

    /**
     * 查询所有
     * @return list
     */
    List<T> getAll();


    /**
     * 新增。null不设值
     * @param t entity
     */
    int insert(T t);

    /**
     * 批量新增, 属性为null时，会插入null数据，注意设置
     * @param t 集合
     * @return int
     */
    void insertBatch(List<T> t);

    /**
     * 删除
     * @param id id
     * @return int
     */
    int  deleteById(Long id);

    /**
     * 批量删除 - 注：主键id在实体类第一个属性
     * @param ids 主键id集合
     * @param c   类.class
     * @return int
     */
    int  deleteByIdBatch(List<Long> ids,Class<T> c);

    /**
     * 删除-精确条件
     * @param t 对象
     * @return int
     */
    int  deleteEqual(T t);

    /**
     * 根据id编辑
     * @param t entity
     * @return int
     */
    int  updateById(T t);


}
