package pohvalister.servicetry;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;


public class BluetoothStatusReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        Log.e("Blue", "onREceive");
        Integer state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);

        Log.e("Blue", "state:" + state);
        if (state == 12) //STATE_ON
            Toast.makeText(context, "Bluetooth is ON\n", Toast.LENGTH_LONG).show();
        if (state == 10)//STATE_OFF
            Toast.makeText(context, "Bluetooth is OFF", Toast.LENGTH_LONG).show();

        context.startService(new Intent(context, SomeService.class));
    }
}
