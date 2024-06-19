package com.example.kpic_app1;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;



import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import android.app.ProgressDialog;
import android.widget.ListView;







public class NeverEndingList extends Activity {
	
	//how many to load on reaching the bottom
	int itemsPerPage = 30;
	boolean loadingMore = false;

	
	//ArrayList<String> myListItems;
	//ArrayAdapter<String> adapter;
	
	
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
	
	ProgressDialog progressDialog;
	LazyAdapter adapter;
	ListView list;
	//Handler handler = new Handler();
	ArrayList<HashMap<String, String>> songsList;	
	
	
	
	//For test data :-)
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listplaceholder);
        
		
		//This will hold the new items
        //myListItems = new ArrayList<String>();
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myListItems);
        
		
			
		
		//add the footer before adding the adapter, else the footer will nod load!
		View footerView = ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.board_list_footer, null, false);
		list=(ListView)findViewById(R.id.list);
		list.addFooterView(footerView);
		
		//Set the adapter
		
		adapter=new LazyAdapter(this, songsList);
		list.setAdapter(adapter);
		
		//this.setListAdapter(adapter);
		
		
		//Here is where the magic happens
		list.setOnScrollListener(new OnScrollListener(){
			
			//useless here, skip!
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
			
			//dumdumdum			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
				
				//what is the bottom iten that is visible
				int lastInScreen = firstVisibleItem + visibleItemCount;				
				
				//is the bottom item visible & not loading more already ? Load more !
				if((lastInScreen == totalItemCount) && !(loadingMore)){					
					Thread thread =  new Thread(null, loadMoreListItems);
			        thread.start();
				}
			}
		});
		
		
		//Load the first 15 items
		Thread thread =  new Thread(null, loadMoreListItems);
        thread.start();
    
    }
    
	
    //Runnable to load the items 
    private Runnable loadMoreListItems = new Runnable() {			
		@Override
		public void run() {
			//Set flag so we cant load new items 2 at the same time
			loadingMore = true;
			
			//Reset the array that holds the new items
	    	//myListItems = new ArrayList<String>();
	    	
	    	
	    	
	    	
	    	
	    	
	    	
			//Simulate a delay, delete this on a production environment!
	    	try { Thread.sleep(1000);
			} catch (InterruptedException e) {}
			
			//Get 15 new listitems
    	
			Board_List_URL = URL + "?z=" + pageNum;
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
	        
		}
	};	
	
    
	//Since we cant update our UI from a thread this Runnable takes care of that! 
    private Runnable returnRes = new Runnable() {
        @Override
        public void run() {
        	
			//Loop thru the new items and add them to the adapter
			if(songsList != null && songsList.size() > 0){
                for(int i=0;i<songsList.size();i++)
                {
                	//adapter.add(songsList.get(i));
                }
            }
        	
			//Update the Application title
        	//setTitle("Neverending List with " + String.valueOf(adapter.getCount()) + " items");
			
			//Tell to the adapter that changes have been made, this will cause the list to refresh
            adapter.notifyDataSetChanged();
			
			//Done loading more.
            loadingMore = false;
        }
    };
}