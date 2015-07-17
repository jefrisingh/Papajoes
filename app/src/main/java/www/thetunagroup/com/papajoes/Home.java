package www.thetunagroup.com.papajoes;

/**
 * Created by DELL-PC on 7/15/2015.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.software.shell.fab.ActionButton;

import java.util.Timer;
import java.util.TimerTask;

import www.thetunagroup.com.papajoes.util.NonSwipeableViewPager;

/**
 * Created by Edwin on 15/02/2015.
 */
public class Home extends AppCompatActivity {

    // Declaring Your View and Variables

    Toolbar toolbar;
    NonSwipeableViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Incoming Orders","Pending Orders","Complete Orders"};
    int Numboftabs = 3;
    int _position = 0;

    Timer timer;
    MyTimerTask myTimerTask;
    int firstDelay=45000,repeteDelay=45000;
    ActionButton FAButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // Creating The Toolbar and setting it as the Toolbar for the activity

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (NonSwipeableViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(positionOffsetPixels == 0) {
                    _position = position;
                    new ChangeView().execute();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        timer = new Timer();
        myTimerTask = new MyTimerTask();
        timer.schedule(myTimerTask,firstDelay,repeteDelay);

        FAButton = (ActionButton) findViewById(R.id.fab_action_button);
        FAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timer.cancel();
                timer.purge();

                timer = new Timer();
                myTimerTask = new MyTimerTask();
                timer.schedule(myTimerTask,firstDelay,repeteDelay);
                //refress view
                ChangeView changeView = new ChangeView();
                changeView.execute(_position);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            //log out
            SharedPreferences sharedPreferences= Home.this.getSharedPreferences("Papajoe", Context.MODE_PRIVATE);
            sharedPreferences.edit().clear().commit();

            Intent splashIntent = new Intent(Home.this, SplashScreen.class);
            startActivity(splashIntent);
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }





    class ChangeView extends AsyncTask<Integer,String,String>
    {
        @Override
        protected String doInBackground(Integer... params) {

            return "some";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapter.refresh(_position,Home.this);
        }
    }


    class MyTimerTask extends TimerTask
    {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ChangeView changeView = new ChangeView();
                    changeView.execute(_position);
                }
            });
        }
    }



}