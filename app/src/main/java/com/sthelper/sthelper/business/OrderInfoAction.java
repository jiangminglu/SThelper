package com.sthelper.sthelper.business;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.ShopingApi;
import com.sthelper.sthelper.bean.CartGoodsItem;
import com.sthelper.sthelper.bean.OrderGoodsItem;
import com.sthelper.sthelper.bean.OrderItem;
import com.sthelper.sthelper.business.profile.EvaluateOrderAction;
import com.sthelper.sthelper.util.Config;
import com.sthelper.sthelper.util.PayResult;
import com.sthelper.sthelper.util.SPUtil;
import com.sthelper.sthelper.util.SignUtils;
import com.sthelper.sthelper.util.ToastUtil;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class OrderInfoAction extends BaseAction {

    private static final int SDK_PAY_FLAG = 1;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
    private static final int SDK_CHECK_FLAG = 2;

    ImageView xdImg, jdImg, shImg;
    View line1, line2;
    TextView shopname, orderTimeTv, orderIdTv, orderTipsTv, telTv, addressTv;
    LinearLayout ordergoodslistview;
    TextView ordergoodstotalprice;
    TextView ordergoodstotalnum;

    private Button delBt, evlatuateBt, payBt;
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

        jdImg = (ImageView) findViewById(R.id.jd_img);
        xdImg = (ImageView) findViewById(R.id.xd_img);
        shImg = (ImageView) findViewById(R.id.sh_img);

        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);


        orderTimeTv = (TextView) findViewById(R.id.order_create_time_tv);
        orderIdTv = (TextView) findViewById(R.id.order_create_id_tv);
        orderTipsTv = (TextView) findViewById(R.id.order_tips_tv);

        telTv = (TextView) findViewById(R.id.address_item_tel);
        addressTv = (TextView) findViewById(R.id.address_item_name);

        shopname = (TextView) findViewById(R.id.shop_name);
        ordergoodslistview = (LinearLayout) findViewById(R.id.order_goods_listview);
        ordergoodstotalprice = (TextView) findViewById(R.id.order_goods_total_price);
        ordergoodstotalnum = (TextView) findViewById(R.id.order_goods_total_num);
        delBt = (Button) findViewById(R.id.delete_order);
        evlatuateBt = (Button) findViewById(R.id.evaluate_order);
        payBt = (Button) findViewById(R.id.pay_order);
        delBt.setOnClickListener(onClickListener);
        payBt.setOnClickListener(onClickListener);
        evlatuateBt.setOnClickListener(onClickListener);
        initGoodsMenu();
        shopname.setText(bean.mainInfo.shop_name);

        orderTipsTv.setText("订单备注:    " + bean.mainInfo.tips);
        orderIdTv.setText("订单编号:    " + bean.mainInfo.order_code);
        try {
            Date date = new Date(bean.mainInfo.create_time * 1000);
            String time = format.format(date);
            orderTimeTv.setText("下单时间:    " + time);
        } catch (Exception e) {

        }
        telTv.setText(bean.mainInfo.mobile);
        addressTv.setText(bean.mainInfo.addr);
        if (bean.mainInfo.status == 0) {//未支付
            payBt.setVisibility(View.VISIBLE);
            evlatuateBt.setVisibility(View.GONE);
            delBt.setVisibility(View.VISIBLE);
        } else {
            payBt.setVisibility(View.GONE);
            if (bean.mainInfo.is_comment == 1) {//已经评价
                evlatuateBt.setVisibility(View.GONE);
            } else {
                evlatuateBt.setVisibility(View.VISIBLE);
            }
        }

        if (bean.mainInfo.status > 0) {//待发货，下单成功
            xdImg.setBackgroundResource(R.mipmap.xd_true);
            line1.setBackgroundColor(getResources().getColor(R.color.app_default_actionbar_bg));
            jdImg.setBackgroundResource(R.mipmap.jd_true);

            line2.setBackgroundColor(getResources().getColor(R.color.app_default_line_bg));
            shImg.setBackgroundResource(R.mipmap.dsh_false);
            if (bean.mainInfo.status > 1) {
                shImg.setBackgroundResource(R.mipmap.dsh_true);
                line2.setBackgroundColor(getResources().getColor(R.color.app_default_actionbar_bg));
            }
        } else {
            xdImg.setBackgroundResource(R.mipmap.xd_false);
            line1.setBackgroundColor(getResources().getColor(R.color.app_default_line_bg));
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
            } else if (view == payBt) {
                processDialog.show();
                pay("购买商品", bean.mainInfo.total_price);
            }
        }
    };

    /**
     * 支付宝支付
     */
    private void pay(String desc, float price) {
        // 订单
        String orderInfo = getOrderInfo("水头商品", desc, price + "");

        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(mActivity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String subject, String body, String price) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + Config.PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + Config.SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, Config.RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(mActivity, "支付成功",
                                Toast.LENGTH_SHORT).show();
                        processDialog.show();
                        processDialog.setCancelable(false);
                        changeOrder(bean.mainInfo.order_id + "", 0);
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(mActivity, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(mActivity, "支付失败",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(mActivity, "检查结果为：" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        }
    };

    private void changeOrder(String order_id, int integral) {
        int uid = SPUtil.getInt("uid");
        if (uid < 1) return;
        ShopingApi api = new ShopingApi();
        api.changeOrderStatus(integral, uid, order_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                if (0 == response.optInt("ret")) {
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
