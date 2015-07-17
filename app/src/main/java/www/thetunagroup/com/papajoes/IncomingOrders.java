package www.thetunagroup.com.papajoes;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import www.thetunagroup.com.papajoes.util.IncomingOrdersCustomAdapter;
import www.thetunagroup.com.papajoes.util.IncomingOrdersDetailsCustomAdapter;
import www.thetunagroup.com.papajoes.util.Interaction;
import www.thetunagroup.com.papajoes.util.Progress;

/**
 * Created by DELL-PC on 7/6/2015.
 */
public class IncomingOrders {

    Activity _activity;
    SwipeMenuListView _listView;
    TextView _notify;
    ArrayList<String> _list;
    ArrayList<String> _order_id;
    String user_id = "";

    SharedPreferences sharedPreferences;
    public IncomingOrders(Activity activity) {
        this._activity = activity;
        this._listView= new SwipeMenuListView(_activity);
        this._listView.setDividerHeight(4);
        this._notify = new TextView(_activity);
        this._list = new ArrayList<String>();
        this._order_id = new ArrayList<String>();

        sharedPreferences= _activity.getSharedPreferences("Papajoe", Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id","");

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                //Start order, finished order,report a problem
                // create "open" item
                SwipeMenuItem StartItem = new SwipeMenuItem(_activity.getApplicationContext());
                StartItem.setBackground(new ColorDrawable(Color.rgb(0x00, 0x00,0xFF)));
                StartItem.setWidth(dp2px(90));
                StartItem.setTitle("Start");
                StartItem.setTitleSize(18);
                StartItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(StartItem);


                SwipeMenuItem finisItem = new SwipeMenuItem(_activity.getApplicationContext());
                finisItem.setBackground(new ColorDrawable(Color.rgb(0x00, 0xFF,0x00)));
                finisItem.setWidth(dp2px(90));
                finisItem.setTitle("Finish");
                finisItem.setTitleSize(18);
                finisItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(finisItem);


                SwipeMenuItem reportItem = new SwipeMenuItem( _activity.getApplicationContext());
                reportItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0x00,0x00)));
                reportItem.setWidth(dp2px(90));
                reportItem.setTitle("Report");
                reportItem.setTitleSize(18);
                reportItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(reportItem);
            }
        };
        // set creator
        this._listView.setMenuCreator(creator);

        // step 2. listener item click event
        this._listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // start
                        new startOrder().execute(_order_id.get(position));
                        break;
                    case 1:
                        // finish
                        break;

                    case 2:
                        // report problem
                        break;
                }
                return false;
            }
        });

        // set SwipeListener
        this._listView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // other setting
