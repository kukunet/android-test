package com.hcbuy.test5;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.hcbuy.test5.R;
import com.hcbuy.test5.adapter.InventoryAdapter;
import com.hcbuy.test5.entity.Inventory;
import com.hcbuy.test5.view.SlideRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * SlideRecyclerView测试
 * <p>
 * Created by DavidChen on 2018/6/1.
 */

public class MainActivity extends AppCompatActivity {

    private SlideRecyclerView recycler_view_list;
    private List<Inventory> mInventories;
    private InventoryAdapter mInventoryAdapter;

    //上下滑变量
    private boolean loading = true;
    private boolean first = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler_view_list = (SlideRecyclerView) findViewById(R.id.recycler_view_list);
        recycler_view_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_inset));
        recycler_view_list.addItemDecoration(itemDecoration);
        mInventories = new ArrayList<>();
        Inventory inventory;
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            inventory = new Inventory();
            inventory.setItemDesc("测试数据" + i);
            inventory.setQuantity(random.nextInt(100000));
            inventory.setItemCode("0120816");
            inventory.setDate("20180219");
            inventory.setVolume(random.nextFloat());
            mInventories.add(inventory);
        }
        mInventoryAdapter = new InventoryAdapter(this, mInventories);
        recycler_view_list.setAdapter(mInventoryAdapter);
        mInventoryAdapter.setOnDeleteClickListener(new InventoryAdapter.OnDeleteClickLister() {
            @Override
            public void onDeleteClick(View view, int position) {
                mInventories.remove(position);
                mInventoryAdapter.notifyDataSetChanged();
                recycler_view_list.closeMenu();
            }
        });

        //给RecyclerView添加上滑下拉
        recycler_view_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                if(pastVisiblesItems==0){
                    return;
                }

                //顶部判断暂时先不判断，应该在onScrolledStatus【大概是这个方法】中判断

                if (loading) { //数据加载完成后更新loading状态
                    if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        // 判断点
                        loading = false;
                        Log.v("...", "已经到底部了!");
                    }
                }
            }
        });
    }
}
