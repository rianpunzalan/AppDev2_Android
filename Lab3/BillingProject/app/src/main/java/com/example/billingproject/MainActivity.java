package com.example.billingproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText client_id_editText;
    private EditText client_name_editText;
    private EditText prd_name_editText;
    private EditText prd_price_editText;
    private EditText prd_qty_editText;
    private TextView ViewBillingTotal_textView;
    private Button TotalInputBilling_Button;
    private Button TotalRecordBilling_Button;
    private Button Prev_Billing_Button;
    private Button Next_Billing_Button;

    private Button button_billing_details;

    private static final String KEY_INDEX = "index";
    private static final String TAG = "Billing Project";

    private static final String KEY_ARRAY_LIST = "billing_array_list";

    private int currentIndex = 0;
    DecimalFormat df = new DecimalFormat("#.##");
    private ArrayList<Billing> Billings = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Billings.add(new Billing(105, "Johnston Jane","Chair",99.99,2));
        Billings.add(new Billing(108, "Fikhali Samuel","Table",139.99,1));
        Billings.add(new Billing(113, "Samson Amina","KeyUSB",14.99,2));



        client_id_editText = findViewById(R.id.client_id_editText);
        client_name_editText = findViewById(R.id.client_name_editText);
        prd_name_editText = findViewById(R.id.prd_Name_editText);
        prd_price_editText = findViewById(R.id.prd_Price_editText);
        prd_qty_editText = findViewById(R.id.prd_Qty_editText);

        ViewBillingTotal_textView = findViewById(R.id.viewTotalBilling_textView);

        TotalInputBilling_Button = findViewById(R.id.TotalInputBilling_Button);
        TotalInputBilling_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Billing Input_Bill = new Billing(Integer.parseInt(client_id_editText.getText().toString()), client_name_editText.getText().toString(), prd_name_editText.getText().toString(), Double.parseDouble(prd_price_editText.getText().toString()), Integer.parseInt(prd_qty_editText.getText().toString()));
                    double total_billing = Input_Bill.CalculateBilling();

                    String Billing_String = "Client: " + String.valueOf(Input_Bill.getClient_id()) + ", "
                            + Input_Bill.getClient_name() + "\n" +
                            "Product: " + Input_Bill.getPrd_Name() + " is "
                            + df.format(total_billing) + "$";
                    ViewBillingTotal_textView.setText(Billing_String);
                    Billings.add(Input_Bill);
                    Log.d(TAG, "Added new Billing from the user input: " + Input_Bill);
                    Toast.makeText(MainActivity.this, Billing_String, Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this, "All spaces must be filled", Toast.LENGTH_SHORT).show();
                }
            }
        });
        TotalRecordBilling_Button = findViewById(R.id.TotalRecordBilling_Button);
        TotalRecordBilling_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                client_id_editText.setText("");
                client_name_editText.setText("");
                prd_name_editText.setText("");
                prd_price_editText.setText("");
                prd_qty_editText.setText("");

                double total_billing = Billings.get(currentIndex).CalculateBilling();
                String Billing_String = "Client: "  + Billings.get(currentIndex).getClient_id() + ", "
                                        + Billings.get(currentIndex).getClient_name()+ "\n"+
                                        "Product: " + Billings.get(currentIndex).getPrd_Name() + " is "
                                        + df.format(total_billing) + "$";
                ViewBillingTotal_textView.setText(Billing_String);
                Toast.makeText(MainActivity.this, Billing_String, Toast.LENGTH_SHORT).show();


            }
        });
        Prev_Billing_Button = (Button) findViewById(R.id.Prev_Billing_Button);
        Prev_Billing_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentIndex == 0){
                    currentIndex = Billings.size() - 1;
                }else{
                    currentIndex = (currentIndex -1) % Billings.size();
                }

                TotalRecordBilling_Button.performClick();
            }
        });
        Next_Billing_Button = (Button) findViewById(R.id.Next_Billing_Button);
        Next_Billing_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex +1) % Billings.size();
                TotalRecordBilling_Button.performClick();
            }
        });

        button_billing_details = findViewById(R.id.button_billing_details);
        button_billing_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int client_id = Billings.get(currentIndex).getClient_id();
                String client_name = Billings.get(currentIndex).getClient_name();
                String prd_name = Billings.get(currentIndex).getPrd_Name();
                double prd_price = Billings.get(currentIndex).getPrd_Price();
                int prd_qty = Billings.get(currentIndex).getPrd_Qty();

                Intent intent = BillingActivity.newIntent(MainActivity.this,
                        client_id,
                        client_name,
                        prd_name,
                        prd_price,
                        prd_qty);

                startActivityIntent.launch(intent);
            }
        });

        if(savedInstanceState != null) {
            Billings.clear();
            Billings.addAll(savedInstanceState.getParcelableArrayList(KEY_ARRAY_LIST));
            currentIndex = savedInstanceState.getInt(KEY_INDEX);
            double total_billing = Billings.get(currentIndex).CalculateBilling();
            String Billing_String = "Client: "  + Billings.get(currentIndex).getClient_id() + ", "
                                    + Billings.get(currentIndex).getClient_name()+ ", "+
                                    "Product: " + Billings.get(currentIndex).getPrd_Name() + " is "
                                    + df.format(total_billing) + "$";
            ViewBillingTotal_textView.setText(Billing_String);
        }

    }//onCreate end

    ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()!= MainActivity.RESULT_OK)
                    {
                        Log.d(TAG, String.valueOf(result.getResultCode()));
                    }
                    else{
                        if(result.getData()==null){
                            Log.d(TAG, "result.getData() is null");
                            return;
                        }
                        Billing billingUpdatedInfo = BillingActivity.sendMessageBillingUpdateResult(result.getData());
                        Billings.set(currentIndex, billingUpdatedInfo);

                        double total_billing = Billings.get(currentIndex).CalculateBilling();
                        String Billing_String = "Client: " + Billings.get(currentIndex).getClient_id() + ", "
                                                + Billings.get(currentIndex).getClient_name()+ "\n"+
                                                "Product: " + Billings.get(currentIndex).getPrd_Name() + " is "
                                                + df.format(total_billing) + "$";
                        Toast.makeText(MainActivity.this, "Updates: "+Billing_String, Toast.LENGTH_SHORT).show();
                        ViewBillingTotal_textView.setText(Billing_String);
                    }
                }
            }
    );

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onSaveIntanceState() called");

        Log.d(TAG, "store CurrentIndex: "+currentIndex);
        savedInstanceState.putInt(KEY_INDEX, currentIndex);

        Log.d(TAG, "store ArrayList: "+Billings);
        savedInstanceState.putParcelableArrayList(KEY_ARRAY_LIST, Billings);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onStart(){
        super.onStart();

        Log.d(TAG, "onStart() called");
    }


    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}