package com.imrokraft.bluetoothnew;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ChatActivity extends ActionBarActivity {

	public final int REQUEST_ENABLE_BT_CONST=1;
	private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
	BluetoothAdapter mBluetoothAdapter;
	boolean bluetoothEnabled=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sample_content_fragment);
		if (savedInstanceState == null) {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			BluetoothChatFragment fragment = new BluetoothChatFragment();
			transaction.replace(R.id.sample_content_fragment1, fragment);
			transaction.commit();
		}

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if(mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()){
			bluetoothEnabled=true;
			new ConnectionAcceptThread(mBluetoothAdapter).start();
		}




	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch ( item.getItemId()) {


		case R.id.action_scan_devices:
			Intent serverIntent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
			return true;

		case R.id.action_turn_on:
			Toast.makeText(getApplicationContext(), "You pressed Turn On/Off",Toast.LENGTH_SHORT).show();
			checkBluetoothStatus();
			return true;

		default:
			break;
		}
		return true;
	}
	//
	private void checkBluetoothStatus(){
		if (mBluetoothAdapter == null) {
			// Device does not support Bluetooth
			Toast.makeText(getApplicationContext(), "No Bluetooth found",Toast.LENGTH_SHORT).show();
			bluetoothEnabled=false;
		}
		else if (!mBluetoothAdapter.isEnabled()) {
			startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT_CONST);
			Toast.makeText(getApplicationContext(), "Please Turn on Bluetooth", Toast.LENGTH_LONG).show();

		}
		else if(mBluetoothAdapter!=null && mBluetoothAdapter.isEnabled()){
			Toast.makeText(getApplicationContext(), "Turning off Bluetooth", Toast.LENGTH_LONG).show();
			mBluetoothAdapter.disable();
		}
	}
	
//			public void sendMessage(String myMessage){
//		
//				if(myMessage!=null){
//					new ConnectToDeviceThread(currentDevice, myMessage).start();
//				}else{
//					Toast.makeText(getApplicationContext(), "Please Enter a Messsage to send", Toast.LENGTH_SHORT).show();
//				}
//		
//		
//			}

}

