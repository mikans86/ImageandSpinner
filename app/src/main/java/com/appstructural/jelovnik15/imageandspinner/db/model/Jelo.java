package com.appstructural.jelovnik15.imageandspinner.db.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Mika on 3/18/2017.
 */
@DatabaseTable(tableName = Jelo.TABELE_NAME_USERS)
public class Jelo {

    public static final String TABELE_NAME_USERS = "jelo";
    public static final String FIELD_NAME_ID = "id";
    public static final String TABELE_NAME_NAZIV= "naziv";
    public static final String TABELE_NAME_VRSTA= "vrsta";
    public static final String TABELE_NAME_OCENA= "ocena";
    public static final String TABELE_NAME_SLIKA = "slika";
    public static final String TABELE_NAME_SASTOJCI= "sastojci";



    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    int mId;

    @DatabaseField(columnName =TABELE_NAME_NAZIV )
    String mNaziv;


    @DatabaseField(columnName =TABELE_NAME_VRSTA )
    String mVrsta;

    @DatabaseField(columnName =TABELE_NAME_OCENA )
    float mOcena;

    @DatabaseField(columnName =TABELE_NAME_SLIKA )
    String mSlika;

    @ForeignCollectionField(columnName = Jelo.TABELE_NAME_SASTOJCI, eager = true)
    private ForeignCollection<Sastojci> sastojci;


    public Jelo() {

    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmNaziv() {
        return mNaziv;
    }

    public void setmNaziv(String mNaziv) {
        this.mNaziv = mNaziv;
    }

    public String getmVrsta() {
        return mVrsta;
    }

    public void setmVrsta(String mVrsta) {
        this.mVrsta = mVrsta;
    }

    public float getmOcena() {
        return mOcena;
    }

    public void setmOcena(float mOcena) {
        this.mOcena = mOcena;
    }

    public String getmSlika() {
        return mSlika;
    }

    public void setmSlika(String mSlika) {
        this.mSlika = mSlika;
    }

    public ForeignCollection<Sastojci> getSastojci() {
        return sastojci;
    }

    public void setSastojci(ForeignCollection<Sastojci> sastojci) {
        this.sastojci = sastojci;
    }

    @Override
    public String toString() {
        return mNaziv;
    }

}

