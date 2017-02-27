package com.samsung.gridprime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.samsung.gridprime.adapter.GridPrimeRecycleAdapter;
import com.samsung.gridprime.async.LoadPrimeCallBack;
import com.samsung.gridprime.async.LoadPrimeTask;
import com.samsung.gridprime.control.PrimeControl;
import com.samsung.gridprime.util.Utils;

public class GridActivity extends AppCompatActivity implements LoadPrimeCallBack {

    GridPrimeRecycleAdapter mGridAdapter = null;
    RecyclerView mGridRecycler = null;
    LoadPrimeTask loadPrimeRunnable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        loadPrimeRunnable = new LoadPrimeTask(this);
        mGridAdapter = new GridPrimeRecycleAdapter(this);

        mGridRecycler = (RecyclerView) findViewById(R.id.rclPrimes);
        mGridRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        mGridRecycler.setAdapter(mGridAdapter);

        // Manage scroll to control data load
        mGridRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    GridLayoutManager glm = (GridLayoutManager) recyclerView.getLayoutManager();
                    int totalItem = glm.getItemCount();

                    // Must do nothing if reached the maximum size list.
                    if (totalItem == Utils.MAX_ALLOWED_VALUE)
                        return;

                    int lastVisible = glm.findLastVisibleItemPosition();

                    // Load contents before reaching the bottom.
                    if (totalItem - lastVisible <= Utils.BOUNDARY_TO_LOAD_MORE_ELEMENTS) {
                        new Thread(loadPrimeRunnable).start();
                    }
                }
            }
        });

        // Load the initial values.
        new Thread(loadPrimeRunnable).start();
    }

    @Override
    public void onPostExecute(final int[] values) {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mGridAdapter.setValues(values);
            }
        });
    }
}
