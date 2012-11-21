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
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ListProximity extends ListActivity {

	private ListView list=null;
	private  ArrayList<HashMap<String,String>> output=null;
	private ArrayList<HashMap<String,String>> output2=null;
	private String table=null;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		list=getListView();
		//CONNECTION AVEC LE WEB SERVICE 
		
		
		ArrayList<NameValuePair> l=new ArrayList<NameValuePair>();
		
        InputStream is=null;
        String result="";
        
        try{
        	HttpClient client=new DefaultHttpClient();
        	HttpPost post=null;
        	switch(ProximityChoice.option){
        	
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
        	finish();
        	Log.i("erreur","http"+e.toString());
        	Global.connection_failure=true;
        	
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
    		if(table.equals("hotels")){
    			map.put("etoile",j.getString("etoile"));
    		}
    		map.put("url",j.getString("url"));
    		map.put("lieu",j.getString("region")+","+j.getString("pays"));
    		output.add(map);
    	}
        }catch(Exception e){
        	Log.i("erreur","registering_2"+e.toString());
        }
      
		//*******************************
        output2=new ArrayList<HashMap<String,String>>();
        Location posA=new Location("depart");
        Location posB=new Location("destination");
        posA.setLongitude(ProximityChoice.longitude);
        posA.setLatitude(ProximityChoice.latitude);
		Log.i("coor",ProximityChoice.longitude+";"+ProximityChoice.latitude);
		if(Global.connection_failure==false){
		if(Global.prox_distance==true){
	        Double dist=getIntent().getExtras().getDouble("distance");

			Log.i("dist",String.valueOf(dist));
			for(int i=0;i<output.size();i++){
				Double longe=Double.valueOf(output.get(i).get("longitude"));
				Double lat=Double.valueOf(output.get(i).get("latitude"));
				Log.i("coor2",longe+";"+lat);
				 posB.setLongitude(longe);
			     posB.setLatitude(lat);
			     if(posA.distanceTo(posB)<=dist){
			    	output2.add(output.get(i));
			    	Log.i("info",output2.get(output2.size()-1).get("nom"));
			     }
			}
		}else 
		{
			output2.add(output.get(0));
			Double longe=Double.valueOf(output.get(0).get("longitude"));
			Double lat=Double.valueOf(output.get(0).get("latitude"));
			Log.i("coor2",longe+";"+lat);
			posB.setLongitude(longe);
		    posB.setLatitude(lat);
		    double min=posA.distanceTo(posB);
		    Log.i("min",String.valueOf(min));
		    for(int i=0;i<output.size();i++){
			    longe=Double.valueOf(output.get(i).get("longitude"));
			    lat=Double.valueOf(output.get(i).get("latitude"));
				posB.setLongitude(longe);
			    posB.setLatitude(lat);
			    if(posA.distanceTo(posB)<min){
			    	min=posA.distanceTo(posB);
			    	output2.remove(0);
			    	output2.add(output.get(i));
			    	Log.i("info",output2.get(0).get("nom"));
			    }
			    
			}
		}
		}
		//rendre null la longitude et la latitude pour le prochain essaie
		//ProximityChoice.latitude=null;
		//ProximityChoice.longitude=null;
		//construire la liste si on a trouvée des établissements 
		if(!output2.isEmpty()){
	   Myadapter adapter=new  Myadapter(this.getBaseContext(),output2,R.layout.detail,new String[]{"nom","lieu"},new int[]{R.id.nom,R.id.adresse});
       list.setAdapter(adapter);
		}
		else{
			finish();
			Global.introuvable=true;
		}
      
     
	}
	
	
    
	
	public void MyHandler(View v){
    	Button cb = (Button) v;
		int position = Integer.parseInt(cb.getTag().toString());
		Log.i("pos",String.valueOf(position));
		if ((cb.getId()==R.id.check)) {
		Global.affiche=output2.get(position);
		try{
			Uri uri=Uri.parse("tel:"+output2.get(position).get("tel"));
	        Intent intent=new Intent(Intent.ACTION_CALL,uri);
	        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	       startActivity(intent);
		}catch(Exception e){
			Log.i("error",e.toString());
		}
			
		}
		if(cb.getId()==R.id.check2){
			Global.resume=output2.get(position).get("region")+'\n'+output2.get(position).get("pays")+'\n'+output2.get(position).get("tel")+'\n'
		    		+output2.get(position).get("site_web")+'\n'+output2.get(position).get("mail")+'\n';
			Global.nom=output2.get(position).get("nom");
			Global.numero=output2.get(position).get("tel");
			Global.latitude=Double.valueOf(output2.get(position).get("latitude"))*1e6;
			Global.longitude=Double.valueOf(output2.get(position).get("longitude"))*1e6;
			Intent intent=new Intent(this,SimpleMapProx.class);
			
			startActivity(intent);
		}
		if(cb.getId()==R.id.check3){
			
			Intent intent=new Intent(this,info.class);
			intent.putExtra("nom",output2.get(position).get("nom"));
			intent.putExtra("reg",output2.get(position).get("region"));
			intent.putExtra("pay",output2.get(position).get("pays"));
			intent.putExtra("tel",output2.get(position).get("tel"));
			intent.putExtra("fax",output2.get(position).get("fax"));
			intent.putExtra("mai",output2.get(position).get("mai"));
			intent.putExtra("sit",output2.get(position).get("site_web"));
			intent.putExtra("url",output2.get(position).get("url"));
			if(table.equals("hotels")){
				intent.putExtra("etoile",output2.get(position).get("etoile"));
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
		Log.i("listprox","ondestroy");
	}
	protected void onStop(){
		super.onStop();
		Log.i("listprox","stop");
	}
	protected void onResume(){
		super.onResume();
		Log.i("listprox","resum");
	}
	protected void onPause(){
		super.onPause();
		Log.i("listprox","pause");
	}
	protected void onRestart(){
		super.onRestart();
		Log.i("listprox","restart");
	
	}

}
