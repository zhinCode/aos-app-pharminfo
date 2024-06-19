package com.example.kpic_app1;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Pharm_SearchList extends Activity{
	String Board_List_URL = "";
	static final String LOG = "Board_List";
	static final String KEY_SONG = "drugs"; // parent node
	static final String KEY_ID = "drug_idx";
	static final String KEY_TITLE = "drug_name";
	static final String KEY_ARTIST = "sunb_name";
	static final String KEY_DURATION = "upso_name";
	static final String KEY_THUMB_URL = "sb_image_url";
	
	public int pageNum = 1;
	static String searchText;
	
	//static String URL = "http://110.9.251.219:8080/Service.Query/test.asp";
	static String URL = "http://110.9.251.219:8080/Service.Query/Query.PharmSearch.asp";
	static String URL_SB = "http://110.9.251.219:8080/Service.Query/Query.PharmSearch.SB.asp";
	
	
	static int pageSize = 10;
	static int lastViewIndex;
	

	ProgressDialog progressDialog;
	Pharm_SearchList_LazyAdapter adapter;
	ListView list;
	Handler handler = new Handler();
	ArrayList<HashMap<String, String>> songsList;
	View header, footer;
	boolean nowListing = false;
	
	InputMethodManager mImm;
	



	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.board_list);

		list=(ListView)findViewById(R.id.list);

		ImageButton ImageButton01 = (ImageButton) findViewById(R.id.ImageButton01); // 텍스트검색 버튼
		
		ImageButton ImageButton03 = (ImageButton) findViewById(R.id.ImageButton03); // 식별검색 버튼
		
		
		
		ImageButton topToggleButton1 =  (ImageButton) findViewById(R.id.topToggleButton1);
		ImageButton topToggleButton2 =  (ImageButton) findViewById(R.id.topToggleButton2);
		
		final View top_1 = (View)  findViewById(R.id.top_1);
		final View top_2 = (View)  findViewById(R.id.top_2);
		top_2.setVisibility(View.INVISIBLE);
		top_1.setVisibility(View.VISIBLE);
		
		
		final View contents_result = (View) findViewById(R.id.contents_result);
		final View contents_mode01 = (View) findViewById(R.id.contents_mode01);
		final View contents_mode02 = (View) findViewById(R.id.contents_mode02);
		final View contents_mode03 = (View) findViewById(R.id.contents_mode03);
		final View contents_mode04 = (View) findViewById(R.id.contents_mode04);
		final View contents_mode05 = (View) findViewById(R.id.contents_mode05);
		
		contents_mode01.setVisibility(View.INVISIBLE);
		contents_mode02.setVisibility(View.INVISIBLE);
		contents_mode03.setVisibility(View.INVISIBLE);
		contents_mode04.setVisibility(View.INVISIBLE);
		contents_mode05.setVisibility(View.INVISIBLE);
		
		contents_result.setVisibility(View.VISIBLE);
		
		list.setVisibility(View.VISIBLE);

		
		
		
		final ImageButton mode01 = (ImageButton) findViewById(R.id.mode01);
		final ImageButton mode02 = (ImageButton) findViewById(R.id.mode02);
		final ImageButton mode03 = (ImageButton) findViewById(R.id.mode03);
		final ImageButton mode04 = (ImageButton) findViewById(R.id.mode04);
		final ImageButton mode05 = (ImageButton) findViewById(R.id.mode05);
		
		final ImageButton mode01on = (ImageButton) findViewById(R.id.mode01on);
		final ImageButton mode02on = (ImageButton) findViewById(R.id.mode02on);
		final ImageButton mode03on = (ImageButton) findViewById(R.id.mode03on);
		final ImageButton mode04on = (ImageButton) findViewById(R.id.mode04on);
		final ImageButton mode05on = (ImageButton) findViewById(R.id.mode05on);

		mode01on.setVisibility(View.INVISIBLE);
		mode02on.setVisibility(View.INVISIBLE);
		mode03on.setVisibility(View.INVISIBLE);
		mode04on.setVisibility(View.INVISIBLE);
		mode05on.setVisibility(View.INVISIBLE);
		
		mode01.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mode01on.setVisibility(View.VISIBLE);
				mode02on.setVisibility(View.INVISIBLE);
				mode03on.setVisibility(View.INVISIBLE);
				mode04on.setVisibility(View.INVISIBLE);
				mode05on.setVisibility(View.INVISIBLE);
				
				contents_mode01.setVisibility(View.VISIBLE);
				contents_mode02.setVisibility(View.INVISIBLE);
				contents_mode03.setVisibility(View.INVISIBLE);
				contents_mode04.setVisibility(View.INVISIBLE);
				contents_mode05.setVisibility(View.INVISIBLE);
				SharedPreferences pref_w =getSharedPreferences("SearchStatus", MODE_PRIVATE);
				SharedPreferences.Editor editor_w = pref_w.edit();

				editor_w.putString("pref_Search_mode",		"2");
				editor_w.putString("pref_Search_Submode",		"1");
				editor_w.commit();
				
				
			}
		});
		mode02.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mode01on.setVisibility(View.INVISIBLE);
				mode02on.setVisibility(View.VISIBLE);
				mode03on.setVisibility(View.INVISIBLE);
				mode04on.setVisibility(View.INVISIBLE);
				mode05on.setVisibility(View.INVISIBLE);
				
				contents_mode01.setVisibility(View.INVISIBLE);
				contents_mode02.setVisibility(View.VISIBLE);
				contents_mode03.setVisibility(View.INVISIBLE);
				contents_mode04.setVisibility(View.INVISIBLE);
				contents_mode05.setVisibility(View.INVISIBLE);
				
				SharedPreferences pref_w =getSharedPreferences("SearchStatus", MODE_PRIVATE);
				SharedPreferences.Editor editor_w = pref_w.edit();
				
				editor_w.putString("pref_Search_mode",		"2");
				editor_w.putString("pref_Search_Submode",		"2");
				editor_w.commit();
				
			}
		});
		mode03.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mode01on.setVisibility(View.INVISIBLE);
				mode02on.setVisibility(View.INVISIBLE);
				mode03on.setVisibility(View.VISIBLE);
				mode04on.setVisibility(View.INVISIBLE);
				mode05on.setVisibility(View.INVISIBLE);
				
				contents_mode01.setVisibility(View.INVISIBLE);
				contents_mode02.setVisibility(View.INVISIBLE);
				contents_mode03.setVisibility(View.VISIBLE);
				contents_mode04.setVisibility(View.INVISIBLE);
				contents_mode05.setVisibility(View.INVISIBLE);
				
				SharedPreferences pref_w =getSharedPreferences("SearchStatus", MODE_PRIVATE);
				SharedPreferences.Editor editor_w = pref_w.edit();
				
				editor_w.putString("pref_Search_mode",		"2");
				editor_w.putString("pref_Search_Submode",		"3");
				editor_w.commit();
			}
		});
		mode04.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mode01on.setVisibility(View.INVISIBLE);
				mode02on.setVisibility(View.INVISIBLE);
				mode03on.setVisibility(View.INVISIBLE);
				mode04on.setVisibility(View.VISIBLE);
				mode05on.setVisibility(View.INVISIBLE);
				
				contents_mode01.setVisibility(View.INVISIBLE);
				contents_mode02.setVisibility(View.INVISIBLE);
				contents_mode03.setVisibility(View.INVISIBLE);
				contents_mode04.setVisibility(View.VISIBLE);
				contents_mode05.setVisibility(View.INVISIBLE);
				
				SharedPreferences pref_w =getSharedPreferences("SearchStatus", MODE_PRIVATE);
				SharedPreferences.Editor editor_w = pref_w.edit();
				
				editor_w.putString("pref_Search_mode",		"2");
				editor_w.putString("pref_Search_Submode",		"4");
				editor_w.commit();
			}
		});
		mode05.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mode01on.setVisibility(View.INVISIBLE);
				mode02on.setVisibility(View.INVISIBLE);
				mode03on.setVisibility(View.INVISIBLE);
				mode04on.setVisibility(View.INVISIBLE);
				mode05on.setVisibility(View.VISIBLE);
				
				contents_mode01.setVisibility(View.INVISIBLE);
				contents_mode02.setVisibility(View.INVISIBLE);
				contents_mode03.setVisibility(View.INVISIBLE);
				contents_mode04.setVisibility(View.INVISIBLE);
				contents_mode05.setVisibility(View.VISIBLE);
				
				SharedPreferences pref_w =getSharedPreferences("SearchStatus", MODE_PRIVATE);
				SharedPreferences.Editor editor_w = pref_w.edit();
				
				editor_w.putString("pref_Search_mode",		"2");
				editor_w.putString("pref_Search_Submode",		"5");
				editor_w.commit();
				
			}
		});
		
	
		
		
		
		//header = getLayoutInflater().inflate(R.layout.board_list_header, null, false);
		//footer = getLayoutInflater().inflate(R.layout.board_list_footer, null, false);
		//list.addHeaderView(header);
		//list.addFooterView(footer);
		
		

