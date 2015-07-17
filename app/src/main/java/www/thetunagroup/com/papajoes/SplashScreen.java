package www.thetunagroup.com.papajoes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import www.thetunagroup.com.papajoes.util.LoginActivity;
import www.thetunagroup.com.papajoes.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class SplashScreen extends Activity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash_screen);

        imageView=(ImageView)findViewById(R.id.splash_imageView);

        //animation with start and end listener
        Animation zoomin = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        zoomin.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //start login activity once animation end
                SharedPreferences sharedPreferences= SplashScreen.this.getSharedPreferences("Papajoe", Context.MODE_PRIVATE);
                String user_id = sharedPreferences.getString("user_id","");
                if(user_id.trim().equals(""))
                {
                    Intent loginIntent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
                else
                {
                    Intent homeIntent = new Intent(SplashScreen.this, Home.class);
                    startActivity(homeIntent);
                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //applying animation to imageview
        imageView.setAnimation(zoomin);
        imageView.startAnimation(zoomin);
    }
}
