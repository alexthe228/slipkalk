package kudrin.sleeptime;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.AlarmClock;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class TimeSelector extends AppCompatActivity {

    int asleepTime = 0;
    int notificationTime = 10;
    int age = 25;

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_time_selector);


        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.LOLLIPOP) {
            //findViewById(R.id.imageView2).setBackground(null);
          //  ImageView image = (ImageView) this.findViewById(R.id.imageView2);
         //   image.setImageBitmap(null);
        }

        MobileAds.initialize(this, "ca-app-pub-2111982870654604~6626567462");

        String androidId =  Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = MD5(androidId).toUpperCase();

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        TimePicker timePicker = (TimePicker) findViewById(R.id.timeToWakeUp);
        timePicker.setIs24HourView(true);

        asleepTime = settings.getInt("asleepTime", 15);
        notificationTime = settings.getInt("notificationTime", 10);
        age = settings.getInt("age", 25);
        int lastTimeHours = settings.getInt("hours", 8);
        int lastTimeMinutes = settings.getInt("minutes", 0);

        timePicker.setCurrentMinute(lastTimeMinutes);
        timePicker.setCurrentHour(lastTimeHours);

        final AlertDialog.Builder builder2 = new AlertDialog.Builder(this);

        builder2.setTitle(getString(R.string.age));
        builder2.setIcon(R.drawable.tip);
        builder2.setMessage(getString(R.string.ageText));
        builder2.setNegativeButton(getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        final AlertDialog ageTipText = builder2.create();

        ImageButton ageTip = (ImageButton) this.findViewById(R.id.ageTip);

        ageTip.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          ageTipText.show();
                                          }
                                  });


        final AlertDialog.Builder builder3 = new AlertDialog.Builder(this);

        builder3.setTitle(getString(R.string.asleep));
        builder3.setMessage(getString(R.string.asleepText));
        builder3.setIcon(R.drawable.tip);
        builder3.setNegativeButton(getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        final AlertDialog asleepTipText = builder3.create();

        ImageButton asleepTip = (ImageButton) this.findViewById(R.id.asleepTip);

        asleepTip.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            asleepTipText.show();
                                        }
                                     });

        SeekBar seekbar = (SeekBar)findViewById(R.id.seekBar);
        seekbar.setProgress(asleepTime);
        TextView asleepTimeValue = (TextView)findViewById(R.id.asleepTimeValue2);

        if (asleepTime==0)
            asleepTimeValue.setText("0");
        else if (asleepTime<6)
            asleepTimeValue.setText("5");
        else if (asleepTime<11)
            asleepTimeValue.setText("10");
        else if (asleepTime<16)
            asleepTimeValue.setText("15");
        else if (asleepTime<21)
            asleepTimeValue.setText("20");
        else if (asleepTime<26)
            asleepTimeValue.setText("25");
        else if (asleepTime<31)
            asleepTimeValue.setText("30");
        else if (asleepTime<36)
            asleepTimeValue.setText("35");
        else
            asleepTimeValue.setText("40");

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            TextView asleepTimeValue = (TextView)findViewById(R.id.asleepTimeValue2);

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                if (i==0) {
                    asleepTimeValue.setText("0");
                    asleepTime = i;
                }
                else if (i<6){
                    asleepTimeValue.setText("5");
                    asleepTime = i;
                }
                else if (i<11){
                    asleepTimeValue.setText("10");
                    asleepTime = i;
                }
                else if (i<16){
                    asleepTimeValue.setText("15");
                    asleepTime = i;
                }
                else if (i<21){
                    asleepTimeValue.setText("20");
                    asleepTime = i;
                }
                else if (i<26){
                    asleepTimeValue.setText("25");
                    asleepTime = i;
                }
                else if (i<31){
                    asleepTimeValue.setText("30");
                    asleepTime = i;
                }
                 else if (i<36){
                    asleepTimeValue.setText("35");
                    asleepTime = i;
                }
                else{
                    asleepTimeValue.setText("40");
                    asleepTime = i;
                }

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
        ageValue.setText(String.valueOf(age));

        seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            TextView ageValue = (TextView)findViewById(R.id.ageValue);

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ageValue.setText(String.valueOf(age));
                age = i;

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

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String time = convertTime(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
        builder.setTitle(getString(R.string.setAlarm) + " " + time);
        builder.setMessage(R.string.sure);

        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {

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

        builder.setNegativeButton(getString(R.string.close), new DialogInterface.OnClickListener() {

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
        intent.putExtra("AGE", age);
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
}
