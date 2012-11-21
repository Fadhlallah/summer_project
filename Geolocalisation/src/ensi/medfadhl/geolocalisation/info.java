package ensi.medfadhl.geolocalisation;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class info extends Activity implements OnClickListener {
	private TextView nom=null;
	private TextView reg=null;
	private TextView pay=null;
	private TextView tel=null;
	private TextView fax=null;
	private TextView mail=null;
	private TextView site_web=null;
	private ImageView image=null;
	private TextView galerie=null;
	private TextView etoile=null;
	private String s=null;
	private Bundle info=null;
   public void onCreate(Bundle savedInstanceState){
	   super.onCreate(savedInstanceState);
	   info=getIntent().getExtras();
	   if(info.getString("table").equals("hotels")){
	   setContentView(R.layout.infohotel);
	   etoile=((TextView)findViewById(R.id.info_etoile));
	   etoile.setText(info.getString("etoile"));
	   }
	   else{
		   setContentView(R.layout.info);
	   }
	   nom=((TextView)findViewById(R.id.info_nom));
	   reg=((TextView)findViewById(R.id.info_reg));
	   pay=((TextView)findViewById(R.id.info_pay));
	   tel=((TextView)findViewById(R.id.info_tel));
	   fax=((TextView)findViewById(R.id.info_fax));
	   mail=((TextView)findViewById(R.id.info_mai));
	   site_web=((TextView)findViewById(R.id.info_sit));
	   image=((ImageView)findViewById(R.id.imageview));
	   galerie=((TextView)findViewById(R.id.info_galerie));
	   
	   nom.setText(info.getString("nom"));
	   reg.setText(info.getString("reg"));
	   pay.setText(info.getString("pay"));
	   tel.setText(info.getString("tel"));
	   fax.setText(info.getString("fax"));
	   mail.setText(info.getString("mai"));
	   site_web.setText(info.getString("sit"));
	   tel.setOnClickListener(this);
	   site_web.setOnClickListener(this);
	   galerie.setOnClickListener(this);
	   //downloadImage(info.getString("url"));
	   s=site_web.getText().toString();
	   
	  
   
   }
@Override
public void onClick(View v) {
	if(v.getId()==R.id.info_tel){
		Uri uri=Uri.parse("tel:"+tel.getText().toString());
        Intent intent=new Intent(Intent.ACTION_CALL,uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(intent);
	}
	
	if((v.getId()==R.id.info_sit)&&(!s.equals("null"))){
		
		Uri uri = Uri.parse(s);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		
		startActivity(intent);
		

	}
	if(v.getId()==R.id.info_galerie){
		if(!(info.getString("url")).equals("null")){
			Intent intent=new Intent(this,GalleryImage.class);
			intent.putExtra("id",info.getString("id"));
			intent.putExtra("url",info.getString("url"));
			startActivity(intent);
		}
		else{
			Toast.makeText(this,"il n'y a pas de galerie pour cet établissement",Toast.LENGTH_SHORT).show();
		}
	}
	}
	

@SuppressWarnings("unused")
private void downloadImage(String chaine) {
	Bitmap bitmap = null;
	
	try {
		Log.i("url_chaine",chaine);
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
protected void onDestroy(){
	super.onDestroy();
	Log.i("info","ondestroy");
}
protected void onStop(){
	super.onStop();
	Log.i("info","stop");
}
protected void onResume(){
	super.onResume();
	Log.i("info","resum");
}
protected void onPause(){
	super.onPause();
	Log.i("info","pause");
}
protected void onRestart(){
	super.onRestart();
	Log.i("info","restart");

}
}
