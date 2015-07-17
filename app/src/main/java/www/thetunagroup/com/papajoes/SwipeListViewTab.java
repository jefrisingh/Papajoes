package www.thetunagroup.com.papajoes;

/**
 * Created by DELL-PC on 7/15/2015.
 */
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenuListView;

/**
 * Created by Edwin on 15/02/2015.
 */
public class SwipeListViewTab extends Fragment {
    //SwipeMenuListView listView;
    FrameLayout container;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View v = inflater.inflate(R.layout.swipelistview_layout, container, false);
        //listView = (SwipeMenuListView) v.findViewById(R.id.listview);
        View v = inflater.inflate(R.layout.swipelistview_layout, container, false);
        this.container = (FrameLayout) v.findViewById(R.id.container);
        return v;
    }
}
