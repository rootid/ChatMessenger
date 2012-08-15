package com.ub.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;


/**
 * This class acts as background server.
 * @author vikram
 *
 */
public class ServerTask extends AsyncTask<MySocket, String , String> {

	public static final String SERVER_TASK_TAG = "server task";
	private ServerSocket serverSocket = null;
	private Socket clientSocket = null;
	private BufferedReader in = null;
	private TextView text = null;
	private String line = null;
	
	public ServerTask(Handler handler,TextView txt) {
		try {
			serverSocket = new ServerSocket(Constants.SERVER_PORT);
			this.text = txt;

		} catch (IOException e) {
			Log.d(SERVER_TASK_TAG,"Failed to create the server socket");
			e.printStackTrace();
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(MySocket... mySockets) {

		if(serverSocket != null) {
			try {
				while(true) {
					clientSocket	= serverSocket.accept();
					
					in = new BufferedReader(new InputStreamReader
							(clientSocket.getInputStream()));
					line = new String();
					while((line = in.readLine()) != null) {
						Log.d(SERVER_TASK_TAG, line);
						publishProgress(line);
					}
			//TODO : close the client socket on post execute		
					//clientSocket.close();
					line = null;
				}
				
					

			} catch (IOException e) {
				Log.d(SERVER_TASK_TAG, "Could not connect to the client");
				e.printStackTrace();
			}
		}

		return "Server connected";
	}

	@Override
	protected void onProgressUpdate(String... values) {
		super.onProgressUpdate(values);
				
		for (String value : values) {
			Log.d(SERVER_TASK_TAG, "value len "+values.length + ": value " + value);	
			text.append("you :"+ value + "\n");	
		}
		
		
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
//		try {
//			clientSocket.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
			

	}



}
