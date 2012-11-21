package ensi.medfadhl.geolocalisation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;


public class Layers extends ItemizedOverlay<OverlayItem> {
	private List<OverlayItem> item=new ArrayList<OverlayItem>();
	private Drawable drawable=null;
    private Context context=null;
    private  ArrayList<HashMap<String,String>> output=null;
	public Layers(Drawable defaultMarker,Context context) {
		super(defaultMarker);
		drawable=defaultMarker;
		this.context=context;
		 
		ArrayList<NameValuePair> list=new ArrayList<NameValuePair>();
	       
        InputStream is=null;
        String result=null;
       
        try{
        	HttpClient client=new DefaultHttpClient();
        	HttpPost post=null;
        	if(GeolocalisationActivity.option==Global.MAP_ENTIRE_HOTEL){
        	 post=new HttpPost("http://10.0.2.2/android/query.php");
        	}
        	else{
        		post=new HttpPost("http://10.0.2.2/android/query_aero.php");
        	}
        	post.setEntity(new UrlEncodedFormEntity(list));
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
    		output.add(map);
    	}
        }catch(Exception e){
        	Log.i("erreur","registering_2"+e.toString());
        }
        for(HashMap<String,String> buf:output){
        	
        	String longi=buf.get("longitude");
		    Double longitude=Double.parseDouble(longi)*1e6;
		    String lat=buf.get("latitude");
		    Log.i("coor",longi+";"+lat);
		    Double latitude=Double.parseDouble(lat)*1e6;
		    String resume=buf.get("region")+'\n'+buf.get("pays")+'\n'+buf.get("tel")+'\n'
		    		+buf.get("site_web")+'\n'+buf.get("mail")+'\n';
		    Log.i("coor",buf.get("url"));
		   item.add(new OverlayItem(new GeoPoint(latitude.intValue(),longitude.intValue()),buf.get("nom"),resume));
        }
        populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated me
		return item.get(i);
	}
	 public void draw(Canvas canvas,MapView mapview,boolean shadow){
	    	super.draw(canvas,mapview,shadow);
	    	boundCenterBottom(drawable);
	    }
	 protected boolean onTap(int i){
			
			
	        LayoutInflater li=LayoutInflater.from(context);
			final View alert=li.inflate(R.layout.call,null);
			Global.numero=output.get(i).get("tel");
            
	    	
			Log.i("num",Global.numero);
		    OverlayItem it=item.get(i);
			AlertDialog.Builder d=new AlertDialog.Builder(context);
			d.setTitle(it.getTitle());
			d.setMessage( it.getSnippet());
			d.setView(alert);
			d.setPositiveButton("Call", new Calling());
			d.show();
			
			return (true);
		}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return item.size();
	}
   
	
	
	
	 @SuppressWarnings("unused")
	private void downloadImage(String chaine,ImageView image) {
	    	Bitmap bitmap = null;
	    	
	    	try {
				URL urlImage = new URL(chaine);
				HttpURLConnection connection = (HttpURLConnection) urlImage.openConnection();
				InputStream inputStream = connection.getInputStream();
				
				bitmap = BitmapFactory.decodeStream(inputStream);
				
				image.setImageBitmap(bitmap);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}   	
	    	
	    }
}
