package com.rd.zhongqipiaoetong.module.more.viewmodel;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.Constant;
import com.rd.zhongqipiaoetong.databinding.MoreMessageItem2Binding;
import com.rd.zhongqipiaoetong.module.more.activity.MsgManageAct;
import com.rd.zhongqipiaoetong.module.more.model.MsgMo;
import com.rd.zhongqipiaoetong.utils.log.Logger;
import com.rd.zhongqipiaoetong.view.CustomDialog;
import com.rd.zhongqipiaoetong.view.swipe.BaseSwipeAdapter;
import com.rd.zhongqipiaoetong.view.swipe.ZSwipeItem;
import com.rd.zhongqipiaoetong.view.swipe.enums.DragEdge;
import com.rd.zhongqipiaoetong.view.swipe.enums.ShowMode;
import com.rd.zhongqipiaoetong.view.swipe.listener.SimpleSwipeListener;

import java.util.HashMap;
import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/18 15:31
 * <p/>
 * Description: 消息列表adapter
 */
public class MsgManageVM extends BaseSwipeAdapter {
    private MsgManageAct   activity;
    private LayoutInflater inflater;
    private List<MsgMo>    beans;
    /**
     * 是否显示checkBox
     */
    private boolean                checkBoxIsShow = false;
    private HashMap<String, MsgMo> hasSelected    = new HashMap<>();
    /**
     * 2.0版本，MoreMessageItemBinding，more_message_item
     * <p/>
     * 云版本，MoreMessageItem2Binding，more_message_item2
     */
    private MoreMessageItem2Binding binding;

    public MsgManageVM(MsgManageAct activity, List<MsgMo> beans) {
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
        this.beans = beans;
    }

    @Override
    public int getCount() {
        return beans == null ? 0 : beans.size();
    }

