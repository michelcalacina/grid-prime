package com.interview.gridprime;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.interview.gridprime.adapter.GridPrimeRecycleAdapter;
import com.interview.gridprime.async.LoadPrimeCallBack;
import com.interview.gridprime.async.LoadPrimeTask;
import com.interview.gridprime.control.CacheControl;
import com.interview.gridprime.util.Utils;

public class GridActivity extends AppCompatActivity implements LoadPrimeCallBack
        , CompoundButton.OnCheckedChangeListener {

    private GridPrimeRecycleAdapter mGridAdapter = null;
    private RecyclerView mGridRecycler = null;
    private LoadPrimeTask loadPrimeRunnable = null;
    private GridLayoutManager mGridLayoutManager = null;

    //Action Bar Controls
    private TextView tvCount = null;
    private SwitchCompat scAutoScroll = null;

    private boolean keepAutoScroll = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        loadPrimeRunnable = new LoadPrimeTask(this);
        mGridAdapter = new GridPrimeRecycleAdapter(this);

        //Configure custom ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.custom_action_bar_toggle);

        tvCount = (TextView) findViewById(R.id.tvCountTotal);

        scAutoScroll = (SwitchCompat) findViewById(R.id.scAutoScroll);
        scAutoScroll.setOnCheckedChangeListener(this);

        mGridRecycler = (RecyclerView) findViewById(R.id.rclPrimes);
        mGridRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        mGridRecycler.setAdapter(mGridAdapter);

        // Manage scroll to control data load
        mGridRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisible = mGridLayoutManager.findLastVisibleItemPosition();
                if (dy > 0 && lastVisible+1 == Utils.MAX_ALLOWED_SIZE) {
                    // Disable auto scroll if enabled.
                    if (keepAutoScroll) {
                        keepAutoScroll = false;
                        scAutoScroll.setChecked(false);
                    }

                    Utils.showSnackBarMessage(getString(R.string.reach_max_allowed)
                            , Snackbar.LENGTH_LONG
                            , GridActivity.this);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_SETTLING)
                    return;

                int lastVisible = mGridLayoutManager.findLastVisibleItemPosition();
                int totalItem = mGridLayoutManager.getItemCount();

                // Must do nothing if reached the maximum size list.
                if (lastVisible+1 == Utils.MAX_ALLOWED_SIZE) {
                    mGridRecycler.removeOnScrollListener(this);
                    CacheControl.clearCache();
                    return;
                }

                // Force verification on each state of scroll.
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        loadDatas(lastVisible);
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        loadDatas(lastVisible);
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        loadDatas(lastVisible);
                        break;
                }
            }
        });

        mGridLayoutManager = (GridLayoutManager) mGridRecycler.getLayoutManager();

        // Load the initial values.
        new Thread(loadPrimeRunnable).start();
        Utils.showSnackBarMessage(getString(R.string.loading), Snackbar.LENGTH_LONG, this);
    }

    // Callback after load elements.
    @Override
    public void onPostExecute(final int[] values) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mGridAdapter.setValues(values);
                tvCount.setText(mGridAdapter.countDatas()+"");
            }
        });
    }

    // Removed after refactory, to accomplish scenario without load.
    // If user try to access elements not load yet, show Snackbar to inform loading.
    /*private void onScrollDragging(int totalItem, int lastVisible) {
        if (lastVisible+1 == totalItem) {
            Utils.showSnackBarMessage(getString(R.string.fetching_more), Snackbar.LENGTH_SHORT
                    , GridActivity.this);
        }

        if ((mGridAdapter.countDatas() - lastVisible+1) <= Utils.BOUNDARY_TO_LOAD_MORE_ELEMENTS) {
            new Thread(loadPrimeRunnable).start();
        }
    }

    // After scroll stop, verify if need to load more, to prevent wait loading content.
    private void onScrollIdle(int totalItem, int lastVisible) {
        if ((mGridAdapter.countDatas() - lastVisible+1) <= Utils.BOUNDARY_TO_LOAD_MORE_ELEMENTS) {
            new Thread(loadPrimeRunnable).start();
        }
    }*/

    private void loadDatas(int lastVisible) {
        if ((mGridAdapter.countDatas() - lastVisible+1) <= Utils.BOUNDARY_TO_LOAD_MORE_ELEMENTS) {
            new Thread(loadPrimeRunnable).start();
        }
    }

    // Control auto scroll through Switch compat event.
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            keepAutoScroll = true;
            autoScrollDown();
        } else {
            keepAutoScroll = false;
        }
    }

    // Recursively call the Handler to execute the runnable, switch is enabled to scroll.
    private void autoScrollDown() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int currentPosition = mGridLayoutManager.findLastVisibleItemPosition();
            @Override
            public void run() {
                if (keepAutoScroll) {
                    if (currentPosition+1 < Utils.MAX_ALLOWED_SIZE) {
                        currentPosition +=20;
                        mGridRecycler.smoothScrollToPosition(currentPosition);
                        handler.postDelayed(this,500);
                    } else {
                        // Fix currentPosition to correct current position.
                        currentPosition = mGridLayoutManager.findLastVisibleItemPosition();
                        if (currentPosition+1 < Utils.MAX_ALLOWED_SIZE) {
                            mGridRecycler.smoothScrollToPosition(Utils.MAX_ALLOWED_SIZE - currentPosition + 1);
                            handler.postDelayed(this,500);
                        } else {
                            scAutoScroll.setChecked(false);
                            keepAutoScroll = false;
                        }
                    }
                }
            }
        };

        handler.postDelayed(runnable,200);
    }
}
