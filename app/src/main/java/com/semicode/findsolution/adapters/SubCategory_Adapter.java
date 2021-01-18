
package com.semicode.findsolution.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.semicode.findsolution.R;
import com.semicode.findsolution.databinding.SubCategoryRowBinding;
import com.semicode.findsolution.models.SingleSubCategoryModel;
import com.semicode.findsolution.ui.activity_department_detials.DepartmentDetialsActivity;

import java.util.List;

import io.paperdb.Paper;

public class SubCategory_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SingleSubCategoryModel> singleSubCategoryModels;
    private Context context;
    private LayoutInflater inflater;
    private String lang;
    private DepartmentDetialsActivity departmentDetialsActivity;
    private int i=0;

    public SubCategory_Adapter(List<SingleSubCategoryModel> singleSubCategoryModels, Context context) {
        this.singleSubCategoryModels = singleSubCategoryModels;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Paper.init(context);
        lang = Paper.book().read("lang", java.util.Locale.getDefault().getLanguage());
        departmentDetialsActivity =(DepartmentDetialsActivity) context;
    }

    @androidx.annotation.NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {


        SubCategoryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.sub_category_row, parent, false);
        return new EventHolder(binding);


    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull RecyclerView.ViewHolder holder, int position) {

        EventHolder eventHolder = (EventHolder) holder;
        eventHolder.binding.setModel(singleSubCategoryModels.get(position));

        eventHolder.itemView.setOnClickListener(new android.view.View.OnClickListener() {
    @Override
    public void onClick(android.view.View v) {
        i=position;
       notifyDataSetChanged();
    }
});

if(i==position){
   eventHolder.binding.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent));
eventHolder.binding.tvtitle.setTextColor(context.getResources().getColor(R.color.white));
 ;
    departmentDetialsActivity.showdata(singleSubCategoryModels.get(eventHolder.getLayoutPosition()));
    }
    else {
    eventHolder.binding.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
    eventHolder.binding.tvtitle.setTextColor(context.getResources().getColor(R.color.gray9));


}
}




    @Override
    public int getItemCount() {
        return singleSubCategoryModels.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        public SubCategoryRowBinding binding;

        public EventHolder(@androidx.annotation.NonNull SubCategoryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
