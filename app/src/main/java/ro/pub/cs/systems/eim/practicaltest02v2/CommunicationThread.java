package ro.pub.cs.systems.eim.practicaltest02v2;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.Socket;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
public class CommunicationThread extends Thread {
    private ServerThread serverThread;
    private Socket socket;

    public CommunicationThread (ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            String input = reader.readLine();
            String op = input.split(",")[0];
            String numString1 =  input.split(",")[1];
            String numString2 = input.split(",")[2];
            int num1 = Integer.parseInt(numString1);
            int num2 = Integer.parseInt(numString2);
            int result = 0;

            if (op.equals("add")) {
                result = num1 + num2;
            } else if (op.equals("mul")) {
                Thread.sleep(2000);
                result = num1 * num2;
            }

            serverThread.setData(result);

            writer.println(result);
            writer.flush();

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
