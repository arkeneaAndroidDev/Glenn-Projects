package com.android.glenn_library;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by admin on 16-11-2016.
 */
public class LibUtils {
    public static void copyFile(File paramFile1, File paramFile2)
    {
        try
        {
            if (!paramFile2.exists())
                paramFile2.createNewFile();
            FileInputStream localFileInputStream = new FileInputStream(paramFile1);
            FileOutputStream localFileOutputStream = new FileOutputStream(paramFile2);
            byte[] arrayOfByte = new byte[(int)paramFile1.length()];
            localFileInputStream.read(arrayOfByte);
            localFileOutputStream.write(arrayOfByte);
            localFileOutputStream.flush();
            localFileOutputStream.close();
            localFileInputStream.close();
            return;
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
    }
}
