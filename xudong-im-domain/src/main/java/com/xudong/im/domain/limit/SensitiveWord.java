package com.xudong.im.domain.limit;

import java.io.Serializable;
import java.util.Date;

/**
*敏感词实体类
*/
public class SensitiveWord implements Serializable {
	private static final long serialVersionUID = 15593388464302L;

	
	
						private Integer id;//		
					private Date gmtModify;//		
					private String words;//敏感词(逗号分隔)		
		
		

		public SensitiveWord() {}

		/**
	*
		 *@param id -- 
		*/
	public SensitiveWord(Integer id) {
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
	public Date getGmtModify() {
		return gmtModify;
	}
	/***/
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}
		/**敏感词(逗号分隔)*/
	public String getWords() {
		return words;
	}
	/**敏感词(逗号分隔)*/
	public void setWords(String words) {
		this.words = words;
	}
		
	@Override
	public String toString() {
		return "SensitiveWord [ id=" + id + ", gmtModify=" + gmtModify + ", words=" + words + "]";
	}
}
