package com.xudong.data.mapper;

import java.util.List;

import com.xudong.domain.model.TalkSkill;
import com.xudong.domain.query.TalkSkillQuery;
import org.apache.ibatis.annotations.Param;
import java.io.Serializable;

public interface TalkSkillMapper  {
	/***/
	TalkSkill load(Integer id);

	/***/
	void insert(TalkSkill talkSkill);

	/***/
	void update(TalkSkill talkSkill);

	/***/
	void updateStatus(@Param("id") Integer id, @Param("status") Serializable status);

    /***/
    void updateIsDeleted(@Param("id") Integer id, @Param("isDeleted") int status);

	/***/
	void delete(Integer id);

	/***/
	List<TalkSkill> queryList(TalkSkillQuery talkSkillQuery);

	/***/
	int queryCount(TalkSkillQuery talkSkillQuery);
}