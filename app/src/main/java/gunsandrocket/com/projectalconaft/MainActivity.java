package gunsandrocket.com.projectalconaft;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private final String  LOG_TAG = "myLog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteDatabase db = null;
        DBAlco dbHelper = new DBAlco(this);
        try {
            db = dbHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Cursor c = db.query("alcogols", null, null, null, null, null,null);

        List<String> list = new ArrayList<String>();
        if (c.moveToFirst()) {
            int nameColIndex = c.getColumnIndex("name");

            do {
                list.add(c.getString(nameColIndex));
            } while (c.moveToNext());
        } else Log.d(LOG_TAG, "0 rows");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        ((ListView)findViewById(R.id.listView)).setAdapter(adapter);

        dbHelper.close();

    }


}
