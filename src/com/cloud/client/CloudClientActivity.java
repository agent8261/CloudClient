package com.cloud.client;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.IntentSender.SendIntentException;
import android.view.Menu;

public class CloudClientActivity extends Activity implements
  ConnectionCallbacks, OnConnectionFailedListener
{
  private static final int REQUEST_CODE_RESOLVE_ERR = 9000;

  private ProgressDialog mConnectionProgressDialog;
  private PlusClient mPlusClient;
  private ConnectionResult mConnectionResult;

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.cloud_client_activity);
  }

  // ---------------------------------------------------------------------------

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.cloud_client, menu);
    return true;
  }

  // ---------------------------------------------------------------------------
  
  @Override
  public void onConnectionFailed(ConnectionResult result)
  {
    if(mConnectionProgressDialog.isShowing() && result.hasResolution())
    {
      // The user clicked the sign-in button already. Start to resolve
      // connection errors. Wait until onConnected() to dismiss the
      // connection dialog.
        try
        {
          result.startResolutionForResult(this, REQUEST_CODE_RESOLVE_ERR);
        }
        catch( SendIntentException e )
        {
          mPlusClient.connect();
        }
    }
    // Save the intent so that we can start an activity when the user clicks
    // the sign-in button.
    mConnectionResult = result;
  }

  // ---------------------------------------------------------------------------
  
  @Override
  public void onConnected(Bundle connectionHint) 
  { // We've resolved any connection errors.
    mConnectionProgressDialog.dismiss();
  }


  // ---------------------------------------------------------------------------
  
  @Override
  public void onDisconnected()
  {
    
  }

}
