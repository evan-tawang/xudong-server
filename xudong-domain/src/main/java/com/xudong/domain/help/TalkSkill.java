package com.xudong.domain.help;

import java.io.Serializable;
import java.util.Date;

/**
*常用话术实体类
*/
public class TalkSkill implements Serializable {
	private static final long serialVersionUID = 15593867104742L;

	
	
						private Integer id;//		
					private Date gmtCreate;//		
					private Date gmtModify;//		
					private Integer isDeleted;//是否删除(0 未删除 1 删除)		
					private Integer status;//状态(1 正常 2 停用)		
					private String content;//内容		
		
		

		public TalkSkill() {}

		/**
	*
		 *@param id -- 
		*/
	public TalkSkill(Integer id) {
					this.id = id;
			}

			/***/
	public Integer getId() {
		return id;
	}
	/***/
	public void setId(Integer id) {
		this.id = id;
	}
		/***/
	public Date getGmtCreate() {
		return gmtCreate;
	}
	/***/
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
		/***/
	public Date getGmtModify() {
		return gmtModify;
	}
	/***/
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}
		/**是否删除(0 未删除 1 删除)*/
	public Integer getIsDeleted() {
		return isDeleted;
	}
	/**是否删除(0 未删除 1 删除)*/
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
		/**状态(1 正常 2 停用)*/
	public Integer getStatus() {
		return status;
	}
	/**状态(1 正常 2 停用)*/
	public void setStatus(Integer status) {
		this.status = status;
	}
		/**内容*/
	public String getContent() {
		return content;
	}
	/**内容*/
	public void setContent(String content) {
		this.content = content;
	}
		
	@Override
	public String toString() {
		return "TalkSkill [ id=" + id + ", gmtCreate=" + gmtCreate + ", gmtModify=" + gmtModify + ", isDeleted=" + isDeleted + ", status=" + status + ", content=" + content + "]";
	}
}
