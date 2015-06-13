package com.sthelper.sthelper.business;

public class BaseAction extends Activity
{
  public Activity mActivity;

  private void initActionBar()
  {
    ActionBar localActionBar = getActionBar();
    localActionBar.setDisplayHomeAsUpEnabled(true);
    localActionBar.setDisplayShowTitleEnabled(true);
    localActionBar.setDisplayUseLogoEnabled(false);
  }

  public void onCreate(Bundle paramBundle)
  {
    this.mActivity = this;
    super.onCreate(paramBundle);
    initActionBar();
  }
}