// 식별검색 검색조건 초기화
		
		
		SharedPreferences pref_sb		= getSharedPreferences("SB_Search", MODE_PRIVATE);
		SharedPreferences.Editor sb_editor	= pref_sb.edit();
		String pref_SB_searchTxt	= pref_sb.getString("pref_SB_mode1_searchTxt", null);
		String pref_SB_mode1_searchTxt1	= pref_sb.getString("pref_SB_mode1_searchTxt1", null);
		String pref_SB_mode2_searchTxt1	= pref_sb.getString("pref_SB_mode2_searchTxt1", null);
		String pref_SB_mode4_searchTxt1	= pref_sb.getString("pref_SB_mode4_searchTxt1", null);
		String pref_SB_color1	= pref_sb.getString("pref_SB_color1", null);
		
		//식별문자
		EditText editText1 = (EditText) findViewById(R.id.editText1);
		
		if (pref_SB_searchTxt != null){
			editText1.setText(pref_SB_searchTxt.toString());
		}else{
			
		}

		
		
		
		
		//제형
		final ImageButton mode_btn02 = (ImageButton) findViewById(R.id.mode_btn02);
		final ImageButton mode_btn03 = (ImageButton) findViewById(R.id.mode_btn03);
		final ImageButton mode_btn04 = (ImageButton) findViewById(R.id.mode_btn04);
		
		final ImageButton mode_btn02on = (ImageButton) findViewById(R.id.mode_btn02on);
		final ImageButton mode_btn03on = (ImageButton) findViewById(R.id.mode_btn03on);
		final ImageButton mode_btn04on = (ImageButton) findViewById(R.id.mode_btn04on);
		
		mode_btn02on.setVisibility(View.INVISIBLE);
		mode_btn03on.setVisibility(View.INVISIBLE);
		mode_btn04on.setVisibility(View.INVISIBLE);
		
		
		
		
