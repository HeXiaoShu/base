package com.tk;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @program: he
 * @description:
 * @author: he
 * @create: 2020-01-14 18:29
 **/
public interface TkMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
