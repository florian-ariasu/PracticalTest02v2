package ro.pub.cs.systems.eim.practicaltest02v2;
import java.net.ServerSocket;
import java.net.Socket;
public class ServerThread extends Thread{
    private int port;
    private ServerSocket serverSocket;
    private int data;
    public ServerThread(int port) {
        this.port = port;
    }

    public synchronized void setData(int input) {
        this.data = input;
    }

    public synchronized int getData() {
        return this.data;
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            while (Thread.currentThread().isInterrupted()) {
                Socket socket = serverSocket.accept();
                CommunicationThread communicationThread = new CommunicationThread(this, socket);
                communicationThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        interrupt();
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
