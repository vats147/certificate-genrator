package com.example.certificate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Sample extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        FloatingActionButton fab = findViewById(R.id.fab);
//        View view = inflater.inflate(R.layout.activity_sample);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bitmap screenshot = captureScreenshot();

                try {
                    // Save the bitmap to a file
                    String fileName = "screenshot.png";
                    File screenshotFile = new File(Environment.getExternalStorageDirectory(), fileName);
                    FileOutputStream outputStream = new FileOutputStream(screenshotFile);
                    screenshot.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    Toast.makeText(Sample.this, "Screenshot saved", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Sample.this, "Failed to save screenshot", Toast.LENGTH_SHORT).show();
                }
//                getScreen();

                Toast.makeText(Sample.this, "Hello", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private Bitmap captureScreenshot() {
        // Inflate the layout file
        View view = getLayoutInflater().inflate(R.layout.activity_sample, null);

        // Create a bitmap with the same dimensions as the view
        Bitmap screenshot = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);

        // Create a canvas with the bitmap
        Canvas canvas = new Canvas(screenshot);
        view.draw(canvas);
        Toast.makeText(this, "Capture Screenshots", Toast.LENGTH_SHORT).show();

        return screenshot;
    }

    public static void saveScreenshot(Context context, View view, String filename) {
        // Get the size of the view
        int width = view.getWidth();
        int height = view.getHeight();

        // Create a bitmap to hold the screenshot
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // Create a canvas to draw the view onto the bitmap
        Canvas canvas = new Canvas(bitmap);

        // Draw the view onto the canvas
        view.draw(canvas);

        // Save the bitmap to a file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}