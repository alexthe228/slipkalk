package kudrin.sleeptime;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;

public class Times extends AppCompatActivity {

    int notificationTime = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_times);

        MobileAds.initialize(this, "ca-app-pub-2111982870654604/7496386884");

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        SwipeBack.attach(this, Position.LEFT)
                .setContentView(R.layout.activity_times)
                .setSwipeBackView(R.layout.activity_time_selector);

        int hour = 0, minutes = 0, asleeptime = 0;

        hour = getIntent().getIntExtra("HOUR", hour);
        minutes = getIntent().getIntExtra("MINUTES", minutes);
        asleeptime = getIntent().getIntExtra("ASLEEPTIME", asleeptime);
        notificationTime = getIntent().getIntExtra("NOTIFICATIONTIME", notificationTime);

        int tempH = hour - 3;
        if (tempH < 0) {
            tempH = 24 + tempH;
        }

        int tempM = minutes + asleeptime;

        String [] times = new String[5];

        for (int i = 0; i < 5; i++)
        {
            tempM = tempM - 30;
            tempH = tempH - 1;

            if (tempM < 0)
            {
                tempM = 60 + tempM;
                tempH = tempH - 1;
            }

            if (tempH < 0) {
                tempH = 24 + tempH;
            }

            times[i] = convert24To12System(tempH, tempM);
        }

        TextView time1 = (TextView) findViewById(R.id.time1);
        TextView time2 = (TextView) findViewById(R.id.time2);
        TextView time3 = (TextView) findViewById(R.id.time3);
        TextView time4 = (TextView) findViewById(R.id.time4);
        TextView time5 = (TextView) findViewById(R.id.time5);

        time1.setText("6 cycles, go to bed at : " + times[4]);
        time2.setText("5 cycles, go to bed at : " + times[3]);
        time3.setText("4 cycles, go to bed at : " + times[2]);
        time4.setText("3 cycles, go to bed at : " + times[1]);
        time5.setText("2 cycles, go to bed at : " + times[0]);

        ImageButton notification1 = (ImageButton) findViewById(R.id.notification1);
        ImageButton notification2 = (ImageButton) findViewById(R.id.notification2);
        ImageButton notification3 = (ImageButton) findViewById(R.id.notification3);
        ImageButton notification4 = (ImageButton) findViewById(R.id.notification4);
        ImageButton notification5 = (ImageButton) findViewById(R.id.notification5);


        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.notification);
        mBuilder.setContentTitle("Notification Alert, Click Me!");
        mBuilder.setContentText("Hi, This is Android Notification Detail!");

        notification1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                mNotificationManager.notify(1, mBuilder.build());

                Toast toast = Toast.makeText(getApplicationContext(), "You will get notification "+ notificationTime +" minutes before that", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        notification2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                mNotificationManager.notify(1, mBuilder.build());

                Toast toast = Toast.makeText(getApplicationContext(), "You will get notification "+ notificationTime +" minutes before that", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        notification3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                mNotificationManager.notify(1, mBuilder.build());

                Toast toast = Toast.makeText(getApplicationContext(), "You will get notification "+ notificationTime +" minutes before that", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        notification4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                mNotificationManager.notify(1, mBuilder.build());

                Toast toast = Toast.makeText(getApplicationContext(), "You will get notification "+ notificationTime +" minutes before that", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        notification5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                mNotificationManager.notify(1, mBuilder.build());

                Toast toast = Toast.makeText(getApplicationContext(), "You will get notification "+ notificationTime +" minutes before that", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public static String convert24To12System (int hour, int minute) {
        String time = "";
        String am_pm = "";
        if (hour < 12 ) {
            if (hour == 0) hour = 12;
            am_pm = "AM";
        }
        else {
            if (hour != 12)
                hour-=12;
            am_pm = "PM";
        }
        String h = hour+"", m = minute+"";
        if(h.length() == 1) h = "0"+h;
        if(m.length() == 1) m = "0"+m;
        time = h+":"+m+" "+am_pm;
        return time;
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.about:
                final AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle("About");
                builder2.setMessage("This application can help you to feel rested after your daily sleep. Just choose the time when you want to wake up and touch 'Calclulate'. Applicaiton will " +
                        " calculate the best options for you to go to bed, depending on your age and time you spent in bed before sleep. ");
                builder2.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                AlertDialog about = builder2.create();
                about.show();
                return true;

            case R.id.notificationTime:
                AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
                builder3.setTitle("Notification time");
                builder3.setMessage("This application can notify you before your bed time in advance. Please set time in minutes.");

                final EditText input2 = new EditText(this);
                input2.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder3.setView(input2);

                builder3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notificationTime = Integer.parseInt(input2.getText().toString());
                    }
                });
                builder3.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder3.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
