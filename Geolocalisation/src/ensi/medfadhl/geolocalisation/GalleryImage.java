package ensi.medfadhl.geolocalisation;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;



public class GalleryImage extends Activity {
	public static ArrayList<HashMap<String,String>> output=null;
	Gallery gallery=null;
	private ArrayList<URL> listimages=null;
	private ImageView image=null;
	private Drawable no_image=null;
	private String SERVER;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallerylist);
		gallery=((Gallery)findViewById(R.id.gallery));
	    SERVER=getIntent().getExtras().getString("url");
        Log.i("url",SERVER);
        
        no_image=this.getResources().getDrawable(R.drawable.ic_launcher);
        image=((ImageView)findViewById(R.id.galerieview));
        listimages=buildListImage();
        Log.i("url",listimages.get(0).toString());
        if(listimages.size()<=0){
        	image.setImageDrawable(no_image);
        }else{
        	setImage(image,listimages.get(0));
        }
        gallery.setAdapter(new AddImgAdp(this));
	    gallery.setSpacing(10);

		
		gallery.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position, long id) {			
				setImage(image, listimages.get(position));
			}

			
		});
   
	}
	
	//********former les urls des image************
	private ArrayList<URL> buildListImage(){
		int nbreimage=21;
		ArrayList<URL> listfic=new ArrayList<URL>();
		for(int i=0;i<nbreimage;i++){
			try{
				listfic.add(new URL(SERVER+"/"+i+".jpg"));
			}catch(MalformedURLException e){
				Log.e("DVP Gallery", "Erreur format URL : " + SERVER+ "/" + i + ".gif");
				e.printStackTrace();
			}
		}
		return listfic;
	}
	//***********************téléchargement des image depuis le serveur**********
	public void setImage(ImageView aView, URL aURL) {
		try {
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, 8192);
            Bitmap bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
            aView.setImageBitmap(bm);
		
		} catch (IOException e) {
			aView.setImageDrawable(no_image);
			Log.e("DVP Gallery", "Erreur téléchargement image URL : " + aURL.toString());
			e.printStackTrace();
		} 
	}
			
	protected void onDestroy(){
		super.onDestroy();
		Log.i("gallery","ondestroy");
	}
	protected void onStop(){
		super.onStop();
		Log.i("gallery","stop");
	}
	protected void onResume(){
		super.onResume();
		Log.i("galley","resum");
	}
	protected void onPause(){
		super.onPause();
		Log.i("gallery","pause");
	}
	protected void onRestart(){
		super.onRestart();
		Log.i("gallery","restart");
	
	}
	private void downloadImage(String chaine) {
		Bitmap bitmap = null;
		
		try {
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
	
	public class AddImgAdp extends BaseAdapter {
		int GalItemBg;
		private Context cont;

		public AddImgAdp(Context c) {
			cont = c;
			TypedArray typArray = obtainStyledAttributes(R.styleable.GalleryTheme);
			GalItemBg = typArray.getResourceId(R.styleable.GalleryTheme_android_galleryItemBackground, 0);
			typArray.recycle();
		}

		public int getCount() {
			return listimages.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			ImageView imgView = null;
	
			if (convertView == null) {
				imgView = new ImageView(cont);
			} else {
				imgView = (ImageView)convertView;
			}
			
			if (listimages.size() <= 0) {
				imgView.setImageDrawable(no_image); 
			} else {
				setImage(imgView, listimages.get(position));
			}
			
			imgView.setLayoutParams(new Gallery.LayoutParams(250, 250));
			imgView.setScaleType(ImageView.ScaleType.FIT_XY);
		    imgView.setBackgroundResource(GalItemBg);

			return imgView;
		}
	}


}
