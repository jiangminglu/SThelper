package com.sthelper.sthelper.business.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.business.BaseAction;

public class EditNameAction extends BaseAction {

    EditText editnameet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name_action);
        initActionBar("修改名称");
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_name_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            String name = editnameet.getText().toString();
            Intent intent = new Intent();
            intent.putExtra("name",name);
            setResult(RESULT_OK,intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        editnameet = (EditText) findViewById(R.id.edit_name_et);
    }
}
