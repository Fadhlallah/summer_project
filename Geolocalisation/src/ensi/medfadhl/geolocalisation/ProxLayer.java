package ensi.medfadhl.geolocalisation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class ProxLayer extends ItemizedOverlay<OverlayItem> {
    private List<OverlayItem> list=new ArrayList<OverlayItem>();
    private Drawable marker=null;
    private Context context=null;
    private ArrayList<HashMap<String,String>> output=new ArrayList<HashMap<String,String>>();
    private ArrayList<HashMap<String,String>> output2=new ArrayList<HashMap<String,String>>();
	//contructeur
    public ProxLayer(Drawable marker,Context context,Double dist) {
		super(marker);
		this.marker=marker;
		this.context=context;
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
		           		break;
		        	case Global.MAP_ENTIRE_AEROPORT:
		           	 post=new HttpPost("http://10.0.2.2/android/query_aero.php");
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
		    		map.put("url",j.getString("url"));
		    		map.put("lieu",j.getString("region")+","+j.getString("pays"));
		    		output.add(map);
		    	}
		        }catch(Exception e){
		        	Log.i("erreur","registering_2"+e.toString());
		        }
				
				//*******************************
		        Location posA=new Location("depart");
		        Location posB=new Location("destination");
		        posA.setLongitude(ProximityChoice.longitude);
		        posA.setLatitude(ProximityChoice.latitude);
				Log.i("coor",ProximityChoice.longitude+";"+ProximityChoice.latitude);
				if(Global.connection_failure==false){
				if(Global.prox_distance==true){
					
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
				//********************************remplir la carte**********************
		

				if(Global.introuvable==false){
					if(!output2.isEmpty()){
			        for(HashMap<String,String> buf:output2){
			        	
			        	String longi=buf.get("longitude");
					    Double longitude=Double.parseDouble(longi)*1e6;
					    String lat=buf.get("latitude");
					    Log.i("coor",longi+";"+lat);
					    Double latitude=Double.parseDouble(lat)*1e6;
					    String resume=buf.get("region")+'\n'+buf.get("pays")+'\n'+buf.get("tel")+'\n'
					    		+buf.get("site_web")+'\n'+buf.get("mail")+'\n';
					    Log.i("coor",buf.get("url"));
					   list.add(new OverlayItem(new GeoPoint(latitude.intValue(),longitude.intValue()),buf.get("nom"),resume));
			        }
			        Global.latitude=Double.valueOf(output2.get(0).get("latitude"))*1e6;
			        Global.longitude=Double.valueOf(output2.get(0).get("longitude"))*1e6;
			        }
					else{
						Global.introuvable=true;
						Global.latitude=(double) 0;
			            Global.longitude=(double) 0;
					}
				}
			        else{
			        
			        	Global.latitude=(double) 0;
			            Global.longitude=(double) 0;
			        }
			        populate();
				
			        
	}

    public ProxLayer(Drawable Marker,Context context,Double longe,Double lat){
    	super(Marker);
    	this.marker=Marker;
    	this.context=context;
    	Log.i("info",lat.intValue()+""+longe.intValue());
    	lat=lat*1E6;
    	longe=longe*1E6;
    	 list.add(new OverlayItem(new GeoPoint(lat.intValue(),longe.intValue()),"vous","votre position actuelle"));
         
         populate();
    }
	protected OverlayItem createItem(int i) {
		
		return list.get(i);
	}

	@Override
	public int size() {
		
		return list.size();
	}
	 public void draw(Canvas canvas,MapView mapview,boolean shadow){
	    	super.draw(canvas,mapview,shadow);
	    	boundCenterBottom(marker);
	    }
	protected boolean onTap(int i){
		
		
        LayoutInflater li=LayoutInflater.from(context);
		final View alert=li.inflate(R.layout.call,null);
		
		
		Log.i("num",Global.numero);
	    OverlayItem it=list.get(i);
		AlertDialog.Builder d=new AlertDialog.Builder(context);
		d.setTitle(it.getTitle());
		d.setMessage( it.getSnippet());
		d.setView(alert);
		d.setPositiveButton("Call", new Calling());
		d.show();
		
		return (true);
	}
}
