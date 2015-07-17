package www.thetunagroup.com.papajoes.util;

/**
 * Created by DELL-PC on 4/13/2015.
 */
import android.os.Build;
import android.os.Environment;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CustomExceptionHandler implements UncaughtExceptionHandler {

        private UncaughtExceptionHandler defaultUEH;
        private String finalPath;

        /*
         * if any of the parameters is null, the respective functionality
         * will not be used
         */
        public CustomExceptionHandler(String localPath) {

            /**
             * creating file For Log
             */
            File root = new File(Environment.getExternalStorageDirectory(), "TunaGroup");
            if (!root.exists()) {
                root.mkdirs();
            }
            File logFile=new File(root,"Papajoe-AppCrash.txt");
            finalPath=logFile.getAbsolutePath();
            this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        }

        public void uncaughtException(Thread t, Throwable e) {
            final Writer result = new StringWriter();
            final PrintWriter printWriter = new PrintWriter(result);
            e.printStackTrace(printWriter);
            String stacktrace = result.toString();
            printWriter.close();

            if (finalPath != null) {
                //writeToFile(stacktrace);
            }
            sendToServer(stacktrace);
            defaultUEH.uncaughtException(t, e);
        }

        private void writeToFile(String sBody) {
            try
            {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());

                FileWriter fw = new FileWriter(finalPath,true); //the true will append the new data
                fw.write(formattedDate+"  1.0.4"+"\n"+sBody+"\n\n");//appends the string to the file
                fw.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        private void sendToServer(String stacktrace) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = df.format(c.getTime());

            String model = Build.MODEL;
            String androidOS = Build.VERSION.RELEASE;
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://thetunagroup.com/app_bugs/api.php");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("app", "Papajoe's"));
            nvps.add(new BasicNameValuePair("crash", formattedDate+" <br> "+model+" <br> "+androidOS+" <br> <br> "+stacktrace.replace("\n","<br>")));
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
                httpClient.execute(httpPost);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
