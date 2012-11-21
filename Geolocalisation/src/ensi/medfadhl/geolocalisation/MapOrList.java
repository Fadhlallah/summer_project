package ensi.medfadhl.geolocalisation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MapOrList extends Activity implements OnClickListener {
	private Button map=null;
	private Button list=null;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entire);
		map=((Button)findViewById(R.id.entire_map));
		list=((Button)findViewById(R.id.entire_info));
		map.setOnClickListener(this);
		list.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		Intent intent=null;
		switch(v.getId()){
		case R.id.entire_map:
			intent=new Intent(this,EntireMap.class);
		break;
		case R.id.entire_info:
			intent=new Intent(this,List.class);
		break;
		}
		startActivity(intent);
	}
	protected void onDestroy(){
		super.onDestroy();
		Log.i("maporlist","ondestroy");
	}
	protected void onStop(){
		super.onStop();
		Log.i("maporlist","stop");
	}
	protected void onResume(){
		super.onResume();
		Log.i("maporlist","resum");
		if(Global.connection_failure==true){
			Log.i("err","false");
			Toast.makeText(this,"la connexion a échoué", Toast.LENGTH_LONG).show();
		}
		Global.connection_failure=false;
	}
	protected void onPause(){
		super.onPause();
		Log.i("maporlist","pause");
	}
	protected void onRestart(){
		super.onRestart();
		Log.i("maporlist","restart");
	
	}	
}
