package com.ub.chat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ub.project0.R;


/**
 * ChatMessenger : Chat Application
 */
/**
 * Note : type localhost 5554
 * 		  redir add tcp:8092:8090
 * 		  exit
 * 		  
 * 		  type localhost 5556
 * 		  redir add tcp:8091:8090
 * 		  exit
 *         
 *       in case on emulator is running at 5556 and 5558
 *       then just change the string EMULATOR_5554 = "15555215556"
 *        and EMULATOR_5556 = "15555215558"
 */

public class MainActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */

	private Button senderButton;
	private TextView textView;
	private EditText editTextView;
	private String request = null;
	private Handler uiHandler;
	private static final String MAIN_ACTIVITY = "main";
	private String phonenumber;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		senderButton = (Button) findViewById(R.id.send_button);
		textView = (TextView) findViewById(R.id.text_view);
		editTextView = (EditText) findViewById(R.id.edit_text);
		senderButton.setOnClickListener(this);		
	}

	
	
	@Override
	protected void onResume() {
		super.onResume();
		//Telephony manager used as parameter to differentiate between clients.
		TelephonyManager tmgr=(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE) ;
		phonenumber = tmgr.getLine1Number();
		Log.d(MAIN_ACTIVITY, "phone number :"+phonenumber);
		
		MySocket mySocket = new MySocket();
		mySocket.setIpAddress(Constants.LOCAL_HOST);
		mySocket.setPortNumber(Constants.SERVER_PORT);
		new ServerTask(uiHandler,textView).execute(mySocket);

	}

	
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.send_button:
			//create the device as client
			request = new String();
			request = editTextView.getText().toString();
			Thread t = new Thread(new ClientThread(request,phonenumber),"Client thread");
			t.start();
			textView.append("Me :"+request +"\n");
			editTextView.setText("");
			request = null;
			break;

		default:
			break;
		}

	}
	
	
}