package com.fro.recylerewards;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.util.List;

public class ImageLabelingActivity extends AppCompatActivity {
    // Declare global variables here \/
    private ImageView imageIv;
    private MaterialButton labelImageBtn;
    private TextView resultTv;
    Button button;

    private ImageLabeler imageLabeler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_labeling);

        // Instantiate variables here \/
        imageIv = findViewById(R.id.imageIv);
        labelImageBtn = findViewById(R.id.labelImageBtn);
        resultTv = findViewById(R.id.resultTv);
        button = findViewById(R.id.back);


        imageLabeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);

        // Bitmap from ImageView
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageIv.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        labelImageBtn.setOnClickListener(v ->labelImage(bitmap));

        button.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), CameraActivity.class)));
    }

    private void  labelImage(Bitmap bitmap){
        // prepare input image from bitmap
        InputImage inputImage = InputImage.fromBitmap(bitmap, 0);

        imageLabeler.process(inputImage)
                .addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
                    @Override
                    public void onSuccess(List<ImageLabel> imageLabels) {
                        // we got labels from image as List<ImageLabel>, now we will get and show detailed info
                        resultTv.setText(" ");
                        for (ImageLabel imageLabel: imageLabels){
                            //get the label (cake, mango,, fruit, tree, etc)
                            String text = imageLabel.getText();
                            //get confidence score in percentage
                            float confidence = imageLabel.getConfidence();
                            int index = imageLabel.getIndex();

                            resultTv.append("text: "+ text +"\nconfidence: "+confidence +"\nindex: "+index+"\n\n");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Task failed, show reason
                        String text = "Failed to label due to "+e.getMessage();
                        resultTv.setText(text);
                    }
                });
    }
}