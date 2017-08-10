package dev.com.vn.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Linh(^0^)Nguyen on 4/18/2017.
 */

public class DBHelper {
    public static SQLiteDatabase copyDataFileToApp(Context context, String databaseName){
        try {
            String fileInApp = context.getApplicationInfo().dataDir+"/databases/"+databaseName;
            File file = new File(fileInApp);
            if(!file.exists()){
                InputStream is = context.getAssets().open(databaseName);
                File folder = new File(context.getApplicationInfo().dataDir+"/databases/");
                if(!folder.exists()){
                    folder.mkdir();
                }
                FileOutputStream fos = new FileOutputStream(fileInApp);
                byte[] buffer = new byte[1024];
                int length;
                while ((length=is.read(buffer))>0){
                    fos.write(buffer, 0, length);
                }
                fos.flush();
                fos.close();
            }

        }catch (IOException ex){
            ex.printStackTrace();
        }
        return context.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
    }
}
