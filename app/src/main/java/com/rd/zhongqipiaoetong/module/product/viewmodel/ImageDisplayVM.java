package com.rd.zhongqipiaoetong.module.product.viewmodel;

import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.databinding.ProductImagedisplayActBinding;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.view.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

public class ImageDisplayVM {

    private List<View> views = new ArrayList<View>();

    public ImageDisplayVM(final ProductImagedisplayActBinding binding, int position, final List<String> images) {
        binding.imagedisplayVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                binding.imagedisplayTv.setText("第" + (position + 1) + "张，共" + images.size() + "张");
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.imagedisplayTv.setText("第1张，共" + images.size() + "张");
        for (int i = 0; i < images.size(); i++) {
            PhotoView view = getImageView(images.get(i));
            views.add(view);
        }
        MyPagerAdapter adapter = new MyPagerAdapter(views);
        binding.imagedisplayVp.setAdapter(adapter);
        binding.imagedisplayVp.setCurrentItem(position);
    }

    private PhotoView getImageView(String url){
        PhotoView imageView = new PhotoView(ActivityUtils.peek());
        imageView.setMinimumScale(1);
        Drawable defaultImage = ContextCompat.getDrawable(imageView.getContext(), R.drawable.default_picture);
        Glide.with(imageView.getContext()).load(url).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).error(defaultImage).into(imageView);
        return imageView;
    }




    public class MyPagerAdapter extends PagerAdapter {
        private List<View> list;

        //huo qu tu pian shu zu
        public MyPagerAdapter(List<View> list) {
            this.list = list;
        }

        @Override
        // huo qu tu pian su zu da xiao
        public int getCount() {
            return list.size();
        }

        @Override
        // pan duan shi fou wei dang qian xiang
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        // yi chu tu pian
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(list.get(position));
        }

        @Override
        // fan hui tu pian dui xiang
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public void startUpdate(ViewGroup container) {
        }
    }
}

