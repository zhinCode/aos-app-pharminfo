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

public class Board_List extends Activity{
	String Board_List_URL = "";
	static final String LOG = "Board_List";
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
	View header, footer;
	boolean nowListing = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		songsList = new ArrayList<HashMap<String, String>>();
		setContentView(R.layout.board_list);
		//header = getLayoutInflater().inflate(R.layout.board_list_header, null, false);
		//footer = getLayoutInflater().inflate(R.layout.board_list_footer, null, false);
		list=(ListView)findViewById(R.id.list);
		//list.addHeaderView(header);
		//list.addFooterView(footer);
		
		adapter=new LazyAdapter(Board_List.this, songsList);
		list.setAdapter(adapter);
		list.setOnScrollListener(new OnScrollListener(){
			//useless here, skip!
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			//dumdumdum			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				int lastInScreen = firstVisibleItem + visibleItemCount;				

				if((lastInScreen >= (totalItemCount-5)) && !(nowListing)){				
					Thread thread =  new Thread(null, loadMoreListItems);
			        thread.start();
				}
			}
		});
		Thread thread =  new Thread(null, loadMoreListItems);
        thread.start();
	}

    //Runnable to load the items 
    private Runnable loadMoreListItems = new Runnable() {			
		@Override
		public void run() {
			nowListing = true;
	    	try { 
	    		//Thread.sleep(100);
				Board_List_URL = URL + "&z=" + pageNum;
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
	            adapter.notifyDataSetChanged();
	            nowListing = false;
			}
			catch (Exception e)
			{
				
			}
        }
    };

}
