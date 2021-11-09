package com.tk;

import com.constant.DateOrder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.model.User;
import com.util.StringUtil;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Hexiaoshu
 * @Date 2021/4/13
 * @modify
 */
public class TkServiceImpl<T,B extends TkMapper<T>> implements TkService<T> {
    @Resource
    private B baseMapper;

    @Override
    public T getById(Long id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public T getOneByParam(T t) {
        return baseMapper.selectOneByExample(t);
    }

    @Override
    public List<T> getListEqual(T t, DateOrder dateOrder) {
        Example example=new Example(t.getClass());
        if (dateOrder.getOrder()){
            example.orderBy("createTime").desc();
        }
        return baseMapper.selectByExample(t);
    }

    @Override
    public List<T> getListLike(Map<String, Object> paraMap, DateOrder dateOrder, Class<T> c) {
        Example example=new Example(c);
        if (dateOrder.getOrder()){
            example.orderBy("createTime").desc();
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
            example.orderBy("createTime").desc();
        }
        List<T> list = baseMapper.selectByExample(t);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<T> getListLikePage(Map<String, Object> paraMap, DateOrder dateOrder, Integer curPage, Integer pageSize, Class<T> c) {
        PageHelper.startPage(curPage,pageSize);
        Example example=new Example(c);
        if (dateOrder.getOrder()){
            example.orderBy("createTime").desc();
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
    public int deleteById(Long id) {
        return baseMapper.deleteByPrimaryKey(id);
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
