package com.payanywhere.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by aahmed on 11/12/15.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper{

    private final String LOG_TAG=getClass().getSimpleName();

    private static final String DATABASE_NAME = "ormtestdb.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the SimpleData table
    private Dao<Account, Integer> simpleDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {

            TableUtils.createTable(connectionSource, Account.class);

            Log.i(LOG_TAG, "Table Created");

            Dao<Account,Integer> accountDao= DaoManager.createDao(connectionSource,Account.class);

            Account account=new Account("Avishek","Pass");



            accountDao.create(account);

            Log.i(LOG_TAG, "Data Inserted");

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public Dao<Account, Integer> getDao() throws SQLException {
        if (simpleDao == null) {
            simpleDao = getDao(Account.class);
        }
        return simpleDao;
    }
}
