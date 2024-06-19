package com.example.kpic_app1;


import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

public class TabWidgetActivity extends TabActivity implements OnTabChangeListener {
    /** Called when the activity is first created. */

    private TabHost m_tabHost = null;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        /** TabHost ID */ 
        m_tabHost= (TabHost)findViewById(android.R.id.tabhost);  
        TabSpec aaa_TabSpec = m_tabHost.newTabSpec("aaa");  
        TabSpec bbb_TabSpec = m_tabHost.newTabSpec("bbb");
        TabSpec ccc_TabSpec = m_tabHost.newTabSpec("ccc");
        TabSpec ddd_TabSpec = m_tabHost.newTabSpec("ddd");
        TabSpec eee_TabSpec = m_tabHost.newTabSpec("eee");
        
        LayoutInflater vi1 = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater vi2 = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater vi3 = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater vi4 = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater vi5 = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View View_1 = (View)vi1.inflate(R.layout.tab_row_item, null);        
        View View_2 = (View)vi2.inflate(R.layout.tab_row_item, null);
        View View_3 = (View)vi3.inflate(R.layout.tab_row_item, null);
        View View_4 = (View)vi4.inflate(R.layout.tab_row_item, null);
        View View_5 = (View)vi5.inflate(R.layout.tab_row_item, null);
        LinearLayout Layout_1 = (LinearLayout)View_1.findViewById(R.id.LinearLayout01);
        LinearLayout Layout_2 = (LinearLayout)View_2.findViewById(R.id.LinearLayout01);
        LinearLayout Layout_3 = (LinearLayout)View_3.findViewById(R.id.LinearLayout01);
        LinearLayout Layout_4 = (LinearLayout)View_4.findViewById(R.id.LinearLayout01);
        LinearLayout Layout_5 = (LinearLayout)View_5.findViewById(R.id.LinearLayout01);
        Layout_1.setBackgroundResource(R.drawable.bg_tab_blue);
        Layout_2.setBackgroundResource(R.drawable.bg_tab_blue);
        Layout_3.setBackgroundResource(R.drawable.bg_tab_blue);
        Layout_4.setBackgroundResource(R.drawable.bg_tab_blue);
        Layout_5.setBackgroundResource(R.drawable.bg_tab_blue);
        
        ImageView iv_1 = (ImageView)View_1.findViewById(R.id.icon);
        ImageView iv_2 = (ImageView)View_2.findViewById(R.id.icon);
        ImageView iv_3 = (ImageView)View_3.findViewById(R.id.icon);
        ImageView iv_4 = (ImageView)View_4.findViewById(R.id.icon);
        ImageView iv_5 = (ImageView)View_5.findViewById(R.id.icon);
        
        //android:background="@drawable/icon_tab1"
        
        
//        Drawable icon1 = (Drawable) getResources().getDrawable(R.drawable.icon_1_off);
//        Drawable icon2 = (Drawable) getResources().getDrawable(R.drawable.icon_2_off);
//        Drawable icon3 = (Drawable) getResources().getDrawable(R.drawable.icon_3_off);
//        Drawable icon4 = (Drawable) getResources().getDrawable(R.drawable.icon_4_off);
//        Drawable icon5 = (Drawable) getResources().getDrawable(R.drawable.icon_5_off);
        
        Drawable icon1 = (Drawable) getResources().getDrawable(R.drawable.icon1);
        Drawable icon2 = (Drawable) getResources().getDrawable(R.drawable.icon3);
        Drawable icon3 = (Drawable) getResources().getDrawable(R.drawable.icon2);
        Drawable icon4 = (Drawable) getResources().getDrawable(R.drawable.icon4);
        Drawable icon5 = (Drawable) getResources().getDrawable(R.drawable.icon5);
        
        iv_1.setImageDrawable(icon1);
        iv_2.setImageDrawable(icon2);
        iv_3.setImageDrawable(icon3);
        iv_4.setImageDrawable(icon4);
        iv_5.setImageDrawable(icon5);
        
        
        //TextView tv_1 = (TextView)View_1.findViewById(R.id.text);
        //TextView tv_2 = (TextView)View_2.findViewById(R.id.text);
        //TextView tv_3 = (TextView)View_3.findViewById(R.id.text);
        //TextView tv_4 = (TextView)View_4.findViewById(R.id.text);
        //TextView tv_5 = (TextView)View_5.findViewById(R.id.text);
        
