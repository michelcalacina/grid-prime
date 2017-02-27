package com.interview.gridprime;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.interview.gridprime.adapter.GridPrimeRecycleAdapter;
import com.interview.gridprime.async.LoadPrimeCallBack;
import com.interview.gridprime.async.LoadPrimeTask;
import com.interview.gridprime.control.CacheControl;
import com.interview.gridprime.util.Utils;

public class GridActivity extends AppCompatActivity implements LoadPrimeCallBack {

    private GridPrimeRecycleAdapter mGridAdapter = null;
    private RecyclerView mGridRecycler = null;
    private LoadPrimeTask loadPrimeRunnable = null;

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
                int lastVisible = ((GridLayoutManager)recyclerView.getLayoutManager())
                        .findLastVisibleItemPosition();
                if (dy > 0 && lastVisible+1 == Utils.MAX_ALLOWED_SIZE) {
                    Utils.showSnackBarMessage("Reach Max allowed size, 32767 primes found."
                            , Snackbar.LENGTH_SHORT
                            , GridActivity.this);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                GridLayoutManager glm = (GridLayoutManager) recyclerView.getLayoutManager();
                int lastVisible = glm.findLastVisibleItemPosition();
                int totalItem = glm.getItemCount();

                // Must do nothing if reached the maximum size list.
                if (lastVisible+1 == Utils.MAX_ALLOWED_SIZE) {
                    mGridRecycler.removeOnScrollListener(this);
                    CacheControl.clearCache();
                    return;
                }

                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        onScrollIdle(totalItem, lastVisible);
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        onScrollDraging(totalItem, lastVisible);
                        break;
                }
            }
        });

        // Load the initial values.
        new Thread(loadPrimeRunnable).start();
        Utils.showSnackBarMessage("Loading...", Snackbar.LENGTH_LONG, this);
    }

    @Override
    public void onPostExecute(final int[] values) {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mGridAdapter.setValues(values);
                setTitle("Grid Prime - Total: " + mGridRecycler.getLayoutManager().getItemCount());
            }
        });
    }

    // If user try to access elements not load yet, show Snackbar to inform loading.
    private void onScrollDraging(int totalItem, int lastVisible) {
        if (lastVisible+1 == totalItem) {
            Utils.showSnackBarMessage("Fetching more primes!", Snackbar.LENGTH_SHORT
                    , GridActivity.this);
        }
    }

    // After scroll stop, verify if need to load more, to prevent wait loading content.
    private void onScrollIdle(int totalItem, int lastVisible) {
        if (totalItem - lastVisible <= Utils.BOUNDARY_TO_LOAD_MORE_ELEMENTS) {
            new Thread(loadPrimeRunnable).start();
        }
    }
}
