package com.appstructural.jelovnik15.imageandspinner.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.appstructural.jelovnik15.imageandspinner.R;
import com.appstructural.jelovnik15.imageandspinner.db.DataHaleper;
import com.appstructural.jelovnik15.imageandspinner.db.model.Jelo;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import static com.appstructural.jelovnik15.imageandspinner.activity.MainActivity.NOTIF_STATUS;
import static com.appstructural.jelovnik15.imageandspinner.activity.MainActivity.NOTIF_TOAST;

public class SecondActivity extends AppCompatActivity {
    private DataHaleper databaseHelper;
    private SharedPreferences prefs;
    private Jelo j;

    private EditText naziv;
    private EditText vrsta;
    private ImageView image;
    private RatingBar rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int key = getIntent().getExtras().getInt(MainActivity.ACTOR_KEY);

        try {
            j = getDatabaseHelper().getJeloDao().queryForId(key);

            naziv = (EditText) findViewById(R.id.jelo_naziv2);
            vrsta = (EditText) findViewById(R.id.jelo_vrsta2);
            rating = (RatingBar) findViewById(R.id.jelo_rating2);
           // image = (ImageView) findViewById(R.id.jelo_image2);

            naziv.setText(j.getmNaziv());
            vrsta.setText(j.getmVrsta());
          //  image.setImageDrawable(j.getmSlika());
            rating.setRating(j.getmOcena());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void showStatusMesage(String message){
        NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_action_notification);
        mBuilder.setContentTitle("Pripremni test");
        mBuilder.setContentText(message);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_not2);

        mBuilder.setLargeIcon(bm);
        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }

    private void showMessage(String message){
        //provera podesenja
        boolean toast = prefs.getBoolean(NOTIF_TOAST, false);
        boolean status = prefs.getBoolean(NOTIF_STATUS, false);

        if (toast){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

        if (status){
            showStatusMesage(message);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.priprema_remove:
                try {
                    getDatabaseHelper().getJeloDao().delete(j);

                    showMessage("Jelo deleted");

                    finish(); //moramo pozvati da bi se vratili na prethodnu aktivnost
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;



        }

        return super.onOptionsItemSelected(item);
    }



    //Metoda koja komunicira sa bazom podataka
    public DataHaleper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DataHaleper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // nakon rada sa bazo podataka potrebno je obavezno
        //osloboditi resurse!
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
