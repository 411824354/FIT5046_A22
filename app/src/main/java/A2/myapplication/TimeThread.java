package A2.myapplication;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**

 * Class:  upDate time
 *

 * describe:
 */
public class TimeThread extends Thread {
    public TextView tvDate;
    private int msgKey1 = 22;

    public TimeThread(TextView tvDate) {
        this.tvDate = tvDate;
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(1000);
                Message msg = new Message();
                msg.what = msgKey1;
                mHandler.sendMessage(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 22:

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String date = sdf.format(new Date());

                    tvDate.setText(date + getWeek());

                    break;

                default:
                    break;
            }
        }
    };

    /**
     * week date
     * @return
     */
    public static String getWeek() {
        Calendar cal = Calendar.getInstance();
        int i = cal.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                return " SUN";
            case 2:
                return " MON";
            case 3:
                return " TUE";
            case 4:
                return " WED";
            case 5:
                return " THU";
            case 6:
                return "FIR";
            case 7:
                return "SAT";
            default:
                return "";
        }
    }
}

