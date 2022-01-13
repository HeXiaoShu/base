package com.tk;

import com.constant.DateOrder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description
 * @Author Hexiaoshu
 * @Date 2021/4/13
 * @modify
 */
public class TkServiceImpl<B extends TkMapper<T>,T> implements TkService<T> {

    private static final String DATE_ORDER_NAME ="createTime";

    @Autowired(required = false)
    private B baseMapper;

    @Override
    public T getById(Long id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public T getOneByParam(T t) {
        return baseMapper.selectOne(t);
    }

    @Override
    public List<T> getListEqual(T t, DateOrder dateOrder) {
        Example example=new Example(t.getClass());
        if (dateOrder.getOrder()){
            example.orderBy(DATE_ORDER_NAME).desc();
        }
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(t);
        return baseMapper.selectByExample(example);
    }

    @Override
    public List<T> getListLike(Map<String, Object> paraMap, DateOrder dateOrder, Class<T> c) {
        Example example=new Example(c);
        if (dateOrder.getOrder()){
            example.orderBy(DATE_ORDER_NAME).desc();
        }
        Example.Criteria criteria = example.createCriteria();
        if (!paraMap.isEmpty()){
            paraMap.forEach((k,v)->{
                String value = StringUtil.appendLike((String) v);
                criteria.andLike(k,value);
            });
        }
        return baseMapper.selectByExample(example);
    }

    @Override
    public PageInfo<T> getListEqualPage(T t,DateOrder dateOrder,Integer curPage, Integer pageSize) {
        PageHelper.startPage(curPage,pageSize);
        Example example=new Example(t.getClass());
        if (dateOrder.getOrder()){
            example.orderBy(DATE_ORDER_NAME).desc();
        }
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(t);
        List<T> list = baseMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<T> getListLikePage(Map<String, Object> paraMap, DateOrder dateOrder, Integer curPage, Integer pageSize, Class<T> c) {
        PageHelper.startPage(curPage,pageSize);
        Example example=new Example(c);
        if (dateOrder.getOrder()){
            example.orderBy(DATE_ORDER_NAME).desc();
        }
        Example.Criteria criteria = example.createCriteria();
        if (!paraMap.isEmpty()){
            paraMap.forEach((k,v)->{
                String value = StringUtil.appendLike((String) v);
                criteria.andLike(k,value);
            });
        }
        List<T> list = baseMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public List<T> getAll() {
        return baseMapper.selectAll();
    }

    @Override
    public int insert(T t) {
        return baseMapper.insertSelective(t);
    }

    @Override
    public Integer insertBatch(List<T> t) {
        return baseMapper.insertListUseAllCols(t);
    }

    @Override
    public int deleteById(Long id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByIdBatch(List<Long> ids, Class<T> c) {
        AtomicReference<String> idName= new AtomicReference<>("");
        Field[] declaredFields = c.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(f->{
            if (f.isAnnotationPresent(Id.class)){
                idName.set(f.getName());
            }
        });
        Example example=new Example(c);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn(idName.get(),ids);
        return baseMapper.deleteByExample(example);
    }

    @Override
    public int deleteEqual(T t) {
        return baseMapper.delete(t);
    }

    @Override
    public int updateById(T t) {
        return baseMapper.updateByPrimaryKeySelective(t);
    }
}
