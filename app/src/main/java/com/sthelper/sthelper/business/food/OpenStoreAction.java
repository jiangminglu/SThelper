package com.sthelper.sthelper.business.food;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.ShopingApi;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.business.auth.LoginAction;
import com.sthelper.sthelper.util.BitmapUtil;
import com.sthelper.sthelper.util.SPUtil;
import com.sthelper.sthelper.util.ToastUtil;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class OpenStoreAction extends BaseAction implements View.OnClickListener {

    public static final int PHOTOHRAPH = 20;//照相
    public static final int IMAGE_CODE = 21;//相册
    private String tempPhotoPath;
    private String filePath;

    int cate_id = 71;
    EditText openstorename;
    ImageView showimg;
    RelativeLayout addimglayout;
    TextView showtype;
    RelativeLayout selecttypelayout;
    TextView showlocation;
    RelativeLayout selectlocationlayout;
    EditText openstorearea;
    EditText openstoretel;
    EditText openstoretime;
    EditText openstoredeliveryprice;

    String status;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_store_action);
        initActionBar("开店");
        tempPhotoPath = app.cachePath + "/temp.jpg";
        status = getIntent().getStringExtra("status");
        initView();

    }

    private void initView() {

        TextView openStatusTv = (TextView) findViewById(R.id.open_status);
        if (status != null && !status.equals("")) {
            openStatusTv.setVisibility(View.VISIBLE);
            openStatusTv.setText(status);
        } else {
            openStatusTv.setVisibility(View.GONE);
        }

        openstorename = (EditText) findViewById(R.id.open_store_name);
        showimg = (ImageView) findViewById(R.id.show_img);
        addimglayout = (RelativeLayout) findViewById(R.id.add_img_layout);
        showtype = (TextView) findViewById(R.id.show_type);
        selecttypelayout = (RelativeLayout) findViewById(R.id.select_type_layout);
        showlocation = (TextView) findViewById(R.id.show_location);
        selectlocationlayout = (RelativeLayout) findViewById(R.id.select_location_layout);
        openstorearea = (EditText) findViewById(R.id.open_store_area);
        openstoretel = (EditText) findViewById(R.id.open_store_tel);
        openstoretime = (EditText) findViewById(R.id.open_store_time);
        openstoredeliveryprice = (EditText) findViewById(R.id.open_store_delivery_price);

        addimglayout.setOnClickListener(this);
        selectlocationlayout.setOnClickListener(this);
        selecttypelayout.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_open_store_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            if (status != null && !status.equals("")) {
                finish();
            } else {
                openShop();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initAlertDialog() {
        new AlertDialog.Builder(this).setTitle("操作").setItems(
                new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if (i == 0) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(tempPhotoPath)));
                            startActivityForResult(intent, PHOTOHRAPH);
                        } else if (i == 1) {
                            Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                            getAlbum.setType("image/*");
                            startActivityForResult(getAlbum, IMAGE_CODE);
                        }
                    }
                }).show();
    }

    private void initStoreDialog() {
        new AlertDialog.Builder(this).setTitle("店铺类型").setItems(
                new String[]{"食品店铺", "饮品店铺"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if (i == 0) {
                            cate_id = 71;
                            showtype.setText("食品店铺");
                        } else if (i == 1) {
                            showtype.setText("饮品店铺");
                            cate_id = 53;
                        }
                    }
                }).show();
    }

    private void initAreaDialog() {
        new AlertDialog.Builder(this).setTitle("地区").setItems(
                new String[]{"水头地区"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        showlocation.setText("水头地区");
                    }
                }).show();
    }

    @Override
    public void onClick(View view) {
        if (view == addimglayout) {
            String tempPath = view.getTag() + "";
            if (tempPath == null || "".equals(tempPath) || "null".equals(tempPath)) {
                initAlertDialog();
            }
        } else if (view == selecttypelayout) {
            initStoreDialog();
        } else if (view == selectlocationlayout) {
            initAreaDialog();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CODE) {// 相册
            if (data == null) {
                return;
            }
            Uri originalUri = data.getData();
            String path = BitmapUtil.getRealPathFromURI(this, originalUri);
            try {
                Bitmap newBitmap = BitmapUtil.getBitmapByPath(path, BitmapUtil.getOptions(path), app.screenW, app.screenH);

                filePath = app.imagePath + "/" + System.currentTimeMillis() + ".jpg";
                File f = new File(filePath);
                f.createNewFile();
                FileOutputStream fOut = new FileOutputStream(f);
                newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
                fOut.flush();
                fOut.close();

                BitmapUtil.setExif(path, filePath);
                showimg.setImageBitmap(newBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (requestCode == PHOTOHRAPH) {// 照相
            try {
                Bitmap newBitmap = BitmapUtil.getBitmapByPath(tempPhotoPath, BitmapUtil.getOptions(tempPhotoPath), app.screenW, app.screenH);
                if (newBitmap == null)
                    return;
                filePath = app.imagePath + "/" + System.currentTimeMillis() + ".jpg";
                File f = new File(filePath);
                f.createNewFile();
                FileOutputStream fOut = new FileOutputStream(f);
                newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
                fOut.flush();
                fOut.close();
                BitmapUtil.setExif(tempPhotoPath, filePath);
                showimg.setImageBitmap(newBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


    private void openShop() {
        int uid = SPUtil.getInt("uid");
        if (uid < 1) {
            Intent intent = new Intent();
            intent.setClass(mActivity, LoginAction.class);
            startActivity(intent);
            return;
        }

        String shop_name = openstorename.getText().toString();
        String addr = openstorearea.getText().toString();
        String tel = openstoretel.getText().toString();
        String business_time = openstoretime.getText().toString();
        String freight = openstoredeliveryprice.getText().toString();


        if (shop_name.equals("")) {
            ToastUtil.showToast("请输入店铺名称");
            return;
        }
        if (addr.equals("")) {
            ToastUtil.showToast("请输入店铺地址");
            return;
        }
        if (business_time.equals("")) {
            ToastUtil.showToast("请输入营业时间");
            return;
        }
        if (freight.equals("")) {
            ToastUtil.showToast("请输入配送费用");
            return;
        }

        RequestParams params = new RequestParams();
        params.put("shop_name", shop_name);
        params.put("addr", addr);
        params.put("area_id", "1");
        params.put("tel", tel);
        params.put("business_time", business_time);
        params.put("cate_id", cate_id);
        params.put("freight", freight);
        params.put("uid", uid);
        try {
            params.put("file", new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        processDialog.show();
        ShopingApi api = new ShopingApi();
        api.openShop(params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();

                if (response.optString("error") != null) {
                    ToastUtil.showToast(response.optString("error"));
                }

                if (response.optInt("ret") == 0) {
                    finish();
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
