package pp222du_am222sq.projectapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class DialogActivity extends AppCompatActivity {


    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        String msg = getIntent().getStringExtra("msg");
        setSupportActionBar(toolbar);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle(msg);

        // set dialog message
        alertDialogBuilder
                .setMessage("Bla bla MSG")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Runnable runnable = new Runnable() {
                            public void run() {

                                long endTime = System.currentTimeMillis() + 20 * 1000;

                                while (System.currentTimeMillis() < endTime) {
                                    synchronized (this) {
                                        try {
                                            InetAddress serverAddr = InetAddress.getByName("77.105.197.96");

                                            socket = new Socket(serverAddr, 8383);

                                            PrintWriter out = new PrintWriter(new BufferedWriter(
                                                    new OutputStreamWriter(socket.getOutputStream())),
                                                    true);
                                            out.println("phone yes lamp");
                                        } catch (UnknownHostException e) {
                                            e.printStackTrace();
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                }
                            }
                        };
                        Thread mythread = new Thread(runnable);
                        mythread.start();

                        Log.d("debug", "YES");
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("debug", "NO");
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        if (!msg.contains("Token"))
            alertDialog.show();
    }

}
