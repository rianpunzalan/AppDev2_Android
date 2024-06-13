package com.example.billingproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BillingActivity extends AppCompatActivity {

    private EditText edit_client_id;
    private EditText edit_client_name;
    private EditText edit_prd_name;
    private EditText edit_prd_price;
    private EditText edit_prd_qty;
    private Button button_billing_update;
    private static final String EXTRA_CLIENT_ID = "com.example.billingproject.client_id";
    private static final String EXTRA_CLIENT_NAME = "com.example.billingproject.client_name";
    private static final String EXTRA_PRD_NAME = "com.example.billingproject.prd_Name";
    private static final String EXTRA_PRD_PRICE = "com.example.billingproject.prd_Price";
    private static final String EXTRA_PRD_QTY = "com.example.billingproject.prd_Qty";

    private int client_id_retrieve;
    private String client_name_retrieve;
    private String prd_name_retrieve;
    private double prd_price_retrieve;
    private int prd_qty_retrieve;
    public static Intent newIntent(Context packageContext,
                                   int client_id,
                                   String client_name,
                                   String prd_name,
                                   double prd_price,
                                   int prd_qty)
    {
     Intent intent = new Intent(packageContext, BillingActivity.class);
     intent.putExtra(EXTRA_CLIENT_ID, client_id);
     intent.putExtra(EXTRA_CLIENT_NAME, client_name);
     intent.putExtra(EXTRA_PRD_NAME, prd_name);
     intent.putExtra(EXTRA_PRD_PRICE,prd_price);
     intent.putExtra(EXTRA_PRD_QTY,prd_qty);

     return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_billing);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        client_id_retrieve = getIntent().getIntExtra(EXTRA_CLIENT_ID,0);
        client_name_retrieve =getIntent().getStringExtra(EXTRA_CLIENT_NAME);
        prd_name_retrieve = getIntent().getStringExtra(EXTRA_PRD_NAME);
        prd_price_retrieve = getIntent().getDoubleExtra(EXTRA_PRD_PRICE,0);
        prd_qty_retrieve = getIntent().getIntExtra(EXTRA_PRD_QTY,0);

        edit_client_id = findViewById(R.id.edit_client_id);
        edit_client_id.setText(String.valueOf(client_id_retrieve));

        edit_client_name = findViewById(R.id.edit_client_name);
        edit_client_name.setText(client_name_retrieve);

        edit_prd_name = findViewById(R.id.edit_prd_name);
        edit_prd_name.setText(prd_name_retrieve);

        edit_prd_price = findViewById(R.id.edit_prd_price);
        edit_prd_price.setText(String.valueOf(prd_price_retrieve));

        edit_prd_qty = findViewById(R.id.edit_prd_qty);
        edit_prd_qty.setText(String.valueOf(prd_qty_retrieve));

        button_billing_update = findViewById(R.id.button_billing_update);

        button_billing_update.setOnClickListener(view -> {

            setBillingUpdateResult(
                    Integer.parseInt(edit_client_id.getText().toString()),
                    edit_client_name.getText().toString(),
                    edit_prd_name.getText().toString(),
                    Double.parseDouble(edit_prd_price.getText().toString()),
                    Integer.parseInt(edit_prd_qty.getText().toString())
            );
        });



    }

    public void setBillingUpdateResult(int client_id, String client_name, String prd_name, double prd_price, int prd_qty)
    {
        Intent dataIntent = new Intent();
        dataIntent.putExtra(EXTRA_CLIENT_ID,client_id);
        dataIntent.putExtra(EXTRA_CLIENT_NAME,client_name);
        dataIntent.putExtra(EXTRA_PRD_NAME,prd_name);
        dataIntent.putExtra(EXTRA_PRD_PRICE,prd_price);
        dataIntent.putExtra(EXTRA_PRD_QTY,prd_qty);

        setResult(RESULT_OK, dataIntent);
        Toast.makeText(BillingActivity.this, "Billing Info Update Success.", Toast.LENGTH_SHORT).show();

    }
    public static Billing sendMessageBillingUpdateResult(Intent resultIntent)
    {
        Billing updated_billing_info = new Billing();
        updated_billing_info.setClient_id(resultIntent.getIntExtra(EXTRA_CLIENT_ID,0));
        updated_billing_info.setClient_name(resultIntent.getStringExtra(EXTRA_CLIENT_NAME));
        updated_billing_info.setPrd_Name(resultIntent.getStringExtra(EXTRA_PRD_NAME));
        updated_billing_info.setPrd_Price(resultIntent.getDoubleExtra(EXTRA_PRD_PRICE,0));
        updated_billing_info.setPrd_Qty(resultIntent.getIntExtra(EXTRA_PRD_QTY,0));

        return updated_billing_info;
    }
}