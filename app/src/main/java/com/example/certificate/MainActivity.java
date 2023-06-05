package com.example.certificate;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int SHARE_REQUEST_CODE = 123;

    private EditText etStudentName, etLevel;
    private ImageView ivLogo;
    private Button btnGenerateCertificate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etStudentName = findViewById(R.id.etStudentName);
        etLevel = findViewById(R.id.etLevel);
        ivLogo = findViewById(R.id.ivLogo);
        btnGenerateCertificate = findViewById(R.id.btnGenerateCertificate);

        File folder = new File(Environment.getExternalStorageDirectory() + "/student_images");
        if (!folder.exists()) {
            folder.mkdirs();
        }


        btnGenerateCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateCertificate();
            }
        });
    }

    private void generateCertificate() {
        String studentName = etStudentName.getText().toString();
        String level = etLevel.getText().toString();

        // Validate inputs
        if (studentName.isEmpty() || level.isEmpty()) {
            Toast.makeText(this, "Please enter student name and level", Toast.LENGTH_SHORT).show();
            return;
        }


// Set the desired width and height of the certificate
        int certificateWidth = 500;
        int certificateHeight = 700;

// Create a bitmap with the specified dimensions
        Bitmap certificateBitmap = Bitmap.createBitmap(certificateWidth, certificateHeight, Bitmap.Config.ARGB_8888);

// Create a canvas with the bitmap
        Canvas canvas = new Canvas(certificateBitmap);

// Set the background color of the certificate
        canvas.drawColor(Color.WHITE);

// Create a Paint object for the text
        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(24f);

// Set the text alignment to center
        textPaint.setTextAlign(Paint.Align.CENTER);

// Define the text content
        String certificateText = "Certificate of Achievement";

// Calculate the text position
        float textX = certificateWidth / 2f;
        float textY = certificateHeight / 2f;

// Draw the text on the canvas
        canvas.drawText(certificateText, textX, textY, textPaint);

// Add more UI elements, draw images, or customize the certificate design as needed

// Save the certificate as an image file
        FileOutputStream outStream;
        try {
            File certificateFile = new File(Environment.getExternalStorageDirectory(), "certificate.png");
            outStream = new FileOutputStream(certificateFile);
            certificateBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Share the certificate via WhatsApp
        shareCertificate(certificateBitmap);
    }

    private void shareCertificate(Bitmap certificateBitmap) {
        // Save the bitmap to the device storage
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), certificateBitmap, "Certificate", null);

        // Create the sharing intent
        Intent i=new Intent(MainActivity.this,Sample.class);
        startActivity(i);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));




        // Specify the package name of WhatsApp to share only through WhatsApp
        shareIntent.setPackage("com.whatsapp");

        startActivityForResult(Intent.createChooser(shareIntent, "Share Certificate"), SHARE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SHARE_REQUEST_CODE) {
            // Handle the result of the share action if needed
        }
    }
}
