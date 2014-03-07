package com.suiding.constant;

public class OrderStatus {
	/**
	 * 无效的
	 */
	public static final int NONE = 0;
	/**
	 * 确认
	 */
	public static final int CONFIRM = 1;
	/**
	 * 被拒绝
	 */
	public static final int REFUSE = 2;
	/**
	 * 等待中
	 */
	public static final int WAITING = 3;
	/**
	 * 已完成
	 */
	public static final int FINISH = 4;
	/**
	 * 用户取消
	 */
	public static final int CANCEL = 5;
	/**
	 * 超时
	 */
	public static final int TIMEOUT = 6;

	public static final String[] orderstatuss = new String[] { 
		"无效的", "确认", "被拒绝", "待确认",
			"已完成", "用户取消", "超时" };

	/**
	 * 获取订单状态名称
	 * 
	 * @param OrderStatus
	 * @return
	 */
	public static String getStatusName(int OrderStatus) {
		if(OrderStatus >= orderstatuss.length){
			OrderStatus = 0;
		}
		return orderstatuss[OrderStatus];
	}
}
