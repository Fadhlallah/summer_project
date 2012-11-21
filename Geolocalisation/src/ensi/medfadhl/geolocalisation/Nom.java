package ensi.medfadhl.geolocalisation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

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

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Nom extends Activity implements android.view.View.OnClickListener  {

	private EditText nom=null;
	private Button chercher=null;
	private RadioGroup nomrb=null;
	private CheckBox c=null;
	public static ArrayList<HashMap<String,String>> output=null;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nom);
		nom=((EditText)findViewById(R.id.nomentry));
		chercher=((Button)findViewById(R.id.chercher));
		nomrb=((RadioGroup)findViewById(R.id.group));
		c=((CheckBox)findViewById(R.id.map));
		
		chercher.setOnClickListener(this);
	}
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onClick(View v) {
		String edit=nom.getText().toString();
		Log.i("edit",edit);
		if(edit.length()!=0){
			if(c.isChecked()){
			if(nomrb.getCheckedRadioButtonId()==R.id.radio1){
				Intent intent=new Intent(this,EntireMap.class);
				intent.putExtra("requet",edit);
				intent.putExtra("table","hotels");
				startActivity(intent);
			     
	        }
			if(nomrb.getCheckedRadioButtonId()==R.id.radio2){
				Intent intent=new Intent(this,EntireMap.class);
				intent.putExtra("requet",edit);
				intent.putExtra("table"," aeroports ");
				startActivity(intent);
			     
	        }
			
			}
			else{
				if(nomrb.getCheckedRadioButtonId()==R.id.radio1){
					Intent intent=new Intent(this,List.class);
					intent.putExtra("requet",edit);
					intent.putExtra("table","hotels");
					startActivity(intent);
				     
		        }
				if(nomrb.getCheckedRadioButtonId()==R.id.radio2){
					Intent intent=new Intent(this,List.class);
					intent.putExtra("requet",edit);
					intent.putExtra("table"," aeroports ");
					startActivity(intent);
				     
		        }
			}
		}else{
			Toast.makeText(this,"le champ est vide",Toast.LENGTH_SHORT).show();
		}
		
		
	}
	protected void onDestroy(){
		super.onDestroy();
		Log.i("nom","ondestroy");
	}
	protected void onStop(){
		super.onStop();
		Log.i("nom","stop");
	}
	protected void onResume(){
		super.onResume();
	
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
		Log.i("nom","pause");
	}
	protected void onRestart(){
		super.onRestart();
		Log.i("nom","restart");
	
	}	

}
