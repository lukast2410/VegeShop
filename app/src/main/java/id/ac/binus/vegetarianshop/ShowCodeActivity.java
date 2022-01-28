package id.ac.binus.vegetarianshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ShowCodeActivity extends AppCompatActivity {
    Toolbar toolbar;
    Vege vege;
    TextView tvProductName;
    ImageView ivProductCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_code);
        settingToolbar();
        getExtra();
        initializeComponent();
        settingComponent();
    }

    private void settingComponent() {
        tvProductName.setText(vege.getName());

        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(vege.getName(), BarcodeFormat.QR_CODE, 512, 512);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            ivProductCode.setImageBitmap(bitmap);
        }catch (Exception e){

        }
    }

    private void initializeComponent() {
        tvProductName = findViewById(R.id.tvProductName);
        ivProductCode = findViewById(R.id.ivProductCode);
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