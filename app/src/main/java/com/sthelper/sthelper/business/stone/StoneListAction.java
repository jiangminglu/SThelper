package com.sthelper.sthelper.business.stone;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.BaseApi;
import com.sthelper.sthelper.api.UserApi;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.business.food.FindAction;
import com.sthelper.sthelper.view.sortlistview.CharacterParser;
import com.sthelper.sthelper.view.sortlistview.PinyinComparator;
import com.sthelper.sthelper.view.sortlistview.SideBar;
import com.sthelper.sthelper.view.sortlistview.SortAdapter;
import com.sthelper.sthelper.view.sortlistview.SortModel;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class StoneListAction extends BaseAction {

    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;

    private ArrayList<SortModel> list = new ArrayList<SortModel>();

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;

    private TextView type1, type2, currentType;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stone_list_action);
        getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_stone_actionbar_bg)));
        initActionBar("水头石材");
        init();
        loadData(1);
    }

    private void init() {

        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        type1 = (TextView) findViewById(R.id.stone_list_type1);
        type2 = (TextView) findViewById(R.id.stone_list_type2);
        type1.setOnClickListener(this);
        type2.setOnClickListener(this);

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView = (ListView) findViewById(R.id.stone_listview);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent();
                intent.setClass(mActivity, StoneItemListAction.class);
                intent.putExtra("stone_id",list.get(position).stone_id);
                startActivity(intent);
            }
        });


        adapter = new SortAdapter(this, list);
        sortListView.setAdapter(adapter);

        findViewById(R.id.stone_list_public).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mActivity, PublishStoneAction.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_food_store_list_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setClass(mActivity, FindAction.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == type1) {
            type1.setBackgroundResource(R.drawable.stone_type);
            type1.setTextColor(getResources().getColor(R.color.app_stone_actionbar_bg));
            type2.setBackground(null);
            type2.setTextColor(Color.parseColor("#a0a0a0"));
            loadData(1);
        } else if (view == type2) {
            type2.setBackgroundResource(R.drawable.stone_type);
            type2.setTextColor(getResources().getColor(R.color.app_stone_actionbar_bg));
            type1.setTextColor(Color.parseColor("#a0a0a0"));
            type1.setBackground(null);
            loadData(2);
        }
    }

    private void loadData(int cate_id) {
        processDialog.show();
        UserApi api = new UserApi();
        api.getStoneList(cate_id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                if (response.optInt("ret") == 0) {
                    try {
                        JsonNode node = BaseApi.mapper.readTree(response.optString("result"));
                        list.removeAll(list);
                        for (JsonNode item : node) {
                            SortModel sortModel = BaseApi.mapper.readValue(item.toString(), SortModel.class);
                            String pinyin = characterParser.getSelling(sortModel.stone_name);
                            String sortString = pinyin.substring(0, 1).toUpperCase();
                            // 正则表达式，判断首字母是否是英文字母
                            if (sortString.matches("[A-Z]")) {
                                sortModel.sortLetters = sortString.toUpperCase();
                            } else {
                                sortModel.sortLetters = "#";
                            }
                            list.add(sortModel);
                        }
                        Collections.sort(list, pinyinComparator);
                        adapter.notifyDataSetChanged();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
