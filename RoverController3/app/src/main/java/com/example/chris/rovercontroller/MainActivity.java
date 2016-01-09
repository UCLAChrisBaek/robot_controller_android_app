package com.example.chris.rovercontroller;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity
{
    float xAxis = 0f;
    float yAxis = 0f;

    private TCPclient mTcpClient;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // connect to the server
        new connectTask().execute("");

        final View touchView = findViewById(R.id.imageView);
        final View rotateCW = findViewById(R.id.rotate_cw_button);
        final View rotateCCW = findViewById(R.id.rotate_ccw_button);

        rotateCW.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int actionPeformed = event.getAction();
                String rotate_command = "stop";

                switch (actionPeformed) {
                    case MotionEvent.ACTION_DOWN: {
                        rotate_command = "cw";
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        rotate_command = "cw";
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        rotate_command = "stop";
                        break;
                    }
                }
                //sends the message to the server
                if (mTcpClient != null) {
                    mTcpClient.sendMessage("rotate|" + rotate_command);
                }
                return true;
            }
        });

        rotateCCW.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int actionPeformed = event.getAction();
                String rotate_command = "stop";

                switch (actionPeformed) {
                    case MotionEvent.ACTION_DOWN: {
                        rotate_command = "ccw";
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        rotate_command = "ccw";
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        rotate_command = "stop";
                        break;
                    }
                }
                //sends the message to the server
                if (mTcpClient != null) {
                    mTcpClient.sendMessage("rotate|" + rotate_command);
                }
                return true;
            }
        });

        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int actionPeformed = event.getAction();

                switch (actionPeformed) {
                    case MotionEvent.ACTION_DOWN: {
                        final float x = event.getX();
                        final float y = event.getY();

                        xAxis = x - 235;
                        yAxis = 330 - y;

                        break;
                    }

                    case MotionEvent.ACTION_MOVE: {
                        final float x = event.getX();
                        final float y = event.getY();

                        xAxis = x - 235;
                        yAxis = 330 - y;

                        break;
                    }

                    case MotionEvent.ACTION_UP: {
                        xAxis = 0;
                        yAxis = 0;

                        break;
                    }
                }

                //sends the message to the server
                if (mTcpClient != null) {
                    mTcpClient.sendMessage("" + Float.toString(xAxis) + "|" + Float.toString(yAxis));
                }

                return true;
            }
        });

    }

    public class connectTask extends AsyncTask<String, String, TCPclient> {

                @Override
                protected TCPclient doInBackground(String... message) {
                    //we create a TCPClient object and
                    mTcpClient = new TCPclient(new TCPclient.OnMessageReceived() {
                        @Override
                        //here the messageReceived method is implemented
                        public void messageReceived(String message) {
                            //this method calls the onProgressUpdate
                            publishProgress(message);
                        }
                    });
                    mTcpClient.run();

                    return null;
                }

                @Override
                protected void onProgressUpdate(String... values) {
                    super.onProgressUpdate(values);
                }
            }

}