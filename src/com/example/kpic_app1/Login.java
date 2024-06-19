package com.example.kpic_app1;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener {
    private EditText id;
    private EditText passwd;
    private ProgressDialog pDialog;
    //private LinearLayout layout01;
    private Button button;
 
    
    
    
 
   @Override
    protected void onCreate(Bundle savedInstanceState) {
     // TODO Auto-generated method stub
         super.onCreate(savedInstanceState);
         setContentView(R.layout.login);
         id=(EditText)findViewById(R.id.editText_id);
         passwd=(EditText)findViewById(R.id.editText_pw);
         button=(Button)findViewById(R.id.button1);
         button.setOnClickListener(this);
         //layout01=(LinearLayout)findViewById(R.id.main);

         
         
 //���б�
         SharedPreferences prefs =getSharedPreferences("LoginStatus", MODE_PRIVATE);
         String pref_Login_ID = prefs.getString("Login_ID", null); //Ű��, ����Ʈ��
         String pref_Login_PW = prefs.getString("Login_PW", null); //Ű��, ����Ʈ��
         String pref_isSavingID = prefs.getString("isSavingID", "NO"); //Ű��, ����Ʈ��
         String pref_isAutoLogin = prefs.getString("isAutoLogin", "NO"); //Ű��, ����Ʈ��
           
           
//Ű������ ��� ���尪 ��������
//        SharedPreferences prefb =getSharedPreferences("test", MODE_PRIVATE);
//        Collection<?> col =  prefb.getAll().values();
//        Iterator<?> it = col.iterator();
//           
//         while(it.hasNext())
//         {
//               String msg = (String)it.next();
//               Log.d("Result", msg);
//         }
         
         

    }

 

   @Override
   public void onClick(View v) {
      // TODO Auto-generated method stub
      loginProcess();      //�α��� ��ư�� Ŭ���Ǹ� �α��� ó���� �����Ѵ�.
    }
 

   // ��Ʈ�� ó������� ȭ�鿡 �ݿ��ϱ� ���� �ȵ���̵� �ڵ鷯
   // responseHandler�� ���� ó���� ����� success�� ��� ����ȭ���� �ʷϻ����� �ٲٰ�
   // �α����� �����ߴٴ� �޽����� �佺Ʈ�� ���
   // �α����� ������ ��� ����ȭ���� ���������� �ٲٰ� �α��ν��� �޽����� �佺Ʈ�� ���
   private final Handler handler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
            pDialog.dismiss();
            String result=msg.getData().getString("RESULT");
            if ( result.equals("success") ) {
                  //layout01.setBackgroundColor(Color.GREEN);
            	
                //SharedPreferences ���� ���� ����////////////////////////////////////////////////////
                SharedPreferences pref =getSharedPreferences("LoginStatus", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("pref_Login_ID",		id.getText().toString());
                editor.putString("pref_Login_PW",		passwd.getText().toString());
                //editor.putString("pref_isSavingID",	"YES" );
                //editor.putString("pref_isAutoLogin",	"YES" );
                editor.commit();
                //SharedPreferences ���� ���� ��////////////////////////////////////////////////////
            	
                  Toast.makeText(Login.this, "���������� �α����Ͽ����ϴ�.",
                                         Toast.LENGTH_LONG).show() ;
                  
                  Intent it = new Intent(Login.this, TabWidgetActivity.class);
                  it.putExtra("getID", id.getText().toString());
                  it.putExtra("getPW", passwd.getText().toString());
                  startActivity(it);
                  finish();
                  
                  
            } else {
                  //layout01.setBackgroundColor(Color.RED);
                  //Toast.makeText(Login.this, "�α��� ����",
                  //                       Toast.LENGTH_LONG).show() ;
                  
                  
                  
                  
                  SharedPreferences pref =getSharedPreferences("LoginStatus", MODE_PRIVATE);
                  SharedPreferences.Editor editor = pref.edit();
                  editor.putString("pref_Login_ID",		id.getText().toString());
                  editor.putString("pref_Login_PW",		passwd.getText().toString());
                  //editor.putString("pref_isSavingID",	"YES" );
                  //editor.putString("pref_isAutoLogin",	"YES" );
                  editor.commit();
                  //SharedPreferences ���� ���� ��////////////////////////////////////////////////////
              	
                    Toast.makeText(Login.this, "���������� �α����Ͽ����ϴ�.",
                                           Toast.LENGTH_LONG).show() ;
                    
                    Intent it = new Intent(Login.this, TabWidgetActivity.class);
                    it.putExtra("getID", id.getText().toString());
                    it.putExtra("getPW", passwd.getText().toString());
                    startActivity(it);
                    finish();
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
             }
       } 
    };
 

