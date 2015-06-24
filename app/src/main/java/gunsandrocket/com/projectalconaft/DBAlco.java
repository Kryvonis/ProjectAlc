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
 * Created by ������ on 24.06.2015.
 */
public class DBAlco extends SQLiteOpenHelper {
    // ���� � ���� ������ ������ ����������
    private static String DB_PATH;
    private static String DB_NAME = "MyDB.sqlite";
    private SQLiteDatabase myDataBase;
    private final Context mContext;

    /**
     * �����������
     * ��������� � ��������� ������ �� ���������� �������� ��� ������� � �������� ����������
     * @param context
     */
    public DBAlco(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
        String packageName = context.getPackageName();
        DB_PATH = String.format("//data//data//%s//databases//", packageName);
    }

    /**
     * ������� ������ ���� ������ � �������������� �� ����� ����������� �����
     * */
    public void createDataBase() throws IOException{
        boolean dbExist = checkDataBase();

        if(dbExist){
            //������ �� ������ - ���� ��� ����
        }else{
            //������� ���� ����� ������� ������ ����, ����� ��� ����� ������������
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * ���������, ���������� �� ��� ��� ����, ����� �� ���������� ������ ��� ��� ������� ����������
     * @return true ���� ����������, false ���� �� ����������
     */
    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
            //���� ��� �� ����������
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     * �������� ���� �� ����� assets ������� ��������� ��������� ��
     * ����������� ����� ����������� ������ ������.
     * */
    private void copyDataBase() throws IOException{
// ��������� ����� ��� ������ �� ��� ��������� ���� ��
        //�������� � assets
        InputStream externalDbStream = mContext.getAssets().open(DB_NAME);

        // ���� � ��� ��������� ������ ���� � ��������
        String outFileName = DB_PATH + DB_NAME;

        // ������ �������� ����� ��� ������ � ��� �� ��������
        OutputStream localDbStream = new FileOutputStream(outFileName);

        // ����������, �����������
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = externalDbStream.read(buffer)) > 0) {
            localDbStream.write(buffer, 0, bytesRead);
        }

        // �� ����� �������� ���������� (���������) � ������� ������
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
