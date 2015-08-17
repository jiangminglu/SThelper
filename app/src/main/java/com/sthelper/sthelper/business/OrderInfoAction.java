package com.sthelper.sthelper.business;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.ShopingApi;
import com.sthelper.sthelper.bean.CartGoodsItem;
import com.sthelper.sthelper.bean.OrderGoodsItem;
import com.sthelper.sthelper.bean.OrderItem;
import com.sthelper.sthelper.business.profile.EvaluateOrderAction;
import com.sthelper.sthelper.util.SPUtil;
import com.sthelper.sthelper.util.ToastUtil;

import org.apache.http.Header;
import org.json.JSONObject;

public class OrderInfoAction extends BaseAction {

    TextView shopname;
    LinearLayout ordergoodslistview;
    TextView ordergoodstotalprice;
    TextView ordergoodstotalnum, orderTpisTv;

    private Button delBt, evlatuateBt;
    private OrderItem bean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info_action);
        initActionBar("订单详情");
        bean = getIntent().getParcelableExtra("bean");
        initView();

    }

    private void initView() {
        shopname = (TextView) findViewById(R.id.shop_name);
        ordergoodslistview = (LinearLayout) findViewById(R.id.order_goods_listview);
        ordergoodstotalprice = (TextView) findViewById(R.id.order_goods_total_price);
        ordergoodstotalnum = (TextView) findViewById(R.id.order_goods_total_num);
        delBt = (Button) findViewById(R.id.delete_order);
        evlatuateBt = (Button) findViewById(R.id.evaluate_order);
        orderTpisTv = (TextView) findViewById(R.id.order_tips_tv);
        delBt.setOnClickListener(onClickListener);
        evlatuateBt.setOnClickListener(onClickListener);
        initGoodsMenu();
        shopname.setText(bean.mainInfo.shop_name);

        if (bean.mainInfo.status == 4) {//已经评价
            evlatuateBt.setVisibility(View.INVISIBLE);
            evlatuateBt.setEnabled(false);
            evlatuateBt.setClickable(false);
        }
    }

    private void initGoodsMenu() {

        int goodsNum = 0;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bean.goodsInfo.size(); i++) {
            OrderGoodsItem info = bean.goodsInfo.get(i);
            stringBuffer.append(info.product_id + ":" + info.num);
            stringBuffer.append(",");
            View view = getLayoutInflater().inflate(R.layout.order_goods_item_layout, null);
            TextView nameTv = (TextView) view.findViewById(R.id.order_goods_item_name);
            TextView numTv = (TextView) view.findViewById(R.id.order_goods_item_num);
            TextView priceTv = (TextView) view.findViewById(R.id.order_goods_item_price);

            nameTv.setText(info.product_name);
            numTv.setText(info.num + "");
            priceTv.setText(info.price + "");
            ordergoodslistview.addView(view);
            goodsNum += info.num;

        }
        orderTpisTv.setText("备注:   "+ bean.mainInfo.tips+"");
        TextView descTv = (TextView) findViewById(R.id.desc);
        descTv.setText(bean.mainInfo.tags);
        ordergoodstotalnum.setText("共计" + goodsNum + "份");
        ordergoodstotalprice.setText(bean.mainInfo.total_price + "￥");
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == evlatuateBt) {
                Intent intent = new Intent();
                intent.setClass(mActivity, EvaluateOrderAction.class);
                intent.putExtra("bean", bean);
                startActivity(intent);
            } else if (view == delBt) {
                int uid = SPUtil.getInt("uid");
                processDialog.show();
                ShopingApi api = new ShopingApi();
                api.deleteOrder(uid, bean.mainInfo.order_id, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        processDialog.dismiss();
                        if (response.optInt("ret") == 0) {
                            ToastUtil.showToast("删除成功");
                            Intent intent = new Intent();
                            intent.putExtra("bean", bean);
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            ToastUtil.showToast(response.toString());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        processDialog.dismiss();
                    }
                });
            }
        }
    };
}
