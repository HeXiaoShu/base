package com.tk;
 
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import tk.mybatis.mapper.annotation.RegisterMapper;
import java.util.List;
 
/**
 * 自定义mapper方法，主要是解决批量插入/更新问题(主键字段也插入)
 * 
 * @author gzy
 * @version : 1.0
 * @since : 2017年1月5日 下午8:59:34
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
	int insertListUseAllCols(List<T> recordList);
}