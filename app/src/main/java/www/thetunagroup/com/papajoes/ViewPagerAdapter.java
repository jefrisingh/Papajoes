package www.thetunagroup.com.papajoes;

/**
 * Created by DELL-PC on 7/15/2015.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.TextView;

import www.thetunagroup.com.papajoes.util.Progress;

/**
 * Created by Edwin on 15/02/2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    int i=0;
    Activity _activity;

    SwipeListViewTab swipeListViewTabIncoming;
    SwipeListViewTab swipeListViewTabPending;
    SwipeListViewTab swipeListViewTabCompleted;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                swipeListViewTabIncoming = new SwipeListViewTab();
                return swipeListViewTabIncoming;
            case 1:
                swipeListViewTabPending = new SwipeListViewTab();
                return swipeListViewTabPending;
            case 2:
                swipeListViewTabCompleted = new SwipeListViewTab();
                return swipeListViewTabCompleted;

            default:
                return null;
        }
    }
    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }


    public void refresh(int position,Activity activity)
    {
        this._activity = activity;
        new ChangeView().execute(position);
    }



    class ChangeView extends AsyncTask<Integer,String,Integer>
    {
        View view;
        Progress progress;
        int _position;
        @Override
        protected Integer doInBackground(Integer... params) {
            _position = params[0];
            switch (_position)
            {
                case 0:
                    // for incoming Order
                    IncomingOrders incomingOrders;
                    incomingOrders = new IncomingOrders(_activity);
                    view = incomingOrders.getIncommingOrders();
                    return  0;
                case 1:
                    // for pending Order
                    PendingOrders pendingOrders;
                    pendingOrders = new PendingOrders(_activity);
                    view = pendingOrders.getPendingOrders();

                    return  1;
                case 2:
                    // for completed Order
                    CompletedOrders completedOrders;
                    completedOrders = new CompletedOrders(_activity);
                    view = completedOrders.getCompletedOrders();
                    return  2;
                case 3:
                    // for logout
                    return  3;
                default:
                    return -1;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new Progress(_activity);
            progress.show();
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            switch (s)
            {
                case 0:
                    swipeListViewTabIncoming.container.removeAllViews();
                    swipeListViewTabIncoming.container.addView(view);
                    break;
                case 1:
                    swipeListViewTabPending.container.removeAllViews();
                    swipeListViewTabPending.container.addView(view);
                    break;
                case 2:
                    swipeListViewTabCompleted.container.removeAllViews();
                    swipeListViewTabCompleted.container.addView(view);
                    break;
                default:
                    break;
            }

            progress.dismiss();
        }
    }


}