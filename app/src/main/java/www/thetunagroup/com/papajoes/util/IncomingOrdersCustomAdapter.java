package www.thetunagroup.com.papajoes.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import www.thetunagroup.com.papajoes.R;

/**
 * Created by DELL-PC on 7/11/2015.
 */
public class IncomingOrdersCustomAdapter extends ArrayAdapter<String> {

    Activity _activity;
    ArrayList<String>  _orderIDs;
    ArrayList<String> _details;
    public ImageLoader imageLoader;




    public IncomingOrdersCustomAdapter(Activity activity, ArrayList<String> orderIds, ArrayList<String> details) {
        super(activity, R.layout.incoming_order_item_list_item, orderIds);
        this._activity = activity;
        this._orderIDs = orderIds;
        this._details = details;
        imageLoader=new ImageLoader(_activity.getApplicationContext());


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            convertView = _activity.getLayoutInflater().inflate(R.layout.incoming_order_item_list_item,null);
        }

        TextView orderId = (TextView) convertView.findViewById(R.id.incoming_order_items_list_item_order_id);
        TextView details = (TextView) convertView.findViewById(R.id.incoming_order_items_list_item_details_textView);

        orderId.setText(_orderIDs.get(position));
        details.setText(_details.get(position));

        return convertView;
    }
}
