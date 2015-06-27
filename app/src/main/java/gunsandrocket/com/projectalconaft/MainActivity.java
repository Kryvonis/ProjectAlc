package gunsandrocket.com.projectalconaft;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.os.Bundle;


import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import android.support.v4.app.LoaderManager.LoaderCallbacks;
import java.io.IOException;
import java.sql.SQLException;



public class MainActivity extends FragmentActivity implements LoaderCallbacks<Cursor>{
    private final String  LOG_TAG = "myLog";
    SimpleCursorAdapter scAdapter;
    SQLiteDatabase db = null;
    DBAlco dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBAlco(this);
        try {
            db = dbHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] from = new String[] { dbHelper.ICON, dbHelper.NAME, dbHelper.SIZE, dbHelper.PRICE };
        int[] to = new int[] { R.id.iv, R.id.tvName, R.id.tvSize, R.id.tvPrice };

        ListView lv = (ListView)findViewById(R.id.listView);
        scAdapter = new SimpleCursorAdapter(this, R.layout.item, null, from, to, 0);
        scAdapter.setViewBinder(new MyViewBinder(this));
        lv.setAdapter(scAdapter);

        // создаем лоадер для чтения данных
        getSupportLoaderManager().initLoader(0, null, this);



    }
    protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе
        dbHelper.close();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(this, db);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        scAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

    }


    static class MyCursorLoader extends CursorLoader {

        SQLiteDatabase db;

        public MyCursorLoader(Context context, SQLiteDatabase db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.query("alcogols", null, null, null, null, null, null);

            return cursor;
        }

    }
}
