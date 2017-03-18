package com.appstructural.jelovnik15.imageandspinner.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.appstructural.jelovnik15.imageandspinner.db.model.Jelo;
import com.appstructural.jelovnik15.imageandspinner.db.model.Sastojci;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Mika on 3/18/2017.
 */

public class DataHaleper extends OrmLiteSqliteOpenHelper {


    private static final String DATA_BASE_NAME= "zavrsni.db";
    private static final  int DATA_BASE_VERSION = 1;

    private Dao<Jelo,Integer> mJeloDao= null;
    private Dao<Sastojci,Integer>mSastojciDao = null;

    public DataHaleper (Context context){

        super(context,DATA_BASE_NAME,null,DATA_BASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, Jelo.class);
            TableUtils.createTable(connectionSource, Sastojci.class);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            TableUtils.dropTable(connectionSource,Jelo.class,true);
            TableUtils.dropTable(connectionSource,Sastojci.class,true);

            onCreate(database,connectionSource);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public Dao<Jelo,Integer>getJeloDao() throws SQLException{
        if(mJeloDao == null){
            mJeloDao = getDao(Jelo.class);
        }return mJeloDao;
    }


    public Dao<Sastojci,Integer> getSastojciDao()throws SQLException {
        if (mSastojciDao == null) {
            mSastojciDao = getDao(Sastojci.class);
        }
        return mSastojciDao;

    }

    //obavezno prilikom zatvarnaj rada sa bazom osloboditi resurse
    @Override
    public void close() {
        mSastojciDao = null;
        mJeloDao = null;

        super.close();
    }

}
