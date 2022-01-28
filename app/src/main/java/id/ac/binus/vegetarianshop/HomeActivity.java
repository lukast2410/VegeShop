package id.ac.binus.vegetarianshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;

import java.util.Vector;

import id.ac.binus.vegetarianshop.adapter.ProductAdapter;

public class HomeActivity extends AppCompatActivity implements ProductAdapter.ProductClickListener {
    public static final int DEFAULT_VIEW = 0x22;
    private static final int REQUEST_CODE_SCAN = 0X01;
    Toolbar toolbar;
    RecyclerView rvProducts;
    ProductAdapter adapter;
    Vector<Vege> veges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        settingToolbar();
        veges = Helper.veges;
        settingListProduct();
    }

    private void settingListProduct() {
        rvProducts = findViewById(R.id.rvProducts);
        rvProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvProducts.setItemAnimator(new DefaultItemAnimator());
        adapter = new ProductAdapter(veges, this);
        rvProducts.setAdapter(adapter);
    }

    private void settingToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions == null || grantResults == null || grantResults.length < 2 || grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (requestCode == DEFAULT_VIEW) {
            ScanUtil.startScan(this, REQUEST_CODE_SCAN, new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE).create());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //receive result after your activity finished scanning
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        // Obtain the return value of HmsScan from the value returned by the onActivityResult method by using ScanUtil.RESULT as the key value.
        if (requestCode == REQUEST_CODE_SCAN) {
            Object obj = data.getParcelableExtra(ScanUtil.RESULT);
            if (obj instanceof HmsScan) {
                if (!TextUtils.isEmpty(((HmsScan) obj).getOriginalValue())) {
                    String result = ((HmsScan) obj).getOriginalValue();
                    Log.i("QR Code Result", result + " <<<<");
                    Vege vege = Helper.mapVege.get(result);
                    if(vege == null){
                        Toast.makeText(this, "Product " + result + " Not Found!", Toast.LENGTH_LONG).show();
                    }else{
                        Intent intent = new Intent(this, VegeDetailActivity.class);
                        intent.putExtra("Product", result);
                        startActivity(intent);
                    }
                }
                return;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.profile){
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }else if(item.getItemId() == R.id.scanner){
            this.requestPermissions(
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                    DEFAULT_VIEW);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void productClicked(int position) {
        Log.i("Product", "Product " + position + " Clicked");
        Intent intent = new Intent(this, VegeDetailActivity.class);
        intent.putExtra("Product", veges.get(position).getName());
        startActivity(intent);
    }
}