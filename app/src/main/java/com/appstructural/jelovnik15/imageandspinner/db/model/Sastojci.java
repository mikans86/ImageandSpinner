package com.appstructural.jelovnik15.imageandspinner.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Mika on 3/18/2017.
 */
@DatabaseTable(tableName = Sastojci.TABELE_NAME_USERS)
public class Sastojci {


    public static final String TABELE_NAME_USERS = "sastojci";
    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_IME= "ime";
    public static final String FIELD_NAME_VRSTA= "vrsta";
    public static final String FIELD_NAME_USER= "user";


    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_NAME_IME)
    private String ime;

    @DatabaseField(columnName = FIELD_NAME_VRSTA)
    private String vrsta;

    @DatabaseField (columnName =FIELD_NAME_USER,foreign = true, foreignAutoRefresh = true)
    private Jelo user;



    public Sastojci() {


    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public Jelo getUser() {
        return user;
    }

    public void setUser(Jelo user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return ime;
    }
}
