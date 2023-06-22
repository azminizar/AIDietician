package com.example.aidietician;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemsRVAdapter extends RecyclerView.Adapter<ItemsRVAdapter.ItemsViewHolder>
   implements View.OnClickListener {

    private List<Items> mItems;
    public ItemsRVAdapter(){
        mItems = buildItemsLibrary();
    }

    public ItemsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.meal_list,viewGroup,false);
        return new ItemsViewHolder(itemView);
    }

    public void onBindViewHolder(final ItemsViewHolder itemsViewHolder , final int i) {
        itemsViewHolder.itemName.setText(mItems.get(i).itemName);
        itemsViewHolder.items = mItems.get(i);

        itemsViewHolder.itemView.setTag(itemsViewHolder);
        itemsViewHolder.itemView.setOnClickListener(this);
    }

    public int getItemCount() {
        return mItems.size();
    }

    public void onClick(View view){
        if (view.getTag() instanceof ItemsViewHolder){
            ItemsViewHolder ivh = (ItemsViewHolder) view.getTag();
            Toast.makeText(view.getContext(), ivh.items.itemName,Toast.LENGTH_SHORT).show();
        }
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder{
        public final TextView itemName;
        public final View itemView;
        public Items items;

        public ItemsViewHolder(View itemView){
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.itemName);
            this.itemView = itemView;
        }
    }

    private List<Items> buildItemsLibrary() {
            List<Items> items = new ArrayList<>();
            items.add(new Items("Rice"));
            items.add(new Items("Chapati"));
            items.add(new Items("Oats"));
            items.add(new Items("Salad"));
            return items;
        }
}
