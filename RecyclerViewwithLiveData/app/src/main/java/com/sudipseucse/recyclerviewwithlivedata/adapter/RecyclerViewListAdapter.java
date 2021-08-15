package com.sudipseucse.recyclerviewwithlivedata.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.sudipseucse.recyclerviewwithlivedata.R;
import com.sudipseucse.recyclerviewwithlivedata.model.HeaderData;
import com.sudipseucse.recyclerviewwithlivedata.model.SKU;
import com.travijuu.numberpicker.library.NumberPicker;


import java.util.List;


public class RecyclerViewListAdapter extends RecyclerView.Adapter<RecyclerViewListAdapter.RecyclerViewHolder> {

    private List<SKU> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public RecyclerViewListAdapter(List<SKU> data){
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            context = parent.getContext();
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        View itemView = layoutInflater.inflate(R.layout.custom_challan_item_layout, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        SKU currentSKU = data.get(position);

        holder.tvSkuImage.setImageResource(R.drawable.sku_image);
        holder.tvSkuName.setText(currentSKU.name);
        holder.tvSkuPrice.setText(String.valueOf(currentSKU.price));
        holder.tvStock.setText(String.valueOf(currentSKU.stock));
        holder.tvChallanQty.setText(String.valueOf(currentSKU.quantity));
        int requiredStockQtyByUser = Integer.parseInt(holder.editTextNumberPicker.getText().toString());

        holder.numberPicker.setValueChangedListener((value, action) -> {
            if (value < 0) {
                value = 0;
            }
            if(value>currentSKU.stock){
                Toast.makeText(context, "No stock available!",
                        Toast.LENGTH_LONG).show();
                value = currentSKU.stock;
            }
            int remainingStock = (currentSKU.stock - value);
            if(remainingStock<0){
                remainingStock = 0;
            }
            holder.numberPicker.setValue(value);
            holder.tvChallanQty.setText(String.valueOf(value));
            holder.tvStock.setText(String.valueOf(remainingStock));

            updateValue(value,currentSKU.price, position);
        });

        holder.editTextNumberPicker.setOnClickListener(view -> {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View dialogView = inflater.inflate(R.layout.custom_user_input_picker_dialog, null);
            dialogBuilder.setView(dialogView);

            EditText editText = dialogView.findViewById(R.id.editTextNumberPicker);
            Button buttonConfirm = dialogView.findViewById(R.id.buttonConfirm);
            Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();
            buttonConfirm.setOnClickListener(view12 -> {

                if(editText.getText().toString().isEmpty()){
                    Toast.makeText(context, "Please add quantity!", Toast.LENGTH_LONG).show();
                }
                else{
                    int stock = Integer.parseInt(editText.getText().toString());
                    if (stock < 0) {
                        stock = 0;
                    }
                    if(stock>currentSKU.stock){
                        Toast.makeText(context, "No stock available!",
                                Toast.LENGTH_LONG).show();
                        stock = 0;
                        editText.setText("0");
                    }
                    int remainingStock = (currentSKU.stock - stock);
                    if(remainingStock<0){
                        remainingStock = 0;
                    }
                    holder.numberPicker.setValue(Integer.parseInt(editText.getText().toString()));
                    holder.tvChallanQty.setText(String.valueOf(stock));
                    holder.tvStock.setText(String.valueOf(remainingStock));
                    updateValue(stock,currentSKU.price, position);
                }
                alertDialog.cancel();
            });

            buttonCancel.setOnClickListener(view1 -> alertDialog.cancel());
        });

    }

    private MutableLiveData<HeaderData> numberPickerLiveData = new MutableLiveData<>();

    public LiveData<HeaderData> getNumberPickerLiveData(){
        return numberPickerLiveData;
    }

    public void updateValue(int value,int itemPrice, int position) {
        int totalPrice = value*itemPrice;
        HeaderData data = new HeaderData(position,value,totalPrice);
        numberPickerLiveData.setValue(data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        public final ImageView tvSkuImage;
        public final TextView tvSkuName;
        public final TextView tvSkuPrice;
        public final TextView tvStock;
        public final TextView tvChallanQty;
        public final NumberPicker numberPicker;
        public final EditText editTextNumberPicker;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSkuImage = itemView.findViewById(R.id.imageViewSkuImage);
            numberPicker = itemView.findViewById(R.id.numberPicker);
            tvSkuName = itemView.findViewById(R.id.textViewSkuName);
            tvSkuPrice = itemView.findViewById(R.id.textViewSkuPrice);
            tvStock = itemView.findViewById(R.id.textView_Stock);
            tvChallanQty = itemView.findViewById(R.id.textViewChallanQuantity);
            editTextNumberPicker = itemView.findViewById(R.id.editTextDisplay);
        }
    }
}
