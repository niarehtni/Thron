package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.view.View;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseRecyclerViewVM;
import com.rd.zhongqipiaoetong.databinding.AccountBottomBinding;
import com.rd.zhongqipiaoetong.module.account.AccountViewOnclick;
import com.rd.zhongqipiaoetong.module.account.model.AccountGridMo;
import com.rd.zhongqipiaoetong.utils.FeatureUtils;
import com.rd.zhongqipiaoetong.view.DividerLine;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Author: Hubert
 * E-mail: hbh@erongdu.com
 * Date: 16/8/11 下午3:20
 * <p/>
 * Description:
 */
public class AccountBottomVM extends BaseRecyclerViewVM<AccountGridMo> {
    private AccountViewOnclick accountViewOnclick;
    private AccountBottomBinding binding;

    @Override
    protected void selectView(ItemView itemView, int position, AccountGridMo item) {
        itemView.set(BR.item, R.layout.grid_account_item).setVariable(BR.isPrincipal, position % 2 == 0)
                .setOnItemClickListener(new ItemView.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        accountViewOnclick.onClick(view, items.get(position).getTitleId());
                    }
                });
    }

    public AccountBottomVM(AccountBottomBinding binding, AccountViewOnclick accountViewOnclick) {
        this.accountViewOnclick = accountViewOnclick;
        this.binding = binding;
        type = DividerLine.CROSS;
        items.addAll(FeatureUtils.getAccountBottomList(binding.getRoot().getContext()));
        emptyState.setLoading(false);
    }

    public void addExp(){
        items.add(new AccountGridMo(binding.getRoot().getContext().getResources().getDrawable(R.drawable.icon_exp), R.string.account_exp));
    }

    public void addRepay(){
        items.add(new AccountGridMo(binding.getRoot().getContext().getResources().getDrawable(R.drawable.account_jiekuan), R.string.account_loan_manage));
        items.add(new AccountGridMo(binding.getRoot().getContext().getResources().getDrawable(R.drawable.account_huankuan), R.string.account_repayment_manage));
    }
}
