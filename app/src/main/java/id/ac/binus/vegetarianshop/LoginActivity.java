package id.ac.binus.vegetarianshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;
import com.huawei.hms.support.api.entity.common.CommonConstant;
import com.huawei.hms.support.api.entity.safetydetect.UserDetectResponse;
import com.huawei.hms.support.api.safetydetect.SafetyDetect;
import com.huawei.hms.support.api.safetydetect.SafetyDetectClient;
import com.huawei.hms.support.api.safetydetect.SafetyDetectStatusCodes;

public class LoginActivity extends AppCompatActivity {

    public static AccountAuthService mAuthService;
    public static AccountAuthParams mAuthParam;
    public static final int REQUEST_CODE_SIGN_IN = 1000;
    public static final String TAG = "Account";
    Button loginBtn, agreeBtn;
    ImageView ivClose;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dialog = new Dialog(this);

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(x -> {
            openPrivacyDialog();
        });
    }

    private void openPrivacyDialog() {
        dialog.setContentView(R.layout.privacy_dialog);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ivClose = dialog.findViewById(R.id.ivClose);
        agreeBtn = dialog.findViewById(R.id.btnAgree);

        ivClose.setOnClickListener(x -> {
            dialog.dismiss();
        });

        agreeBtn.setOnClickListener(x -> {
            silentSignInByHwId();
        });
        dialog.show();
    }

    private void dealWithResultOfSignIn(AuthAccount authAccount) {
        Log.i(TAG, "code:" + authAccount.getAuthorizationCode());
        Helper.name = authAccount.getDisplayName();
        Helper.gender = authAccount.getGender();
        if(authAccount.getEmail() != null)
            Helper.email = authAccount.getEmail();
        if(authAccount.getAvatarUri() != null)
            Helper.avatarUrl = authAccount.getAvatarUri().toString();

        detectUser();
    }

    public void detectUser() {
        SafetyDetectClient client = SafetyDetect.getClient(this);
        client.userDetection(Helper.appId).addOnSuccessListener(new OnSuccessListener<UserDetectResponse>() {
            @Override
            public void onSuccess(UserDetectResponse userDetectResponse) {
                // Indicates communication with the service was successful.
                String responseToken = userDetectResponse.getResponseToken();
                if (!responseToken.isEmpty()) {
                    dialog.dismiss();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                // There was an error communicating with the service.
                String errorMsg;
                if (e instanceof ApiException) {
                    // An error with the HMS API contains some additional details.
                    // You can use the apiException.getStatusCode() method to get the status code.
                    ApiException apiException = (ApiException) e;
                    errorMsg = SafetyDetectStatusCodes.getStatusCodeString(apiException.getStatusCode()) + ": "
                            + apiException.getMessage();
                } else {
                    // Unknown type of error has occurred.
                    errorMsg = e.getMessage();
                }
                Log.i(TAG, "User detection fail. Error info: " + errorMsg);
            }
        });
    }

    private void silentSignInByHwId() {
        mAuthParam = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                .setEmail()
                .createParams();
        mAuthService = AccountAuthManager.getService(this, mAuthParam);

        Task<AuthAccount> task = mAuthService.silentSignIn();
        task.addOnSuccessListener(new OnSuccessListener<AuthAccount>() {
            @Override
            public void onSuccess(AuthAccount authAccount) {
                // The silent sign-in is successful. Process the returned account object AuthAccount to obtain the HUAWEI ID information.
                dealWithResultOfSignIn(authAccount);
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                // The silent sign-in fails. Use the getSignInIntent() method to show the authorization or sign-in screen.
                if (e instanceof ApiException) {
                    ApiException apiException = (ApiException) e;
                    Intent signInIntent = mAuthService.getSignInIntent();
                    signInIntent.putExtra(CommonConstant.RequestParams.IS_FULL_SCREEN, true);

                    startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SIGN_IN) {
            Log.i(TAG, "onActivitResult of sigInInIntent, request code: " + REQUEST_CODE_SIGN_IN);
            Task<AuthAccount> authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data);
            if (authAccountTask.isSuccessful()) {
                AuthAccount authAccount = authAccountTask.getResult();
                dealWithResultOfSignIn(authAccount);

                Log.i(TAG, "onActivitResult of sigInInIntent, request code: " + REQUEST_CODE_SIGN_IN);
            } else {
                Log.e(TAG, "sign in failed : " +((ApiException)authAccountTask.getException()).getStatusCode());
            }
        }
    }
}