//		if(pref_SB_mode1_searchTxt1 != null){
//			mode_btn02.setVisibility(View.VISIBLE);
//			mode_btn02on.setVisibility(View.INVISIBLE);
//		}else{
//			mode_btn02.setVisibility(View.INVISIBLE);
//			mode_btn02on.setVisibility(View.VISIBLE);
//		}
//
//		if(pref_SB_mode1_searchTxt2 != null){
//			mode_btn03.setVisibility(View.VISIBLE);
//			mode_btn03on.setVisibility(View.INVISIBLE);
//		}else{
//			mode_btn03.setVisibility(View.INVISIBLE);
//			mode_btn03on.setVisibility(View.VISIBLE);
//		}
//		if(pref_SB_mode1_searchTxt3 != null){
//			mode_btn04.setVisibility(View.VISIBLE);
//			mode_btn04on.setVisibility(View.INVISIBLE);
//		}else{
//			mode_btn04.setVisibility(View.INVISIBLE);
//			mode_btn04on.setVisibility(View.VISIBLE);
//		}
		
		mode_btn02.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_mode1_searchTxt1",		"경질캡슐");
				editor_sbw.commit();

				mode_btn02.setVisibility(View.VISIBLE);
				mode_btn03.setVisibility(View.VISIBLE);
				mode_btn04.setVisibility(View.VISIBLE);
				mode_btn02on.setVisibility(View.VISIBLE);
				mode_btn03on.setVisibility(View.INVISIBLE);
				mode_btn04on.setVisibility(View.INVISIBLE);
			}
		});
		mode_btn03.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_mode1_searchTxt1",		"연질캡슐");
				editor_sbw.commit();
				mode_btn02.setVisibility(View.VISIBLE);
				mode_btn03.setVisibility(View.VISIBLE);
				mode_btn04.setVisibility(View.VISIBLE);
				mode_btn02on.setVisibility(View.INVISIBLE);
				mode_btn03on.setVisibility(View.VISIBLE);
				mode_btn04on.setVisibility(View.INVISIBLE);
			}
		});
		mode_btn04.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_mode1_searchTxt1",		"정제");
				editor_sbw.commit();
				mode_btn02.setVisibility(View.VISIBLE);
				mode_btn03.setVisibility(View.VISIBLE);
				mode_btn04.setVisibility(View.VISIBLE);
				mode_btn02on.setVisibility(View.INVISIBLE);
				mode_btn03on.setVisibility(View.INVISIBLE);
				mode_btn04on.setVisibility(View.VISIBLE);
			}
		});
		
		mode_btn02on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_mode1_searchTxt1",		null);
				editor_sbw.commit();
				mode_btn02.setVisibility(View.VISIBLE);
				mode_btn03.setVisibility(View.VISIBLE);
				mode_btn04.setVisibility(View.VISIBLE);
				mode_btn02on.setVisibility(View.INVISIBLE);
				mode_btn03on.setVisibility(View.INVISIBLE);
				mode_btn04on.setVisibility(View.INVISIBLE);
			}
		});
		mode_btn03on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_mode1_searchTxt1",		null);
				editor_sbw.commit();
				mode_btn02.setVisibility(View.VISIBLE);
				mode_btn03.setVisibility(View.VISIBLE);
				mode_btn04.setVisibility(View.VISIBLE);
				mode_btn02on.setVisibility(View.INVISIBLE);
				mode_btn03on.setVisibility(View.INVISIBLE);
				mode_btn04on.setVisibility(View.INVISIBLE);
			}
		});
		mode_btn04on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_mode1_searchTxt1",		null);
				editor_sbw.commit();
				mode_btn02.setVisibility(View.VISIBLE);
				mode_btn03.setVisibility(View.VISIBLE);
				mode_btn04.setVisibility(View.VISIBLE);
				mode_btn02on.setVisibility(View.INVISIBLE);
				mode_btn03on.setVisibility(View.INVISIBLE);
				mode_btn04on.setVisibility(View.INVISIBLE);
			}
		});
		
		//모양
		final ImageButton mode2_btn02 = (ImageButton) findViewById(R.id.mode2_btn02);
		final ImageButton mode2_btn03 = (ImageButton) findViewById(R.id.mode2_btn03);
		final ImageButton mode2_btn04 = (ImageButton) findViewById(R.id.mode2_btn04);
		
		final ImageButton mode2_btn02on = (ImageButton) findViewById(R.id.mode2_btn02on);
		final ImageButton mode2_btn03on = (ImageButton) findViewById(R.id.mode2_btn03on);
		final ImageButton mode2_btn04on = (ImageButton) findViewById(R.id.mode2_btn04on);
		
		
		mode2_btn02on.setVisibility(View.INVISIBLE);
		mode2_btn03on.setVisibility(View.INVISIBLE);
		mode2_btn04on.setVisibility(View.INVISIBLE);
		
//		if(pref_SB_mode2_searchTxt1 != null){
//			mode2_btn02.setVisibility(View.VISIBLE);
//			mode2_btn02on.setVisibility(View.INVISIBLE);
//		}else{
//			mode2_btn02.setVisibility(View.INVISIBLE);
//			mode2_btn02on.setVisibility(View.VISIBLE);
//		}
//		
//		if(pref_SB_mode2_searchTxt2 != null){
//			mode2_btn03.setVisibility(View.VISIBLE);
//			mode2_btn03on.setVisibility(View.INVISIBLE);
//		}else{
//			mode2_btn03.setVisibility(View.INVISIBLE);
//			mode2_btn03on.setVisibility(View.VISIBLE);
//		}
//		
//		if(pref_SB_mode2_searchTxt3 != null){
//			mode2_btn04.setVisibility(View.VISIBLE);
//			mode2_btn04on.setVisibility(View.INVISIBLE);
//		}else{
//			mode2_btn04.setVisibility(View.INVISIBLE);
//			mode2_btn04on.setVisibility(View.VISIBLE);
//		}
		
		
		mode2_btn02.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_mode2_searchTxt1",		"원형");
				editor_sbw.commit();
				mode2_btn02.setVisibility(View.VISIBLE);
				mode2_btn03.setVisibility(View.VISIBLE);
				mode2_btn04.setVisibility(View.VISIBLE);
				
				mode2_btn02on.setVisibility(View.VISIBLE);
				mode2_btn03on.setVisibility(View.INVISIBLE);
				mode2_btn04on.setVisibility(View.INVISIBLE);
			}
		});
		mode2_btn03.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_mode2_searchTxt1",		"사각형");
				editor_sbw.commit();
				mode2_btn02.setVisibility(View.VISIBLE);
				mode2_btn03.setVisibility(View.VISIBLE);
				mode2_btn04.setVisibility(View.VISIBLE);
				
				mode2_btn02on.setVisibility(View.INVISIBLE);
				mode2_btn03on.setVisibility(View.VISIBLE);
				mode2_btn04on.setVisibility(View.INVISIBLE);
			}
		});
		mode2_btn04.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_mode2_searchTxt1",		"타원형");
				editor_sbw.commit();
				mode2_btn02.setVisibility(View.VISIBLE);
				mode2_btn03.setVisibility(View.VISIBLE);
				mode2_btn04.setVisibility(View.VISIBLE);
				
				mode2_btn02on.setVisibility(View.INVISIBLE);
				mode2_btn03on.setVisibility(View.INVISIBLE);
				mode2_btn04on.setVisibility(View.VISIBLE);
			}
		});
		
		mode2_btn02on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_mode2_searchTxt1",		null);
				editor_sbw.commit();
				mode2_btn02.setVisibility(View.VISIBLE);
				mode2_btn03.setVisibility(View.VISIBLE);
				mode2_btn04.setVisibility(View.VISIBLE);
				
				mode2_btn02on.setVisibility(View.INVISIBLE);
				mode2_btn03on.setVisibility(View.INVISIBLE);
				mode2_btn04on.setVisibility(View.INVISIBLE);
			}
		});
		mode2_btn03on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_mode2_searchTxt1",		null);
				editor_sbw.commit();
				mode2_btn02.setVisibility(View.VISIBLE);
				mode2_btn03.setVisibility(View.VISIBLE);
				mode2_btn04.setVisibility(View.VISIBLE);
				
				mode2_btn02on.setVisibility(View.INVISIBLE);
				mode2_btn03on.setVisibility(View.INVISIBLE);
				mode2_btn04on.setVisibility(View.INVISIBLE);
			}
		});
		mode2_btn04on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_mode2_searchTxt1",		null);
				editor_sbw.commit();
				mode2_btn02.setVisibility(View.VISIBLE);
				mode2_btn03.setVisibility(View.VISIBLE);
				mode2_btn04.setVisibility(View.VISIBLE);
				
				mode2_btn02on.setVisibility(View.INVISIBLE);
				mode2_btn03on.setVisibility(View.INVISIBLE);
				mode2_btn04on.setVisibility(View.INVISIBLE);
			}
		});
		
		
		
		//분할선
		final ImageButton mode4_btn02 = (ImageButton) findViewById(R.id.mode4_btn02);
		final ImageButton mode4_btn03 = (ImageButton) findViewById(R.id.mode4_btn03);
		
		final ImageButton mode4_btn02on = (ImageButton) findViewById(R.id.mode4_btn02on);
		final ImageButton mode4_btn03on = (ImageButton) findViewById(R.id.mode4_btn03on);
		
		
		mode4_btn02on.setVisibility(View.INVISIBLE);
		mode4_btn03on.setVisibility(View.INVISIBLE);
		
