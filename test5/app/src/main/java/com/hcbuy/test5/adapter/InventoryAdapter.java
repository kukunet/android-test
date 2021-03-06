package com.hcbuy.test5.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hcbuy.test5.R;
import com.hcbuy.test5.entity.Inventory;

import java.util.List;

/**
 * 清单列表adapter
 * <p>
 * Created by DavidChen on 2018/5/30.
 */

public class InventoryAdapter extends BaseRecyclerViewAdapter<Inventory> {

    private OnDeleteClickLister mDeleteClickListener;
    private OnAddClickLister mAddClickListener;

    //上拉、下拉
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    public InventoryAdapter(Context context, List<Inventory> data) {
        super(context, data, R.layout.item_inventroy);
    }


    @Override
    protected void onBindData(RecyclerViewHolder holder, Inventory bean, int position) {
        View view = holder.getView(R.id.tv_delete);
        view.setTag(position);
        if (!view.hasOnClickListeners()) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDeleteClickListener != null) {
                        mDeleteClickListener.onDeleteClick(v, (Integer) v.getTag());
                    }
                }
            });
        }
        ((TextView) holder.getView(R.id.tv_item_desc)).setText(bean.getItemDesc());
        String quantity = bean.getQuantity() + "箱";
        ((TextView) holder.getView(R.id.tv_quantity)).setText(quantity);
        String detail = bean.getItemCode() + "/" + bean.getDate();
        ((TextView) holder.getView(R.id.tv_detail)).setText(detail);
        String volume = bean.getVolume() + "方";
        ((TextView) holder.getView(R.id.tv_volume)).setText(volume);

        View addView = holder.getView(R.id.tv_add);
        if (!addView.hasOnClickListeners()) {
            addView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAddClickListener != null) {
                        mAddClickListener.onAddClick(v);
                    }
                }
            });
        }
    }

    public void setOnDeleteClickListener(OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }

    public void setOnAddClickListener(OnAddClickLister listener) {
        this.mAddClickListener = listener;
    }

    public interface OnDeleteClickLister {
        void onDeleteClick(View view, int position);
    }

    public interface OnAddClickLister {
        void onAddClick(View view);
    }
}
