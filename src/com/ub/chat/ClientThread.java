package com.ub.chat;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

/**
 * This class creates client socket to communicate with the server.
 * @author vikram
 *
 */
public class ClientThread implements Runnable{

	//15555215554 5554:8092:8090
	//15555215556 5556:8091:8090
	public static final String CLIENT_THREAD_TAG = "Client thread";
	private OutputStream out;
	private String data;
	private byte [] dataBytes;
	private String port;

	public ClientThread(String data,String port) {
		this.data = data;
		this.dataBytes = data.getBytes();
		this.port = port;
	}

	public void run() {

		try {
			Socket serverSocket = null ;
			if(this.port.equalsIgnoreCase(Constants.EMULATOR_5554)) {
				serverSocket = new Socket(Constants.LOCAL_HOST,Constants.RE_DIRECTIONAL_PORT_91);
			}
			else if (this.port.equalsIgnoreCase(Constants.EMULATOR_5556)){
				serverSocket = new Socket(Constants.LOCAL_HOST,Constants.RE_DIRECTIONAL_PORT_92);
			}			
			Log.d(CLIENT_THREAD_TAG, "Connection established");
			Log.d(CLIENT_THREAD_TAG,"data to be sent :"+this.data);			
			out = serverSocket.getOutputStream();
			out.write(this.dataBytes);
			serverSocket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			Log.d(CLIENT_THREAD_TAG,"Cannot connect to the host");
		} catch (IOException e) {
			Log.d(CLIENT_THREAD_TAG,"Unable to connect to the socket");
			e.printStackTrace();
		}

	}

}
