package ensi.medfadhl.geolocalisation;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;


public class EntireMap extends MapActivity {

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
	        Drawable place=getResources().getDrawable(R.drawable.place);
	        campus.setBounds(0,0,campus.getIntrinsicWidth(),campus.getIntrinsicHeight());
	        Double latitude = null;
	        Double langitude=null;
	        if((GeolocalisationActivity.option==Global.MAP_ENTIRE_HOTEL)||(GeolocalisationActivity.option==Global.MAP_ENTIRE_AEROPORT)){
	        google.getOverlays().add(new Layers(campus,this));
	        latitude=35.38905*1e6;
	        langitude=9.525146*1e6;
	        if(Global.connection_failure==true){
	        	finish();
	        }
	        control.setZoom(5);
	        }
	        else if(GeolocalisationActivity.option==Global.MAP_NOM){
	        	String nom=getIntent().getExtras().getString("requet");
	        	String table=getIntent().getExtras().getString("table");
	        	TriLayer t=new TriLayer(campus,this,nom,table);
	          
	        	google.getOverlays().add(t);
	            if((Global.introuvable==true)||(Global.connection_failure==true)){
	            	
	            	finish();
	            }
	        	latitude=Global.latitude;
	        	langitude=Global.longitude;
	            
	        }
	        else if(GeolocalisationActivity.option==Global.MAP_REGION){
	        	String table=getIntent().getExtras().getString("table");
	        	String region=getIntent().getExtras().getString("region");
	        	String pays=getIntent().getExtras().getString("pays");
	        	google.getOverlays().add(new TriLayer(campus,this,table,region,pays));
                 if((Global.introuvable==true)||(Global.connection_failure==true)){
	            	
	            	finish();
	            }
	        	latitude=Global.latitude;
	        	langitude=Global.longitude;
	        }
	        
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
