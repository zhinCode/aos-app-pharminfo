package com.example.kpic_app1;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Board_List2 extends Activity{
	// All static variables
	//static String URL = "http://110.9.251.219:8080/Service.Query/Query.Board.List.xml";
	
	
	
	
	String Board_List_URL = "";
	static final String LOG = "Board_List";
	// XML node keys
	static final String KEY_SONG = "song"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_TITLE = "title";
	static final String KEY_ARTIST = "artist";
	static final String KEY_DURATION = "duration";
	static final String KEY_THUMB_URL = "thumb_url";
	public int pageNum = 1;
	
	static String URL = "http://110.9.251.219:8080/Service.Query/test.asp";
	
	
	static int pageSize = 10;
	
	static int lastViewIndex;
	
	
	
	ProgressDialog progressDialog;
	LazyAdapter adapter;
	ListView list;
	Handler handler = new Handler();
	ArrayList<HashMap<String, String>> songsList;
	
	
	private boolean mLockListView;
	View header, footer;
	private TextView mFooterText, mHeaderText;
	boolean justOnce = true;
	//isHeader = false, isFooter = false;
	boolean nowListing = false;
	
	
	
	//LayoutInflater mInflater;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		songsList = new ArrayList<HashMap<String, String>>();
		setContentView(R.layout.board_list);
		//mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//list.addFooterView(mInflater.inflate(R.layout.board_list_preloading, null));
		
		header = getLayoutInflater().inflate(R.layout.board_list_header, null, false);
		footer = getLayoutInflater().inflate(R.layout.board_list_footer, null, false);
		list=(ListView)findViewById(R.id.list);
		list.addHeaderView(header);
		list.addFooterView(footer);
		
		adapter=new LazyAdapter(Board_List2.this, songsList);
		list.setAdapter(adapter);
		
		//list.setTranscriptMode(0);
		//list.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

		//TRANSCRIPT_MODE_DISABLED 
		//TRANSCRIPT_MODE_ALWAYS_SCROLL


		addItems(pageNum);

//		list.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//
//
//			}
//		});		
	}

	private void addItems(final int pageNums)
	{
		progressDialog = ProgressDialog.show(Board_List2.this, "", "Loading...");
		new Thread(){            
			public void run() {
				
				nowListing = true;
				Board_List_URL = URL + "?z=" + pageNums;
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
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//						adapter.notifyDataSetChanged();
//						if (pageNums > 2)
//						{
//							list.setSelection(songsList.size() -20);
//						}
//						//list.setSelection()
//						progressDialog.cancel();
//						//Toast.makeText(Board_List.this, pageNum + "" , Toast.LENGTH_LONG).show() ; 
//						nowListing = false;	
//					}
//				});
				
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						adapter.notifyDataSetChanged();
						if (pageNums > 2)
						{
							list.setSelection(songsList.size() -20);
						}
						//list.setSelection()
						progressDialog.cancel();
						//Toast.makeText(Board_List.this, pageNum + "" , Toast.LENGTH_LONG).show() ; 
						nowListing = false;	
					}
				});
				
				
				
			}
		}.start();

		list.setOnScrollListener(new OnScrollListener(){
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				mHeaderText = (TextView)header.findViewById(R.id.tv_list_header);
				mFooterText = (TextView)footer.findViewById(R.id.tv_list_footer);
				if((firstVisibleItem+visibleItemCount) == totalItemCount && !(nowListing)){ /// footer
						lastViewIndex = visibleItemCount;
						pageNum = pageNum + 1;
						addItems(pageNum);
				} 
				else if((firstVisibleItem == 0)) { /// header
					if(!nowListing) {
						mHeaderText.setText("헤더 로드됨");
					}
				} 
				else {
					mHeaderText.setText("헤더 로드전");
				}
				
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
				
				
			}
			
		});
	
	} // END addlist() 

	
	
	

}
