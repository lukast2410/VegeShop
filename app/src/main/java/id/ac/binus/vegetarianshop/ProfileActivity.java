package id.ac.binus.vegetarianshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.service.AccountAuthService;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "Profile";
    private AccountAuthService mAuthService;
    private AccountAuthParams mAuthParam;
    Toolbar toolbar;
    ImageView ivProfile, ivGender;
    TextView tvName, tvEmail, tvGender;
    LinearLayout llLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        settingToolbar();
        initializeAccountService();
        initializeComponent();
        settingComponent();
    }

    private void initializeAccountService() {
        mAuthParam = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setEmail()
                .setAuthorizationCode()
                .createParams();

        mAuthService = AccountAuthManager.getService(this, mAuthParam);
    }

    private void settingComponent() {
        tvName.setText(Helper.name);
        if(!Helper.email.equals(""))
            tvEmail.setText(Helper.email);

        if(Helper.gender == -1) {
            tvGender.setText("Male");
            ivGender.setImageResource(R.drawable.ic_baseline_male_24);
        }else{
            tvGender.setText("Female");
            ivGender.setImageResource(R.drawable.ic_baseline_female_24);
        }

        if(!Helper.avatarUrl.equals(""))
            Glide.with(this).load(Helper.avatarUrl).centerCrop().into(ivProfile);

        llLogout.setOnClickListener(x -> {
            signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void signOut() {
        if (mAuthService == null) {
            return;
        }
        Task<Void> signOutTask = mAuthService.signOut();
        signOutTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG, "signOut Success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.i(TAG, "signOut fail");
            }
        });
    }

    private void initializeComponent() {
        ivProfile = findViewById(R.id.ivProfile);
        ivGender = findViewById(R.id.ivGender);
        tvName = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        tvGender = findViewById(R.id.tvGender);
        llLogout = findViewById(R.id.llLogout);
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