package ro.pub.cs.systems.eim.practicaltest02v2;

import android.os.AsyncTask;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTask extends Thread {
    private String address;
    private int port;
    private int result;
    private TextView resultTextView;

    public ClientTask(String address, int port, int result, TextView resultTextView) {
        this.address = address;
        this.port = port;
        this.result = result;
        this.resultTextView = resultTextView;
    }

    public void execute() {
        try {
            Socket socket = new Socket(address, port);
            if (socket == null) {
                return;
            }
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            resultTextView.setText("Result: " + result);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

        }
    }
}
