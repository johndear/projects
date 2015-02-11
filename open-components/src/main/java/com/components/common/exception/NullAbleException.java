package com.components.common.exception;

import com.components.common.util.G4Constants;

/**
 * 非空异常校验�?br>
 * 
 * @author XiongChun
 * @since 2009-07-15
 */
public class NullAbleException extends RuntimeException {

	private String nullField;

	/**
	 * 构�?函数1
	 * 
	 * @param 非空校验�?	 */
	public NullAbleException() {
		super(G4Constants.Exception_Head + "对象不能为空,请检�?");
	}

	/**
	 * 构�?函数2
	 * 
	 * @param 非空校验�?	 */
	public NullAbleException(Class cs) {
		super(G4Constants.Exception_Head + "对象不能为空,请检�?[" + cs + "]");
	}

	/**
	 * 构�?函数3
	 * 
	 * @param pNullField
	 *            异常附加信息
	 */
	public NullAbleException(String pNullField) {
		super(G4Constants.Exception_Head + "对象属�?[" + pNullField + "]不能为空,请检�?");
		this.setNullField(pNullField);
	}

	public String getNullField() {
		return nullField;
	}

	public void setNullField(String nullField) {
		this.nullField = nullField;
	}
}
