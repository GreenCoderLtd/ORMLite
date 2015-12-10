package com.payanywhere.ormlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG=getClass().getSimpleName();
    @Bind(R.id.nameEditText) EditText name;
    @Bind(R.id.passwordEditText) EditText pass;
    private DatabaseHelper databaseHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getHelper();
    }

    public void saveClick(View view) {

        try {

            Dao<Account, Integer> simpleDao=databaseHelper.getDao();

            Account acc=new Account(name.getText().toString(),pass.getText().toString());

            simpleDao.create(acc);


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void retrieveClick(View view) {

        try {

            Dao<Account, Integer> simpleDao=databaseHelper.getDao();

            List<Account> allAccount=simpleDao.queryForAll();

            for(Account acc:allAccount)
            {
                Log.i(LOG_TAG,acc.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DatabaseHelper getHelper() {

        if (databaseHelper == null) {
            databaseHelper =
                    OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