//		listView.setCloseInterpolator(new BounceInterpolator());

        // test item long click
        this._listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(_activity, position + " long click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // test item click
        this._listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new orderDetails().execute(_order_id.get(position));
            }
        });
    }




    public View getIncommingOrders() {
        if(!user_id.equals("")) {
            try {
                _list.clear();
                _order_id.clear();

                MultipartEntity entity = new MultipartEntity();
                entity.addPart("user_id", new StringBody(user_id));

                String response = Interaction.postGetData(AppUrls.incommingOrders, entity);
                if (response.contains("success")) {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("orders");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        /*
                            {
                                "order_id": "1",
                                "bill_id": "1",
                                "user_device_id": "1",
                                "type": "1",
                                "source": "1",
                                "first_name": "Jophi",
                                "last_name": "Joe",
                                "address1": "addddd",
                                "address2": "addd",
                                "city": "Birmingham",
                                "state": "MI",
                                "zip": "48009",
                                "mobile": "999999999"
                            }
                         */

                        JSONObject current = jsonArray.getJSONObject(i);
                        String details ="\t\tNAME : " + current.getString("first_name")+" "+current.getString("last_name") + "\n\t\t" +current.getString("mobile")+"\n";
                        _list.add(details);
                        _order_id.add(current.getString("order_id"));
                    }
                }


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //ArrayAdapter<String> simpleAdapter = new ArrayAdapter<String>(_activity, android.R.layout.simple_list_item_1, _list);
            IncomingOrdersCustomAdapter simpleAdapter = new IncomingOrdersCustomAdapter(_activity,_order_id,_list);
            _listView.setAdapter(simpleAdapter);
            return _listView;
        }
        else
        {
            _notify.setText("Problem with loading , please logout then login");
            return _notify;
        }
    }

    class startOrder extends AsyncTask<String,String,Boolean>
    {
        Progress progress;
        String serverResponse = "";
        String serverMessage = "";
        @Override
        protected Boolean doInBackground(String... params) {

            String orderId = params[0];
            if(!user_id.equals("") && !orderId.equals("")) {
                try {

                    MultipartEntity entity = new MultipartEntity();
                    entity.addPart("user_id", new StringBody(user_id));
                    entity.addPart("order_id", new StringBody(orderId));
                    entity.addPart("type",new StringBody("2"));

                    String response = Interaction.postGetData(AppUrls.changeOrderStatus, entity);
                    //{"response":"success","timer":"240","message":"Processing started for this order."}
                    JSONObject jsonObject = new JSONObject(response);
                    serverResponse = jsonObject.getString("response");
                    serverMessage = jsonObject.getString("message");
                    if(serverResponse.contains(""))
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                } catch (Exception e) {

                }
            }
            else
            {
                //user id not found login again
            }

            return false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new Progress(_activity);
            progress.show();
        }

        @Override
        protected void onPostExecute(Boolean s) {
            super.onPostExecute(s);
            if (s)
            {
                Toast.makeText(_activity,serverMessage,Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(_activity,"Something went wrong, "+serverMessage,Toast.LENGTH_LONG).show();
            }
            progress.dismiss();
        }
    }

    class orderDetails extends AsyncTask<String,String,String>
    {
        Progress progress;
        IncomingOrdersDetailsCustomAdapter incomingOrdersCustomAdapter = null;
        @Override
        protected String doInBackground(String... params) {
            String orderId = params[0];

            if(!user_id.equals("") && !orderId.equals("")) {
                try {
                    //user_id
                    //order_id

                    MultipartEntity entity = new MultipartEntity();
                    entity.addPart("user_id", new StringBody(user_id));
                    entity.addPart("order_id", new StringBody(orderId));

                    String response = Interaction.postGetData(AppUrls.getOrdersItems, entity);
                    JSONObject jsonObject = new JSONObject(response);
                    String serverResponce = jsonObject.getString("response");
                    if(serverResponce.contains("success"))
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("order_items");
                        int jsonArraySize = jsonArray.length();
                        if(jsonArraySize>0)
                        {
                            ArrayList<JSONObject> resultSets = new ArrayList<JSONObject>();
                            ArrayList<String> objects = new ArrayList<String>();
                            for(int i=0;i<jsonArraySize;i++)
                            {
                                resultSets.add(jsonArray.getJSONObject(i));
                                objects.add(""+i);
                            }

                            incomingOrdersCustomAdapter = new IncomingOrdersDetailsCustomAdapter(_activity,objects,resultSets);

                        }

                    }
                    else
                    {
                        //error or failed
                    }

                } catch (Exception e) {

                }
            }

            return "some";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new Progress(_activity);
            progress.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(incomingOrdersCustomAdapter != null)
            {
                ListView itemList = new ListView(_activity);
                itemList.setAdapter(incomingOrdersCustomAdapter);

                AlertDialog alertDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(_activity);
                builder.setTitle("Order Details");
                builder.setView(itemList);
                builder.setPositiveButton("CLOSE",null);
                alertDialog= builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
            else
            {
                //something went wrong
                Toast.makeText(_activity,"Something went wrong",Toast.LENGTH_LONG).show();
            }
            progress.dismiss();
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                _activity.getResources().getDisplayMetrics());
    }
}
