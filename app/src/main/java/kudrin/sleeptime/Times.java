package kudrin.sleeptime;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Calendar;

public class Times extends AppCompatActivity {

    int notificationTime = 10;
    int hour = 0, minutes = 0, asleeptime = 0, age = 0;
    String [] resultTimes = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_times);

        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.LOLLIPOP) {
            //findViewById(R.id.imageView2).setBackground(null);
            ScrollView image = (ScrollView) this.findViewById(R.id.sw);
            image.setBackgroundColor(Color.TRANSPARENT);
            RelativeLayout image2 = (RelativeLayout) this.findViewById(R.id.rw);
            image2.setBackgroundColor(Color.TRANSPARENT);
        }


       // MobileAds.initialize(this, "ca-app-pub-2111982870654604~6626567462");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        hour = getIntent().getIntExtra("HOUR", hour);
        minutes = getIntent().getIntExtra("MINUTES", minutes);
        asleeptime = getIntent().getIntExtra("ASLEEPTIME", asleeptime);
        age = getIntent().getIntExtra("AGE", age);
        notificationTime = getIntent().getIntExtra("NOTIFICATIONTIME", notificationTime);

        TextView tip = (TextView) findViewById(R.id.asleepTime2);
        TextView tip2 = (TextView) findViewById(R.id.textView);
        AlphaAnimation fadeOut = new AlphaAnimation( 1.0f , 0.0f ) ;
        fadeOut.setDuration(7000);
        fadeOut.setFillAfter(true);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                TextView tip = (TextView) findViewById(R.id.asleepTime2);
                tip.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        tip.startAnimation(fadeOut);
        tip2.startAnimation(fadeOut);

        if (asleeptime==0)
            asleeptime = 0;
        else if (asleeptime<6)
            asleeptime = 5;
        else if (asleeptime<11)
            asleeptime = 10;
        else if (asleeptime<16)
            asleeptime = 15;
        else if (asleeptime<21)
            asleeptime = 20;
        else if (asleeptime<26)
            asleeptime = 25;
        else if (asleeptime<31)
            asleeptime = 30;
        else if (asleeptime<36)
            asleeptime = 35;
        else
            asleeptime = 40;

        int tempH = hour;
        int tempM = minutes - asleeptime;

        String [] times = new String[12];

        for (int i = 0; i < 11; i++)
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

            times[i] = convertTime(tempH, tempM);
        }

        String [] cycles = new String[5];

        if (age < 1) {
            resultTimes[0] = times[10];
            resultTimes[1] = times[9];
            resultTimes[2] = times[8];
            resultTimes[3] = times[7];
            resultTimes[4] = times[6];

            cycles[0] = "11";
            cycles[1] = "10";
            cycles[2] = "9";
            cycles[3] = "8";
            cycles[4] = "7";
        }
        else if(age < 3) {
            resultTimes[0] = times[8];
            resultTimes[1] = times[7];
            resultTimes[2] = times[6];
            resultTimes[3] = times[5];
            resultTimes[4] = times[4];

            cycles[0] = "9";
            cycles[1] = "8";
            cycles[2] = "7";
            cycles[3] = "6";
            cycles[4] = "5";
        }
        else if(age<10) {
            resultTimes[0] = times[6];
            resultTimes[1] = times[7];
            resultTimes[2] = times[5];
            resultTimes[3] = times[4];
            resultTimes[4] = times[3];

            cycles[0] = "7";
            cycles[1] = "8";
            cycles[2] = "6";
            cycles[3] = "5";
            cycles[4] = "4";
        }
        else if(age<47) {
            resultTimes[0] = times[5];
            resultTimes[1] = times[6];
            resultTimes[2] = times[4];
            resultTimes[3] = times[3];
            resultTimes[4] = times[2];

            cycles[0] = "6";
            cycles[1] = "7";
            cycles[2] = "5";
            cycles[3] = "4";
            cycles[4] = "3";

        }
        else {
            resultTimes[0] = times[4];
            resultTimes[1] = times[5];
            resultTimes[2] = times[3];
            resultTimes[3] = times[2];
            resultTimes[4] = times[1];

            cycles[0] = "5";
            cycles[1] = "6";
            cycles[2] = "4";
            cycles[3] = "3";
            cycles[4] = "2";
        }

        TextView time1 = (TextView) findViewById(R.id.time1);
        TextView time2 = (TextView) findViewById(R.id.time2);
        TextView time3 = (TextView) findViewById(R.id.time3);
        TextView time4 = (TextView) findViewById(R.id.time4);
        TextView time5 = (TextView) findViewById(R.id.time5);

        time1.setText(cycles[0] + getString(R.string.result) + resultTimes[0]);
        time2.setText(cycles[1] + getString(R.string.result) + resultTimes[1]);
        time3.setText(cycles[2] + getString(R.string.result) + resultTimes[2]);
        time4.setText(cycles[3] + getString(R.string.result) + resultTimes[3]);
        time5.setText(cycles[4] + getString(R.string.result) + resultTimes[4]);

        ImageButton notification1 = (ImageButton) findViewById(R.id.notification1);
        ImageButton notification2 = (ImageButton) findViewById(R.id.notification2);
        ImageButton notification3 = (ImageButton) findViewById(R.id.notification3);
        ImageButton notification4 = (ImageButton) findViewById(R.id.notification4);
        ImageButton notification5 = (ImageButton) findViewById(R.id.notification5);

        notification1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resultTime = resultTimes[0];
                int resultH = Integer.parseInt(resultTime.split(":")[0]);
                int resultM = Integer.parseInt(resultTime.split(":")[1]);

                resultM = resultM - notificationTime;

                if (resultM<0)
                {
                    resultH = resultH - 1;
                    if (resultH<0)
                        resultH = resultH + 24;

                    resultM = resultM + 60;
                }

                String time = convertTime(resultH, resultM);

                setNotification(resultH, resultM);

                Toast toast = Toast.makeText(getApplicationContext(),  getString(R.string.notificationToast) + time, Toast.LENGTH_LONG);
                toast.show();

            }
        });

        notification2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resultTime = resultTimes[1];
                int resultH = Integer.parseInt(resultTime.split(":")[0]);
                int resultM = Integer.parseInt(resultTime.split(":")[1]);
                setNotification(resultH, resultM);

                resultM = resultM - notificationTime;

                if (resultM<0)
                {
                    resultH = resultH - 1;
                    if (resultH<0)
                        resultH = resultH + 24;

                    resultM = resultM + 60;
                }

                String time = convertTime(resultH, resultM);

                Toast toast = Toast.makeText(getApplicationContext(),  getString(R.string.notificationToast)  + time, Toast.LENGTH_LONG);                toast.show();
                toast.show();
            }
        });

        notification3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resultTime = resultTimes[2];
                int resultH = Integer.parseInt(resultTime.split(":")[0]);
                int resultM = Integer.parseInt(resultTime.split(":")[1]);

                setNotification(resultH, resultM);

                resultM = resultM - notificationTime;

                if (resultM<0)
                {
                    resultH = resultH - 1;
                    if (resultH<0)
                        resultH = resultH + 24;

                    resultM = resultM + 60;
                }

                String time = convertTime(resultH, resultM);

                Toast toast = Toast.makeText(getApplicationContext(),  getString(R.string.notificationToast)  + time, Toast.LENGTH_LONG);          toast.show();
                toast.show();
            }
        });

        notification4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resultTime = resultTimes[3];
                int resultH = Integer.parseInt(resultTime.split(":")[0]);
                int resultM = Integer.parseInt(resultTime.split(":")[1]);

                setNotification(resultH, resultM);

                resultM = resultM - notificationTime;

                if (resultM<0)
                {
                    resultH = resultH - 1;
                    if (resultH<0)
                        resultH = resultH + 24;

                    resultM = resultM + 60;
                }

                String time = convertTime(resultH, resultM);

                Toast toast = Toast.makeText(getApplicationContext(),  getString(R.string.notificationToast)  + time, Toast.LENGTH_LONG);toast.show();
                toast.show();
            }
        });

        notification5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resultTime = resultTimes[4];
                int resultH = Integer.parseInt(resultTime.split(":")[0]);
                int resultM = Integer.parseInt(resultTime.split(":")[1]);

                setNotification(resultH, resultM);

                resultM = resultM - notificationTime;

                if (resultM<0)
                {
                    resultH = resultH - 1;
                    if (resultH<0)
                        resultH = resultH + 24;

                    resultM = resultM + 60;
                }

                String time = convertTime(resultH, resultM);

                Toast toast = Toast.makeText(getApplicationContext(),  getString(R.string.notificationToast) + time, Toast.LENGTH_LONG);toast.show();
                toast.show();
            }
        });

        ImageButton backButton = (ImageButton) findViewById(R.id.imageButton3);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


    public void setNotification(int hour, int minutes)
    {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent;
        Intent myIntent = new Intent(Times.this, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(Times.this, 0, myIntent,0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public static String convertTime (int hour, int minute) {
        String time = "";
        String sHour = String.valueOf(hour);
        String sMinute = String.valueOf(minute);;

        if (hour<10)
            sHour = "0" + sHour;

        if (minute<10)
            sMinute = "0" + sMinute;

        time = sHour + ":" + sMinute;

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
                builder2.setTitle(getString(R.string.about));
                builder2.setIcon(R.mipmap.ic_launcher);
                builder2.setMessage(getString(R.string.aboutText));
                builder2.setNegativeButton("Close", new DialogInterface.OnClickListener() {
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
                builder3.setTitle(getString(R.string.setnotification));
                builder3.setMessage(getString(R.string.notificationTitle));
                builder3.setIcon(R.drawable.sleep);

                final EditText input2 = new EditText(this);
                input2.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder3.setView(input2);

                builder3.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notificationTime = Integer.parseInt(input2.getText().toString());

                        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.notificationTip) + notificationTime, Toast.LENGTH_LONG);
                        toast.show();

                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("notificationTime", notificationTime);
                        editor.commit();

                    }
                });
                builder3.setNegativeButton(getString(R.string.close), new DialogInterface.OnClickListener() {
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
