import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tim on 06.04.2017.
 */
public class CurrentDateImager extends Thread {
    static Date time = new Date();

    CurrentDateImager(){
        this.start();
    }

    @Override
    public void run() {

        while(true) {
            time = new Date();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void getTime(JLabel label){
        DateFormat format = new SimpleDateFormat("dd.MM.YY HH.mm.ss");
        //while(true) {
            String timeString = format.format(time);
            label.setText(timeString);
        //}
    }

    public static void main(String[] args) {

    }

}
