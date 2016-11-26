package com.example.sasa.zavrsni;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sasa.zavrsni.db.PripremaORMLightHelper;
import com.example.sasa.zavrsni.db.model.Beleska;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;

import static  com.example.sasa.zavrsni.ListActivity.NOTIF_STATUS;
import static  com.example.sasa.zavrsni.ListActivity.NOTIF_TOAST;

public class DetailActivity extends AppCompatActivity {

    private PripremaORMLightHelper databaseHelper;
    private EditText name;
    private EditText bio;
    private EditText birth;

    private static final int SELECT_PICTURE = 1;

    private SharedPreferences prefs;
    private Beleska a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        final int key = getIntent().getExtras().getInt(ListActivity.ACTOR_KEY);

        if (key != 0) {

            try {

                    a = getDatabaseHelper().getBeleskaDao().queryForId(key);

                    name = (EditText) findViewById(R.id.beleska_name);
                    bio = (EditText) findViewById(R.id.beleska_opis);
                    birth = (EditText) findViewById(R.id.beleska_datum);


                    name.setText(a.getmNaslov());
                    bio.setText(a.getmOpis());
                    birth.setText(a.getmDatum());


            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

            Button save = (Button) findViewById(R.id.save_beleska);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText name = (EditText) findViewById(R.id.beleska_name);
                    EditText bio = (EditText) findViewById(R.id.beleska_opis);
                    EditText birth = (EditText) findViewById(R.id.beleska_datum);

                    Beleska a = new Beleska();
                    a.setmNaslov(name.getText().toString());
                    a.setmOpis(bio.getText().toString());
                    a.setmDatum(birth.getText().toString());


                    try {

                        if(key!= 0) {
                            getDatabaseHelper().getBeleskaDao().update(a);
                        }else {
                            getDatabaseHelper().getBeleskaDao().create(a);
                        }
                        //provera podesenja
                        boolean toast = prefs.getBoolean(NOTIF_TOAST, false);
                        boolean status = prefs.getBoolean(NOTIF_STATUS, false);

                        if (toast) {
                            Toast.makeText(getBaseContext(), "Beleska je Snimljena", Toast.LENGTH_SHORT).show();
                        }

                        if (status) {
                            showMessage("Beleska is updated");

                        }

                        //REFRESH
                        finish();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                }
            });


        Button cancel = (Button) findViewById(R.id.cancel_beleska);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    finish();

            }
        });

    }

    private void showStatusMesage(String message){
        NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setContentTitle(a.getmNaslov().toString());
        mBuilder.setContentText(message);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_add);

        mBuilder.setLargeIcon(bm);
        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }


        // prikazuje poruku
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

    //MENI BUTTONI
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void selectPicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    /**
     * Sismtemska metoda koja se automatksi poziva ako se
     * aktivnost startuje u startActivityForResult rezimu
     *
     * Ako je ti slucaj i ako je sve proslo ok, mozemo da izvucemo
     * sadrzaj i to da prikazemo. Rezultat NIJE sliak nego URI do te slike.
     * Na osnovu toga mozemo dobiti tacnu putnaju do slike ali i samu sliku
     * */
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            if (requestCode == SELECT_PICTURE) {
//                Uri selectedImageUri = data.getData();
//
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
//                    imagePath = selectedImageUri.toString();
//
//                    if (preview != null){
//                        preview.setImageBitmap(bitmap);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    //za dodavanja u ovom slucaju filmova

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
//            case R.id.priprema_add_movie:
//                //OTVORI SE DIALOG UNESETE INFORMACIJE
//                final Dialog dialog = new Dialog(this);
//                dialog.setContentView(R.layout.priprema_add_movie);
//
//                Button add = (Button) dialog.findViewById(R.id.add_movie);
//                add.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        EditText name = (EditText) dialog.findViewById(R.id.movie_name);
//                        EditText genre = (EditText) dialog.findViewById(R.id.movie_genre);
//                        EditText year = (EditText) dialog.findViewById(R.id.movie_year);
//
//                        Movie m = new Movie();
//                        m.setmName(name.getText().toString());
//                        m.setmGenre(genre.getText().toString());
//                        m.setmYear(year.getText().toString());
//                        m.setmUser(a);
//
//                        try {
//                            getDatabaseHelper().getMovieDao().create(m);
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                        //URADITI REFRESH
//                        refresh();
//
//                        showMessage("New movie added to actor");
//
//                        dialog.dismiss();
//                    }
//                });
//
//                dialog.show();
//
//                break;
            case R.id.priprema_edit:
                //POKUPITE INFORMACIJE SA EDIT POLJA
                a.setmNaslov(name.getText().toString());
                a.setmDatum(birth.getText().toString());
                a.setmOpis(bio.getText().toString());

                try {
                    getDatabaseHelper().getBeleskaDao().update(a);

                    showMessage("Beleska is updated");
                    Toast.makeText(this, "Beleska je Update-ovana", Toast.LENGTH_SHORT).show();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.priprema_remove:
                try {
                    getDatabaseHelper().getBeleskaDao().delete(a);

                    showMessage("Beleska is deleted");

                    finish(); //moramo pozvati da bi se vratili na prethodnu aktivnost
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    //Metoda koja komunicira sa bazom podataka
    public PripremaORMLightHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, PripremaORMLightHelper.class);
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
