package com.rd.zhongqipiaoetong.module.account;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.databinding.CashRecordHeadBinding;
import com.rd.zhongqipiaoetong.databinding.CashRecordItemBinding;
import com.rd.zhongqipiaoetong.module.account.model.CashRecordItem;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/11/15 下午2:24
 * <p/>
 * Description:资金明细
 */
public class CashRecordAdapter extends RecyclerView.Adapter<CashRecordAdapter.CashRecordHolder> implements
        StickyRecyclerHeadersAdapter<CashRecordAdapter.CashRecordHeadHolder> {
    private List<CashRecordItem> data = new ArrayList<>();

    public CashRecordAdapter() {
        setHasStableIds(true);
    }

    /** 创建基础view */
    @Override
    public CashRecordAdapter.CashRecordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CashRecordItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cash_record_item, parent, false);
        return new CashRecordAdapter.CashRecordHolder(binding);
    }

    /** 绑定基础view */
    @Override
    public void onBindViewHolder(final CashRecordAdapter.CashRecordHolder holder, int position) {
        CashRecordItemBinding binding = holder.getBinding();
        binding.setItem(data.get(position));
    }

    /** 获取item总个数 */
    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * 设置数据源
     */
    public void addAll(List<CashRecordItem> lists) {
        if (null != lists) {
            this.data.addAll(lists);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).hashCode();
    }

    //**************************粘性头部start*******************//

    /**
     * 创建头部
     *
     * @param parent
     *         父类布局
     */
    @Override
    public CashRecordAdapter.CashRecordHeadHolder onCreateHeaderViewHolder(ViewGroup parent) {
        CashRecordHeadBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cash_record_head, parent, false);
        return new CashRecordHeadHolder(binding);
    }

    /**
     * 绑定头部数据
     *
     * @param holder
     *         头部控件
     * @param position
     *         头部数据对应的下标
     */
    @Override
    public void onBindHeaderViewHolder(CashRecordAdapter.CashRecordHeadHolder holder, int position) {
        CashRecordHeadBinding binding = holder.getBinding();
        binding.header.setText(data.get(position).getHeadValue());
    }

    @Override
    public long getHeaderId(int position) {
        return data.get(position).getHeadId(position);
    }

    //头部类
    class CashRecordHeadHolder extends BaseViewHolder {
        CashRecordHeadBinding binding;

        public CashRecordHeadHolder(CashRecordHeadBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public CashRecordHeadBinding getBinding() {
            return binding;
        }
    }

    //**************************粘性头部end*******************//
    //基础类
    class CashRecordHolder extends BaseViewHolder {
        private CashRecordItemBinding binding;

        public CashRecordHolder(CashRecordItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public CashRecordItemBinding getBinding() {
            return binding;
        }
    }
}