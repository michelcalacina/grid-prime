package com.interview.gridprime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.interview.gridprime.util.Utils;
import com.interview.gridprime.R;

/**
 * This Adapter Extends RecyclerView Adapter
 * for optimal performance with large values.
 *
 * Created by michelcalacina on 25/02/17.
 */

public class GridPrimeRecycleAdapter extends
        RecyclerView.Adapter<GridPrimeRecycleAdapter.PrimeViewHolder> {

    /** For optimal insert and read, SparseIntArray complexity is O(1),
     * without collisions because of control key.
     * **/
    private SparseIntArray values;
    private final LayoutInflater mInflater;

    public GridPrimeRecycleAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        values = new SparseIntArray();
    }

    // Added to provide total datas available.
    public int countDatas() {
        return values.size();
    }

    // Accumulate values on HashMap according user interaction.
    public synchronized void setValues(int[] newValues) {
        int controlKey = values.size();

        // Limit reached must do nothing.
        if(controlKey == Utils.MAX_ALLOWED_SIZE)
            return;

        // Only some elements from array must be inserted on values.
        if(Utils.MAX_ALLOWED_SIZE - controlKey < newValues.length) {
            int temp = controlKey;
            int limit = Utils.MAX_ALLOWED_SIZE - temp;
            for(int i=0; i < limit; i++) {
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
        return new PrimeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PrimeViewHolder holder, int position) {
        if (position < values.size())
            holder.tvValue.setText( values.get(position+1)+"");
        else
            holder.tvValue.setText("");
    }

    @Override
    public int getItemCount() {
        return Utils.MAX_ALLOWED_SIZE;
    }

    class PrimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvValue;

        public PrimeViewHolder(View itemView) {
            super(itemView);
            tvValue = (TextView) itemView.findViewById(R.id.tvValue);
        }
    }
}
