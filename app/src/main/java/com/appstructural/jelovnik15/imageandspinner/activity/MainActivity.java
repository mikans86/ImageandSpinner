package com.appstructural.jelovnik15.imageandspinner.activity;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.appstructural.jelovnik15.imageandspinner.R;
import com.appstructural.jelovnik15.imageandspinner.db.DataHaleper;
import com.appstructural.jelovnik15.imageandspinner.db.model.Jelo;
import com.appstructural.jelovnik15.imageandspinner.preferenc.Preferenc;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {

    public DataHaleper dataBaseHelper;
    private SharedPreferences prefers;
    public static String ACTOR_KEY = "ACTOR_KEY";
    public static String NOTIF_TOAST = "notif_toast";
    public static String NOTIF_STATUS = "notif_statis";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instanciranje toolbara
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }


        prefers = PreferenceManager.getDefaultSharedPreferences(this);

        final ListView listView = (ListView) findViewById(R.id.lista_jela);

        try {
            List<Jelo> list = getDataBaseHelper().getJeloDao().queryForAll();

            ListAdapter adapter = new ArrayAdapter<>(MainActivity.this, R.layout.list_item, list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Jelo p = (Jelo) listView.getItemAtPosition(position);

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra(ACTOR_KEY, p.getmId());
                    startActivity(intent);
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void refresh() {
        ListView listview = (ListView) findViewById(R.id.lista_jela);

        if (listview != null){
            ArrayAdapter<Jelo> adapter = (ArrayAdapter<Jelo>) listview.getAdapter();

            if(adapter!= null)
            {
                try {
                    adapter.clear();
                    List<Jelo> list = getDataBaseHelper().getJeloDao().queryForAll();

                    adapter.addAll(list);

                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void showStatusMessage (String message){

        NotificationManager mNotificationMenager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_action_notification);
        mBuilder.setContentTitle("Zadnji test");
        mBuilder.setContentText(message);

        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.ic_action_not2);

        mBuilder.setLargeIcon(bm);

        mNotificationMenager.notify(1,mBuilder.build());



    }

    @Override
    protected void onResume() {
        super.onResume();

        refresh();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.add_jelo:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.add_jelo_layout);

                final Spinner imagesSpinner = (Spinner) dialog.findViewById(R.id.jelo_image);
                List<String> imagesList = new ArrayList<String>();
                imagesList.add("apples.jpg");
                imagesList.add("bananas.jpg");
                imagesList.add("oranges.jpg");
                ArrayAdapter<String> imagesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, imagesList);
                imagesSpinner.setAdapter(imagesAdapter);
                imagesSpinner.setSelection(0);


                Button add = (Button)dialog.findViewById(R.id.add);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText naziv = (EditText) dialog.findViewById(R.id.jelo_naziv);
                        EditText vrsta = (EditText) dialog.findViewById(R.id.jelo_vrsta);
                        RatingBar rating = (RatingBar) dialog.findViewById(R.id.jelo_rating);
                        String image = (String) imagesSpinner.getSelectedItem();




                        Jelo a = new Jelo();

                        a.setmNaziv(naziv.getText().toString());
                        a.setmVrsta(vrsta.getText().toString());
                        a.setmOcena(rating.getRating());
                        a.setmSlika(image);
                        try {
                            getDataBaseHelper().getJeloDao().create(a);


                            boolean status = prefers.getBoolean(NOTIF_STATUS,false);


                            if(status) {
                                showStatusMessage("Added a new Jelo");
                            }
                            refresh();



                        } catch (SQLException e) {
                            e.printStackTrace();}




                        dialog.dismiss();
                    }
                });
                dialog.show();

                Button cancel = (Button) dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                break;

            case R.id.preferenc:
                startActivity(new Intent(MainActivity.this, Preferenc.class));
                break;


        }

        return super.onOptionsItemSelected(item);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.first_menu, menu);


        return super.onCreateOptionsMenu(menu);
    }

    //Metoda koja komunicira sa bazom podataka
    public DataHaleper getDataBaseHelper(){

        if(dataBaseHelper == null){
            dataBaseHelper= OpenHelperManager.getHelper(this, DataHaleper.class);
        }
        return dataBaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // nakon rada sa bazo podataka potrebno je obavezno i sloboditi resurse
        if (dataBaseHelper!=null){
            OpenHelperManager.releaseHelper();
            dataBaseHelper=null;
        }
    }
}
