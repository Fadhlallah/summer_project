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
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class SimpleLayer extends ItemizedOverlay<OverlayItem> {
	private List<OverlayItem> item=new ArrayList<OverlayItem>();
	private Drawable drawable=null;
    private Context context=null;
    
	public SimpleLayer(Drawable defaultMarker,Context context) {
		super(defaultMarker);
		drawable=defaultMarker;
		this.context=context;
		 
	   item.add(new OverlayItem(new GeoPoint(Global.latitude.intValue(),Global.longitude.intValue()),Global.nom,Global.resume));
	  
        populate();
     
	}
	
    public SimpleLayer(Drawable Marker,Context context,Double longe,Double lat){
    	super(Marker);
    	drawable=Marker;
    	this.context=context;
    	Log.i("info",lat.intValue()+""+longe.intValue());
    	lat=lat*1E6;
    	longe=longe*1E6;
    	 item.add(new OverlayItem(new GeoPoint(lat.intValue(),longe.intValue()),"vous","votre position actuelle"));
         
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
}
