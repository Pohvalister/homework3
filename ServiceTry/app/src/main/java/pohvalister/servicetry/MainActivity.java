package pohvalister.servicetry;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN";
    public static final String pictureName = "Epicture.jpg";
    public static final String downloadedByte = "downloaded.txt";
    public static final String RRCT = "REGISTER_RECEIVER_CONNET_TOGETHER";

    ImageView image;
    TextView errorText;

    private BroadcastReceiver downloadCheck = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            pictureShow();
        }
    }, bluetoothCheck = new BluetoothStatusReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.picture);
        image.setVisibility(View.INVISIBLE);
        errorText = (TextView) findViewById(R.id.error);
        errorText.setVisibility(View.VISIBLE);

        final IntentFilter netFilter = new IntentFilter();//2 types of adding filter
        netFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(bluetoothCheck, new IntentFilter(netFilter));

        registerReceiver(downloadCheck, new IntentFilter(RRCT));


        pictureShow();

    }

    private void pictureShow() {
        Log.e(TAG, "pictureShow");
        image.setVisibility(View.INVISIBLE);
        errorText.setVisibility(View.VISIBLE);
        File downloadFile = new File(getFilesDir(), downloadedByte);
        if (downloadFile.exists()) {
            File tmpFile = new File(getFilesDir(), pictureName);

            if (tmpFile.exists())
                try {
                    image.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(tmpFile)));
                    image.setVisibility(View.VISIBLE);
                    errorText.setVisibility(View.INVISIBLE);

                } catch (Exception e) {
                    Log.e(TAG, "pictureShowError");
                    e.printStackTrace();
                }
        }
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        unregisterReceiver(downloadCheck);
        unregisterReceiver(bluetoothCheck);
    }
}
