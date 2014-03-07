package com.suiding.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;

import com.suiding.activity.framework.ListViewActivity;
import com.suiding.adapter.OrderListAdapter;
import com.suiding.adapter.framework.ListAdapterBase;
import com.suiding.application.AppExceptionHandler;
import com.suiding.application.OrderConsole;
import com.suiding.application.SuidingApp;
import com.suiding.constant.OrderStatus;
import com.suiding.constant.StoreTypeEnum;
import com.suiding.dao.OrderEntityDao;
import com.suiding.domain.IOrderDomain;
import com.suiding.entity.OrderEntity;
import com.suiding.layoutbind.ModuleNodata;
import com.suiding.layoutbind.ModuleTitleOther;
import com.suiding.model.Consumer;
import com.suiding.model.Coupon;
import com.suiding.model.Order;
import com.suiding.model.Page;
import com.suiding.model.StoreBase;
import com.suiding.service.DomainFactory;
import com.suiding.thread.framework.ListViewTask;
import com.suiding.thread.framework.TaskBase;
import com.suiding.util.ExtraUtil;

public class ListOrderActivity extends ListViewActivity implements OnClickListener {

	public static final String EXTRA_EM_FILTE = "EXTRA_EM_FILTE";
	public static final String EXTRA_BL_NOTFY = "EXTRA_BL_ISNOTFY";

	public static final int FILTE_NONE = OrderStatus.NONE;
	public static final int FILTE_WAITING = OrderStatus.WAITING;
	public static final int FILTE_CONFIRM = OrderStatus.CONFIRM;
	public static final int FILTE_CANCEL = OrderStatus.REFUSE;

	private int mIndex = 0;
	private int mFilte = FILTE_NONE;
	private int[] mFilters = new int[]{};
	//����Ƿ��ɶ���֪ͨ����
	private boolean mIsNotyify = false;
	//��ǰ������Order����
	private Order mOperateOrder = null;

	@Override
	protected void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//���ж��Ƿ��ɶ���֪ͨ����ҳ��
		Intent intent = getIntent();
		if(intent != null){
			//����Ƕ�������
			mIsNotyify = intent.getBooleanExtra(EXTRA_BL_NOTFY, false);
			if(mIsNotyify == true){
				//���û����Ļ�ȡ�ı�Ķ���
				mltData = new ArrayList<Object>(OrderConsole.getListOrderChanged(this));
				mTitleOther.setTitle(R.string.title_activity_ordermanage);
				mTitleOther.setOnGoBackListener(this);
				return ;
			}
		}
		
		//�������֪ͨ���ÿ�����ҳ
		mIsPaging = true;
		mFilte = ExtraUtil.getExtraInt(EXTRA_EM_FILTE, FILTE_NONE);