//		if(pref_SB_mode4_searchTxt1 != null){
//			mode4_btn02.setVisibility(View.VISIBLE);
//			mode4_btn02on.setVisibility(View.INVISIBLE);
//		}else{
//			mode4_btn02.setVisibility(View.INVISIBLE);
//			mode4_btn02on.setVisibility(View.VISIBLE);
//		}
//		
//		if(pref_SB_mode4_searchTxt2 != null){
//			mode4_btn03.setVisibility(View.VISIBLE);
//			mode4_btn03on.setVisibility(View.INVISIBLE);
//		}else{
//			mode4_btn03.setVisibility(View.INVISIBLE);
//			mode4_btn03on.setVisibility(View.VISIBLE);
//		}
//		


		mode4_btn02.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_mode4_searchTxt1",		"+");
				editor_sbw.commit();
				mode4_btn02.setVisibility(View.VISIBLE);
				mode4_btn03.setVisibility(View.VISIBLE);
				
				mode4_btn02on.setVisibility(View.VISIBLE);
				mode4_btn03on.setVisibility(View.INVISIBLE);
			}
		});
		mode4_btn03.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_mode4_searchTxt1",		"-");
				editor_sbw.commit();
				mode4_btn02.setVisibility(View.VISIBLE);
				mode4_btn03.setVisibility(View.VISIBLE);
				
				mode4_btn02on.setVisibility(View.INVISIBLE);
				mode4_btn03on.setVisibility(View.VISIBLE);
			}
		});
		
		mode4_btn02on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_mode4_searchTxt1",		null);
				editor_sbw.commit();
				mode4_btn02.setVisibility(View.VISIBLE);
				mode4_btn03.setVisibility(View.VISIBLE);
				
				mode4_btn02on.setVisibility(View.INVISIBLE);
				mode4_btn03on.setVisibility(View.INVISIBLE);
			}
		});
		mode4_btn03on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_mode4_searchTxt1",		null);
				editor_sbw.commit();
				mode4_btn02.setVisibility(View.VISIBLE);
				mode4_btn03.setVisibility(View.VISIBLE);
				
				mode4_btn02on.setVisibility(View.INVISIBLE);
				mode4_btn03on.setVisibility(View.INVISIBLE);
			}
		});
		
		
		
		
		//색상
		final ImageButton btn_color_01 = (ImageButton) findViewById(R.id.btn_color_01);
		final ImageButton btn_color_02 = (ImageButton) findViewById(R.id.btn_color_02);
		final ImageButton btn_color_03 = (ImageButton) findViewById(R.id.btn_color_03);
		final ImageButton btn_color_04 = (ImageButton) findViewById(R.id.btn_color_04);
		final ImageButton btn_color_05 = (ImageButton) findViewById(R.id.btn_color_05);
		final ImageButton btn_color_06 = (ImageButton) findViewById(R.id.btn_color_06);
		final ImageButton btn_color_07 = (ImageButton) findViewById(R.id.btn_color_07);
		final ImageButton btn_color_08 = (ImageButton) findViewById(R.id.btn_color_08);
		final ImageButton btn_color_09 = (ImageButton) findViewById(R.id.btn_color_09);
		final ImageButton btn_color_10 = (ImageButton) findViewById(R.id.btn_color_10);
		final ImageButton btn_color_11 = (ImageButton) findViewById(R.id.btn_color_11);
		final ImageButton btn_color_12 = (ImageButton) findViewById(R.id.btn_color_12);
		final ImageButton btn_color_13 = (ImageButton) findViewById(R.id.btn_color_13);
		final ImageButton btn_color_14 = (ImageButton) findViewById(R.id.btn_color_14);
		final ImageButton btn_color_15 = (ImageButton) findViewById(R.id.btn_color_15);
		
		final ImageButton btn_color_01on = (ImageButton) findViewById(R.id.btn_color_01on);
		final ImageButton btn_color_02on = (ImageButton) findViewById(R.id.btn_color_02on);
		final ImageButton btn_color_03on = (ImageButton) findViewById(R.id.btn_color_03on);
		final ImageButton btn_color_04on = (ImageButton) findViewById(R.id.btn_color_04on);
		final ImageButton btn_color_05on = (ImageButton) findViewById(R.id.btn_color_05on);
		final ImageButton btn_color_06on = (ImageButton) findViewById(R.id.btn_color_06on);
		final ImageButton btn_color_07on = (ImageButton) findViewById(R.id.btn_color_07on);
		final ImageButton btn_color_08on = (ImageButton) findViewById(R.id.btn_color_08on);
		final ImageButton btn_color_09on = (ImageButton) findViewById(R.id.btn_color_09on);
		final ImageButton btn_color_10on = (ImageButton) findViewById(R.id.btn_color_10on);
		final ImageButton btn_color_11on = (ImageButton) findViewById(R.id.btn_color_11on);
		final ImageButton btn_color_12on = (ImageButton) findViewById(R.id.btn_color_12on);
		final ImageButton btn_color_13on = (ImageButton) findViewById(R.id.btn_color_13on);
		final ImageButton btn_color_14on = (ImageButton) findViewById(R.id.btn_color_14on);
		final ImageButton btn_color_15on = (ImageButton) findViewById(R.id.btn_color_15on);
		
		
		btn_color_01on.setVisibility(View.INVISIBLE);
		btn_color_02on.setVisibility(View.INVISIBLE);
		btn_color_03on.setVisibility(View.INVISIBLE);
		btn_color_04on.setVisibility(View.INVISIBLE);
		btn_color_05on.setVisibility(View.INVISIBLE);
		btn_color_06on.setVisibility(View.INVISIBLE);
		btn_color_07on.setVisibility(View.INVISIBLE);
		btn_color_08on.setVisibility(View.INVISIBLE);
		btn_color_09on.setVisibility(View.INVISIBLE);
		btn_color_10on.setVisibility(View.INVISIBLE);
		btn_color_11on.setVisibility(View.INVISIBLE);
		btn_color_12on.setVisibility(View.INVISIBLE);
		btn_color_13on.setVisibility(View.INVISIBLE);
		btn_color_14on.setVisibility(View.INVISIBLE);
		btn_color_15on.setVisibility(View.INVISIBLE);
		
		
		
