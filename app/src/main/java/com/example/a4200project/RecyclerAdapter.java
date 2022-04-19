package com.example.a4200project;

import static com.example.a4200project.R.layout.item_list_view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    private final Context context;
    private final ArrayList<Item> itemsArrayList;

    public RecyclerAdapter(Context context, ArrayList<Item> objects){

        this.context = context;
        this.itemsArrayList = objects;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(item_list_view, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item currentItem = itemsArrayList.get(position);
        holder.itemName.setText(String.valueOf(currentItem.getItemName()));
        holder.date.setText("Purchase Date: " + currentItem.getPurchaseDate());
        holder.location.setText("Storage Location: " + currentItem.getStorageLocation());

        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(new Locale("en","CA"));
        decimalFormat.applyPattern("0.00");
        String format = decimalFormat.format(currentItem.getPurchasePrice());
        holder.price.setText("$" + format);

       holder.mainLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent update = new Intent(context, UpdateItem.class);
               int itemId = currentItem.getId();
               update.putExtra("itemId", itemId);
               context.startActivity(update);

           }
       });
    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public TextView date;
        public TextView location;
        public TextView price;
        ConstraintLayout mainLayout;
        CardView itemCard;
        public ItemViewHolder(@NonNull View view) {
            super(view);
            itemCard = view.findViewById(R.id.itemCard);
            itemName = view.findViewById(R.id.layout_itemName);
            date = view.findViewById(R.id.layout_purchase_date);
            location = view.findViewById(R.id.layout_location);
            price =  view.findViewById(R.id.price);
            mainLayout = view.findViewById(R.id.mainLayout);
        }
    }
}