		switch (mFilte) {
		case FILTE_WAITING:
			mFilters = OrderConsole.FILTES_WAITING;
			mTitleOther.setTitle(R.string.title_activity_waitconfirm);
			break;
		case FILTE_CONFIRM:
			mltData = new ArrayList<Object>(OrderConsole.getListOrderChangedConfirm(this));
			mFilters = OrderConsole.FILTES_CONFIRM;
			mTitleOther.setTitle(R.string.title_activity_reservation);
			break;
		case FILTE_CANCEL:
			mltData = new ArrayList<Object>(OrderConsole.getListOrderChangedRefused(this));
			mFilters = OrderConsole.FILTES_CANCEL;
			mTitleOther.setTitle(R.string.title_activity_refusing);
			break;
		case FILTE_NONE:
		default:
			mTitleOther.setTitle(R.string.title_activity_ordermanage);
			break;
		}

	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case ModuleTitleOther.ID_GOBACK:
			//�����֪ͨ���ò��� APP ǰ̨�Ѿ��ر�
			SuidingApp app = SuidingApp.getApp();
			if(mIsNotyify && !app.getIsForegroundRunning()){
				//����ǰ̨ҳ��
				app.startForeground(this);
			}
			this.finish();
			break;
		default:
			super.onClick(v);
			break;
		}
		
	}

	/**
	 * ���б�رյ�ʱ�� ����ǰ̨
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			//�����֪ͨ���ò��� APP ǰ̨�Ѿ��ر�
			SuidingApp app = SuidingApp.getApp();
			if(mIsNotyify && !app.getIsForegroundRunning()){
				//����ǰ̨ҳ��
				app.startForeground(this);
			}
		}
		return super.dispatchKeyEvent(event);
	}
	
	@Override
	protected void onNodata(ModuleNodata nodata) {
		// TODO Auto-generated method stub
		nodata.setDescription(ModuleNodata.TEXT_NOORDER);
	}

	@Override
	protected ListViewTask getTask(int task) {
		// TODO Auto-generated method stub
		return new OrderTask(task);
	}

	@Override
	protected ListAdapterBase getAdapter(List<Object> ltdata) {
		// TODO Auto-generated method stub
		return new OrderListAdapter(this, ltdata);
	}
	
	@Override
	public void onItemClick(AdapterView<?> absview, View view, int index,
			long id) {
		// TODO Auto-generated method stub
		super.onItemClick(absview, view, index, id);
		index = mModuleListView.getIndex(index);
		Object obj = mltData.get(index);
		if(obj instanceof Order){
			Order order = (Order) mltData.get(index);
			doCheckOrder(order);
			doStartOrderInfiPage(order);
		}
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> absview, View view,
			int index, long id) {
		// TODO Auto-generated method stub
    	index = mModuleListView.getIndex(index);
    	if(mFilte == FILTE_WAITING &&index >= 0)
    	{
    		mIndex  = index;
    		mOperateOrder = (Order) mltData.get(index);
			doCheckOrder(mOperateOrder);
    		Builder dialog = new Builder(this);
    		dialog.setTitle("ѡ�����");
    		dialog.setIcon(android.R.drawable.ic_dialog_info);
    		dialog.setItems(new String[] { "ȡ������", "�鿴��ϸ"}, this);
    		dialog.show();
    	}
		return super.onItemLongClick(absview, view, index, id);
	}
    
    @Override
    public void onClick(DialogInterface dialog, int which) {
    	// TODO Auto-generated method stub
    	switch (which) {
		case 0:
			postTask(new OrderTask(OrderTask.TASK_CANCELORDER));
			showProgressDialog("����ȡ��...");
			break;
		case 1:
			doStartOrderInfiPage(mOperateOrder);
			break;
		}
    }

    private void doCheckOrder(Order order) {
		// TODO Auto-generated method stub
    	if(order != null){
    		Coupon coupon = order.getCoupon();
    		StoreBase store = order.getStoreBase();
    		if(store == null && coupon != null && coupon.StoreBase != null){
    			store = coupon.StoreBase;
    		}	
    	}
	}

	private void doStartOrderInfiPage(Order order) {
		// TODO Auto-generated method stub
		StoreBase store = null;
		if (order != null && (store = order.getStoreBase()) != null) {
			Intent intent = new Intent();
			switch (store.Type) {
			case StoreTypeEnum.HOTEL:
				ExtraUtil.putExtra(OrderInfoHotelActivity.EXTRA_ORDER,order);
				intent.setClass(this, OrderInfoHotelActivity.class);
				startActivity(intent);
				break;
			default:
			case StoreTypeEnum.FOODANDBEVERAGE:
				ExtraUtil.putExtra(OrderInfoResturantActivity.EXTRA_ORDER,order);
				intent.setClass(this, OrderInfoResturantActivity.class);
				startActivity(intent);
				break;
			}
		}else{
			showToastShort("���������Ѿ���Ч");
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		TaskBase task = TaskBase.getTask(msg);
		OrderTask otask = (OrderTask)task;
		switch (task.mTask) {
		case OrderTask.TASK_LOAD:
			if(mIsNotyify){
				return true;//�����֪ͨ ����load����
			}else if(mltData!=null&&mltData.size() > 0){
				otask.mltData = mltData;
			}
			break;
		case OrderTask.TASK_REFRESH:
			if(task.mResult == TaskBase.RESULT_FINISH){
				if(mFilte == FILTE_WAITING){
					OrderConsole.refreshWaitOrder(otask.ltData);
				}else if(mFilte == FILTE_CONFIRM){
					OrderConsole.refreshConfirmOrder(otask.ltData);
				}else if(mFilte == FILTE_CANCEL){
					OrderConsole.refreshRefusedOrder(otask.ltData);
				}
			}
			break;
		case OrderTask.TASK_CANCELORDER:
			if(task.mResult != TaskBase.RESULT_FINISH){
				showToastLong(task.mErrors);
			}else{
				mAdapter.remove(mIndex);
			}
			return true;
		}
		return super.handleMessage(msg);
	}

	private class OrderTask extends ListViewTask implements Comparator<Object> {
		
		public static final int TASK_CANCELORDER = 10000;
		
		public List<Order> ltData = new ArrayList<Order>();

		public OrderTask(int task) {
			super(new Handler(ListOrderActivity.this), task);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected List<? extends Object> onLoad() {
			// TODO Auto-generated method stub
			//����ǲ鿴�ȴ����� onload �����κ����� ֱ����һ�� onRefresh()
			//�����֪ͨ���� onload Ҳ�����κ����� ����Handler�л��ں�̨��������
			if(mFilte == FILTE_WAITING || mIsNotyify){
				return mltData;
			}
			//������������ݿ�������ݻ���
			OrderEntityDao dao = new OrderEntityDao(getBaseContext());
			if(mFilte == FILTE_NONE){
				ltData = OrderEntity.listToModel(dao.getAll());
			}else{
				ltData = OrderEntity.listToModel(dao.getAll(mFilte));
			}
			//��������
			try {
				Collections.sort(ltData, this);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();//handled
				AppExceptionHandler.handler(e, "�����б�ҳ��Load����֮���������");
			}
			return ltData;
		}

		@Override
		protected List<? extends Object> onRefresh() throws Exception {
			// TODO Auto-generated method stub
			Consumer user = SuidingApp.getLoginUser();
			if (user == null) {
				Thread.sleep(1000);
				return mltData;
			} else {
				Page page = new Page(PAGESIZE, 0);
				IOrderDomain domain = DomainFactory.getOrderDomain();
				ltData = domain.GetListByFilters(user.getID(),mFilters,page);
				
				try {
					//����
					Collections.sort(ltData, this);
					// ���»���
					OrderEntityDao dao = new OrderEntityDao(getBaseContext());
					dao.updateCache(OrderEntity.listFormModel(ltData),mFilters);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();//handled
					AppExceptionHandler.handler(e, "�����б�ҳ�����»������");
				}
				return ltData;//getOrderFilter(ltData);
			}
		}

		@Override
		protected List<? extends Object> onMore(Page page) throws Exception {
			// TODO Auto-generated method stub
			Consumer user = SuidingApp.getLoginUser();
			if (user == null) {
				Thread.sleep(1000);
				return mltData;
			} else {
				IOrderDomain domain = DomainFactory.getOrderDomain();
				ltData = domain.GetListByFilters(user.getID(),mFilters,page);

				try {
					//����
					Collections.sort(ltData, this);
					// ���»���
					OrderEntityDao dao = new OrderEntityDao(getBaseContext());
					dao.appendCache(OrderEntity.listFormModel(ltData));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();//handled
					AppExceptionHandler.handler(e, "�����б�ҳ����ӻ������");
				}
				return ltData;//getOrderFilter(ltData);
			}
		}
		
		@Override
		public int compare(Object lhs, Object rhs) {
			// TODO Auto-generated method stub
			if(lhs instanceof Order && rhs instanceof Order){
				Order lorder = (Order)lhs;
				Order rorder = (Order)rhs;
				return rorder.Date.compareTo(lorder.Date);
			}
			return 0;
		}
		
		@Override
		protected boolean onWorking() throws Exception {
			// TODO Auto-generated method stub
			switch (mTask) {
			case TASK_CANCELORDER:{
				if(mOperateOrder != null){
					IOrderDomain domain = DomainFactory.getOrderDomain();
					Order order = domain.GetByID(mOperateOrder.getID());
					if(order.OrderStatus != OrderStatus.WAITING){
						try {
							OrderEntityDao dao = new OrderEntityDao(getContext());
							dao.updateOrderStatus(new OrderEntity(order));
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();//handled
							AppExceptionHandler.handler(e, "�����б�ҳ��ȡ������ʱ���»���ʧ��");
						}
						if(OrderConsole.isOrderConfirm(order)){
							throw new Exception("���Ķ����Ѿ����̼�ȷ�ϣ�����ȡ����");
						}else{
							throw new Exception("���Ķ����Ѿ���ʱ���߱��̼�ȡ����");
						}
					}else{
						order.OrderStatus = OrderStatus.CANCEL;
						domain.Update(order);
						mOperateOrder = order;
						try {
							OrderEntityDao dao = new OrderEntityDao(getContext());
							dao.updateOrderStatus(new OrderEntity(order));
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();//handled
							AppExceptionHandler.handler(e, "�����б�ҳ��ȡ������ʱ���»���ʧ��");
						}
					}
				}
			}
				break;
			}
			return false;
		}
	}
}
