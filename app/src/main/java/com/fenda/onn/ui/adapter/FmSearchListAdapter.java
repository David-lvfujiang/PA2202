package com.fenda.onn.ui.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fenda.onn.R;
import com.fenda.onn.bean.FmStationBean;
import com.fenda.onn.utils.ToastUtils;

import org.litepal.LitePal;

import java.util.List;

/**
 * @Author: david.lvfujiang
 * @Date: 2020/1/6
 * @Describe: FM搜索列表适配器
 */
public class FmSearchListAdapter extends BaseQuickAdapter<FmStationBean, BaseViewHolder> {
    public FmSearchListAdapter(int layoutResId, @Nullable List<FmStationBean> data) {
        super(layoutResId, data);
    }

    /**
     * 重写该方法防止checkBox复用错乱
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    protected void convert(BaseViewHolder helper, FmStationBean item) {

        //查询收藏列表
        List<FmStationBean> fmStationBeans = LitePal.where("stationName=?",
                item.getStationName()).find(FmStationBean.class);
        if (fmStationBeans.size() > 0) {
            helper.setChecked(R.id.rb_fm_love, true);
        }
        helper.setText(R.id.tv_fm_rate, item.getStationName());
        helper.setOnCheckedChangeListener(R.id.rb_fm_love, (buttonView, isChecked) -> {
            if (isChecked) {
                if (fmStationBeans.size() > 0) {
                    ToastUtils.show(R.string.fm_already_collect);
                } else {
                    //保存收藏
                    item.save();
                    ToastUtils.show(R.string.fm_collect_success);
                }
            } else {
                //删除收藏
                LitePal.deleteAll(FmStationBean.class, "stationName = ?", item.getStationName().trim());
                ToastUtils.show(R.string.fm_collect_cancel);
            }
        });
        helper.addOnClickListener(R.id.bt_compile);
    }
}
