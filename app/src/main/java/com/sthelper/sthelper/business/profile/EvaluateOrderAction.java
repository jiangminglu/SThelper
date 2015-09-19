package com.sthelper.sthelper.business.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.SApp;
import com.sthelper.sthelper.api.ShopingApi;
import com.sthelper.sthelper.bean.OrderItem;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.business.OrderFragment;
import com.sthelper.sthelper.util.Config;
import com.sthelper.sthelper.util.ImageLoadUtil;
import com.sthelper.sthelper.util.SPUtil;
import com.sthelper.sthelper.util.ToastUtil;

import org.apache.http.Header;
import org.json.JSONObject;

import java.math.BigDecimal;

public class EvaluateOrderAction extends BaseAction {

    ImageView shopimg;
    TextView shopname;
    TextView speedscore;
    TextView tastescore;
    TextView pricescore;
    TextView servicescore;
    EditText evaluateet;
    Button commit;
    RatingBar shopRatingbar, speedRating, tasteRating, priceRating, serviceRating;
    private OrderItem bean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_order_action);
        initActionBar("评价订单");
        bean = getIntent().getParcelableExtra("bean");
        initView();
    }

    private void initView() {
        shopimg = (ImageView) findViewById(R.id.shop_img);
        shopname = (TextView) findViewById(R.id.shop_name);
        speedscore = (TextView) findViewById(R.id.speed_score);
        tastescore = (TextView) findViewById(R.id.taste_score);
        pricescore = (TextView) findViewById(R.id.price_score);
        servicescore = (TextView) findViewById(R.id.service_score);
        evaluateet = (EditText) findViewById(R.id.evaluate_et);
        commit = (Button) findViewById(R.id.commit);
        shopRatingbar = (RatingBar) findViewById(R.id.shop_score);

        speedRating = (RatingBar) findViewById(R.id.speed_rating);
        tasteRating = (RatingBar) findViewById(R.id.taste_rating);
        priceRating = (RatingBar) findViewById(R.id.price_rating);
        serviceRating = (RatingBar) findViewById(R.id.service_rating);

        commit.setOnClickListener(onClickListener);

        speedRating.setOnRatingBarChangeListener(onRatingBarChangeListener);
        tasteRating.setOnRatingBarChangeListener(onRatingBarChangeListener);
        priceRating.setOnRatingBarChangeListener(onRatingBarChangeListener);
        serviceRating.setOnRatingBarChangeListener(onRatingBarChangeListener);

        ImageLoadUtil.getCommonImage(shopimg, SApp.IMG_URL + bean.mainInfo.photo);
        shopname.setText(bean.mainInfo.shop_name);
        shopRatingbar.setRating(bean.mainInfo.score);


    }

    private RatingBar.OnRatingBarChangeListener onRatingBarChangeListener = new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
//            BigDecimal decimal = new BigDecimal(v);
//            float temp = decimal.setScale(1,BigDecimal.ROUND_HALF_UP).floatValue();
//            int score = temp

            if (ratingBar == speedRating) {
                speedscore.setText((int) v + "分");
                speedscore.setTag((int) v);
            } else if (ratingBar == tasteRating) {
                tastescore.setText((int) v + "分");
                tastescore.setTag((int) v);
            } else if (ratingBar == priceRating) {
                pricescore.setText((int) v + "分");
                pricescore.setTag((int) v);
            } else if (ratingBar == serviceRating) {
                servicescore.setText((int) v + "分");
                servicescore.setTag((int) v);
            }
        }
    };
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final RequestParams params = new RequestParams();
            params.put("order_id", bean.mainInfo.order_id);
            params.put("speed", Integer.parseInt(speedscore.getTag() + ""));
            params.put("taste", Integer.parseInt(tastescore.getTag() + ""));
            params.put("price", Integer.parseInt(pricescore.getTag() + ""));
            params.put("server", Integer.parseInt(servicescore.getTag() + ""));
            params.put("content", evaluateet.getText() + "");
            int uid = SPUtil.getInt("uid");
            params.put("uid", uid);

            processDialog.show();
            ShopingApi api = new ShopingApi();
            api.evaluateOrder(params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    processDialog.dismiss();
                    if (response.optInt("ret") == 0) {
                        ToastUtil.showToast("评价成功");
                        setResult(RESULT_OK);
                        Intent intent = new Intent(OrderFragment.ORDER_ACTION);
                        sendBroadcast(intent);
                        finish();
                    } else {
                        String error = response.optString("error");
                        ToastUtil.showToast(error);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    processDialog.dismiss();
                }
            });

        }
    };
}
