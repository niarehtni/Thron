package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.databinding.AccountMyAssetsListGroupBinding;
import com.rd.zhongqipiaoetong.databinding.AccountMyAssetsListHeaderBinding;
import com.rd.zhongqipiaoetong.module.account.model.AssetsItemMo;
import com.rd.zhongqipiaoetong.view.entity.PieArc;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/15 16:43
 * <p/>
 * Description: 账户总览({@link MyAssetsVM}) - adapter
 */
public class MyAssetsAdapter extends RecyclerView.Adapter<MyAssetsAdapter.ViewHolder> {
    public static final int HEADER = 0;
    public static final int GROUP  = 1;
    public static final int CHILD  = 2;
    // 饼状图 - 数据
    private List<PieArc>       list;
    // 饼状图 - title
    private String             title;
    // list - 数据
    private List<AssetsItemMo> data;
    private LayoutInflater     inflater;

    public MyAssetsAdapter(List<PieArc> list, String title, List<AssetsItemMo> data) {
        this.list = list;
        this.title = title;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        switch (type) {
            // 设置饼状图view
            case HEADER: {
                return new ViewHolder(DataBindingUtil.inflate(inflater, R.layout.account_my_assets_list_header, parent, false));
            }

            // 设置list的一级菜单
            case GROUP: {
                return new ViewHolder(DataBindingUtil.inflate(inflater, R.layout.account_my_assets_list_group, parent, false));
            }

            // 设置list的二级菜单
            case CHILD:
            default: {
                return new ViewHolder(DataBindingUtil.inflate(inflater, R.layout.account_my_assets_list_child, parent, false));
            }
        }
    }

    /**
     * 为不同的view binding数据
     */
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final AssetsItemMo item = data.get(position);
        switch (item.getType()) {
            // 为饼状图绑定数据
            case HEADER:
                ((AccountMyAssetsListHeaderBinding) holder.binding).setTitle(title);
                ((AccountMyAssetsListHeaderBinding) holder.binding).assetsPie.setArcs(list);
                break;

            // 为一级菜单绑定数据
            case GROUP:
                final AccountMyAssetsListGroupBinding binding = ((AccountMyAssetsListGroupBinding) holder.binding);
                holder.binding.setVariable(BR.item, item);
                // 如果没有二级菜单数据，则不显示指示icon
                if (item.getChild() == null) {
                    binding.imageIndicator.setVisibility(View.GONE);
                } else {
                    binding.imageIndicator.setVisibility(View.VISIBLE);
                    // 如果有二级菜单数据，则设置click事件，且对数据做处理
                    holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 二级菜单展开的时候，点击
                            if (item.getChild() == null) {
                                item.setChild(new ArrayList<AssetsItemMo>());
                                int count = 0;
                                int pos   = data.indexOf(item);
                                while (data.size() > pos + 1 && data.get(pos + 1).getType() == CHILD) {
                                    item.getChild().add(data.remove(pos + 1));
                                    count++;
                                }
                                notifyItemRangeRemoved(pos + 1, count);
                                binding.imageIndicator.setImageResource(R.drawable.ic_arrow_right);
                            } else {
                                // 二级菜单合并的时候，点击
                                int pos   = data.indexOf(item);
                                int index = pos + 1;
                                for (AssetsItemMo i : item.getChild()) {
                                    data.add(index, i);
                                    index++;
                                }
                                notifyItemRangeInserted(pos + 1, index - pos - 1);
                                binding.imageIndicator.setImageResource(R.drawable.ic_arrow_bottom);
                                item.setChild(null);
                            }
                        }
                    });
                }
                break;

            // 为二级菜单绑定数据
            case CHILD:
                holder.binding.setVariable(BR.item, item);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }
}