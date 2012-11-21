package ensi.medfadhl.geolocalisation;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;

public class Myadapter extends SimpleAdapter {
    LayoutInflater inflater=null;
	public Myadapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		inflater=LayoutInflater.from(context);
	}
	public Object getItem(int position){
		return super.getItem(position);
	}
	public View getView(int position,View convertview,ViewGroup parent){
		if(convertview==null){
			convertview=inflater.inflate(R.layout.detail,null);
			Button cb=(Button)convertview.findViewById(R.id.check);
			cb.setTag(position);
			Button cb2=(Button)convertview.findViewById(R.id.check2);
			cb2.setTag(position);
			Button cb3=(Button)convertview.findViewById(R.id.check3);
			cb3.setTag(position);
		}
		
		return super.getView(position, convertview, parent);
		
	}

}
