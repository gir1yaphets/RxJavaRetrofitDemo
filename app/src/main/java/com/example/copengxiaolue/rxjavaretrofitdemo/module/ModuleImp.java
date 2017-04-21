package com.example.copengxiaolue.rxjavaretrofitdemo.module;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by copengxiaolue on 2017/04/19.
 */

public class ModuleImp implements IModule {

    private static final String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "fade.mp3";

    @Override
    public boolean onFileSave(InputStream in) {

        boolean result = false;
        if (in != null) {
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(FILE_PATH);
                byte[] buffer = new byte[4 * 1024];
                int readLen;
                while ((readLen = in.read(buffer)) != -1) {
                    out.write(buffer, 0, readLen);
                }
                out.flush();
                result = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return result;
    }
}
