package ensi.medfadhl.geolocalisation;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GeolocalisationActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	private Button map1=null;
	private Button exit=null;
	private Button nom=null;
	private Button region=null;
	private Button prox=null;
	public static int option;
	public static Context context2;
	final public static int MENU_HOTEL=0;
	final public static int MENU_AERO=1;
	
    public void onCreate(Bundle savedInstanceState) {
    	 //Global.context=this;
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        context2=this;
        map1=((Button)findViewById(R.id.google));
        exit=((Button)findViewById(R.id.exit));
        nom=((Button)findViewById(R.id.nom));
        region=((Button)findViewById(R.id.region));
        prox=((Button)findViewById(R.id.prox));
        map1.setOnClickListener(this);
        exit.setOnClickListener(this);
        nom.setOnClickListener(this);
        region.setOnClickListener(this);
        registerForContextMenu(map1);
        prox.setOnClickListener(this);
        
    }
    
	@Override
	public void onClick(View v) {
	Intent intent=null;   
	switch(v.getId()){
	   /*case R.id.google:
		   option=Global.MAP_ENTIRE;
		   intent=new Intent(this,EntireMap.class);
		   startActivity(intent);
	    break;*/
	   case R.id.exit :
		finish();
		break;
	   case R.id.nom:
			option=Global.MAP_NOM;
			intent=new Intent(this,Nom.class);
			startActivity(intent);
		break;
	   case R.id.region:
		   option=Global.MAP_REGION;
			intent=new Intent(this,Region.class);
			startActivity(intent);
		   break;
	   case R.id.prox:
		   option=Global.prox;
		   intent=new Intent(this,ProximityChoice.class);
			startActivity(intent);
		   break;
	   }
	  
	}
	protected void onDestroy(){
		super.onDestroy();
		Log.i("geoloc","ondestroy");
	}
	protected void onStop(){
		super.onStop();
		Log.i("geoloc","stop");
	}
	protected void onResume(){
		super.onResume();
		Log.i("geoloc","resum");
	}
	protected void onPause(){
		super.onPause();
		Log.i("geoloc","pause");
	}
	protected void onRestart(){
		super.onRestart();
		Log.i("geoloc","restart");
	}
	public void onCreateContextMenu(ContextMenu context,View v,ContextMenuInfo info){
		
		
		if(v.getId()==R.id.google){
			context.setHeaderIcon(R.drawable.google);
			context.setHeaderTitle("choisir");
			context.add(0,MENU_HOTEL,0,"Hotels").setIcon(R.drawable.tinybed);
			context.add(0,MENU_AERO,0,"Aeroports").setIcon(R.drawable.tinyplane);	
			
		}
		
		super.onCreateContextMenu(context, v, info);
	}
	public boolean onContextItemSelected(MenuItem item){
	    
		switch(item.getItemId()){
	    case MENU_HOTEL:
	    	option=Global.MAP_ENTIRE_HOTEL;
	    break;
	    case MENU_AERO:
	    	option=Global.MAP_ENTIRE_AEROPORT;
		break;
	    }
	    Intent intent=new Intent(this,MapOrList.class);
	    startActivity(intent);
		return super.onContextItemSelected(item);	
	}
}