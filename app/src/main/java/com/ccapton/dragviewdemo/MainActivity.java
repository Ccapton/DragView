package com.ccapton.dragviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.ccapton.dragview.DragView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((DragView)findViewById(R.id.dragview)).setOnDragViewClickListener(new DragView.OnDragViewClickListener() {
            @Override
            public void onDragViewClick(View view) {
                Toast.makeText(MainActivity.this,"hello",Toast.LENGTH_SHORT).show();
            }
        });



    }
}
