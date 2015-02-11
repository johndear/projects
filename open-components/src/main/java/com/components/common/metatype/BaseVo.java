package com.components.common.metatype;

import java.io.Serializable;

import com.components.common.metatype.impl.BaseDto;
import com.components.common.util.G4Utils;

/**
 * �?��值对�?br>
 * �?��值对象和数据库表没有�?��对应关系<br>
 * <b>注意：没有特殊需要建议不用VO,对于固定的数据存储结构请使用Domain;对于动�?可变�? * 的数据存储结构请使用Dto来代�?
 * @author XiongChun
 * @since 2009-06-23
 * @see java.io.Serializable
 */
public abstract class BaseVo implements Serializable{
	
    /**
     * 将�?对象转换为Dto对象
     * 
     * @return dto 返回的Dto对象
     */
	public Dto toDto(){
		Dto dto = new BaseDto();
		G4Utils.copyPropFromBean2Dto(this, dto);
		return dto;
	}
	
	/**
	 * 将�?对象转换为XML字符�?	 * @param pStyle XML文档风格
	 * @return String 返回的XML格式字符�?	 */
    public String toXml(String pStyle){
    	Dto dto = toDto();
    	return dto.toXml(pStyle);
    }
    
	/**
	 * 将�?对象转换为JSON字符�?	 * @return String 返回的JSON格式字符�?	 */
    public String toJson(){
    	Dto dto = toDto();
    	return dto.toJson();
    }
}
