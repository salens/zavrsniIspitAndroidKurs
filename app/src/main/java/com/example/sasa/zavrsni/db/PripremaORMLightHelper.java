package com.example.sasa.zavrsni.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.sasa.zavrsni.db.model.Beleska;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class PripremaORMLightHelper extends OrmLiteSqliteOpenHelper{

    private static final String DATABASE_NAME    = "beleska.db";
    private static final int    DATABASE_VERSION = 1;

    private Dao<Beleska, Integer> mBeleskaDao = null;

    public PripremaORMLightHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Beleska.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Beleska.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Dao<Beleska, Integer> getBeleskaDao() throws SQLException {
        if (mBeleskaDao == null) {
            mBeleskaDao = getDao(Beleska.class);
        }

        return mBeleskaDao;
    }

    //obavezno prilikom zatvarnaj rada sa bazom osloboditi resurse
    @Override
    public void close() {
        mBeleskaDao = null;

        super.close();
    }
}
