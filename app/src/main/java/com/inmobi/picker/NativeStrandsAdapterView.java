package com.inmobi.picker;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.inmobi.ads.AdNetworkRequest;
import com.inmobi.ads.InMobiStrandAdapter;
import com.inmobi.ads.InMobiStrandAdapter.NativeStrandAdListener;
import com.inmobi.ads.InMobiStrandPositioning;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dipal.patel on 4/13/16.
 */
public class NativeStrandsAdapterView extends ListActivity {
    private InMobiStrandAdapter mStrandAdapter;

    private ArrayList<FeedData.FeedItem> mFeedItems;

    private static final String TAG = "Dipal";

    private long slotId;

    private final NativeStrandAdListener mAdListener = new NativeStrandAdListener() {
        @Override
        public void onAdLoadSucceeded(int i) {
            Log.d(TAG, "Strand loaded at position " + i);
        }

        @Override
        public void onAdRemoved(int i) {
            Log.d(TAG, "Strand removed at position: " + i);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nativelistview);

        String appid = this.getIntent().getExtras().getString("appid");
        slotId = Long.valueOf(appid).longValue();

        String adServer = this.getIntent().getExtras().getString("adServer");
        AdNetworkRequest.setAdServerUrl(adServer);

        mFeedItems = FeedData.generateFeedItems(50);
        //Load the ads after activity has been created.
        prepareToLoadAds();
    }

    public void prepareToLoadAds() {
        final BaseAdapter originalAdapter = new FeedItemAdapter(this, mFeedItems);
        final InMobiStrandPositioning.InMobiClientPositioning clientPositioning =
                new InMobiStrandPositioning.InMobiClientPositioning();
        clientPositioning.addFixedPosition(2)
                .addFixedPosition(4)
                .addFixedPosition(8)
                .enableRepeatingPositions(5);

        if (mStrandAdapter != null) {
            mStrandAdapter.destroy();
        }

        mStrandAdapter = new InMobiStrandAdapter(this,
                slotId, originalAdapter, clientPositioning);
        mStrandAdapter.setListener(mAdListener);
        originalAdapter.notifyDataSetChanged();
        mStrandAdapter.load();
        setListAdapter(mStrandAdapter);
    }

    @Override
    public void onDestroy() {
        if (mStrandAdapter != null) {
            mStrandAdapter.destroy();
        }
        super.onDestroy();
    }

    private void refreshAds() {
        if (null == mStrandAdapter || null == getListView()) {
            Log.e(TAG, "The list-view or the adapter cannot be null!");
        } else {
            mStrandAdapter.refreshAds(getListView());
        }
    }

    public class FeedItemAdapter extends ArrayAdapter<FeedData.FeedItem> {
        private ArrayList<FeedData.FeedItem> users;
        private LayoutInflater layoutInflater;

        class ContentViewHolder {
            TextView title;
            TextView subtitle;
            TextView time_tt;
            TextView description_tt;
            ImageView thumb_image;
            ImageView big_image;
            ImageView bottom_img;
        }

        public FeedItemAdapter(Context context, ArrayList<FeedData.FeedItem> users) {
            super(context, R.layout.listitem, R.id.title, users);
            this.users = users;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            if (null == rowView) {
                rowView = layoutInflater.inflate(R.layout.listitem, parent, false);
                ContentViewHolder viewHolder = new ContentViewHolder();
                viewHolder.title = (TextView) rowView.findViewById(R.id.title);
                viewHolder.subtitle = (TextView) rowView.findViewById(R.id.subtitle);
                viewHolder.time_tt = (TextView) rowView.findViewById(R.id.time_tt);
                viewHolder.description_tt = (TextView) rowView.findViewById(R.id.description_tt);
                viewHolder.thumb_image = (ImageView) rowView.findViewById(R.id.thumb_image);
                viewHolder.big_image = (ImageView) rowView.findViewById(R.id.big_image);
                viewHolder.bottom_img = (ImageView) rowView.findViewById(R.id.bottom_img);

                rowView.setTag(viewHolder);
            }

            FeedData.FeedItem user = users.get(position);
            ContentViewHolder holder = (ContentViewHolder) rowView.getTag();
            holder.title.setText(user.getTitle());
            holder.subtitle.setText(user.getSubtitle());
            holder.time_tt.setText(user.getTimestamp());
            holder.description_tt.setText(user.getDescription());

            Picasso.with(getContext()).load(getResources().getIdentifier(user.getThumbImage(), "drawable", getPackageName()))
                    .into(holder.thumb_image);

            Picasso.with(getContext()).load(getResources().getIdentifier(user.getBigImage(), "drawable", getPackageName()))
                    .into(holder.big_image);

            Picasso.with(getContext()).load(R.drawable.linkedin_bottom)
                    .into(holder.bottom_img);

            return rowView;
        }
    }

    private static boolean canListViewScrollUp(ListView listView) {
        if (android.os.Build.VERSION.SDK_INT >= 14) {
            return ViewCompat.canScrollVertically(listView, -1);
        } else {
            return listView.getChildCount() > 0 &&
                    (listView.getFirstVisiblePosition() > 0
                            || listView.getChildAt(0).getTop() < listView.getPaddingTop());
        }
    }
}
