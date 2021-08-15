package com.sudipseucse.recyclerviewwithlivedata.viewmodel;

import androidx.lifecycle.ViewModel;

import com.sudipseucse.recyclerviewwithlivedata.model.HeaderData;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MainViewModel extends ViewModel {
    LinkedHashMap<Integer,Integer> hashMapSKUCountData = new LinkedHashMap<>();
    LinkedHashMap<Integer,Integer> hashMapTotalPriceData = new LinkedHashMap<>();
    public int totalSKUCount = 0;
    public int totalPrice = 0;

    public void doCalculation(HeaderData value){
        totalSKUCount = 0;
        int userValue = value.pickerValue;
        int position = value.position;
        int price = value.itemPrice;
        hashMapSKUCountData.put(position , userValue);
        hashMapTotalPriceData.put(position , price);
        Set<Map.Entry<Integer,Integer>> skuCountDataSet = hashMapSKUCountData.entrySet();
        Set<Map.Entry<Integer,Integer>> totalPriceDataSet = hashMapTotalPriceData.entrySet();
        for (Map.Entry<Integer,Integer> itemCount: skuCountDataSet)
        {
            totalSKUCount = (totalSKUCount +itemCount.getValue());
            totalPrice = 0;
            for (Map.Entry<Integer,Integer> itemPrice: totalPriceDataSet)
            {
                totalPrice = (totalPrice +itemPrice.getValue());
            }
        }
    }
}