    @Override
    public MsgMo getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_item;
    }

    /**
     * convertView为null时，初始化调用
     */
    @Override
    public View generateView(int position, ViewGroup parent) {
        binding = DataBindingUtil.inflate(inflater, R.layout.more_message_item2, parent, false);
        binding.getRoot().setTag(binding);
        return binding.getRoot();
    }

    @Override
    public void fillValues(final int position, final View convertView) {
        binding = (MoreMessageItem2Binding) convertView.getTag();
        binding.setVariable(BR.item, beans.get(position));
        binding.setIsShow(checkBoxIsShow);
        // 初始化侧滑item
        initZSwipe(convertView, position);

        // item项的点击事件
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxIsShow) {
                    MsgMo bean = beans.get(position);
                    if (hasSelected.containsKey(bean.getId())) {
                        hasSelected.remove(bean.getId());
                    } else {
                        hasSelected.put(bean.getId(), bean);
                    }
                    activity.setSelectText(hasSelected.size());
                } else {
                    if (!getItem(position).getIsReaded().equals(Constant.STATUS_2)) {
                        // 向服务器发送标记已读请求
                        activity.req_getSettingMsg(beans.get(position).getId(), Constant.STATUS_3);
                        // 界面上标记为已读
                        beans.get(position).setIsReaded(Constant.STATUS_2);
                    }
                    // 设置是否显示详情；只针对 more_message_item2.xml
                    if (getItem(position).isShow()) {
                        beans.get(position).setIsShow(false);
                    } else {
                        beans.get(position).setIsShow(true);
                    }
                    // TODO 跳转到查看详情界面
                    // Intent intent = new Intent(activity, ProjectDetailPreviewAct.class);
                    // intent.putExtra(Constant.PROJECT_TITLE, beans.get(position).getTitle());
                    // intent.putExtra(Constant.PROJECT_CONTENT, beans.get(position).getContent() + "<br /><br />\u3000" + beans.get(position).getTime());
                    // activity.startActivity(intent);
                }
                notifyDataSetChanged();
            }
        });

        // 设置item项的CheckBox选中与否
        if (hasSelected.containsKey(getItem(position).getId())) {
            beans.get(position).setIsCheck(true);
        } else {
            beans.get(position).setIsCheck(false);
        }
    }

    /**
     * 侧滑Item初始化
     */
    private void initZSwipe(View convertView, final int position) {
        // view初始化
        final ZSwipeItem swipeItem   = (ZSwipeItem) convertView.findViewById(R.id.swipe_item);
        final TextView   item_delete = (TextView) convertView.findViewById(R.id.item_delete);
        final TextView   item_mark   = (TextView) convertView.findViewById(R.id.item_mark);
        swipeItem.setShowMode(ShowMode.PullOut);
        swipeItem.setDragEdge(DragEdge.Right);

        swipeItem.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(ZSwipeItem layout) {
                Logger.d(TAG, "打开:" + position);
            }

            @Override
            public void onClose(ZSwipeItem layout) {
                Logger.d(TAG, "关闭:" + position);
            }

            @Override
            public void onStartOpen(ZSwipeItem layout) {
                Logger.d(TAG, "准备打开:" + position);
                if (beans.get(position).getIsReaded().equals(Constant.STATUS_2)) {
                    item_mark.setText(activity.getString(R.string.msg_mark_unread));
                } else {
                    item_mark.setText(activity.getString(R.string.msg_mark_read));
                }
            }

            @Override
            public void onStartClose(ZSwipeItem layout) {
                Logger.d(TAG, "准备关闭:" + position);
            }

            @Override
            public void onHandRelease(ZSwipeItem layout, float xvel, float yvel) {
                Logger.d(TAG, "手势释放");
            }

            @Override
            public void onUpdate(ZSwipeItem layout, int leftOffset, int topOffset) {
                Logger.d(TAG, "位置更新");
            }
        });

        // 删除 - 点击
        item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showZSwipeDialog(swipeItem, position);
            }
        });

        // 标记 - 点击
        item_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status;
                if (beans.get(position).getIsReaded().equals(Constant.STATUS_1)) {
                    status = Constant.STATUS_3;
                    beans.get(position).setIsReaded(Constant.STATUS_2);
                } else {
                    status = Constant.STATUS_2;
                    beans.get(position).setIsReaded(Constant.STATUS_1);
                }
                // 标记
                activity.req_getSettingMsg(beans.get(position).getId(), status);
                MsgManageVM.this.notifyDataSetChanged();
                swipeItem.close();
            }
        });
    }

    /**
     * 显示Dialog提示框
     */
    private void showZSwipeDialog(final ZSwipeItem swipeItem, final int position) {
        CustomDialog.Builder builder = new CustomDialog.Builder(activity).setMessage(activity.getString(R.string.msg_delete))
                .setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        swipeItem.close();
                    }
                }).setPositiveButton(activity.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletePattern(swipeItem, position);
                    }
                });

        builder.create().setCanceledOnTouchOutside(false);
        builder.create().show();
    }

    /**
     * 是否显示CheckBox
     */
    public boolean getIsShow() {
        return this.checkBoxIsShow;
    }

    /**
     * 设置是否显示CheckBox
     */
    public void setIsShow(boolean flag) {
        this.checkBoxIsShow = flag;
        if (flag) {
            hasSelected = new HashMap<String, MsgMo>();
        }
        notifyDataSetChanged();
    }

    public HashMap<String, MsgMo> getSelected() {
        return hasSelected;
    }

    private void deletePattern(final View view, final int position) {

        Animation.AnimationListener al = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                activity.req_getSettingMsg(beans.get(position).getId(), Constant.STATUS_1);
                MsgManageVM.this.notifyDataSetChanged();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        collapse(view, al);
    }

    private void collapse(final View view, Animation.AnimationListener al) {
        final int originHeight = view.getMeasuredHeight();

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1.0f) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = originHeight - (int) (originHeight * interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        if (al != null) {
            animation.setAnimationListener(al);
        }
        animation.setDuration(300);
        view.startAnimation(animation);
    }
}