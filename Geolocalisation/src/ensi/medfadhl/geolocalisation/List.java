package ensi.medfadhl.geolocalisation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class List extends ListActivity   {
	private ListView list=null;
	private  ArrayList<HashMap<String,String>> output=null;
    private String table;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		list=getListView();
		//CONNECTION AVEC LE WEB SERVICE 
		
		
		ArrayList<NameValuePair> l=new ArrayList<NameValuePair>();
		if((GeolocalisationActivity.option==Global.MAP_NOM)||(GeolocalisationActivity.option==Global.MAP_REGION)){
			String nom=getIntent().getExtras().getString("requet");
			table=getIntent().getExtras().getString("table");
			String region=getIntent().getExtras().getString("region");
			String pays=getIntent().getExtras().getString("pays");
	    l.add(new BasicNameValuePair("nom",nom));
	    l.add(new BasicNameValuePair("table",table));
	    l.add(new BasicNameValuePair("region",region));
	    l.add(new BasicNameValuePair("pays",pays));
		}
        InputStream is=null;
        String result="";
        
        try{
        	HttpClient client=new DefaultHttpClient();
        	HttpPost post=null;
        	switch(GeolocalisationActivity.option){
        	case Global.MAP_NOM:
        	 post=new HttpPost("http://10.0.2.2/android/query_nom.php");
        	break;
        	case Global.MAP_REGION:
        	 post=new HttpPost("http://10.0.2.2/android/query_region.php");
        		break;
        	case Global.MAP_ENTIRE_HOTEL:
           	 post=new HttpPost("http://10.0.2.2/android/query.php");
           	 table="hotels";
           		break;
        	case Global.MAP_ENTIRE_AEROPORT:
           	 post=new HttpPost("http://10.0.2.2/android/query_aero.php");
           	 table="";
           		break;
        	}
        	post.setEntity(new UrlEncodedFormEntity(l));
        	HttpResponse rep=client.execute(post);
        	HttpEntity ent=rep.getEntity();
        	is=ent.getContent();
        }
        catch(Exception e){
        	Log.i("erreur","http"+e.toString());
        	Global.connection_failure=true;
        	finish();
        }
        try{
        	BufferedReader br=new BufferedReader(new InputStreamReader(is,"UTF-8"));
        	StringBuilder sb=new StringBuilder();
        	String line=null;
        	while((line=br.readLine())!=null){
        		sb.append(line+"\n");
        	}
        	is.close();
        	result=sb.toString();
        	Log.i("result","::"+result);
        }
        catch(Exception e){
        	Log.i("erreur","parsing"+e.toString());
        }
        
        
		JSONArray array=null;
	     output=new ArrayList<HashMap<String,String>>();
		try{
        	
        	JSONObject obj=new JSONObject(result);
       
        	array=obj.getJSONArray("hotels");
        	
        }
        catch(Exception e){
        	Log.i("erreur","registering"+e.toString());
        	
        	
        	
        	Global.introuvable=true;
        	finish();
        	
        
        }
        
        try{for(int i=0;i<array.length();i++){
    		JSONObject j=array.getJSONObject(i);
    		HashMap<String,String> map= new HashMap<String,String>();
    		map.put("nom",j.getString("nom"));
    		map.put("region",j.getString("region"));
    		map.put("pays",j.getString("pays"));
    		map.put("tel",j.getString("tel_1"));
    		map.put("longitude",j.getString("longitude"));
    		map.put("latitude",j.getString("latitude"));
    		map.put("fax",j.getString("fax"));
    		map.put("mail",j.getString("mail"));
    		map.put("site_web",j.getString("site_web"));
    		map.put("url",j.getString("url"));
    		map.put("id",j.getString("id"));
    		if(table.equals("hotels")){
    			map.put("etoile",j.getString("etoile"));
    		}
    		map.put("lieu",j.getString("region")+","+j.getString("pays"));
    		output.add(map);
    	}
        }catch(Exception e){
        	Log.i("erreur","registering_2"+e.toString());
        }
		
		//*******************************
		
		Myadapter adapter=new  Myadapter(this.getBaseContext(),output,R.layout.detail,new String[]{"nom","lieu"},new int[]{R.id.nom,R.id.adresse});
       list.setAdapter(adapter);
      
     
	}
	
	
    
	
	public void MyHandler(View v){
    	Button cb = (Button) v;
		int position = Integer.parseInt(cb.getTag().toString());
		Log.i("pos",String.valueOf(position));
		if ((cb.getId()==R.id.check)) {
		Global.affiche=output.get(position);
		try{
			Uri uri=Uri.parse("tel:"+output.get(position).get("tel"));
	        Intent intent=new Intent(Intent.ACTION_CALL,uri);
	        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	       startActivity(intent);
		}catch(Exception e){
			Log.i("error",e.toString());
		}
			
		}
		if(cb.getId()==R.id.check2){
			Global.resume=output.get(position).get("region")+'\n'+output.get(position).get("pays")+'\n'+output.get(position).get("tel")+'\n'
		    		+output.get(position).get("site_web")+'\n'+output.get(position).get("mail")+'\n';
			Global.nom=output.get(position).get("nom");
			Global.numero=output.get(position).get("tel");
			Global.latitude=Double.valueOf(output.get(position).get("latitude"))*1e6;
			Global.longitude=Double.valueOf(output.get(position).get("longitude"))*1e6;
			Intent intent=new Intent(this,SimpleMap.class);
			
			startActivity(intent);
		}
		if(cb.getId()==R.id.check3){
			
			Intent intent=new Intent(this,info.class);
			intent.putExtra("nom",output.get(position).get("nom"));
			intent.putExtra("reg",output.get(position).get("region"));
			intent.putExtra("pay",output.get(position).get("pays"));
			intent.putExtra("tel",output.get(position).get("tel"));
			intent.putExtra("fax",output.get(position).get("fax"));
			intent.putExtra("mai",output.get(position).get("mai"));
			intent.putExtra("sit",output.get(position).get("site_web"));
			intent.putExtra("url",output.get(position).get("url"));
			intent.putExtra("id",output.get(position).get("id"));
			if(table.equals("hotels")){
			intent.putExtra("etoile",output.get(position).get("etoile"));
			intent.putExtra("table",table);
			}
			else{
				intent.putExtra("table",table);
			}
				
			startActivity(intent);
		}
    }
	protected void onDestroy(){
		super.onDestroy();
		Log.i("list","ondestroy");
	}
	protected void onStop(){
		super.onStop();
		Log.i("list","stop");
	}
	protected void onResume(){
		super.onResume();
		Log.i("list","resum");
	}
	protected void onPause(){
		super.onPause();
		Log.i("list","pause");
	}
	protected void onRestart(){
		super.onRestart();
		Log.i("list","restart");
	
	}




}
