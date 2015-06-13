package com.sthelper.sthelper.business.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sthelper.sthelper.R;
import com.sthelper.sthelper.business.BaseAction;

public class LoginAction extends BaseAction {

    private Button loginBt;
    private EditText passwordEt;
    private TextView registerTv;
    private EditText usernameEt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_action);
        init();
    }

    private void init() {
        this.usernameEt = ((EditText) findViewById(R.id.login_username_et));
        this.passwordEt = ((EditText) findViewById(R.id.login_password_et));
        this.registerTv = ((TextView) findViewById(R.id.goto_register));
        this.registerTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                Intent localIntent = new Intent();
                localIntent.setClass(LoginAction.this, RegisterAction.class);
                LoginAction.this.startActivity(localIntent);
            }
        });
        this.loginBt = ((Button) findViewById(R.id.login_btn));
        this.loginBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