//		if(pref_SB_color1 != null){
//			btn_color_01.setVisibility(View.VISIBLE);
//			btn_color_01on.setVisibility(View.INVISIBLE);
//		}else{
//			btn_color_01.setVisibility(View.INVISIBLE);
//			btn_color_01on.setVisibility(View.VISIBLE);
//		}
//		
//		if(pref_SB_color2 != null){
//			btn_color_02.setVisibility(View.VISIBLE);
//			btn_color_02on.setVisibility(View.INVISIBLE);
//		}else{
//			btn_color_02.setVisibility(View.INVISIBLE);
//			btn_color_02on.setVisibility(View.VISIBLE);
//		}
//		if(pref_SB_color3 != null){
//			btn_color_03.setVisibility(View.VISIBLE);
//			btn_color_03on.setVisibility(View.INVISIBLE);
//		}else{
//			btn_color_03.setVisibility(View.INVISIBLE);
//			btn_color_03on.setVisibility(View.VISIBLE);
//		}
//		if(pref_SB_color4 != null){
//			btn_color_04.setVisibility(View.VISIBLE);
//			btn_color_04on.setVisibility(View.INVISIBLE);
//		}else{
//			btn_color_04.setVisibility(View.INVISIBLE);
//			btn_color_04on.setVisibility(View.VISIBLE);
//		}
//		if(pref_SB_color5 != null){
//			btn_color_05.setVisibility(View.VISIBLE);
//			btn_color_05on.setVisibility(View.INVISIBLE);
//		}else{
//			btn_color_05.setVisibility(View.INVISIBLE);
//			btn_color_05on.setVisibility(View.VISIBLE);
//		}
//		if(pref_SB_color6 != null){
//			btn_color_06.setVisibility(View.VISIBLE);
//			btn_color_06on.setVisibility(View.INVISIBLE);
//		}else{
//			btn_color_06.setVisibility(View.INVISIBLE);
//			btn_color_06on.setVisibility(View.VISIBLE);
//		}
//		if(pref_SB_color7 != null){
//			btn_color_07.setVisibility(View.VISIBLE);
//			btn_color_07on.setVisibility(View.INVISIBLE);
//		}else{
//			btn_color_07.setVisibility(View.INVISIBLE);
//			btn_color_07on.setVisibility(View.VISIBLE);
//		}
//		if(pref_SB_color8 != null){
//			btn_color_08.setVisibility(View.VISIBLE);
//			btn_color_08on.setVisibility(View.INVISIBLE);
//		}else{
//			btn_color_08.setVisibility(View.INVISIBLE);
//			btn_color_08on.setVisibility(View.VISIBLE);
//		}
//		if(pref_SB_color9 != null){
//			btn_color_09.setVisibility(View.VISIBLE);
//			btn_color_09on.setVisibility(View.INVISIBLE);
//		}else{
//			btn_color_09.setVisibility(View.INVISIBLE);
//			btn_color_09on.setVisibility(View.VISIBLE);
//		}
//		if(pref_SB_color10 != null){
//			btn_color_10.setVisibility(View.VISIBLE);
//			btn_color_10on.setVisibility(View.INVISIBLE);
//		}else{
//			btn_color_10.setVisibility(View.INVISIBLE);
//			btn_color_10on.setVisibility(View.VISIBLE);
//		}
//		if(pref_SB_color11 != null){
//			btn_color_11.setVisibility(View.VISIBLE);
//			btn_color_11on.setVisibility(View.INVISIBLE);
//		}else{
//			btn_color_11.setVisibility(View.INVISIBLE);
//			btn_color_11on.setVisibility(View.VISIBLE);
//		}
//		if(pref_SB_color12 != null){
//			btn_color_12.setVisibility(View.VISIBLE);
//			btn_color_12on.setVisibility(View.INVISIBLE);
//		}else{
//			btn_color_12.setVisibility(View.INVISIBLE);
//			btn_color_12on.setVisibility(View.VISIBLE);
//		}
//		if(pref_SB_color13 != null){
//			btn_color_13.setVisibility(View.VISIBLE);
//			btn_color_13on.setVisibility(View.INVISIBLE);
//		}else{
//			btn_color_13.setVisibility(View.INVISIBLE);
//			btn_color_13on.setVisibility(View.VISIBLE);
//		}
//		if(pref_SB_color14 != null){
//			btn_color_14.setVisibility(View.VISIBLE);
//			btn_color_14on.setVisibility(View.INVISIBLE);
//		}else{
//			btn_color_14.setVisibility(View.INVISIBLE);
//			btn_color_14on.setVisibility(View.VISIBLE);
//		}
//		if(pref_SB_color15 != null){
//			btn_color_15.setVisibility(View.VISIBLE);
//			btn_color_15on.setVisibility(View.INVISIBLE);
//		}else{
//			btn_color_15.setVisibility(View.INVISIBLE);
//			btn_color_15on.setVisibility(View.VISIBLE);
//		}
//		
//		
		

	
		
		btn_color_01.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		"하양");
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
				
				btn_color_01on.setVisibility(View.VISIBLE);
			}
		});
		btn_color_02.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		"노랑");
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.VISIBLE);
			}
		});
		btn_color_03.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		"주황");
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.VISIBLE);
			}
		});
		btn_color_04.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		"분홍");
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.VISIBLE);
			}
		});
		btn_color_05.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		"빨강");
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.VISIBLE);
			}
		});
		btn_color_06.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		"갈색");
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.VISIBLE);
			}
		});
		btn_color_07.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		"연두");
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.VISIBLE);
			}
		});
		btn_color_08.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		"초록");
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.VISIBLE);
			}
		});
		btn_color_09.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		"청록");
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.VISIBLE);
			}
		});
		btn_color_10.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		"파랑");
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.VISIBLE);
			}
		});
		btn_color_11.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		"남색");
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.VISIBLE);
			}
		});
		btn_color_12.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		"자주");
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.VISIBLE);
			}
		});
		btn_color_13.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		"보라");
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.VISIBLE);
			}
		});
		btn_color_14.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		"회색");
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.VISIBLE);
			}
		});
		btn_color_15.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		"투명");
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.VISIBLE);
			}
		});
