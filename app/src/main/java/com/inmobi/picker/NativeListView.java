package com.inmobi.picker;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNativeStrand;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NativeListView extends ListActivity {

    private final List<InMobiNativeStrand> mStrands = new ArrayList<>();

    private int[] mAdPositions = new int[]{2, 7, 13, 19};

    private static final int NUM_FEED_ITEMS = 20;

    private static final int NUM_CONTENT_FEED_VIEW_TYPES = 1;

    private static final int NUM_AD_FEED_VIEW_TYPES = 1;

    private static final int VIEW_TYPE_CONTENT_FEED = 0;

    private static final int VIEW_TYPE_INMOBI_STRAND = 1;

    private BaseAdapter mFeedAdapter;

    private ArrayList<FeedData.FeedItem> mFeedItems;

    private static final String TAG = "Dipal";

    private long slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nativelistview);

        String appid = this.getIntent().getExtras().getString("appid");
        slotId = Long.valueOf(appid).longValue();

        String adServer = this.getIntent().getExtras().getString("adServer");
        AdNetworkRequest.setAdServerUrl(adServer);

        mFeedItems = FeedData.generateFeedItems(NUM_FEED_ITEMS);
        mFeedAdapter = new FeedItemAdapter(this, mFeedItems);
        setListAdapter(mFeedAdapter);

        for (int position : mAdPositions) {
            final InMobiNativeStrand nativeStrand = new InMobiNativeStrand(this,
                    slotId, new StrandAdListener(position));
            mStrands.add(nativeStrand);
        }
        loadAds();
    }

    private void loadAds() {
        for (InMobiNativeStrand strand : mStrands) {
            strand.load();
        }
    }

    private void refreshAds() {
        clearAds();
        loadAds();
    }

    private void clearAds() {
        Iterator<FeedData.FeedItem> feedItemIterator = mFeedItems.iterator();
        while (feedItemIterator.hasNext()) {
            final FeedData.FeedItem feedItem = feedItemIterator.next();
            if (feedItem instanceof AdFeedItem) {
                feedItemIterator.remove();
            }
        }
        mFeedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        clearAds();
        for (InMobiNativeStrand strand : mStrands) {
            strand.destroy();
        }
        mStrands.clear();
        super.onDestroy();
    }

    private class FeedItemAdapter extends ArrayAdapter<FeedData.FeedItem> {
        private Context context;
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
            this.context = context;
            this.users = users;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getViewTypeCount() {
            return NUM_CONTENT_FEED_VIEW_TYPES + NUM_AD_FEED_VIEW_TYPES;
        }

        @Override
        public int getItemViewType(int position) {
            FeedData.FeedItem feedItem = mFeedItems.get(position);
            if (feedItem instanceof AdFeedItem) {
                return VIEW_TYPE_INMOBI_STRAND;
            }
            return VIEW_TYPE_CONTENT_FEED;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            final int itemViewType = getItemViewType(position);
            final FeedData.FeedItem feedItem = mFeedItems.get(position);

            if (itemViewType == VIEW_TYPE_CONTENT_FEED) {
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

                FeedData.FeedItem feed = users.get(position);
                ContentViewHolder holder = (ContentViewHolder) rowView.getTag();
                holder.title.setText(feed.getTitle());
                holder.subtitle.setText(feed.getSubtitle());
                holder.time_tt.setText(feed.getTimestamp());
                holder.description_tt.setText(feed.getDescription());

                Picasso.with(context)
                        .load(getResources().getIdentifier(feed.getThumbImage(), "drawable", getPackageName()))
                        .into(holder.thumb_image);

                Picasso.with(context)
                        .load(getResources().getIdentifier(feed.getBigImage(), "drawable", getPackageName()))
                        .into(holder.big_image);

                Picasso.with(context)
                        .load(R.drawable.linkedin_bottom)
                        .into(holder.bottom_img);

                return rowView;
            } else {
                //If ad feed, request InMobiStrand View. Note InMobiNativeStrand.getStrandView
                //returns null if ad is not loaded.
                //As we add AdFeed only on AdLoad success & clear the AdFeed before re-loading
                //ad, here we do not run into Null pointer Exception
                final InMobiNativeStrand nativeStrand = ((AdFeedItem) feedItem).mNativeStrand;
                rowView = nativeStrand.getStrandView(rowView, parent);
                return rowView;
            }
        }
    }

    private static class AdFeedItem extends FeedData.FeedItem {
        InMobiNativeStrand mNativeStrand;

        public AdFeedItem(InMobiNativeStrand nativeStrand) {
            super("", "", "", "", "", "");
            mNativeStrand = nativeStrand;
        }
    }

    private final class StrandAdListener implements InMobiNativeStrand.NativeStrandAdListener {

        private int mPosition;

        public StrandAdListener(int position) {
            mPosition = position;
        }

        @Override
        public void onAdLoadSucceeded(@NonNull InMobiNativeStrand inMobiNativeStrand) {
            Log.d(TAG, "Strand loaded at position " + mPosition);
            if (!mFeedItems.isEmpty()) {
                FeedData.FeedItem feedItem = mFeedItems.get(mPosition);
                if (feedItem instanceof AdFeedItem) {
                    mFeedItems.remove(mPosition);
                }
                mFeedItems.add(mPosition, new AdFeedItem(inMobiNativeStrand));
                mFeedAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onAdLoadFailed(@NonNull InMobiNativeStrand inMobiNativeStrand, @NonNull final InMobiAdRequestStatus inMobiAdRequestStatus) {
            Log.w(TAG, "Ad Load failed (" + inMobiAdRequestStatus.getMessage() + ")");
        }

        @Override
        public void onAdImpressed(@NonNull InMobiNativeStrand inMobiNativeStrand) {
            Log.i(TAG, "Impression recorded for strand at position:" + mPosition);
        }

        @Override
        public void onAdClicked(@NonNull InMobiNativeStrand inMobiNativeStrand) {
            Log.i(TAG, "Click recorded for ad at position:" + mPosition);
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