        //tv_1.setText("일정");
        //tv_2.setText("메모");
        //tv_3.setText("알림");
        //tv_4.setText("게시");
        //tv_5.setText("대화");
        
        aaa_TabSpec.setIndicator(View_1);
        aaa_TabSpec.setContent(new Intent(this,Main_Diary.class));  
        bbb_TabSpec.setIndicator(View_2);
        bbb_TabSpec.setContent(new Intent(this,Main_Memo.class));  
        ccc_TabSpec.setIndicator(View_3);
        ddd_TabSpec.setContent(new Intent(this,Main_Messenger.class));  
        ddd_TabSpec.setIndicator(View_4);
        eee_TabSpec.setContent(new Intent(this,Main_Notice.class));
        eee_TabSpec.setIndicator(View_5);
        ccc_TabSpec.setContent(new Intent(this,Pharm_SearchList.class));
          
        
       /** 탭을 TabHost 에 추가한다 */ 
        m_tabHost.addTab(aaa_TabSpec);
        m_tabHost.addTab(bbb_TabSpec);
        m_tabHost.addTab(ccc_TabSpec);
        m_tabHost.addTab(ddd_TabSpec);
        m_tabHost.addTab(eee_TabSpec);
        
        m_tabHost.getTabWidget().setCurrentTab(0);  
        
        m_tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.bg_tab_teal);
        m_tabHost.setOnTabChangedListener(this);
        
    }

	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		//m_tabHost= (TabHost)findViewById(android.R.id.tabhost);  
		//TabWidget tw = m_tabHost.getTabWidget();
		
	
		//Drawable bg_blue =  (Drawable) getResources().getDrawable(R.drawable.bg_tab_blue);
	    //Drawable bg_black =  (Drawable) getResources().getDrawable(R.drawable.bg_tab_black);
	    //Drawable bg_green =  (Drawable) getResources().getDrawable(R.drawable.bg_tab_green);
	    //Drawable bg_purple =  (Drawable) getResources().getDrawable(R.drawable.bg_tab_purple);
	    //Drawable bg_red =  (Drawable) getResources().getDrawable(R.drawable.bg_tab_red);
	    //Drawable bg_teal =  (Drawable) getResources().getDrawable(R.drawable.bg_tab_teal);
		int now = m_tabHost.getCurrentTab();
		//Toast.makeText(TabWidgetActivity.this, now + "", Toast.LENGTH_LONG).show() ;
		
	     if(now == 0) 
	     {
	    	 m_tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.bg_tab_teal);
	    	 m_tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.bg_tab_blue);
	    	 m_tabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.bg_tab_blue);
	    	 m_tabHost.getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.bg_tab_blue);
	    	 m_tabHost.getTabWidget().getChildAt(4).setBackgroundResource(R.drawable.bg_tab_blue);
	     } 
	     else if(now == 1) 
	     {
	    	 m_tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.bg_tab_blue);
	    	 m_tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.bg_tab_red);
	    	 m_tabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.bg_tab_blue);
	    	 m_tabHost.getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.bg_tab_blue);
	    	 m_tabHost.getTabWidget().getChildAt(4).setBackgroundResource(R.drawable.bg_tab_blue);
	     } 
	     else if(now == 2) 
	     {
	    	 m_tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.bg_tab_blue);
	    	 m_tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.bg_tab_blue);
	    	 m_tabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.bg_tab_purple);
	    	 m_tabHost.getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.bg_tab_blue);
	    	 m_tabHost.getTabWidget().getChildAt(4).setBackgroundResource(R.drawable.bg_tab_blue);
	     }
	     else if(now == 3) 
	     {
	    	 m_tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.bg_tab_blue);
	    	 m_tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.bg_tab_blue);
	    	 m_tabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.bg_tab_blue);
	    	 m_tabHost.getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.bg_tab_green);
	    	 m_tabHost.getTabWidget().getChildAt(4).setBackgroundResource(R.drawable.bg_tab_blue);
	     }
	     else if(now == 4) 
	     {
	    	 m_tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.bg_tab_blue);
	    	 m_tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.bg_tab_blue);
	    	 m_tabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.bg_tab_blue);
	    	 m_tabHost.getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.bg_tab_blue);
	    	 m_tabHost.getTabWidget().getChildAt(4).setBackgroundResource(R.drawable.bg_tab_black);
	     }




		
		
		
	}
}