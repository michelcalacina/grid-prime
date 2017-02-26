package com.samsung.gridprime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samsung.gridprime.R;
import com.samsung.gridprime.util.Utils;

import java.util.HashMap;

/**
 * Created by michelcalacina on 25/02/17.
 */

public class GridPrimeRecycleAdapter extends
        RecyclerView.Adapter<GridPrimeRecycleAdapter.PrimeViewHolder> {

    // For optimal insert and read, HashMap complexity is O(1) without collisions because of control key.
    private HashMap<Integer,Integer> values;
    private LayoutInflater mInflater = null;

    public GridPrimeRecycleAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        values = new HashMap<Integer,Integer>();
    }

    // Accumulate values on HashMap according user interaction.
    public void setValues(int[] newValues) {
        int controlKey = values.size();

        // Limit reached must do nothing.
        if(controlKey == Utils.MAX_ALLOWED_VALUE)
            return;

        // Only some elements from array must be inserted on values.
        if(Utils.MAX_ALLOWED_VALUE - controlKey < newValues.length) {
            int temp = controlKey;
            for(int i=0; i < Utils.MAX_ALLOWED_VALUE - temp; i++) {
                controlKey++;
                values.put(controlKey, newValues[i]);
            }
        } else { // Insert everything.
            for(int v : newValues) {
                controlKey++;
                values.put(controlKey, v);
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public PrimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.grid_item, parent, false);
        PrimeViewHolder holder = new PrimeViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(PrimeViewHolder holder, int position) {
        holder.tvValue.setText( values.get(position+1).toString() );
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    class PrimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvValue;

        public PrimeViewHolder(View itemView) {
            super(itemView);
            tvValue = (TextView) itemView.findViewById(R.id.tvValue);
        }
    }
}