/////////////////////////////////
		
		btn_color_01on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		null);
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
			}
		});
		btn_color_02on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		null);
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
			}
		});
		btn_color_03on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		null);
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
			}
		});
		btn_color_04on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		null);
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
			}
		});
		btn_color_05on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		null);
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
			}
		});
		btn_color_06on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		null);
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
			}
		});
		btn_color_07on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		null);
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
			}
		});
		btn_color_08on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		null);
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
			}
		});
		btn_color_09on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		null);
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
			}
		});
		btn_color_10on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		null);
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
			}
		});
		btn_color_11on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		null);
				editor_sbw.commit();
				btn_color_11.setVisibility(View.VISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
			}
		});
		btn_color_12on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		null);
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
			}
		});
		btn_color_13on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		null);
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
			}
		});
		btn_color_14on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		null);
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
			}
		});
		btn_color_15on.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences pref_sbw =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbw = pref_sbw.edit();
				editor_sbw.putString("pref_SB_color1",		null);
				editor_sbw.commit();
				btn_color_01on.setVisibility(View.INVISIBLE);
				btn_color_02on.setVisibility(View.INVISIBLE);
				btn_color_03on.setVisibility(View.INVISIBLE);
				btn_color_04on.setVisibility(View.INVISIBLE);
				btn_color_05on.setVisibility(View.INVISIBLE);
				btn_color_06on.setVisibility(View.INVISIBLE);
				btn_color_07on.setVisibility(View.INVISIBLE);
				btn_color_08on.setVisibility(View.INVISIBLE);
				btn_color_09on.setVisibility(View.INVISIBLE);
				btn_color_10on.setVisibility(View.INVISIBLE);
				btn_color_11on.setVisibility(View.INVISIBLE);
				btn_color_12on.setVisibility(View.INVISIBLE);
				btn_color_13on.setVisibility(View.INVISIBLE);
				btn_color_14on.setVisibility(View.INVISIBLE);
				btn_color_15on.setVisibility(View.INVISIBLE);
			}
		});
///////////////////////
		
		
		
		
		
		
		topToggleButton1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				top_1.setVisibility(View.INVISIBLE);
				top_2.setVisibility(View.VISIBLE);
				SharedPreferences pref_r =getSharedPreferences("SearchStatus", MODE_PRIVATE);
				SharedPreferences.Editor editor = pref_r.edit();
				String pref_Search_mode = pref_r.getString("pref_Search_mode", "1"); //키값, 디폴트값
				String pref_Search_Submode = pref_r.getString("pref_Search_Submode", "1"); //키값, 디폴트값
				
				if (pref_Search_Submode == "1"){
					mode01on.setVisibility(View.VISIBLE);
					mode02on.setVisibility(View.INVISIBLE);
					mode03on.setVisibility(View.INVISIBLE);
					mode04on.setVisibility(View.INVISIBLE);
					mode05on.setVisibility(View.INVISIBLE);
					
					contents_mode01.setVisibility(View.VISIBLE);
					contents_mode02.setVisibility(View.INVISIBLE);
					contents_mode03.setVisibility(View.INVISIBLE);
					contents_mode04.setVisibility(View.INVISIBLE);
					contents_mode05.setVisibility(View.INVISIBLE);
				}else if (pref_Search_Submode == "2"){
					mode01on.setVisibility(View.INVISIBLE);
					mode02on.setVisibility(View.VISIBLE);
					mode03on.setVisibility(View.INVISIBLE);
					mode04on.setVisibility(View.INVISIBLE);
					mode05on.setVisibility(View.INVISIBLE);
					
					contents_mode01.setVisibility(View.INVISIBLE);
					contents_mode02.setVisibility(View.VISIBLE);
					contents_mode03.setVisibility(View.INVISIBLE);
					contents_mode04.setVisibility(View.INVISIBLE);
					contents_mode05.setVisibility(View.INVISIBLE);
				}else if (pref_Search_Submode == "3"){
					mode01on.setVisibility(View.INVISIBLE);
					mode02on.setVisibility(View.INVISIBLE);
					mode03on.setVisibility(View.VISIBLE);
					mode04on.setVisibility(View.INVISIBLE);
					mode05on.setVisibility(View.INVISIBLE);
					
					contents_mode01.setVisibility(View.INVISIBLE);
					contents_mode02.setVisibility(View.INVISIBLE);
					contents_mode03.setVisibility(View.VISIBLE);
					contents_mode04.setVisibility(View.INVISIBLE);
					contents_mode05.setVisibility(View.INVISIBLE);
				}else if (pref_Search_Submode == "4"){
					mode01on.setVisibility(View.INVISIBLE);
					mode02on.setVisibility(View.INVISIBLE);
					mode03on.setVisibility(View.INVISIBLE);
					mode04on.setVisibility(View.VISIBLE);
					mode05on.setVisibility(View.INVISIBLE);
					
					contents_mode01.setVisibility(View.INVISIBLE);
					contents_mode02.setVisibility(View.INVISIBLE);
					contents_mode03.setVisibility(View.INVISIBLE);
					contents_mode04.setVisibility(View.VISIBLE);
					contents_mode05.setVisibility(View.INVISIBLE);
				}else if (pref_Search_Submode == "5"){
					mode01on.setVisibility(View.INVISIBLE);
					mode02on.setVisibility(View.INVISIBLE);
					mode03on.setVisibility(View.INVISIBLE);
					mode04on.setVisibility(View.INVISIBLE);
					mode05on.setVisibility(View.VISIBLE);
					
					contents_mode01.setVisibility(View.INVISIBLE);
					contents_mode02.setVisibility(View.INVISIBLE);
					contents_mode03.setVisibility(View.INVISIBLE);
					contents_mode04.setVisibility(View.INVISIBLE);
					contents_mode05.setVisibility(View.VISIBLE);
				}
				else
				{
					mode01on.setVisibility(View.VISIBLE);
					mode02on.setVisibility(View.INVISIBLE);
					mode03on.setVisibility(View.INVISIBLE);
					mode04on.setVisibility(View.INVISIBLE);
					mode05on.setVisibility(View.INVISIBLE);
					
					contents_mode01.setVisibility(View.VISIBLE);
					contents_mode02.setVisibility(View.INVISIBLE);
					contents_mode03.setVisibility(View.INVISIBLE);
					contents_mode04.setVisibility(View.INVISIBLE);
					contents_mode05.setVisibility(View.INVISIBLE);
				}
				contents_result.setVisibility(View.INVISIBLE);
				
				
				
				
			}
		});
		topToggleButton2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				top_2.setVisibility(View.INVISIBLE);
				top_1.setVisibility(View.VISIBLE);
				
				
				contents_mode01.setVisibility(View.INVISIBLE);
				contents_mode02.setVisibility(View.INVISIBLE);
				contents_mode03.setVisibility(View.INVISIBLE);
				contents_mode04.setVisibility(View.INVISIBLE);
				contents_mode05.setVisibility(View.INVISIBLE);
				
				contents_result.setVisibility(View.VISIBLE);
				
			}
		});
		
		




		mImm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		
		

		
		
