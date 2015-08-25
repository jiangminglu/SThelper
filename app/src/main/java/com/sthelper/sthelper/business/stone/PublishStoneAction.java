package com.sthelper.sthelper.business.stone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.api.UserApi;
import com.sthelper.sthelper.bean.Address;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.business.auth.LoginAction;
import com.sthelper.sthelper.business.profile.AddAddressAction;
import com.sthelper.sthelper.util.BitmapUtil;
import com.sthelper.sthelper.util.SPUtil;
import com.sthelper.sthelper.util.ToastUtil;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PublishStoneAction extends BaseAction {

    public static final int PHOTOHRAPH = 20;//照相
    public static final int IMAGE_CODE = 21;//相册


    LinearLayout publishimglayout;
    EditText publishstonepriceet;
    EditText publishstonenameet;
    EditText publishstoneaddresset;
    EditText publishstonecompanyet;
    EditText publishstonecompanyaddresset;
    EditText publishstonecompanytelet;
    EditText publishstoneremarket;

    private String tempPhotoPath;
    private ArrayList<String> imgList = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_stone_action);
        tempPhotoPath = app.cachePath + "/temp.jpg";

        getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_stone_actionbar_bg)));
        initActionBar("");
        initView();
    }

    private void initView() {
        publishimglayout = (LinearLayout) findViewById(R.id.publish_img_layout);
        publishstonepriceet = (EditText) findViewById(R.id.publish_stone_price_et);
        publishstonenameet = (EditText) findViewById(R.id.publish_stone_name_et);
        publishstoneaddresset = (EditText) findViewById(R.id.publish_stone_address_et);
        publishstonecompanyet = (EditText) findViewById(R.id.publish_stone_company_et);
        publishstonecompanyaddresset = (EditText) findViewById(R.id.publish_stone_company_address_et);
        publishstonecompanytelet = (EditText) findViewById(R.id.publish_stone_company_tel_et);
        publishstoneremarket = (EditText) findViewById(R.id.publish_stone_remark_et);


        addItemView(null, null);
    }

    private void addItemView(Bitmap bitmap, final String path) {
        View itemView = getLayoutInflater().inflate(R.layout.publish_img_item_layout, null);


        ImageView deleteImg = (ImageView) itemView.findViewById(R.id.delete_img);
        ImageView srcImg = (ImageView) itemView.findViewById(R.id.publish_item_img);
        if (bitmap != null) {
            srcImg.setImageBitmap(bitmap);
        } else {
            srcImg.setImageResource(R.mipmap.publish_stone_add_icon);
            deleteImg.setVisibility(View.GONE);
        }
        deleteImg.setTag(path);
        srcImg.setTag(path);
        deleteImg.setOnClickListener(deleteOnClick);
        srcImg.setOnClickListener(addOnClick);

        int side = getResources().getDimensionPixelSize(R.dimen.publish_item_img_side);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(side, side);


        if (bitmap != null) {
            publishimglayout.addView(itemView, 0, params);
        } else {
            publishimglayout.addView(itemView, publishimglayout.getChildCount(), params);
        }

        int count = publishimglayout.getChildCount();
        if (count > 3) {
            publishimglayout.removeViewAt(3);
        }
    }

    private View.OnClickListener addOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String tempPath = view.getTag() + "";
            if (tempPath == null || "".equals(tempPath) || "null".equals(tempPath)) {
                initAlertDialog();
            }
        }
    };
    private View.OnClickListener deleteOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String path = view.getTag() + "";
            int index = 0;
            for (int i = 0; i < imgList.size(); i++) {

                if (path.equals(path)) {
                    index = i;
                    break;
                }
            }
            publishimglayout.removeViewAt(index);
            addItemView(null, null);
        }
    };

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_publish_stone_action, menu);
        return true;
    }

    //    /index.php?g=api&m=User&sig=123456789987654321&t=1324567&token=6757f12e404086c15d40982e6c920ee2&
//    uid=3&a=postmsg&
//my_stone_name=精品白洞石13&
//stone_name=白洞石&
//cate_id=1&
// price=100&
// origin=巴西&
// shop_name=店&
// adress=店铺地址&
// tips=免费送货&
// tel=13988888888
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            int uid = SPUtil.getInt("uid");
            if (uid < 1) {
                Intent intent = new Intent();
                intent.setClass(mActivity, LoginAction.class);
                startActivity(intent);
                return true;
            }


            final String price = publishstonepriceet.getText().toString();
            String my_stone_name = publishstonenameet.getText().toString();
            String origin = publishstoneaddresset.getText().toString();
            String shop_name = publishstonecompanyet.getText().toString();
            String adress = publishstonecompanyaddresset.getText().toString();
            String tel = publishstonecompanytelet.getText().toString();
            String tips = publishstoneremarket.getText().toString();

            String stone_name = "哈哈哈";
            String cate_id = "1";

            processDialog.show();
            RequestParams params = new RequestParams();
            params.put("price", price);
            params.put("uid", uid);
            params.put("origin", origin);
            params.put("my_stone_name", my_stone_name);
            params.put("shop_name", shop_name);
            params.put("adress", adress);
            params.put("tel", tel);
            params.put("tips", tips);
            params.put("stone_name", stone_name);
            params.put("cate_id", cate_id);

            for (int i = 0; i < imgList.size(); i++) {
                try {
                    params.put("file", new File(imgList.get(i)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            UserApi api = new UserApi();
            api.publishStone(params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    processDialog.dismiss();
                    if (response.optInt("ret") == 0) {
                        ToastUtil.showToast("发布成功");
                        finish();
                    }else{
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
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

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

                String newPath = app.imagePath + "/" + System.currentTimeMillis() + ".jpg";
                File f = new File(newPath);
                f.createNewFile();
                FileOutputStream fOut = new FileOutputStream(f);
                newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
                fOut.flush();
                fOut.close();

                BitmapUtil.setExif(path, newPath);
                imgList.add(newPath);
                addItemView(newBitmap, newPath);
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
                String newPath = app.imagePath + "/" + System.currentTimeMillis() + ".jpg";
                File f = new File(newPath);
                f.createNewFile();
                FileOutputStream fOut = new FileOutputStream(f);
                newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
                fOut.flush();
                fOut.close();
                BitmapUtil.setExif(tempPhotoPath, newPath);
                imgList.add(newPath);
                addItemView(newBitmap, newPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
