package gunsandrocket.com.projectalconaft;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.SimpleCursorAdapter;

import java.io.IOException;
import java.io.InputStream;
import android.widget.ImageView;

/**
 * Created by Äלטענמ on 27.06.2015.
 */
public class MyViewBinder implements SimpleCursorAdapter.ViewBinder  {
    private Context mContext;

    MyViewBinder(Context context){
        mContext = context;
    }
    @Override
    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
        if(view.getId() == R.id.iv){
            InputStream ims = null;
            try {
                ims = mContext.getAssets().open(cursor.getString(columnIndex));

            } catch (IOException e) {
                e.printStackTrace();
            }
            Drawable d = Drawable.createFromStream(ims, null);
            ((ImageView)view).setImageDrawable(d);
            return true;
        }
        return false;
    }
}
