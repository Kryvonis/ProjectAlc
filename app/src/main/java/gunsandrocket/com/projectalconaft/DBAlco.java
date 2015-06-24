package gunsandrocket.com.projectalconaft;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by ƒмитро on 24.06.2015.
 */
public class DBAlco extends SQLiteOpenHelper {
    // путь к базе данных вашего приложени€
    private static String DB_PATH;
    private static String DB_NAME = "MyDB.sqlite";
    private SQLiteDatabase myDataBase;
    private final Context mContext;

    /**
     *  онструктор
     * ѕринимает и сохран€ет ссылку на переданный контекст дл€ доступа к ресурсам приложени€
     * @param context
     */
    public DBAlco(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
        String packageName = context.getPackageName();
        DB_PATH = String.format("//data//data//%s//databases//", packageName);
    }

    /**
     * —оздает пустую базу данных и перезаписывает ее нашей собственной базой
     * */
    public void createDataBase() throws IOException{
        boolean dbExist = checkDataBase();

        if(dbExist){
            //ничего не делать - база уже есть
        }else{
            //вызыва€ этот метод создаем пустую базу, позже она будет перезаписана
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * ѕровер€ет, существует ли уже эта база, чтобы не копировать каждый раз при запуске приложени€
     * @return true если существует, false если не существует
     */
    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
            //база еще не существует
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     *  опирует базу из папки assets заместо созданной локальной Ѕƒ
     * ¬ыполн€етс€ путем копировани€ потока байтов.
     * */
    private void copyDataBase() throws IOException{
// ќткрываем поток дл€ чтени€ из уже созданной нами Ѕƒ
        //источник в assets
        InputStream externalDbStream = mContext.getAssets().open(DB_NAME);

        // ѕуть к уже созданной пустой базе в андроиде
        String outFileName = DB_PATH + DB_NAME;

        // “еперь создадим поток дл€ записи в эту Ѕƒ побайтно
        OutputStream localDbStream = new FileOutputStream(outFileName);

        // —обственно, копирование
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = externalDbStream.read(buffer)) > 0) {
            localDbStream.write(buffer, 0, bytesRead);
        }

        // ћы будем хорошими мальчиками (девочками) и закроем потоки
        localDbStream.close();

        externalDbStream.close();
    }

    public SQLiteDatabase openDataBase() throws SQLException, IOException {
        String path = DB_PATH + DB_NAME;
        if (myDataBase == null) {
            createDataBase();
            myDataBase = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }
        return myDataBase;
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
