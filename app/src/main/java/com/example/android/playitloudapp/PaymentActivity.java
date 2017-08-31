package com.example.android.playitloudapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by rajnish on 30/8/17.
 */

public class PaymentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        TextView detailPaymentApi = (TextView) findViewById(R.id.api_detail);
        detailPaymentApi.setText(getString(R.string.payment_detail));

        View.OnClickListener detailsButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri payPalUri = Uri.parse("https://developer.paypal.com/webapps/developer/docs/api/");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, payPalUri);
                startActivity(webIntent);
            }
        };

        Button detailsButton = (Button) findViewById(R.id.button_api_details);

        detailsButton.setOnClickListener(detailsButtonListener);

    }
}
