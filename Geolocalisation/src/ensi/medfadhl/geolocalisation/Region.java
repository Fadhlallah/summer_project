package ensi.medfadhl.geolocalisation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Region extends Activity implements OnClickListener {
	
	EditText region=null;
	EditText pays=null;
	RadioGroup choix=null;
	CheckBox map=null;
	Button chercher=null;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.region);
		region=((EditText)findViewById(R.id.region_region));
		pays=((EditText)findViewById(R.id.region_pays));
		choix=((RadioGroup)findViewById(R.id.group2));
		map=((CheckBox)findViewById(R.id.map2));
		chercher=((Button)findViewById(R.id.chercher2));
		chercher.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		String reg=region.getText().toString();
		String pay=pays.getText().toString();
		String table;
		if(choix.getCheckedRadioButtonId()==R.id.radio12){
			table="hotels";
		}
		else if(choix.getCheckedRadioButtonId()==R.id.radio22){
			table="aeroports";
		}else{
			table="";
		}
		if((!((reg.length()==0)&&(pay.length()==0)))&&(table.length()!=0)){
			
			Intent intent=null;
			if(map.isChecked()){
				intent=new Intent(this,EntireMap.class);
				intent.putExtra("region", reg);
				intent.putExtra("pays",pay);
				intent.putExtra("table",table);
				startActivity(intent);
			}else{
				intent=new Intent(this,List.class);
				intent.putExtra("region", reg);
				intent.putExtra("pays",pay);
				intent.putExtra("table",table);
				startActivity(intent);
			}
		}
		else{
			Toast.makeText(this,"veuillez indiquer une region ou un pays ou choisir le type d'établissement cherché",Toast.LENGTH_SHORT).show();
		}
	}
	protected void onDestroy(){
		super.onDestroy();
		Log.i("Region","ondestroy");
	}
	protected void onStop(){
		super.onStop();
		Log.i("Region","stop");
	}
	protected void onResume(){
		super.onResume();
		Log.i("Region","resum");
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
		Log.i("Region","pause");
	}
	protected void onRestart(){
		super.onRestart();
		Log.i("Region","restart");
	
	}
   
    	
    
}
