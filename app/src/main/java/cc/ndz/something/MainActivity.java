package cc.ndz.something;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    TextView textView_main, tvbd, tvbb, tvtb;
    long min, hour, day, diff, s;
    DateFormat df;
    Date d1, d2, bd;
    long bhour, bdiff;
    double bday;
    SpannableString spannableString;
    String stringTe;
    Timer timer;
    int year, age;
    String str_day;
    NumberFormat nf;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView_main = findViewById(R.id.maintv);
        tvbd = findViewById(R.id.tvbd);
        tvbb = findViewById(R.id.textView5);
        tvtb = findViewById(R.id.textView3);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // Translucent navigation bar
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        //获取系统的日期
        //年
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        age = year - 1999;
        tvbb.setText(year + "-10-24");
        tvtb.setText("距离她的" + age + "岁的生日");

        timer = new Timer(true);
        timer.schedule(task, 30, 1000); //延时333ms后执行，1000ms执行一次
        timer.schedule(task2, 30, 3600000*10);
        //timer.cancel(); //退出计时器

    }

    TimerTask task = new TimerTask() {
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    TimerTask task2 = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 2;
            handler.sendMessage(message);
        }
    };
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                try {
                    //Date d2 = df.parse("2006-07-02 11:20:00");
                    d2 = new Date(System.currentTimeMillis());//你也可以获取当前时间
                    d1 = df.parse("2018-03-08 00:39:00");
                    bd = df.parse(year + "-10-24 00:00:00");
                    bdiff = bd.getTime() - d2.getTime();
                    bhour = (bdiff / (60 * 60 * 1000));
                    //bmin = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
                    //bs = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

                    diff = d2.getTime() - d1.getTime();
                    day = diff / (24 * 60 * 60 * 1000);
                    hour = (diff / (60 * 60 * 1000) - day * 24);
                    min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
                    s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (s < 10)
                    stringTe = day + "天\n" + hour + "小时" + min + "分0" + s + "秒";
                else
                    stringTe = day + "天\n" + hour + "小时" + min + "分" + s + "秒";
                spannableString = new SpannableString(stringTe);
                str_day = day + "";
                char[] ch = stringTe.toCharArray();
                spannableString.setSpan(new AbsoluteSizeSpan(100, true), 0, str_day.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                for (int i = str_day.length() + 1; i < stringTe.length(); i++) {
                    int asc = ch[i];
                    if (!(asc >= 48 && asc <= 58))
                        spannableString.setSpan(new AbsoluteSizeSpan(10, true), i + 0, i + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    else
                        spannableString.setSpan(new AbsoluteSizeSpan(20, true), i + 0, i + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                bday = bhour / 24.0;

                tvbd.setText(nf.format(bday) + "天");
                textView_main.setText(spannableString);
            }else if(msg.what ==2){
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                age = year - 1999;
                tvbb.setText(year + "-10-24");
                tvtb.setText("距离她的" + age + "岁的生日");
            }
        }
    };

}

