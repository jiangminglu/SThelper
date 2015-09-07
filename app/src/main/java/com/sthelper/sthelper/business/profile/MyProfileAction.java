package com.sthelper.sthelper.business.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sthelper.sthelper.R;
import com.sthelper.sthelper.SApp;
import com.sthelper.sthelper.api.BaseApi;
import com.sthelper.sthelper.api.UserApi;
import com.sthelper.sthelper.bean.UserInfo;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.util.BitmapUtil;
import com.sthelper.sthelper.util.ImageLoadUtil;
import com.sthelper.sthelper.util.SPUtil;
import com.sthelper.sthelper.util.ToastUtil;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyProfileAction extends BaseAction {

    public static final int PHOTOHRAPH = 20;//照相
    public static final int IMAGE_CODE = 21;//相册
    public static final int PHOTO_REQUEST_CUT = 22;

    ImageView useravatarimg;
    RelativeLayout useravatarlayout;
    TextView usernametv;
    RelativeLayout usernamelayout;
    TextView userteltv;
    TextView usertotaltv;

    private String tempPhotoPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_action);
        initActionBar("个人信息");
        initView();
        getUserInfo();
        tempPhotoPath = app.cachePath + "/temp.jpg";
    }

    private void initView() {
        useravatarimg = (ImageView) findViewById(R.id.user_avatar_img);
        useravatarlayout = (RelativeLayout) findViewById(R.id.user_avatar_layout);
        usernametv = (TextView) findViewById(R.id.user_name_tv);
        usernamelayout = (RelativeLayout) findViewById(R.id.user_name_layout);
        userteltv = (TextView) findViewById(R.id.user_tel_tv);
        usertotaltv = (TextView) findViewById(R.id.user_total_tv);

        useravatarlayout.setOnClickListener(onClickListener);
        usernamelayout.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.user_avatar_layout) {
                initAlertDialog();
            } else if (view.getId() == R.id.user_name_layout) {
                Intent intent = new Intent();
                intent.setClass(mActivity, EditNameAction.class);
                startActivityForResult(intent, 100);
            }
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

    private void getUserInfo() {
        int uid = SPUtil.getInt("uid");
        String token = SPUtil.getString("token");
        if (uid < 1) return;
        processDialog.show();
        final UserApi api = new UserApi();
        api.getUserInfo(uid, token, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                try {
                    JsonNode node = BaseApi.mapper.readTree(response.toString());
                    if (node.path("ret").asInt() == 0) {
                        UserInfo userInfo = BaseApi.mapper.readValue(node.findPath("userinfo").toString(), UserInfo.class);
                        app.currentUserInfo = userInfo;
                        refreshUI();
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

    private void refreshUI() {
        if (app.currentUserInfo == null) return;
        usernametv.setText(app.currentUserInfo.nickname);
        ImageLoadUtil.getCircleAvatarImage(useravatarimg, SApp.IMG_URL + app.currentUserInfo.face);
        userteltv.setText(app.currentUserInfo.account);
        usertotaltv.setText(app.currentUserInfo.integral + "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CODE) {// 相册
            if (data == null) {
                return;
            }
            Uri originalUri = data.getData();
            startPhotoZoom(originalUri);
        } else if (requestCode == PHOTOHRAPH) {// 照相
            File file = new File(tempPhotoPath);
            startPhotoZoom(Uri.fromFile(file));
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            sentPicToNext(data);
        } else if (requestCode == 100) {
            String name = data.getStringExtra("name");
            usernametv.setText(name);
            editUserInfo(null, name);
        }
    }

    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    private void sentPicToNext(Intent picdata) {
        Bundle bundle = picdata.getExtras();
        if (bundle != null) {
            Bitmap photo = bundle.getParcelable("data");
            if (photo == null) {
                useravatarimg.setImageResource(R.mipmap.default_avatar);
            } else {
//                useravatarimg.setImageBitmap(photo);
                String newPath = app.imagePath + "/" + System.currentTimeMillis() + ".jpg";
                File f = new File(newPath);
                try {
                    f.createNewFile();
                    FileOutputStream fOut = new FileOutputStream(f);
                    photo.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
                    fOut.flush();
                    fOut.close();
                    ImageLoadUtil.getCircleAvatarImage(useravatarimg, "file://" + newPath);
                    editUserInfo(f, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void editUserInfo(File file, String nickname) {
        processDialog.show();
        UserApi api = new UserApi();
        int uid = SPUtil.getInt("uid");
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        if (nickname != null && nickname.trim().length() > 0) {
            params.put("nickname", nickname);
        }
        try {
            params.put("file", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        api.editUserInfo(params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                processDialog.dismiss();
                if (response.optInt("ret") == 0) {

                } else {
                    ToastUtil.showToast(response.optString("error"));
                    if (3001 == response.optInt("ret"))
                        SPUtil.save("uid", 0);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                processDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}
