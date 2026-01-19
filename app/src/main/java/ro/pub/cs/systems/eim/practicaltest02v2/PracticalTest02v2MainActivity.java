package ro.pub.cs.systems.eim.practicaltest02v2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PracticalTest02v2MainActivity extends AppCompatActivity {
    private EditText addressEditText;
    private EditText portEditText;
    private EditText inputEditText;

    private Button sendButton;
    private TextView resultTextView;

    private ServerThread serverThread = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02v2_main);

        addressEditText = findViewById(R.id.client_address_edit_text);
        portEditText = findViewById(R.id.client_port_edit_text);
        inputEditText = findViewById(R.id.input_edit_text);
        sendButton = findViewById(R.id.send_button);
        resultTextView = findViewById(R.id.result_text_view);


        sendButton.setOnClickListener(view -> {
            String port = portEditText.getText().toString();
            String address = addressEditText.getText().toString();
            int result = serverThread.getData();
            if (port.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Server port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }

            serverThread = new ServerThread(Integer.parseInt(port));
            serverThread.start();
            Toast.makeText(getApplicationContext(), "Server started on port " + port, Toast.LENGTH_SHORT).show();

            resultTextView.setText("");
            new ClientTask(address, Integer.parseInt(port), result, resultTextView).execute();
        });
    }

    @Override
    protected void onDestroy() {
        if (serverThread != null) {
            serverThread.stopServer();
        }
        super.onDestroy();
    }
}
