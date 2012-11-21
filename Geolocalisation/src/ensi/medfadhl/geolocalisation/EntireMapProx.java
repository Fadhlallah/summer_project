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

public class EntireMapProx extends MapActivity {

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
	        
	        	if(Global.prox_distance==true){
	        	Double dist=getIntent().getExtras().getDouble("distance");
	        	Log.i("dis", String.valueOf(dist));
	        	google.getOverlays().add(new ProxLayer(campus,this,dist));
	        	}
	        	else{
	        		
		       
		        	google.getOverlays().add(new ProxLayer(campus,this,Double.valueOf(0)));
	        	}
	        	google.getOverlays().add(new ProxLayer(place,this,ProximityChoice.longitude,ProximityChoice.latitude));
	        	 if((Global.introuvable==true)||(Global.connection_failure==true)){
		            	
		            	finish();
		            }
	        	 latitude=Global.latitude;
		         langitude=Global.longitude;
	        
   	 	   
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
			Log.i("mapProx","stop");
		}
		protected void onResume(){
			super.onResume();
			Log.i("mapProx","resum");
			if(Global.connection_failure==true){
				Toast.makeText(this,"la connexion a échoué", Toast.LENGTH_LONG).show();
			}
			else if(Global.introuvable==true){
				Toast.makeText(this,"il n'y a pas un établissement aussi proche", Toast.LENGTH_LONG).show();
			}
			Global.introuvable=false;
			Global.connection_failure=false;
		
		}
		protected void onPause(){
			super.onPause();
			Log.i("mapProx","pause");
		}
		protected void onRestart(){
			super.onRestart();
			Log.i("mapProx","restart");
		
		}	
}