//�������� ���۵� XML �����͸� �Ľ��ϱ� ���� �޼���
//<result>success</result>
//�����ϴ� ��� <result>failed</result>�� ��ȯ�ϵ��� ������ �ξ���. 
    public String parsingData(InputStream input){
        String result=null;
        try {
             XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
             XmlPullParser parser = factory.newPullParser();
             parser.setInput(new InputStreamReader(input));
             while ( parser.next() != XmlPullParser.END_DOCUMENT) {
                 String name=parser.getName();
                  if ( name != null && name.equals("result"))
                         result=parser.nextText();
              }
         }catch(Exception e){e.printStackTrace();}
         return result;
     }
 

//�α��� ��ư�� Ŭ���Ǹ� ����Ǵ� �޼���
//  responseHandler�� Http��û�� ���� HttpResponse�� ��ȯ�Ǹ� ����� ó���ϱ� ����
//  �ݹ�޼��带 �����ϰ� �ִ� ��ü�̴�.
//  Response�� �ް� �Ǹ� parsingData()�޼��带 ȣ���Ͽ� ������ ���� ���� XML ������ ó���Ͽ�
// �װ���� result ���ڿ��� ��ȯ�޴´�.
// �̷��� ��ȯ���� result���ڿ��� ȭ�鿡 �ݿ��ϱ����� �ȵ���̵�UI�ڵ鷯�� handler�� ���� ���� �����Ѵ�.
    public void loginProcess() {
          final ResponseHandler<String> responseHandler=
               new ResponseHandler<String>() {

                 @Override
                 public String handleResponse(HttpResponse response)
                                                  throws ClientProtocolException, IOException {
                         String result=null;
                         HttpEntity entity=response.getEntity(); 
                         result=parsingData(entity.getContent());
                         Message message=handler.obtainMessage();
                         Bundle bundle=new Bundle();
                         if ( result.equals("success") ) 
                                      bundle.putString("RESULT", "success");
                         else
                                      bundle.putString("RESULT", "failed");
                         message.setData(bundle);
                         handler.sendMessage(message);
                         return result;
                 }
    };

 

     // �α����� ó���ǰ� �ִٴ� ���̾�α׸� ȭ�鿡 ǥ���Ѵ�.
     pDialog=ProgressDialog.show(this, "", "�α��� ó����....");

 

     // ������ HTTP ó�� ��û�� ���ο� �����带 �����Ͽ� �񵿱������ ó���ϴ°��� ȿ�����̴�.
     new Thread() {

           @Override
            public void run() {
                 String url = "http://110.9.251.219:8080/Service.Query/Query.Login.asp";
                 HttpClient http = new DefaultHttpClient();
                 try { 
                     // ������ ������ �Ķ���� ����   
                     ArrayList<NameValuePair> nameValuePairs = 
                                                              new ArrayList<NameValuePair>();
                     nameValuePairs.add(new BasicNameValuePair("id", id.getText().toString()));
                     nameValuePairs.add(new BasicNameValuePair("pw", passwd.getText().toString()));
            

                   //     ����ð��� 5�ʰ� ������ timeout ó���Ϸ��� �Ʒ� �ڵ��� Ŀ��Ʈ�� Ǯ�� �����Ѵ�.
                   //     HttpParams params = http.getParams();
                   //     HttpConnectionParams.setConnectionTimeout(params, 5000);
                   //     HttpConnectionParams.setSoTimeout(params, 5000);

 

                  // HTTP�� ���� ������ ��û�� �����Ѵ�.

               // ��û�� ���Ѱ���� responseHandler�� handleResponse()�޼��尡 ȣ��Ǿ� ó���Ѵ�.

                  // ������ ���޵Ǵ� �Ķ���Ͱ��� ���ڵ��ϱ����� UrlEncodedFormEntity() �޼��带 ����Ѵ�.

                  HttpPost httpPost = new HttpPost(url);
                  UrlEncodedFormEntity entityRequest = 
                                       new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
                  httpPost.setEntity(entityRequest);
                  http.execute(httpPost,responseHandler); 
                }catch(Exception e){e.printStackTrace();}
           }
      }.start();    //�����带 �����Ų��.
 } 

}



