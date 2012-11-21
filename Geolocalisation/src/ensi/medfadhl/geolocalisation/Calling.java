package ensi.medfadhl.geolocalisation;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;

public class Calling implements OnClickListener {

	@Override
	public void onClick(DialogInterface dialog, int which) {
		
		Uri uri=Uri.parse("tel:"+ensi.medfadhl.geolocalisation.Global.numero);
        Intent intent=new Intent(Intent.ACTION_CALL,uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       Global.context.getApplicationContext().startActivity(intent);

	}

}
