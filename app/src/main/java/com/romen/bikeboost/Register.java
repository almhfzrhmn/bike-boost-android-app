package com.romen.bikeboost;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    private EditText etFullName, etEmail, etPassword, etConfirmPassword;
    private CheckBox cbTerms;
    private Button btnCreateAccount;
    private TextView tvTerms, tvPrivacy, tvLogin;
    private ImageView ivTogglePassword, ivToggleConfirmPassword;
    private ImageView ivFacebookLogin, ivGoogleLogin;

    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        initViews();
        setupListeners();

        adjustLayouttoFitScreen();
    }

    private void adjustLayouttoFitScreen() {
        android.util.DisplayMetrics metrics = new android.util.DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float screenHeightDp = metrics.heightPixels / metrics.density;

        // jika layar lebih kecil dari threshold, perkecil semua layout

        if (screenHeightDp < 700) {
            View rootView = findViewById(android.R.id.content);
            scaleViewandChildren(rootView, 0.85f);
        }
    }

    private void scaleViewandChildren(View view, float scale) {
        view.setScaleX(scale);
        view.setScaleY(scale);

        if(view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                scaleViewandChildren(group.getChildAt(i), scale);
            }
        }
    }

    private void initViews() {
        etFullName = findViewById(R.id.et_full_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        cbTerms = findViewById(R.id.cb_terms);
        btnCreateAccount = findViewById(R.id.btn_create_account);
        tvTerms = findViewById(R.id.tv_terms);
        tvPrivacy = findViewById(R.id.tv_privacy);
        tvLogin = findViewById(R.id.tv_login);
        ivTogglePassword = findViewById(R.id.iv_toggle_password);
        ivToggleConfirmPassword = findViewById(R.id.iv_toggle_confirm_password);
        ivFacebookLogin = findViewById(R.id.iv_facebook_login);
        ivGoogleLogin = findViewById(R.id.iv_google_login);

        // Set initial button state
        btnCreateAccount.setEnabled(false);
        btnCreateAccount.setAlpha(0.5f);
    }

    private void setupListeners() {
        // Password visibility toggle
        ivTogglePassword.setOnClickListener(v -> togglePasswordVisibility());
        ivToggleConfirmPassword.setOnClickListener(v -> toggleConfirmPasswordVisibility());

        // Terms checkbox listener
        cbTerms.setOnCheckedChangeListener((buttonView, isChecked) -> {
            boolean formValid = validateForm();
            btnCreateAccount.setEnabled(isChecked && formValid);
            btnCreateAccount.setAlpha(isChecked && formValid ? 1.0f : 0.5f);
        });

        // Form validation listeners
        etFullName.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateAndEnableButton();
            }
        });

        etEmail.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateAndEnableButton();
            }
        });

        etPassword.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateAndEnableButton();
            }
        });

        etConfirmPassword.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateAndEnableButton();
            }
        });

        // Button click listeners
        btnCreateAccount.setOnClickListener(v -> handleCreateAccount());
        tvLogin.setOnClickListener(v -> navigateToLogin());
        tvTerms.setOnClickListener(v -> showTermsAndConditions());
        tvPrivacy.setOnClickListener(v -> showPrivacyPolicy());

        // Social login listeners
        ivFacebookLogin.setOnClickListener(v -> handleFacebookLogin());
        ivGoogleLogin.setOnClickListener(v -> handleGoogleLogin());
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

    private void toggleConfirmPasswordVisibility() {
        if (isConfirmPasswordVisible) {
            etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ivToggleConfirmPassword.setImageResource(R.drawable.ic_eye_off);
        } else {
            etConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            ivToggleConfirmPassword.setImageResource(R.drawable.ic_eye);
        }
        etConfirmPassword.setSelection(etConfirmPassword.getText().length());
        isConfirmPasswordVisible = !isConfirmPasswordVisible;
    }

    private void validateAndEnableButton() {
        boolean formValid = validateForm();
        boolean termsChecked = cbTerms.isChecked();
        boolean shouldEnable = termsChecked && formValid;

        btnCreateAccount.setEnabled(shouldEnable);
        btnCreateAccount.setAlpha(shouldEnable ? 1.0f : 0.5f);
    }

    private boolean validateForm() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        return !TextUtils.isEmpty(fullName) &&
                !TextUtils.isEmpty(email) &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                !TextUtils.isEmpty(password) &&
                password.length() >= 6 &&
                !TextUtils.isEmpty(confirmPassword) &&
                password.equals(confirmPassword);
    }

    private void handleCreateAccount() {
        if (!validateForm()) {
            showValidationErrors();
            return;
        }

        if (!cbTerms.isChecked()) {
            Toast.makeText(this, "Please agree to the Terms & Conditions",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Show loading state
        btnCreateAccount.setEnabled(false);
        btnCreateAccount.setText("Creating Account...");

        // Simulate account creation (replace with actual implementation)
        createAccount();
    }

    private void showValidationErrors() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(fullName)) {
            etFullName.setError("Full name is required");
            etFullName.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email address");
            etEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
        } else if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
        } else if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmPassword.setError("Please confirm your password");
            etConfirmPassword.requestFocus();
        } else if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
        }
    }

    private void createAccount() {
        // TODO: Implement actual account creation logic
        // This could involve Firebase Auth, Retrofit API call, etc.

        // Simulate network delay
        new android.os.Handler().postDelayed(() -> {
            // Reset button state
            btnCreateAccount.setEnabled(true);
            btnCreateAccount.setText("Create Account");

            // Show success message
            Toast.makeText(Register.this, "Account created successfully!",
                    Toast.LENGTH_SHORT).show();

            // Navigate to main activity or login
            navigateToMain();

        }, 2000);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    private void navigateToMain() {
        // Navigate to main activity after successful registration
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showTermsAndConditions() {
        // TODO: Implement terms and conditions dialog/activity
        Toast.makeText(this, "Terms & Conditions", Toast.LENGTH_SHORT).show();
    }

    private void showPrivacyPolicy() {
        // TODO: Implement privacy policy dialog/activity
        Toast.makeText(this, "Privacy Policy", Toast.LENGTH_SHORT).show();
    }

    private void handleFacebookLogin() {
        // TODO: Implement Facebook login
        Toast.makeText(this, "Facebook login coming soon", Toast.LENGTH_SHORT).show();
    }

    private void handleGoogleLogin() {
        // TODO: Implement Google login
        Toast.makeText(this, "Google login coming soon", Toast.LENGTH_SHORT).show();
    }

    // Simple TextWatcher implementation
    private abstract static class SimpleTextWatcher implements android.text.TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(android.text.Editable s) {}
    }
}