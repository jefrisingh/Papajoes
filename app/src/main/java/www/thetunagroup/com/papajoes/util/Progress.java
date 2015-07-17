package www.thetunagroup.com.papajoes.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import www.thetunagroup.com.papajoes.R;


/**
 * Created by JINS on 3/11/2015.
 */
public class Progress extends Dialog {
    public Progress(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view=getLayoutInflater().inflate(R.layout.progress,null);
        WebView webView=(WebView)view.findViewById(R.id.progress_webView);
        webView.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlData = "<html>\n" +
                "<body style=\"background:transparent;\">" +
                "<center>" +
                "<img style=\"background:transparent;\" src=\"file:///android_asset/ring.gif\">" +
                "</center>" +
                "</body>" +
                "</html>";
        webView.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "utf-8", null);
        webView.setBackgroundColor(Color.TRANSPARENT);
        setContentView(view);
        setCancelable(false);
    }

    public Progress(Context context,String message,String videoLoader) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view=getLayoutInflater().inflate(R.layout.progress,null);
        WebView webView=(WebView)view.findViewById(R.id.progress_webView);
        webView.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlData = "<html>\n" +
                "<body style=\"background:transparent;\">" +
                "<center>" +
                "<img style=\"background:transparent;\" src=\"file:///android_asset/videoloader.GIF\">" +
                "</center>" +
                "</body>" +
                "</html>";
        webView.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "utf-8", null);
        webView.setBackgroundColor(Color.TRANSPARENT);
        setContentView(view);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }

    public Progress(Context context,String message,Boolean cancellable) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view=getLayoutInflater().inflate(R.layout.progress,null);
        TextView messageText=(TextView)view.findViewById(R.id.progress_textview);
        messageText.setText(message);
        WebView webView=(WebView)view.findViewById(R.id.progress_webView);
        webView.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String htmlData = "<html>\n" +
                "<body style=\"background:transparent;\">" +
                "<center>" +
                "<img style=\"background:transparent;\" src=\"file:///android_asset/ring.gif\">" +
                "</center>" +
                "</body>" +
                "</html>";
        webView.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "utf-8", null);
        webView.setBackgroundColor(Color.TRANSPARENT);
        setContentView(view);
        setCancelable(cancellable);
    }

}
