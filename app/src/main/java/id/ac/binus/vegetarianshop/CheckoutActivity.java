package id.ac.binus.vegetarianshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

public class CheckoutActivity extends AppCompatActivity {
    Toolbar toolbar;
    Vege vege;
    TextView tvPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        settingToolbar();
        getExtra();
        initializeComponent();
        settingComponent();
    }

    private void initializeComponent() {
        tvPrice = findViewById(R.id.tvProductPrice);
    }

    private void settingComponent() {
        String price = "Rp. " + vege.getPrice();
        tvPrice.setText(price);
    }

    private void getExtra() {
        String name = getIntent().getStringExtra("Product");
        vege = Helper.mapVege.get(name);
    }

    @SuppressLint("RestrictedApi")
    private void settingToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}