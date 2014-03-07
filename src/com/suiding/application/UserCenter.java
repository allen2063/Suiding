package com.suiding.application;

import java.util.UUID;

import android.os.Handler;
import android.os.Message;

import com.suiding.dao.OrderEntityDao;
import com.suiding.domain.IUserDomain;
import com.suiding.model.Consumer;
import com.suiding.model.Product;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.NetworkUtil;

public class UserCenter {

	/**
	 * interface INotifyNetworkStatus
	 * @author SCWANG ��������ͳ�Ƹı�֪ͨ
	 */
	public static interface INotifyFavoriteNumber {
		void onFavoriteNumberChanged(UUID userid,long favorite);
	}
	
	// Ĭ�ϼ���ʱ���� 2����
	public static final int DURATION_SHORT = 1 * 60 * 1000;

	private static long mFavoriteNum; // �ղ�����
	
	//��ǰ��¼�û�
	private static Consumer mConsumer = null;
	//��ʱ��ǰ����Ϣ
	private static Message mHandlerMessage = null;
	
	public static long getFavoriteNum() {
		return mFavoriteNum;
	}
	/**
	 * ���õ�ǰ�ĵ�¼�û�
	 * 
	 * @param obj
	 *            ����Ȩ����֤ ����this
	 * @param user
	 *            ��ǰ�� ��¼�û�
	 */
	public synchronized static void setLoginUser(Object obj, Consumer user) {
		if (obj instanceof SuidingApp) {
			OrderConsole.setLoginUser(obj, user);
			if (user != null) {// �û���¼ ���߸���
				if (mConsumer == null || !mConsumer.getID().equals(user.getID())) {
					mConsumer = user;
					doUserLogin(user);
				}else{//�û���Ϣ����
					mConsumer = user;
					doUserUpdate(user);
				}
			}else{//�û��˳�
				doUserLogout();
			}
		}
	}

	/**
	 * ����App����״̬
	 * 
	 * @param obj
	 *            ����thisָ�� ������֤Ȩ��
	 * @param networkState
	 *            ָ��������״̬
	 */
	public synchronized static void setNetworkStatus(Object obj, int status) {
		// TODO Auto-generated method stub
		if (obj instanceof SuidingApp) {
			OrderConsole.setNetworkStatus(obj, status);
		}
	}

	/**
	 * 
	 * @param store
	 * @return
	 */
	public synchronized static boolean getCommentPower(StoreBase store) {
		if (mConsumer == null) {
			return false;
		}
		OrderEntityDao dao = new OrderEntityDao(SuidingApp.getApp());
		return dao.hasOrderFinishFor(mConsumer, store);
	}

	/**
	 * 
	 * @param product
	 * @return
	 */
	public synchronized static boolean getCommentPower(Product product) {
		if (mConsumer == null) {
			return false;
		}
		OrderEntityDao dao = new OrderEntityDao(SuidingApp.getApp());
		return dao.hasOrderFinishFor(mConsumer, product);
	}

	private static void doUserLogin(Consumer user) {
		// TODO Auto-generated method stub
		//�������
		doClearUserData();
		//��ֵ�û�
		mConsumer = user;
		//�û���¼��һ�μ��صȴ���������
	}

	private static void doUserLogout() {
		// TODO Auto-generated method stub
		mConsumer = null;
		//�������
		doClearUserData();
		//ɾ��֪ͨ
		NotifyCenter.cancel(NotifyCenter.ID_ORDER);
		//�رն�ʱ��
		mHandlerMessage = null;
	}

	private static void doClearUserData() {
		// TODO Auto-generated method stub
		//�������
		mFavoriteNum = 0;
		mHandlerMessage = null;
	}
	
	private static void doUserUpdate(Consumer user) {
		// TODO Auto-generated method stub
		
	}
	
	private static void doReadyTimer(int task, Consumer user) {
		// TODO Auto-generated method stub
		//��֤��ʱ�� �Ķ����ǲ��ǵ�ǰ�� mConsumer ���Ǿͺ���
		if(user != null && mConsumer != null && user.getID().equals(mConsumer.getID())){
			if(mHandlerMessage == null){
				Message msg = Message.obtain(mHandler, task,mConsumer);
				mHandler.sendMessageDelayed(msg, DURATION_SHORT);
				mHandlerMessage = msg;
			}else{
				//��ȡ��ʱ���󶨵��û�����
				Consumer tuser = (Consumer)mHandlerMessage.obj;
				//����󶨵��û����Ǳ���Ҫ�󶨵��û�
				if(!user.getID().equals(tuser.getID())){
					Message msg = Message.obtain(mHandler, task,mConsumer);
					mHandler.sendMessageDelayed(msg, DURATION_SHORT);
					mHandlerMessage = msg;
				}
			}
		}
	}
	
	// ��ʱ������ Handler
	private static Handler mHandler = new Handler(SuidingApp.getLooper()) {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(mHandlerMessage == null){
				return ;//�����ʱ���Ѿ��رգ�����
			}
			//��ȡ��ʱ���󶨵��û�����
			Consumer user = (Consumer)msg.obj;
			//��Ƕ�ʱ������
			mHandlerMessage = null;
			//��֤��ʱ�� �Ķ����ǲ��ǵ�ǰ�� mConsumer ���Ǿͺ���
			if(user != null && mConsumer != null && user.getID().equals(mConsumer.getID())){
				//�����ʱ���ﵽʱ ���������
				if(SuidingApp.getNetworkStatus() == NetworkUtil.TYPE_NONE){
					doReadyTimer(msg.what, user);//׼����һ�ζ�ʱ��
					return ;//���β�ִ�ж�ʱ������
				}
				
			}
		}
	};

	public static class UserCenterTask extends TaskBase {

		private static final int TASK_COUNTFAVORITE = 10;
		
		public Consumer mUser = null;

		public UserCenterTask(Consumer user,int task) {
			super(SuidingApp.getLooper(), task);
			// TODO Auto-generated constructor stub
			mUser = user;
		}

		/**
		 * �첽�߳�ִ����������
		 */
		@Override
		protected void onWorking(Message msg) throws Exception {
			// TODO Auto-generated method stub
			switch (mTask) {
			case TASK_COUNTFAVORITE:
				IUserDomain domain = DomainFactory.getUserDomain();
				mFavoriteNum = domain.getFavoriteStoreCount(mUser.getID());
				break;

			default:
				break;
			}
		}

		/**
		 * ���߳̽�������
		 */
		@Override
		protected boolean onHandle(Message msg) {
			// TODO Auto-generated method stub
			if(mConsumer == null || !mConsumer.getID().equals(mUser.getID())){//ִ������������û��Ѿ��˳�
				return true;
			}
			switch (mTask) {
			case TASK_COUNTFAVORITE:
				if(mResult != RESULT_FINISH){
					
				}else{
					SuidingApp app = SuidingApp.getApp();
					app.setFavoriteNumber(this,mUser.getID(), mFavoriteNum);
				}
				break;
			}
			return true;
		}

	}
}
