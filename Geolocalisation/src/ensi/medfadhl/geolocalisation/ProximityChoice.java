package ensi.medfadhl.geolocalisation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ProximityChoice extends Activity implements OnClickListener {
	private Button chercher=null;
	private EditText text=null;
	private RadioGroup grp=null;
	private CheckBox map=null;
	public static Double longitude=null;
	public static Double latitude=null;
	public static int option;
public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.proximity);
	chercher=((Button)findViewById(R.id.prox_c));
	text=((EditText)findViewById(R.id.distance));
	grp=((RadioGroup)findViewById(R.id.prox_group));
	map=((CheckBox)findViewById(R.id.prox_map));
	//recuperation des coordonnées de l'utilisateur
	LocationManager mloc=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
	MyListener mlist=new MyListener();
	mloc.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,mlist);
    chercher.setOnClickListener(this);
	
}

class MyListener implements LocationListener{

	@Override
	public void onLocationChanged(Location location) {
	 longitude=location.getLongitude();
	 latitude=location.getLatitude();	
	}

	@Override
	public void onProviderDisabled(String provider) {
	Toast.makeText(getBaseContext(),"GPS disabled",Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(getBaseContext(),"GPS enabled",Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
}

protected void onDestroy(){
	super.onDestroy();
	Log.i("prox","ondestroy");
}
protected void onStop(){
	super.onStop();
	Log.i("prox","stop");
}
protected void onResume(){
	super.onResume();
	Log.i("prox","resum");
	//rendre null la longitude et la latitude pour le prochain essaie
			ProximityChoice.latitude=null;
			ProximityChoice.longitude=null;
	
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
	Log.i("prox","pause");
}
protected void onRestart(){
	super.onRestart();
	Log.i("prox","restart");
}
@Override
public void onClick(View v) {
	String dist=text.getText().toString();
	Intent intent=null;
	
		if(!((longitude==null)||(latitude==null))){
			
		
			if(grp.getCheckedRadioButtonId()==R.id.prox_hotel){
				
				option=Global.MAP_ENTIRE_HOTEL;
			}
			else{
				
				option=Global.MAP_ENTIRE_AEROPORT;
			}
			if(map.isChecked()){
				intent=new Intent(this,EntireMapProx.class);
			}
			else{
				intent=new Intent(this,ListProximity.class);
				
			}
			if(dist.isEmpty()){
				Global.prox_distance=false;
				
			}else{
				Global.prox_distance=true;
				intent.putExtra("distance",Double.valueOf(dist)*1000);
			}
			
			startActivity(intent);
		}
		else{
			Toast.makeText(getBaseContext(),"problème dans GPS",Toast.LENGTH_SHORT).show();
		}
	}
	

}
