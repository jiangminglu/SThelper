package com.sthelper.sthelper.business.food;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.SApp;
import com.sthelper.sthelper.api.BaseApi;
import com.sthelper.sthelper.api.CommonApi;
import com.sthelper.sthelper.api.ShopingApi;
import com.sthelper.sthelper.bean.FoodStoreBean;
import com.sthelper.sthelper.bean.Goods;
import com.sthelper.sthelper.bean.GoodsInfo;
import com.sthelper.sthelper.bean.StoneItemBean;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.business.adapter.FoodStoreListAdapter;
import com.sthelper.sthelper.business.adapter.GoodsItemAdapter;
import com.sthelper.sthelper.business.adapter.StoneItemAdapter;
import com.sthelper.sthelper.business.auth.LoginAction;
import com.sthelper.sthelper.business.stone.StoneInfoAction;
import com.sthelper.sthelper.util.ImageLoadUtil;
import com.sthelper.sthelper.util.SPUtil;
import com.sthelper.sthelper.util.ToastUtil;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class FindAction extends BaseAction implements View.OnClickListener {

    LinearLayout searchshoplayout;
    LinearLayout searchgoodslayout;
    LinearLayout searchstonelayout;
    LinearLayout searchtypelayout;
    ListView searchlistview;
    EditText searchEt;

    /**
     * 0:店铺
     * 1：商品
     * 2：石头
     */
    private int type;

    private ArrayList<FoodStoreBean> shopList;
    private FoodStoreListAdapter shopAdapter;


    private ArrayList<GoodsInfo> goodsList = null;
    private GoodsItemAdapter goodsAdapter = null;
    private Dialog detailDialog;

    public ArrayList<StoneItemBean> stoneList;
    public StoneItemAdapter stoneAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_shop_action);
        initActionBar("搜索");
        initView();
    }

    private void initView() {
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_blue_actionbar_bg)));
        View view = getLayoutInflater().inflate(R.layout.search_layout, null);
        view.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(view);

        searchEt = (EditText) view.findViewById(R.id.search_et);


        searchshoplayout = (LinearLayout) findViewById(R.id.search_shop_layout);
        searchgoodslayout = (LinearLayout) findViewById(R.id.search_goods_layout);
        searchstonelayout = (LinearLayout) findViewById(R.id.search_stone_layout);
        searchtypelayout = (LinearLayout) findViewById(R.id.search_type_layout);
        searchlistview = (ListView) findViewById(R.id.search_listview);


        searchEt.setOnClickListener(this);
        searchshoplayout.setOnClickListener(this);
        searchgoodslayout.setOnClickListener(this);
        searchstonelayout.setOnClickListener(this);
        searchtypelayout.setOnClickListener(this);


        searchtypelayout.setVisibility(View.GONE);
        searchlistview.setOnItemClickListener(onItemClickListener);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchtypelayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        searchEt.addTextChangedListener(textWatcher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_find_shop_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == searchEt) {
            searchtypelayout.setVisibility(View.VISIBLE);
        } else if (view == searchshoplayout) {
            type = 0;
            searchtypelayout.setVisibility(View.GONE);
            searchShop();
        } else if (view == searchgoodslayout) {
            type = 1;
            searchtypelayout.setVisibility(View.GONE);
            searchGoods();
        } else if (view == searchstonelayout) {
            type = 2;
            searchtypelayout.setVisibility(View.GONE);
            searchStone();
        }
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (type == 0) {
                Intent intent = new Intent();
                intent.setClass(mActivity, TakingOrderAction.class);
                intent.putExtra("bean", shopList.get(i));
                intent.putExtra("type", 100);
                startActivity(intent);
            } else if (type == 2) {
                Intent intent = new Intent();
                intent.setClass(mActivity, StoneInfoAction.class);
                intent.putExtra("bean", stoneList.get(i));
                startActivity(intent);
            } else if (type == 1) {
                initDialog(goodsList.get(i));
                detailDialog.show();
            }
        }
    };

    private void initDialog(final GoodsInfo info) {
        detailDialog = new Dialog(mActivity, R.style.full_dialog);
        View view = getLayoutInflater().inflate(R.layout.goods_item_detail_layout, null);
        detailDialog.setContentView(view);
        TextView itemNameTv = (TextView) view.findViewById(R.id.goods_item_name);
        TextView itemContentTv = (TextView) view.findViewById(R.id.goods_item_desc);
        ImageView img = (ImageView) view.findViewById(R.id.goods_item_photo_img);
        itemNameTv.setText(info.product_name);
        itemContentTv.setText(info.instructions);
        ImageLoadUtil.getCommonImage(img, SApp.IMG_URL + info.photo);
        final ImageView favImg = (ImageView) view.findViewById(R.id.goods_item_favorite_img);

        final int uid = SPUtil.getInt("uid");
        if (uid < 1) {
            return;
        }
        ShopingApi api = new ShopingApi();
        api.isFavGoods(uid, info.product_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (0 == response.optInt("ret")) {
                    favImg.setTag("true");
                    favImg.setImageResource(R.mipmap.icon_favorited);

                } else if (1 == response.optInt("ret")) {
                    favImg.setTag("false");
                    favImg.setImageResource(R.mipmap.icon_favorite);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
        favImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uid < 1) {
                    Intent intent = new Intent();
                    intent.setClass(mActivity, LoginAction.class);
                    startActivity(intent);
                    return;
                }

                String tag = favImg.getTag() + "";
                if ("true".equals(tag)) {
                    delFav(uid, info.product_id, favImg);
                } else {
                    addFav(uid, info.product_id, favImg);
                }
            }
        });

    }

    private void searchShop() {

        shopList = new ArrayList<FoodStoreBean>();
        shopAdapter = new FoodStoreListAdapter(shopList, mActivity, 100);
        searchlistview.setAdapter(shopAdapter);
        processDialog.show();
        CommonApi api = new CommonApi();
        api.searchShop(searchEt.getText().toString(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                try {
                    JsonNode node = BaseApi.mapper.readTree(response.toString());
                    if (0 == node.path("ret").asInt()) {
                        JsonNode result = node.path("result");
                        if (result.isArray()) {
                            Gson gson = new Gson();
                            for (JsonNode item : result) {
                                FoodStoreBean bean = gson.fromJson(item.toString(), FoodStoreBean.class);
                                shopList.add(bean);
                            }
                            shopAdapter.notifyDataSetChanged();
                        }
                    } else {
                        String error = node.path("result").textValue();
                        ToastUtil.showToast(error);
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

    private void searchGoods() {

        goodsList = new ArrayList<GoodsInfo>();
        goodsAdapter = new GoodsItemAdapter(goodsList, mActivity, 100);
        searchlistview.setAdapter(goodsAdapter);
        CommonApi api = new CommonApi();
        api.searchGoods(searchEt.getText().toString(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            JsonNode node = BaseApi.mapper.readTree(response.toString());
                            if (0 == node.path("ret").asInt()) {
                                JsonNode result = node.path("result");
                                for (JsonNode bean : result) {
                                    GoodsInfo info = new GoodsInfo();
                                    info.product_name = bean.path("product_name").asText();
                                    info.area_id = bean.path("area_id").asInt();
                                    info.business_id = bean.path("business_id").asInt();
                                    info.cate_id = bean.path("cate_id").asInt();
                                    info.product_id = bean.path("product_id").asInt();
                                    info.price = bean.path("price").asDouble();
                                    info.shop_id = bean.path("shop_id").asInt();
                                    info.photo = bean.path("photo").asText();
                                    info.instructions = bean.path("instructions").asText();
                                    goodsList.add(info);
                                }
                                goodsAdapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject
                            errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                }

        );
    }

    private void searchStone() {
        stoneList = new ArrayList<StoneItemBean>();
        stoneAdapter = new StoneItemAdapter(stoneList, mActivity);
        searchlistview.setAdapter(stoneAdapter);

        processDialog.show();
        CommonApi api = new CommonApi();
        api.searchStone(searchEt.getText().toString(), new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                processDialog.dismiss();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                if (0 == response.optInt("ret")) {
                    try {
                        JsonNode node = BaseApi.mapper.readTree(response.optString("result"));
                        for (JsonNode item : node) {
                            StoneItemBean bean = BaseApi.mapper.readValue(item.toString(), StoneItemBean.class);
                            stoneList.add(bean);
                        }
                        stoneAdapter.notifyDataSetChanged();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void addFav(int uid, int goods_id, final ImageView img) {
        ShopingApi api = new ShopingApi();
        api.addFav(uid, goods_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (response.optInt("ret") == 0) {
                    ToastUtil.showToast("添加收藏成功");
                    img.setImageResource(R.mipmap.icon_favorited);
                    img.setTag("true");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    /**
     * 加入到购物车
     *
     * @param position
     */
    public void add2Car(int position) {
        GoodsInfo info = goodsList.get(position);

        addGoods2cart(info);
    }

    /**
     * 添加商品到购物车
     *
     * @param goodsInfo
     */
    private void addGoods2cart(final GoodsInfo goodsInfo) {
        int uid = SPUtil.getInt("uid");
        if (uid < 1) {
            Intent intent = new Intent();
            intent.setClass(mActivity, LoginAction.class);
            startActivity(intent);
            return;
        }
        processDialog.show();
        ShopingApi api = new ShopingApi();
        api.add2Cart(uid, goodsInfo.product_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                ToastUtil.showToast(response.optString("error"));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                processDialog.dismiss();
            }
        });
    }

    private void delFav(int uid, int goods_id, final ImageView img) {
        ShopingApi api = new ShopingApi();
        api.delFavGoods(uid, goods_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (response.optInt("ret") == 0) {
                    ToastUtil.showToast("删除收藏成功");
                    img.setImageResource(R.mipmap.icon_favorite);
                    img.setTag("false");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (searchtypelayout.getVisibility() == View.VISIBLE) {
            searchtypelayout.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }
}
