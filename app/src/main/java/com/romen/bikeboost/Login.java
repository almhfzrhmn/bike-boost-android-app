package com.romen.bikeboost;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private CheckBox rememberMe;
    private Button btnLogin;
    private TextView tvForget, tvSignUp;
    private ImageView ivTogglePassword;
    private ImageView ivFacebookLogin, ivGoogleLogin;

    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        initViews();
        setupListeners();
    }

    private void initViews() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        ivTogglePassword = findViewById(R.id.iv_toggle_password);
        rememberMe = findViewById(R.id.rememberMe);
        btnLogin = findViewById(R.id.btnLogin);
        tvForget = findViewById(R.id.tvForget);
        tvSignUp = findViewById(R.id.tvSignup);
        ivFacebookLogin = findViewById(R.id.iv_facebook_login);
        ivGoogleLogin = findViewById(R.id.iv_google_login);

        // Set initial button state
        btnLogin.setEnabled(false);
        btnLogin.setAlpha(0.5f);
    }

    private void setupListeners() {
        ivTogglePassword.setOnClickListener(v -> togglePasswordVisibility());

        // Enable/disable login button based on checkbox and form validity
        rememberMe.setOnCheckedChangeListener((buttonView, isChecked) -> validateAndEnableButton());

        // Text change listeners
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateAndEnableButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        etEmail.addTextChangedListener(watcher);
        etPassword.addTextChangedListener(watcher);

        btnLogin.setOnClickListener(v -> performLogin());

        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });

        tvForget.setOnClickListener(v -> {
            Toast.makeText(this, "Forgot password clicked", Toast.LENGTH_SHORT).show();
            // TODO: Tambahkan intent ke halaman reset password jika ada
        });
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ivTogglePassword.setImageResource(R.drawable.ic_eye_off);
        } else {
            etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            ivTogglePassword.setImageResource(R.drawable.ic_eye);
        }
        etPassword.setSelection(etPassword.getText().length());
        isPasswordVisible = !isPasswordVisible;
    }

    private boolean validateForm() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        boolean isEmailValid = !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
        boolean isPasswordValid = !TextUtils.isEmpty(password) && password.length() >= 6;

        return isEmailValid && isPasswordValid;
    }

    private void validateAndEnableButton() {
        boolean isFormValid = validateForm();
        boolean isChecked = rememberMe.isChecked();
        btnLogin.setEnabled(isFormValid && isChecked);
        btnLogin.setAlpha((isFormValid && isChecked) ? 1.0f : 0.5f);
    }

    private void performLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (validateForm()) {
            // ✅ Simpan login state
            getSharedPreferences("auth", MODE_PRIVATE)
                    .edit()
                    .putBoolean("isLoggedIn", true)
                    .apply();

            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

            // ✅ Arahkan ke Dashboard
            Intent intent = new Intent(Login.this, Dashboard.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Please enter valid email and password", Toast.LENGTH_SHORT).show();
        }
    }
}
