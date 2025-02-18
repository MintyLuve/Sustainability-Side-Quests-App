package com.fro.sustainabilitysidequests;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.util.List;

public class ImageLabelingActivity extends AppCompatActivity {
    // Declare global variables here \/
    ImageView image;
    TextView resultT;
    TextView resultC;
    ImageLabeler imageLabeler;
    Button home;
    ImageButton pic;
    Button shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_labeling);

        // Instantiate variables here \/
        image = findViewById(R.id.image);
        resultT = findViewById(R.id.resultT);
        resultC = findViewById(R.id.resultC);
        home = findViewById(R.id.home);
        pic = findViewById(R.id.pic);
        shop = findViewById(R.id.shop);

        // set threshold to be 70% or higher
        ImageLabelerOptions imageLabelerOptions = new ImageLabelerOptions.Builder()
                .setConfidenceThreshold(0.7f)
                .build();
        imageLabeler = ImageLabeling.getClient(imageLabelerOptions);

        // Bitmap from Values.image
        Bitmap bitmap = rotateBitmap(Values.image.toBitmap(), 90);
        Drawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
        image.setBackground(bitmapDrawable);

        // Label the image yay
        labelImage(bitmap);

        //To go to pages
        home.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RecycleActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        shop.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ShopActivity.class)));
    }

    private void  labelImage(Bitmap bitmap){
        // prepare input image from bitmap
        InputImage inputImage = InputImage.fromBitmap(bitmap, 0);

        imageLabeler.process(inputImage)
                .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                    @Override
                    public void onSuccess(List<ImageLabel> imageLabels) {
                        // we got labels from image as List<ImageLabel>, now we will get and show detailed info
                        resultT.setText("");
                        resultC.setText("");

                        for (ImageLabel imageLabel: imageLabels){
                            //get the label (cake, mango,, fruit, tree, etc)
                            String text = imageLabel.getText();
                            //get confidence score in percentage
                            float confidence = imageLabel.getConfidence();

                            resultT.append("Type: "+ text +"\n");
                            resultC.append("Confidence: "+ confidence +"\n");

                            // Adding 10 points
                            Values.points += 10;
                            Toast.makeText(ImageLabelingActivity.this, "+10 Points", Toast.LENGTH_SHORT).show();

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Task failed, show reason
                        String text = "Failed to label due to "+e.getMessage();
                        resultT.setText(text);
                    }
                });
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}