package id.ac.binus.vegetarianshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class VegeDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    Vege vege;
    ImageView ivProductImage;
    TextView tvProductName, tvProductPrice, tvProductDesc;
    Button buyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vege_detail);
        settingToolbar();
        getExtra();
        initializeComponent();
        settingComponent();
    }

    private void settingComponent() {
        ivProductImage.setImageResource(vege.getImageId());
        tvProductName.setText(vege.getName());
        String price = "Rp. " + vege.getPrice();
        tvProductPrice.setText(price);
        tvProductDesc.setText(vege.getDescription());

        buyBtn.setOnClickListener(x -> {
            Intent intent = new Intent(this, CheckoutActivity.class);
            intent.putExtra("Product", vege.getName());
            startActivity(intent);
        });
    }

    private void initializeComponent() {
        ivProductImage = findViewById(R.id.ivProductImage);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductDesc = findViewById(R.id.tvProductDesc);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        buyBtn = findViewById(R.id.buyBtn);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.qrcode){
            Intent intent = new Intent(this, ShowCodeActivity.class);
            intent.putExtra("Product", vege.getName());
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}