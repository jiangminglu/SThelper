package com.sthelper.sthelper.business.food;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.fasterxml.jackson.databind.JsonNode;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.BaseApi;
import com.sthelper.sthelper.api.ShopingApi;
import com.sthelper.sthelper.bean.Address;
import com.sthelper.sthelper.bean.CartGoodsItem;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.business.profile.AddAddressAction;
import com.sthelper.sthelper.business.profile.AddressManagerAction;
import com.sthelper.sthelper.util.Config;
import com.sthelper.sthelper.util.PayResult;
import com.sthelper.sthelper.util.SPUtil;
import com.sthelper.sthelper.util.SignUtils;
import com.sthelper.sthelper.util.ToastUtil;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class VerifyOrderAction extends BaseAction {


    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;

    private ArrayList<String> orderIds = new ArrayList<String>();

    private TextView totalNumTv, totalPriceTv;
    private LinearLayout goodsListView;
    private TextView addAddressTv;//添加收货地址
    private View addressLayout;
    private double totalPrice;
    private Address address;

    private ArrayList<CartGoodsItem> list = new ArrayList<CartGoodsItem>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_order_action);
        initActionBar("确认订单");
        init();
        getAddressListData();
    }

    private void init() {

        list = getIntent().getParcelableArrayListExtra("list");
        goodsListView = (LinearLayout) findViewById(R.id.order_goods_listview);

        totalPriceTv = (TextView) findViewById(R.id.order_goods_total_price);
        totalNumTv = (TextView) findViewById(R.id.order_goods_total_num);
        addAddressTv = (TextView) findViewById(R.id.add_address);
        addressLayout = findViewById(R.id.address_layout);
        goodsListView.removeAllViews();
        initGoodsMenu();
        initAddress();

        addressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mActivity, AddressManagerAction.class);
                startActivityForResult(intent, 1024);
            }
        });
    }

    private void initGoodsMenu() {
        int totalNum = 0;
        totalPrice = 0;
        for (int i = 0; i < list.size(); i++) {
            CartGoodsItem info = list.get(i);
            info.isSelect = false;

            View view = getLayoutInflater().inflate(R.layout.order_goods_item_layout, null);
            TextView nameTv = (TextView) view.findViewById(R.id.order_goods_item_name);
            TextView numTv = (TextView) view.findViewById(R.id.order_goods_item_num);
            TextView priceTv = (TextView) view.findViewById(R.id.order_goods_item_price);

            nameTv.setText(info.product_name);
            numTv.setText(info.num + "");
            priceTv.setText(info.price + "");
            goodsListView.addView(view);

            totalNum = totalNum + info.num;
            totalPrice = totalPrice + info.num * info.price;
        }


        totalNumTv.setText("共计" + totalNum + "份");
        totalPriceTv.setText(totalPrice + "￥");
    }

    private void initAddress() {
        getAddressListData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_verify_order_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            processDialog.show();
            submitOrder();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getAddressListData() {
        processDialog.show();
        ShopingApi api = new ShopingApi();
        api.getAddressList(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                try {
                    JsonNode jsonNode = BaseApi.mapper.readTree(response.toString());
                    if (jsonNode.get("ret").asInt() == 0) {
                        ArrayList<Address> list = new ArrayList<Address>();
                        JsonNode result = jsonNode.get("result");
                        if (result.isArray()) {
                            list.removeAll(list);
                            for (JsonNode item : result) {
                                Address address = BaseApi.mapper.readValue(item.toString(), Address.class);
                                list.add(address);
                            }
                        }
                        if (list.size() == 0) {
                            addressLayout.setVisibility(View.GONE);
                            addAddressTv.setVisibility(View.VISIBLE);
                            addAddressTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent();
                                    intent.setClass(mActivity, AddAddressAction.class);
                                    startActivity(intent);
                                }
                            });
                        } else {
                            addressLayout.setVisibility(View.VISIBLE);
                            addAddressTv.setVisibility(View.GONE);
                            address = list.get(0);
                            TextView nameTv = (TextView) addressLayout.findViewById(R.id.address_item_name);
                            TextView telTv = (TextView) addressLayout.findViewById(R.id.address_item_tel);
                            nameTv.setText(address.name);
                            telTv.setText(address.mobile);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                processDialog.dismiss();
            }
        });
    }

    /**
     * 提交订单
     */
    private void submitOrder() {
        if (address == null) {
            ToastUtil.showToast("请选择收货地址");
            return;
        }
        CartGoodsItem temp = null;
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).isSelect) {
                temp = list.get(i);
                break;
            }
        }
        if (temp == null) {
            processDialog.dismiss();
            pay("购买商品", 0.01);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        float tempTotal = 0;
        float ps = 0;
        int shop_id = 0;
        for (int i = 0; i < list.size(); i++) {
            CartGoodsItem item = list.get(i);
            if (!item.isSelect && item.shop_id == temp.shop_id) {
                tempTotal = tempTotal + item.num * item.price;
                item.isSelect = true;
                ps = item.freight;
                shop_id = item.shop_id;
                stringBuffer.append(item.product_id + ":" + item.num);
                stringBuffer.append(",");
            }
        }
        String goodString = null;
        if (stringBuffer.length() > 0) {
            goodString = stringBuffer.substring(0, stringBuffer.length() - 1);
        }
        RequestParams params = new RequestParams();
        params.put("total_price", tempTotal);
        params.put("addr_id", address.addr_id);
        params.put("shop_id", shop_id);
        int uid = SPUtil.getInt("uid");
        params.put("uid", uid);
        params.put("transport_fee", ps);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
        String time = format.format(new Date());
        params.put("create_time", time);
        params.put("goods", goodString);

        ShopingApi api = new ShopingApi();
        api.submitOrder(params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
//                {"ret":0,"result":{"order_id":33}}
                if (0 == response.optInt("ret")) {
                    JSONObject result = response.optJSONObject("result");
                    String order_id = result.optString("order_id");
                    orderIds.add(order_id);
                    submitOrder();

                } else {
                    ToastUtil.showToast(response.optString("error"));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                processDialog.dismiss();
            }
        });

    }

    /**
     * 支付宝支付
     */
    private void pay(String desc, double price) {
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
                        for (int i = 0; i < orderIds.size(); i++) {
                            changeOrder(orderIds.get(i));
                        }
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                processDialog.dismiss();
                                finish();
                            }
                        }, 5000);


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1024) {
                address = data.getParcelableExtra("bean");
                TextView nameTv = (TextView) addressLayout.findViewById(R.id.address_item_name);
                TextView telTv = (TextView) addressLayout.findViewById(R.id.address_item_tel);
                nameTv.setText(address.name);
                telTv.setText(address.mobile);
            }
        }
    }

    private void changeOrder(String order_id) {
        int uid = SPUtil.getInt("uid");
        if (uid < 1) return;
        ShopingApi api = new ShopingApi();
        api.changeOrderStatus(uid, order_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (0 == response.optInt("ret")) {
                } else {
                    ToastUtil.showToast(response.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
