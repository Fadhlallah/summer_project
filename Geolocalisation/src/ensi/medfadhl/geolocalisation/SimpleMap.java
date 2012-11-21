package ensi.medfadhl.geolocalisation;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class SimpleMap extends MapActivity {

	private MapView google=null;
	   private MapController control=null;
	 
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main2);
	         Global.context=this;
	        google=((MapView)findViewById(R.id.view));
	        control=google.getController();
	        google.setSatellite(true);
	       
	        //google.setStreetView(true);
	        google.setBuiltInZoomControls(true);
	        Drawable campus=getResources().getDrawable(R.drawable.marker);
	        Drawable me=getResources().getDrawable(R.drawable.place);
	        campus.setBounds(0,0,campus.getIntrinsicWidth(),campus.getIntrinsicHeight());
	        Double latitude = null;
	        Double langitude=null;
	        
	        google.getOverlays().add(new SimpleLayer(campus,this));
	        
	        latitude=Global.latitude;
	        langitude=Global.longitude;
	       
	        control.setZoom(5);
	        
	     
	        GeoPoint gp=new GeoPoint(latitude.intValue(),langitude.intValue());
	        control.setCenter(gp);
	        control.setZoom(5);
	    	
	    }

		@Override
		protected boolean isRouteDisplayed() {
			// TODO Auto-generated method stub
			return true;
		}
		
		protected void onDestroy(){
			super.onDestroy();
			Log.i("map","ondestroy");
		}
		protected void onStop(){
			super.onStop();
			Log.i("map","stop");
		}
		protected void onResume(){
			super.onResume();
			Log.i("map","resum");
		}
		protected void onPause(){
			super.onPause();
			Log.i("map","pause");
		}
		protected void onRestart(){
			super.onRestart();
			Log.i("map","restart");
		
		}	

}
