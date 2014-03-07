package com.suiding.adapter;

import java.util.List;

import com.suiding.activity.R;
import com.suiding.entity.AreaEntity;
import com.suiding.util.CityNameUtil;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ResultPopDownAdapter  extends BaseAdapter{

    private int itemheight;
    private int selectedcolor;
    private Context mContext = null;
    private List<AreaEntity> mltData = null;
	
	public ResultPopDownAdapter(Context context,List<AreaEntity> ltData){
        mltData = ltData;
        mContext = context;
        itemheight = (int)context.getResources().getDimension(R.dimen.result_popdown_gvi_height);
        selectedcolor = context.getResources().getColor(R.color.theme_gray_dark);
	}

    public void notifyUpdate()
    {
        // TODO Auto-generated method stub
        notifyDataSetChanged();
    }
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mltData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mltData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int index, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
	    TextView tTextView = null;
		if(view == null){
			tTextView = new TextView(mContext);
			tTextView.setGravity(Gravity.CENTER);
			tTextView.setTextColor(Color.BLACK);
			tTextView.setHeight(itemheight);
		}else{
		    tTextView = (TextView)view;
		}
		AreaEntity area = mltData.get(index);
		if(tTextView != null && area != null){
	        tTextView.setText(index == 0?"È«³Ç":CityNameUtil.SimplifyCityName(area.Name));
	        tTextView.setBackgroundColor(area.isSelected()?selectedcolor:0);
		}
		return tTextView;
	}
}
