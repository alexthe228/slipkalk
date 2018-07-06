package kudrin.sleeptime;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.AlarmClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.sql.Time;


public class TimeSelector extends AppCompatActivity {

    int asleepTime = 10;
    int notificationTime = 10;
    int age = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_time_selector);

        MobileAds.initialize(this, "ca-app-pub-2111982870654604/7496386884");

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        TimePicker timePicker = (TimePicker) findViewById(R.id.timeToWakeUp);
        timePicker.setIs24HourView(true);

        TextView text = (TextView) findViewById(R.id.text);
        text.setText("Set the time when you want to wake up");


        int asleepTime = settings.getInt("asleepTime", 10);
        int notificationTime = settings.getInt("notificationTime", 10);
        int age = settings.getInt("age", 25);
        int lastTimeHours = settings.getInt("hours", 8);
        int lastTimeMinutes = settings.getInt("minutes", 0);

        timePicker.setCurrentMinute(lastTimeMinutes);
        timePicker.setCurrentHour(lastTimeHours);

        SeekBar seekbar = (SeekBar)findViewById(R.id.seekBar);
        seekbar.setProgress(asleepTime);
        TextView asleepTimeValue = (TextView)findViewById(R.id.asleepTimeValue2);
        asleepTimeValue.setText(String.valueOf(asleepTime));

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            TextView asleepTimeValue = (TextView)findViewById(R.id.asleepTimeValue2);

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                asleepTimeValue.setText(String.valueOf(i));

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("asleepTime", i);
                editor.commit();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        SeekBar seekbar2 = (SeekBar)findViewById(R.id.seekBar2);
        seekbar2.setProgress(age);
        TextView ageValue = (TextView)findViewById(R.id.ageValue);

        if (age<4)
            ageValue.setText("0-3");
        else if (age<10)
            ageValue.setText("3-9");
        else if (age<18)
            ageValue.setText("9-17");
        else if (age<26)
            ageValue.setText("17-25");
        else if (age<39)
            ageValue.setText("25-38");
        else if (age<55)
            ageValue.setText("38-54");
        else
            ageValue.setText(">54");

        seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            TextView ageValue = (TextView)findViewById(R.id.ageValue);

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i<4)
                    ageValue.setText("0-3");
                else if (i<10)
                    ageValue.setText("3-9");
                else if (i<18)
                    ageValue.setText("9-17");
                else if (i<26)
                    ageValue.setText("17-25");
                else if (i<39)
                    ageValue.setText("25-38");
                else if (i<55)
                    ageValue.setText("38-54");
                else
                    ageValue.setText(">54");

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("age", i);
                editor.commit();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        TextView tip = (TextView) findViewById(R.id.asleepTime);
        TextView tip2 = (TextView) findViewById(R.id.textView2);
        AlphaAnimation fadeOut = new AlphaAnimation( 1.0f , 0.0f ) ;
        fadeOut.setDuration(7000);
        fadeOut.setFillAfter(true);

        if ((notificationTime==10)) {
            tip.setText("Notification time is set to 10 which is default, you can change it in the settings");
            tip2.setText("▲");
        }

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                TextView tip = (TextView) findViewById(R.id.asleepTime);
                tip.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        tip.startAnimation(fadeOut);
        tip2.startAnimation(fadeOut);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Set alarm at this time?");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);

                TimePicker timeToWakeUp = (TimePicker) findViewById(R.id.timeToWakeUp);
                int hour = timeToWakeUp.getCurrentHour();
                int minutes = timeToWakeUp.getCurrentMinute();

                intent.putExtra(AlarmClock.EXTRA_HOUR, hour);
                intent.putExtra(AlarmClock.EXTRA_MINUTES, minutes);

                startActivity(intent);

                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });


        ImageButton alarmButton = (ImageButton) findViewById(R.id.alarmButton);
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        Button calculateButton = (Button) findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePicker timeToWakeUp = (TimePicker) findViewById(R.id.timeToWakeUp);
                int hour = timeToWakeUp.getCurrentHour();
                int minutes = timeToWakeUp.getCurrentMinute();

                openTimes(hour, minutes);
            }
        });
    }

    public void openTimes(int hour, int minutes){
        Intent intent = new Intent (this, Times.class);
        intent.putExtra("HOUR", hour);
        intent.putExtra("MINUTES", minutes);
        intent.putExtra("ASLEEPTIME", asleepTime);
        intent.putExtra("NOTIFICATIONTIME", notificationTime);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("hours", hour);
        editor.putInt("minutes", minutes);
        editor.commit();

        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
            /*
            case R.id.setAsleep:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Asleep time.");
                builder.setMessage("Please set amount of minutes that you usually spent in hte bed before falling asleep.");

                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        asleepTime = Integer.parseInt(input.getText().toString());
                        TextView parametersView = (TextView) findViewById(R.id.asleepTime);
                        parametersView.setText("Asleep time: "+asleepTime);

                        AlphaAnimation fadeOut = new AlphaAnimation( 1.0f , 0.0f ) ;
                        fadeOut.setDuration(10000);
                        fadeOut.setFillAfter(true);

                        fadeOut.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                TextView tip = (TextView) findViewById(R.id.asleepTime);
                                tip.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                        parametersView.startAnimation(fadeOut);

                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("asleepTime", asleepTime);
                        editor.commit();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


                builder.show();
                return true;

            case R.id.age:
                AlertDialog.Builder builder4 = new AlertDialog.Builder(this);
                builder4.setTitle("Age.");
                builder4.setMessage("Amount of sleeping cycles needed for good rest sleep depends on age. Please set your age.");

                final EditText input4 = new EditText(this);
                input4.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder4.setView(input4);

                builder4.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        age = Integer.parseInt(input4.getText().toString());
                        TextView parametersView = (TextView) findViewById(R.id.asleepTime);
                        parametersView.setText("Age: "+ age);

                        AlphaAnimation fadeOut = new AlphaAnimation( 1.0f , 0.0f ) ;
                        fadeOut.setDuration(10000);
                        fadeOut.setFillAfter(true);

                        fadeOut.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                TextView tip = (TextView) findViewById(R.id.asleepTime);
                                tip.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                        parametersView.startAnimation(fadeOut);


                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("age", age);
                        editor.commit();

                    }
                });
                builder4.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


                builder4.show();
                return true;
        */

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

                        TextView parametersView = (TextView) findViewById(R.id.asleepTime);
                        parametersView.setText("Notification time: " + notificationTime);

                        AlphaAnimation fadeOut = new AlphaAnimation( 1.0f , 0.0f ) ;
                        fadeOut.setDuration(10000);
                        fadeOut.setFillAfter(true);

                        fadeOut.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                TextView tip = (TextView) findViewById(R.id.asleepTime);
                                tip.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                        parametersView.startAnimation(fadeOut);


                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("notificationTime", notificationTime);
                        editor.commit();

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
