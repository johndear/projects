package com.components.common.metatype;

/**
 * 非空数据传输对象接口<br>
 * @author XiongChun
 * @since 2009-07-06
 */
public interface PKey extends Dto{
	/**
	 * 对Key数据传输对象进行键�?非空性校�?	 * @throws Exception 
	 */
	public void validateNullAble();
}
