package pp222du_am222sq.projectapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private Button on_button_lamp, off_button_lamp, on_button_fan, off_button_fan;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;
    private TextView mInformationTextView;

    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getIntent().hasExtra("msg")) {
            setContentView(R.layout.activity_main_2);

            on_button_fan = (Button) findViewById(R.id.onfan);
            off_button_fan = (Button) findViewById(R.id.offfan);
            on_button_lamp = (Button) findViewById(R.id.onlamp);
            off_button_lamp = (Button) findViewById(R.id.offlamp);

            on_button_lamp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                                        out.println("phone lamp on");
                                        socket.close();
                                        break;
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

                }
            });

            off_button_lamp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                                        out.println("phone lamp off");
                                        socket.close();
                                        break;
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

                }
            });

            on_button_fan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                                        out.println("phone fan on");
                                        socket.close();
                                        break;
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

                }
            });

            off_button_fan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                                        out.println("phone fan off");
                                        socket.close();
                                        break;
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

                }
            });
            String msg = getIntent().getExtras().getString("msg", null);
            Log.d("debug", "GOT INTENT");
            if (msg != null) {
                if (msg.contains("lamp"))
                    openDialogLamp(msg);
                else
                    openDialogFan(msg);
            }
        } else {
            setContentView(R.layout.activity_main);
            mRegistrationProgressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);
            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                    SharedPreferences sharedPreferences =
                            PreferenceManager.getDefaultSharedPreferences(context);
                    boolean sentToken = sharedPreferences
                            .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                    if (sentToken) {
                        mInformationTextView.setText(getString(R.string.gcm_send_message));
                    } else {
                        mInformationTextView.setText(getString(R.string.token_error_message));
                    }
                }
            };
            mInformationTextView = (TextView) findViewById(R.id.informationTextView);

            if (checkPlayServices()) {
                // Start IntentService to register this application with GCM.
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
        Bundle bundle = getIntent().getExtras();
        Log.d("debug", "ONResume");
        if (bundle != null) {
            String notificationData = bundle.getString("msg");
            if (notificationData != null) {
                if (notificationData.contains("lamp"))
                    openDialogLamp(notificationData);
                else
                    openDialogFan(notificationData);
            }

        }

    }

    private void openDialogFan(String msg) {
        Log.d("debug", "Inside open dialog. Msg: " + msg);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Temperature warning!");

        // set dialog message
        alertDialogBuilder
                .setMessage(msg + ". Choose an option:")
                .setCancelable(false)
                .setPositiveButton("Fan on", new DialogInterface.OnClickListener() {
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
                                            out.println("phone fan on");
                                            socket.close();
                                            break;
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

                        Log.d("debug", "FAN ON");
                    }
                })
                .setNegativeButton("Fan off", new DialogInterface.OnClickListener() {
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
                                            out.println("phone fan off");
                                            socket.close();
                                            break;
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

                        Log.d("debug", "FAN OFF");

                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        if (!msg.contains("Token"))
            alertDialog.show();
    }


    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private void openDialogLamp(String msg) {


        Log.d("debug", "Inside open dialog. Msg: " + msg);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Luminosity warning!");

        // set dialog message
        alertDialogBuilder
                .setMessage(msg + ". Choose an option:")
                .setCancelable(false)
                .setPositiveButton("Lamp on", new DialogInterface.OnClickListener() {
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
                                            out.println("phone lamp on");
                                            socket.close();
                                            break;
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

                        Log.d("debug", "LAMP ON");
                    }
                })
                .setNegativeButton("Lamp off", new DialogInterface.OnClickListener() {
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
                                            out.println("phone lamp off");
                                            socket.close();
                                            break;
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

                        Log.d("debug", "LAMP OFF");

                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        if (!msg.contains("Token"))
            alertDialog.show();
    }

}

