package com.suiding.util;

/**
 * �����Զ����쳣��
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
		 * IO�����
		 */
		ERROR_IO,
		/**
		 * ����һ�����󣬲����������δ֪�����µ�ԭ����鿴Message��InterMessage
		 */
		ERROR,
		/**
		 * IO���쳣
		 */
		EXCEPTION_IO,
		/**
		 * ִ�����ݷ����µ��쳣
		 */
		EXCEPTION_SERVICE,
		/**
		 * ����һ��ץ��Excption���͵��쳣
		 */
		EXCEPTION_UNKNOW,
		/*/
		 * һ���Excption����
		 */
		EXCEPTION
	}

}
