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


        try {
            // Get the assets logo file Inputstream
            inputStream = getAssets().open("logo.jpg");

            // Create a destination file in the cache directory which will be opened using an Intent
            destFile = new File(getCacheDir(), "/logo");
            String path = destFile.getPath();

            // Get the output stream of the file
            outputStream = new FileOutputStream(destFile);

            byte[] buffer = new byte[4 * 1024]; // or other buffer size
            int read;

            // Copy the Assets Input stream to the output stream
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();


            // Create a new intent to open the file
            Intent intent = new Intent("android.intent.action.VIEW", Uri.fromFile(destFile));

            // Get the content uri
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".OpenFile", destFile);

            // Set the intent data type which in this case is image/jpeg
            intent.setDataAndType(contentUri, "image/jpeg");

            // Add the Read flag
            intent.addFlags(1);

            // Start the Activity using the intent
            startActivityForResult(intent, 1);


        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
