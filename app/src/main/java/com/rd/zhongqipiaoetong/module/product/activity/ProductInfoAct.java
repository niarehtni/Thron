package com.rd.zhongqipiaoetong.module.product.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BaseParams;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.common.ui.BaseViewPagerVM;
import com.rd.zhongqipiaoetong.databinding.CommonViewPagerBinding;
import com.rd.zhongqipiaoetong.module.product.fragment.BorrowerDescriptionFrag;
import com.rd.zhongqipiaoetong.module.product.fragment.BorrowerDetailFrag;
import com.rd.zhongqipiaoetong.module.product.fragment.BorrowerInfoListFrag;
import com.rd.zhongqipiaoetong.module.product.model.ProductInfoMo;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/3/9 15:20
 * <p/>
 * Description: 借款项目详情，了解项目
 * 借款方详情({@link BorrowerDetailFrag})
 * <p/>
 * 借款描述({@link BorrowerDescriptionFrag})
 * <p/>
 * 借款资料({@link BorrowerInfoListFrag})
 */
public class ProductInfoAct extends BaseActivity {
    private BaseViewPagerVM         viewModel;
    private CommonViewPagerBinding  binding;
    private BorrowerDescriptionFrag borrowerDescriptionFrag;
    private BorrowerInfoListFrag    borrowerInfoListFrag;
    private ProductInfoMo           productInfoMo;
    String[] title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.common_view_pager);
        productInfoMo = (ProductInfoMo) getIntent().getSerializableExtra(BundleKeys.PRODUCTINFO);
        title = getResources().getStringArray(R.array.productMoreInfoTitles);
        if (productInfoMo.getBorrowVO().getBorrowContentList() == null || productInfoMo.getBorrowVO().getBorrowContentList().size()
                == 0) {
            if (BaseParams.SPECIALLAYOUT == 2) {
                title = getResources().getStringArray(R.array.productMoreInfoTitlesNoData);
            }
            viewModel = new BaseViewPagerVM(title, getSupportFragmentManager());
            viewModel.items.add(BorrowerDescriptionFrag.newInstance(productInfoMo.getBorrowVO().getContent()));
            if (BaseParams.SPECIALLAYOUT != 2) {
                viewModel.items.add(BorrowerInfoListFrag.newInstance(productInfoMo));
            }
        } else {
            if (BaseParams.SPECIALLAYOUT != 2) {
                title = new String[productInfoMo.getBorrowVO().getBorrowContentList().size() + 1];
            } else {
                title = new String[productInfoMo.getBorrowVO().getBorrowContentList().size()];
            }
            for (int i = 0; i < productInfoMo.getBorrowVO().getBorrowContentList().size(); i++) {
                title[i] = productInfoMo.getBorrowVO().getBorrowContentList().get(i).getName();
            }
            if (BaseParams.SPECIALLAYOUT != 2) {
                title[productInfoMo.getBorrowVO().getBorrowContentList().size()] = "产品说明";
            }
            viewModel = new BaseViewPagerVM(title, getSupportFragmentManager());
            for (int i = 0; i < title.length; i++) {
                if (i == title.length - 1) {
                    viewModel.items.add(BorrowerInfoListFrag.newInstance(productInfoMo));
                } else {
                    if (BaseParams.SPECIALLAYOUT != 2) {
                        viewModel.items.add(BorrowerDescriptionFrag.newInstance(productInfoMo.getBorrowVO().getBorrowContentList().get(i).getContent()));
                    }
                }
            }
        }

        binding.setViewModel(viewModel);
        //        borrowerDescriptionFrag = new BorrowerDescriptionFrag();
        //        borrowerInfoListFrag = new BorrowerInfoListFrag();
        //        viewModel.items.add(borrowerDescriptionFrag);
        //        viewModel.items.add(borrowerInfoListFrag);

        binding.executePendingBindings();
        binding.tabs.setupWithViewPager(binding.pager);
        binding.pager.setOffscreenPageLimit(viewModel.items.size() - 1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.product_borrower_title);
    }

    //    private void reqProductData(){
    //        Call<ProductInfoMo> call = RDClient.getService(ProductService.class).tenderInfomation(getIntent().getStringExtra(BundleKeys.UUID));
    //        call.enqueue(new RequestCallBack<ProductInfoMo>() {
    //            @Override
    //            public void onSuccess(Call<ProductInfoMo> call, Response<ProductInfoMo> response) {
    //                borrowerDescriptionFrag.setContent(response.body().getBorrowVO().getContent());
    //                borrowerInfoListFrag.setImages(response.body().getBorrowVO().getBorrowImageList(),response.body().getQiniu_domain());
    //            }
    //        });
    //    }
    //
    //    private void reqBondData(){
    //        Call<ProductInfoMo> call = RDClient.getService(ProductService.class).tenderInfomation(getIntent().getStringExtra(BundleKeys.BORROWID));
    //        call.enqueue(new RequestCallBack<ProductInfoMo>() {
    //            @Override
    //            public void onSuccess(Call<ProductInfoMo> call, Response<ProductInfoMo> response) {
    //                borrowerDescriptionFrag.setContent(response.body().getBorrowVO().getContent());
    //                borrowerInfoListFrag.setImages(response.body().getBorrowVO().getBorrowImageList(),response.body().getQiniu_domain());
    //            }
    //        });
    //    }
}