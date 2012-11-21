package ensi.medfadhl.geolocalisation;

import java.util.HashMap;

import android.content.Context;
import android.widget.ImageView;

public class Global {
	public  static String numero="";
	public static Context context;
	public static ImageView img;
	public static HashMap<String,String> affiche=null;
	public static Double longitude;
	public static Double latitude;
	public static final int MAP_ENTIRE_HOTEL=0;
	public static final int MAP_NOM=1;
	public static final int MAP_REGION=2;
	public static final int MAP_ENTIRE_AEROPORT=3;
	public static String resume=null;
	public static String nom=null;
	public static String tel=null;
	public static boolean introuvable;
	public static final int prox=4;
	public static final int MAP_ENTIRE=5;
	public static int id;
	public static boolean prox_distance;
	public static boolean connection_failure;
}
