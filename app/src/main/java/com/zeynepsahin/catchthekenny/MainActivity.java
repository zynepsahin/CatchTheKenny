package com.zeynepsahin.catchthekenny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView timeText, scoreText;
    int score, screenX, screenY;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeText = findViewById(R.id.timeText);
        scoreText = findViewById(R.id.scoreText);
        imageView = findViewById(R.id.imageView);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenX = size.x;
        screenY = size.y;

        score = 0;
        startGame();
    }
    public void startGame() {
        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Time: " + millisUntilFinished / 1000);
                float rdmX = new Random().nextInt(screenX - imageView.getMeasuredWidth());
                float rdmY = new Random().nextInt(screenY - 2 * imageView.getMeasuredHeight());
                imageView.setX(rdmX);
                imageView.setY(rdmY);
            }
            @Override
            public void onFinish() {
                timeText.setText("Time's Off!");
                imageView.setVisibility(View.INVISIBLE);
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Restart");
                alert.setMessage("Do you want to try again?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        score = 0;
                        startGame();
                        imageView.setVisibility(View.VISIBLE);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Game Over!", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.show();
            }
        }.start();
    }
    public void increaseScore (View view){
        score++;
        scoreText.setText("Score: " + score);
    }
}