////////////////////////////////////////////////////////////////////////////////////////////////////
// 텍스트 검색버튼 시작
////////////////////////////////////////////////////////////////////////////////////////////////////

		ImageButton01.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				contents_mode01.setVisibility(View.INVISIBLE);
				contents_mode02.setVisibility(View.INVISIBLE);
				contents_mode03.setVisibility(View.INVISIBLE);
				contents_mode04.setVisibility(View.INVISIBLE);
				contents_mode05.setVisibility(View.INVISIBLE);

				contents_result.setVisibility(View.VISIBLE);
				// TODO Auto-generated method stub
				EditText inputText = (EditText) findViewById(R.id.editext_searchText);
				searchText = inputText.getText().toString();

				songsList = new ArrayList<HashMap<String, String>>();
				adapter = new Pharm_SearchList_LazyAdapter(
						Pharm_SearchList.this, songsList);

				mImm.hideSoftInputFromWindow(inputText.getWindowToken(), 0);

				list.setAdapter(adapter);

				if (searchText.length() <= 0) {
					AlertDialog.Builder ad = new AlertDialog.Builder(
							Pharm_SearchList.this);
					ad.setMessage("검색어를 입력하세요.")
					// .setTitle("타이틀").setCancelable(false)
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											searchText = "";
										}
									});
					ad.show();

				} else if (searchText.length() < 2) {
					AlertDialog.Builder ad = new AlertDialog.Builder(
							Pharm_SearchList.this);
					ad.setMessage("검색어는 2자 이상이 여야 합니다.")
					// .setTitle("타이틀").setCancelable(false)
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											searchText = "";
										}
									});
					ad.show();
				} else if (searchText.length() > 10) {
					AlertDialog.Builder ad = new AlertDialog.Builder(
							Pharm_SearchList.this);
					ad.setMessage("검색어는 10자 이하여야 합니다.")
					// .setTitle("타이틀").setCancelable(false)
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											searchText = "";
										}
									});
					ad.show();
				} else if ((searchText.indexOf("'") > 0)
						|| (searchText.indexOf("<") > 0)
						|| (searchText.indexOf(">") > 0)
						|| (searchText.indexOf("--") > 0)
						|| (searchText.indexOf("\"") > 0)
						|| (searchText.indexOf(",") > 0)
						|| (searchText.indexOf("&") > 0)
						|| (searchText.indexOf("|") > 0)
						|| (searchText.indexOf("!") > 0)
						|| (searchText.indexOf("{") > 0)
						|| (searchText.indexOf("}") > 0)
						|| (searchText.indexOf("`") > 0)) {
					AlertDialog.Builder ad = new AlertDialog.Builder(
							Pharm_SearchList.this);
					ad.setMessage("특수문자는 입력 하실 수 없습니다.")
					// .setTitle("타이틀").setCancelable(false)
							.setPositiveButton("확인",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											searchText = "";
										}
									});
					ad.show();
				} else {
					progressDialog = ProgressDialog.show(Pharm_SearchList.this,
							"", "데이터 로딩중...");

					list.setOnScrollListener(new OnScrollListener() {
						// useless here, skip!
						@Override
						public void onScrollStateChanged(AbsListView view,
								int scrollState) {
						}

						// dumdumdum
						@Override
						public void onScroll(AbsListView view,
								int firstVisibleItem, int visibleItemCount,
								int totalItemCount) {

							int lastInScreen = firstVisibleItem
									+ visibleItemCount;
							////////////////////// 일시적으로 주석처리함
							/*
							if ((lastInScreen >= (totalItemCount - 5))
									&& !(nowListing)) {
								Thread thread = new Thread(null,
										loadMoreListItems);
								thread.start();
							}
							*/
						}
					});
					Thread thread = new Thread(null, loadMoreListItems);
					thread.start();
				}

			} // END 텍스트 검색버튼 onclick()
		});
