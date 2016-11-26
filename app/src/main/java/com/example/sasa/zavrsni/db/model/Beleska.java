package com.example.sasa.zavrsni.db.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by android on 26.11.16..
 */
@DatabaseTable(tableName = Beleska.TABLE_NAME_BELESKA)
public class Beleska {

    public static final String TABLE_NAME_BELESKA = "beleska";
    public static final String FIELD_NAME_ID     = "id";
    public static final String TABLE_MOVIE_NASLOV = "naslov";
    public static final String TABLE_MOVIE_OPIS= "opis";
    public static final String TABLE_MOVIE_DATE = "datum";



    public Beleska() {

    }

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = TABLE_MOVIE_NASLOV)
    private String mNaslov;

    @DatabaseField(columnName = TABLE_MOVIE_OPIS)
    private String mOpis;


    @DatabaseField(columnName = TABLE_MOVIE_DATE)
    private String mDatum;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmNaslov() {
        return mNaslov;
    }

    public void setmNaslov(String mNaslov) {
        this.mNaslov = mNaslov;
    }

    public String getmOpis() {
        return mOpis;
    }

    public void setmOpis(String mOpis) {
        this.mOpis = mOpis;
    }

    public String getmDatum() {
        return mDatum;
    }

    public void setmDatum(String mDatum) {
        this.mDatum = mDatum;
    }


    @Override
    public String toString() {
        return mNaslov;
    }
}
