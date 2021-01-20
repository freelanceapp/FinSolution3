package com.semicode.findsolution.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.semicode.findsolution.R;
import com.semicode.findsolution.databinding.SubscriptionRowBinding;
import com.semicode.findsolution.models.SinglePakcgesModel;
import com.semicode.findsolution.ui.activity_packges.PackgesActivity;

import java.util.List;

import io.paperdb.Paper;

public class Packges_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SinglePakcgesModel> singlePakcgesModels;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private PackgesActivity packgesActivity;

    public Packges_Adapter(List<SinglePakcgesModel> singlePakcgesModels, Context context) {
        this.singlePakcgesModels = singlePakcgesModels;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", java.util.Locale.getDefault().getLanguage());
    }

    @androidx.annotation.NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {


        SubscriptionRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.subscription_row, parent, false);
        return new EventHolder(binding);


    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull RecyclerView.ViewHolder holder, int position) {

        EventHolder eventHolder = (EventHolder) holder;
        eventHolder.binding.setModel(singlePakcgesModels.get(position));
       eventHolder.binding.btsubscribe.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(context instanceof PackgesActivity){
                   packgesActivity=(PackgesActivity)context;
                   packgesActivity.choosepackge(singlePakcgesModels.get(position));
               }
           }
       });
/*
if(i==position){
    if(i!=0) {
        if (((EventHolder) holder).binding.expandLayout.isExpanded()) {
            ((EventHolder) holder).binding.tvTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ((EventHolder) holder).binding.recView.setLayoutParams(new FrameLayout.LayoutParams(0, 0));
            ((EventHolder) holder).binding.expandLayout.collapse(true);
            ((EventHolder) holder).binding.expandLayout.setVisibility(View.GONE);



        }
        else {

          //  ((EventHolder) holder).binding.tvTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ((EventHolder) holder).binding.recView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
            ((EventHolder) holder).binding.expandLayout.setVisibility(View.VISIBLE);

           ((EventHolder) holder).binding.expandLayout.expand(true);
        }
    }
    else {
        eventHolder.binding.tvTitle.setBackground(activity.getResources().getDrawable(R.drawable.linear_bg_green));

        ((EventHolder) holder).binding.tvTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ((EventHolder) holder).binding.recView.setLayoutParams(new FrameLayout.LayoutParams(0, 0));

    }
}
if(i!=position) {
    eventHolder.binding.tvTitle.setBackground(activity.getResources().getDrawable(R.drawable.linear_bg_white));
    ((EventHolder) holder).binding.tvTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

    ((EventHolder) holder).binding.recView.setLayoutParams(new FrameLayout.LayoutParams(0, 0));
    ((EventHolder) holder).binding.expandLayout.collapse(true);


}*/

    }

    @Override
    public int getItemCount() {
        return singlePakcgesModels.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        public SubscriptionRowBinding binding;

        public EventHolder(@androidx.annotation.NonNull SubscriptionRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
