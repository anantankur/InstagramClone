package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UserFeedActivity extends AppCompatActivity {

    LinearLayout linLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);

        linLayout = findViewById(R.id.linLayout);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        setTitle(username + "'s Photos");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Image");

        Log.i("Image", username);
        query.whereEqualTo("username", username);
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject object : objects) {
                        Log.i("Image", "getting images");
                        ParseFile file = (ParseFile) object.get("image");

                        ImageView imageView = new ImageView(getApplicationContext());
                        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        ));

                        Glide.with(getApplicationContext()).load(file.getUrl()).into(imageView);
                        linLayout.addView(imageView);

//                        file.getDataInBackground(new GetDataCallback() {
//                            @Override
//                            public void done(byte[] data, ParseException e) {
//                                if (e == null && data != null) {
//                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//
//                                    Log.i("Image", "Processing ....");
//
//                                    ImageView imageView = new ImageView(getApplicationContext());
//                                    imageView.setLayoutParams(new ViewGroup.LayoutParams(
//                                            ViewGroup.LayoutParams.MATCH_PARENT,
//                                            ViewGroup.LayoutParams.WRAP_CONTENT
//                                    ));
//
//                                    imageView.setImageBitmap(bitmap);
//
//                                    linLayout.addView(imageView);
//                                } else if (data == null) {
//                                    Log.i("Image", "null");
//                                } else {
//                                    Log.e("Image", e.toString() );
//                                }
//                            }
//                        });

                    }
                }
            }
        });




    }
}
