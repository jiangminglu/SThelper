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

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.business.BaseAction;
import com.sthelper.sthelper.util.BitmapUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class OpenStoreAction extends BaseAction implements View.OnClickListener {

    public static final int PHOTOHRAPH = 20;//照相
    public static final int IMAGE_CODE = 21;//相册
    private String tempPhotoPath;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_store_action);
        initActionBar("开店");
        tempPhotoPath = app.cachePath + "/temp.jpg";
        initView();

    }

    private void initView() {
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
                            showtype.setText("食品店铺");
                        } else if (i == 1) {
                            showtype.setText("饮品店铺");
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

                String newPath = app.imagePath + "/" + System.currentTimeMillis() + ".jpg";
                File f = new File(newPath);
                f.createNewFile();
                FileOutputStream fOut = new FileOutputStream(f);
                newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
                fOut.flush();
                fOut.close();

                BitmapUtil.setExif(path, newPath);
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
                String newPath = app.imagePath + "/" + System.currentTimeMillis() + ".jpg";
                File f = new File(newPath);
                f.createNewFile();
                FileOutputStream fOut = new FileOutputStream(f);
                newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
                fOut.flush();
                fOut.close();
                BitmapUtil.setExif(tempPhotoPath, newPath);
                showimg.setImageBitmap(newBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


}
