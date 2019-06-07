package com.xudong.data.mapper;

import java.util.List;

import com.xudong.domain.model.SensitiveWord;
import com.xudong.domain.query.SensitiveWordQuery;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

public interface SensitiveWordMapper  {
	/***/
	SensitiveWord load(Integer id);

	/***/
	void insert(SensitiveWord sensitiveWord);

	/***/
	void update(SensitiveWord sensitiveWord);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

    /***/
    void updateIsDeleted(@Param("id") Integer id, @Param("isDeleted") int status);

	/***/
	void delete(Integer id);

	/***/
	List<SensitiveWord> queryList(SensitiveWordQuery sensitiveWordQuery);

	/***/
	int queryCount(SensitiveWordQuery sensitiveWordQuery);
}