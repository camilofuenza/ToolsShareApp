package com.example.myapplication.Adapters;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.Model.Costumer;
import com.example.myapplication.R;

import java.util.List;

public class CostumerAdapter extends BaseAdapter {
    private Context myContext;
    private List<Costumer> costumerList;

    public CostumerAdapter(Context myContext, List<Costumer> costumerList) {
        this.myContext = myContext;
        this.costumerList= costumerList;
    }

    @Override
    public int getCount() {
        return costumerList.size();
    }

    @Override
    public Object getItem(int position) {
        return costumerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return costumerList.get(position).getIdCostumer();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v= View.inflate(myContext  , R.layout.costumer_listview, null);
        TextView tvidCostumer= (TextView)v.findViewById(R.id.tv_costumerid);
        TextView tvnameCostumer= (TextView)v.findViewById(R.id.tv_costumerName);
        TextView tvlocationCostumer= (TextView)v.findViewById(R.id.tv_costumerLocation);


        tvidCostumer.setText(String.valueOf(costumerList.get(position).getIdCostumer()));
        tvnameCostumer.setText(costumerList.get(position).getCostumerName());
        tvlocationCostumer.setText(String.valueOf(costumerList.get(position).getIdLocation()));

        return v;
    }
}
