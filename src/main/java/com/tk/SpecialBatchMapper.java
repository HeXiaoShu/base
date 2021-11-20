package com.tk;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import tk.mybatis.mapper.annotation.RegisterMapper;
import java.util.List;

/**
 * 自定义mapper方法，主要是解决批量插入/更新问题(主键字段也插入)
 */
@RegisterMapper
public interface SpecialBatchMapper<T> {

	/**
	 * 批量插入数据库，所有字段都插入，包括主键
	 *
	 * @return
	 */
	@Options(useGeneratedKeys = true, keyProperty = "id")
	@InsertProvider(type = SpecialBatchProvider.class, method = "insertListUseAllCols")
	int insertBatchAllCols(List<T> recordList);

}