////////////////////////////////////////////////////////////////////////////////////////////////////
// 텍스트 검색버튼 끝
////////////////////////////////////////////////////////////////////////////////////////////////////		

		
		
		
////////////////////////////////////////////////////////////////////////////////////////////////////
//식별 검색버튼 시작
////////////////////////////////////////////////////////////////////////////////////////////////////

		ImageButton03.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				contents_mode01.setVisibility(View.INVISIBLE);
				contents_mode02.setVisibility(View.INVISIBLE);
				contents_mode03.setVisibility(View.INVISIBLE);
				contents_mode04.setVisibility(View.INVISIBLE);
				contents_mode05.setVisibility(View.INVISIBLE);

				contents_result.setVisibility(View.VISIBLE);
				// TODO Auto-generated method stub
				EditText inputText = (EditText) findViewById(R.id.editext_searchText);
				searchText = inputText.getText().toString();

				songsList = new ArrayList<HashMap<String, String>>();
				adapter = new Pharm_SearchList_LazyAdapter(
						Pharm_SearchList.this, songsList);

				mImm.hideSoftInputFromWindow(inputText.getWindowToken(), 0);

				list.setAdapter(adapter);

				
				
				
				
				
				
					progressDialog = ProgressDialog.show(Pharm_SearchList.this,"", "데이터 로딩중...");

					list.setOnScrollListener(new OnScrollListener() {
						// useless here, skip!
						@Override
						public void onScrollStateChanged(AbsListView view,
								int scrollState) {
						}

						// dumdumdum
						@Override
						public void onScroll(AbsListView view,
								int firstVisibleItem, int visibleItemCount,
								int totalItemCount) {

							int lastInScreen = firstVisibleItem
									+ visibleItemCount;

							
							////////////////////// 일시적으로 주석처리함
							/*
							if ((lastInScreen >= (totalItemCount))
									&& !(nowListing)) {
								Thread thread = new Thread(null,
										loadMoreListItems_SB);
								thread.start();
							}
							*/
						}
					});
					Thread thread = new Thread(null, loadMoreListItems_SB);
					thread.start();


			} // END 텍스트 검색버튼 onclick()
		});
////////////////////////////////////////////////////////////////////////////////////////////////////
//식별 검색버튼 끝
////////////////////////////////////////////////////////////////////////////////////////////////////
		
	} // END onCreate()

    //일반검색
    private Runnable loadMoreListItems = new Runnable() {			
		@Override
		public void run() {
			nowListing = true;
	    	try { 
	    		//Thread.sleep(100);
				Board_List_URL = URL + "?drug_name=" + searchText + "&z=" + pageNum;
				//?drug_name=%EC%95%84%EC%8A%A4%ED%94%BC%EB%A6%B0
				
				
				//Board_List_URL = URL;
				XMLParser parser = new XMLParser();
				String xml = parser.getXmlFromUrl(Board_List_URL); // getting XML from URL
				Document doc = parser.getDomElement(xml); // getting DOM element
				NodeList nl = doc.getElementsByTagName(KEY_SONG);

				//progressDialog = ProgressDialog.show(Board_List.this, "", "Loading...");
				for (int i = 0; i < nl.getLength(); i++) {
					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();
					Element e = (Element) nl.item(i);
					// adding each child node to HashMap key => value
					map.put(KEY_ID, parser.getValue(e, KEY_ID));
					map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
					map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));
					map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
					map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));
					// adding HashList to ArrayList
					songsList.add(map);
				}   	
				//Done! now continue on the UI thread
		        runOnUiThread(returnRes);
	    	} catch (Exception e) {}
		}
	};
	//식별검색
    private Runnable loadMoreListItems_SB = new Runnable() {			
		@Override
		public void run() {
			nowListing = true;
	    	try { 
	    		//Thread.sleep(100);
				//Board_List_URL = URL_SB + "?drug_name=" + searchText + "&z=" + pageNum;
	    		
	    		
	    		SharedPreferences pref_sbr =getSharedPreferences("SB_Search", MODE_PRIVATE);
				SharedPreferences.Editor editor_sbr = pref_sbr.edit();
				String pref_SB_searchTxt = pref_sbr.getString("pref_SB_searchTxt", "");
				String pref_SB_mode1_searchTxt1 = pref_sbr.getString("pref_SB_mode1_searchTxt1", "");
				String pref_SB_mode2_searchTxt1 = pref_sbr.getString("pref_SB_mode2_searchTxt1", "");
				String pref_SB_mode4_searchTxt1 = pref_sbr.getString("pref_SB_mode4_searchTxt1", "");
				String pref_SB_color1 = pref_sbr.getString("pref_SB_color1", "");
				
				Board_List_URL = URL_SB + "?print=" + pref_SB_searchTxt + "&drug_form=" + pref_SB_mode1_searchTxt1 + "&drug_shape=" + pref_SB_mode2_searchTxt1 + "&drug_line=" + pref_SB_mode4_searchTxt1 + "&drug_color=" + pref_SB_color1 + "&z=" + pageNum;
				
				
				//?drug_name=%EC%95%84%EC%8A%A4%ED%94%BC%EB%A6%B0

				//Board_List_URL = URL;
				XMLParser parser = new XMLParser();
				String xml = parser.getXmlFromUrl(Board_List_URL); // getting XML from URL
				Document doc = parser.getDomElement(xml); // getting DOM element
				NodeList nl = doc.getElementsByTagName(KEY_SONG);

				//progressDialog = ProgressDialog.show(Board_List.this, "", "Loading...");
				for (int i = 0; i < nl.getLength(); i++) {
					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();
					Element e = (Element) nl.item(i);
					// adding each child node to HashMap key => value
					map.put(KEY_ID, parser.getValue(e, KEY_ID));
					map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
					map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));
					map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
					map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));
					// adding HashList to ArrayList
					songsList.add(map);
				}   	
				//Done! now continue on the UI thread
		        runOnUiThread(returnRes);
	    	} catch (Exception e) {}
		}
	};	
	
    private Runnable returnRes = new Runnable() {
        @Override
        public void run() {
			try
			{
				if(songsList != null && songsList.size() > 0){
	                for(int i=0;i<songsList.size();i++)
	                {
	                	//adapter.add(songsList.get(i));
	                }
	            }
				progressDialog.cancel();
	            adapter.notifyDataSetChanged();
	            
	            nowListing = false;
			}
			catch (Exception e)
			{
				
			}
        }
    };

}
