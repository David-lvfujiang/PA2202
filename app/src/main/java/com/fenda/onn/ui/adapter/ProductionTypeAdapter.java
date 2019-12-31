package com.fenda.onn.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fenda.onn.R;
import com.fenda.onn.bean.ProductionSeriesBean;
import com.fenda.onn.bean.ProductionTypeBean;

import java.util.List;

public class ProductionTypeAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<ProductionTypeBean> mData;

    public interface OnProductionTypeItemClickListener {
        void onProductionTypeItemClick(ProductionTypeBean bean);
    }

    private OnProductionTypeItemClickListener mOnProductionTypeItemClickListener;


    public void setOnProductionTypeItemClickListener(OnProductionTypeItemClickListener onProductionTypeItemClickListener) {
        this.mOnProductionTypeItemClickListener = onProductionTypeItemClickListener;
    }

    public ProductionTypeAdapter(Context context, List<ProductionTypeBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_production_type, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        ProductionTypeBean bean = mData.get(position);
        myHolder.ivPic.setImageResource(bean.getImgResId());
        myHolder.tvName.setText(bean.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnProductionTypeItemClickListener != null) {
                    mOnProductionTypeItemClickListener.onProductionTypeItemClick(bean);
                }
            }
        });
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView ivPic;
        TextView tvName;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.ivPic);
            tvName = itemView.findViewById(R.id.tvName);
        }

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
}
