package com.suiding.util;

/**
 * 乐搜自定义异常类
 * @author Administrator
 *
 */
public class LeSouException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LeSouExceptionType ExcptionType ;	
	public String Message ="";
	public String InterMessage = "";
	public Throwable ExceptionSource = null;
	
	public LeSouException()
	{
		
	}

	public LeSouException(String message)
	{
		super(message);
		this.ExcptionType = LeSouExceptionType.EXCEPTION;
		this.Message = message;
	}
	public LeSouException(LeSouExceptionType type,String message,String interMessage,Throwable source)
	{
		super(message);
		this.ExcptionType = type;
		this.Message = message;
		this.InterMessage = interMessage;
		this.ExceptionSource = source;
		this.initCause(source);
	}
	
	public enum LeSouExceptionType
	{
		/**
		 * IO类错误
		 */
		ERROR_IO,
		/**
		 * 这是一个错误，并且这个错误未知，导致的原因请查看Message和InterMessage
		 */
		ERROR,
		/**
		 * IO类异常
		 */
		EXCEPTION_IO,
		/**
		 * 执行数据服务导致的异常
		 */
		EXCEPTION_SERVICE,
		/**
		 * 这是一个抓到Excption类型的异常
		 */
		EXCEPTION_UNKNOW,
		/*/
		 * 一般的Excption类型
		 */
		EXCEPTION
	}

}
