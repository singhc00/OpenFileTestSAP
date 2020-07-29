package com.sapn.fileopener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openFile(View mButton) {
        Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_SHORT).show();

        File destFile = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        //File destFile = getAssets().open("logo.jpg");

        try {
            inputStream = getAssets().open("logo.jpg");
            destFile = new File(getCacheDir(), "/logo");
            String path = destFile.getPath();
            outputStream = new FileOutputStream(destFile);

            byte[] buffer = new byte[4 * 1024]; // or other buffer size
            int read;

            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();

            Intent intent = new Intent("android.intent.action.VIEW", Uri.fromFile(destFile));
            //if (this._mimeType != null) {
                //Context context = AttachmentViewer.cordovaInterface.getActivity().getApplicationContext();
                Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".OpenFile", destFile);

                //Uri contentUri = FileProviderWithWorkaround.getUriForFile(getApplicationContext(), context.getPackageName() + ".KapselAttachmentViewer", destFile);

                intent.setDataAndType(contentUri, "image/jpeg");

            //}

            intent.addFlags(1);
            startActivityForResult(intent, 1);


        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
