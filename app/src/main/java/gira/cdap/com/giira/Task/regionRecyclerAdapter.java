package gira.cdap.com.giira.Task;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import gira.cdap.com.giira.R;
import gira.cdap.com.giira.Search_results_filter;


public class regionRecyclerAdapter extends RecyclerView.Adapter<FeedListRowHolder> {
    private List<region> feedItemList;
    private Context mContext;

    String regionID,regionname;

//    Search_results_filter actionbar;

    public regionRecyclerAdapter(Context context, List<region> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public FeedListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_recycler_region, null);

        FeedListRowHolder viewHolder = new FeedListRowHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final FeedListRowHolder customViewHolder, int i) {

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedListRowHolder holder = (FeedListRowHolder) view.getTag();
                int position = holder.getPosition();

                region feedItem = feedItemList.get(position);
                Toast.makeText(mContext, feedItem.getRegiontype(), Toast.LENGTH_SHORT).show();

                regionID=feedItem.getRegionID().toString();
                regionname=feedItem.getRegiontype().toString();

                Intent intent;
                intent = new Intent(mContext,Search_results_filter.class);

                intent.putExtra("id", regionID);
                intent.putExtra("regionType", regionname);

                mContext.startActivity(intent);

//                actionbar = new Search_results_filter();
//                actionbar.SetActionbarText(categoryname);


            }
        };

//        final TextView checkbox=customViewHolder.checkbox;
//
//
//        View.OnClickListener checkboxListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if ((int)checkbox.getTag() == R.drawable.redshape) {
//                    checkbox.setCompoundDrawablesWithIntrinsicBounds(
//                            R.drawable.blackshape, 0, 0, 0);
//                    checkbox.setTag(R.drawable.blackshape);
//
//                } else {
//                    customViewHolder.checkbox.setCompoundDrawablesWithIntrinsicBounds(
//                            R.drawable.redshape, 0, 0, 0);
//                    customViewHolder.checkbox.setTag(R.drawable.redshape);
//                }
//            }
//            };

        customViewHolder.title.setOnClickListener(clickListener);
        customViewHolder.thumbnail.setOnClickListener(clickListener);
        // customViewHolder.checkbox.setOnClickListener(checkboxListener);

        customViewHolder.title.setTag(customViewHolder);
        customViewHolder.thumbnail.setTag(customViewHolder);
        //  customViewHolder.checkbox.setTag(R.drawable.redshape);

        region feedItem = feedItemList.get(i);

        //Download image using picasso library
        Picasso.with(mContext).load(feedItem.getRegionimage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(customViewHolder.thumbnail);

        //Setting text view title
        customViewHolder.title.setText(Html.fromHtml(feedItem.getRegiontype()));

//        AccID=Html.fromHtml(feedItem.getAccid()).toString();



    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }
}