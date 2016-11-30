package pohvalister.servicetry;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public final class SomeService extends Service {
    final String LOG_TAG = "loader";
    private final String EpictureURL = "http://luxfon.com/images/201505/luxfon.com_35100.jpg";//39 MB!!!

    private File picture, downloadByte;
    boolean loading = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(LOG_TAG, "onStartAommand");
        downloadByte = new File(getFilesDir(), MainActivity.downloadedByte);
        picture = new File(getFilesDir(), MainActivity.pictureName);


        Log.e(LOG_TAG, "loading: " + downloadByte.exists() + " " + loading);
        Log.e(LOG_TAG, "gotFile:" + picture.exists());

        if (!downloadByte.exists() && !loading)
            getPicture(EpictureURL, picture);
        else
            stopSelf();
        return START_STICKY;
    }


    void getPicture(final String url, final File picPoint) {
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                if (downloadByte.exists())
                    downloadByte.delete();
                loading = true;
                Log.e(LOG_TAG, "backGroundLoad");

                InputStream inS;
                FileOutputStream outS;
                try {
                    inS = new BufferedInputStream(new URL(url).openConnection().getInputStream());
                    outS = new FileOutputStream(picPoint);
                    byte[] tmpBuff = new byte[1024];

                    int tmpCount;
                    while ((tmpCount = inS.read(tmpBuff)) != -1)
                        outS.write(tmpBuff, 0, tmpCount);
                    outS = new FileOutputStream(downloadByte);
                    outS.write(1);
                    loading = false;
                    inS.close();
                    outS.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    picPoint.delete();
                    e.printStackTrace();
                }

                stopSelf();
                sendBroadcast(new Intent(MainActivity.RRCT));
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}