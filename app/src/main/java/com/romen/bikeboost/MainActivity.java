package com.romen.bikeboost;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tombol Start Now
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Let's Boost!", Toast.LENGTH_SHORT).show();

            // Navigasi ke halaman Register
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);

            // Tambahkan efek transisi (fade in - fade out)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        // Logo dengan efek gradasi "Boost"
        TextView logoText = findViewById(R.id.logo);
        logoText.post(() -> applyGradientToBoost(logoText));
    }

    private void applyGradientToBoost(TextView textView) {
        String fullText = textView.getText().toString();
        String targetWord = "Boost";

        int start = fullText.indexOf(targetWord);
        if (start == -1) return;

        int end = start + targetWord.length();
        SpannableString spannable = new SpannableString(fullText);

        spannable.setSpan(new GradientSpan(targetWord), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable);
    }

    private static class GradientSpan extends CharacterStyle implements UpdateAppearance {
        private final String text;

        public GradientSpan(String text) {
            this.text = text;
        }

        @Override
        public void updateDrawState(TextPaint tp) {
            float width = tp.measureText(text);
            Shader shader = new LinearGradient(
                    0, 0, width, 0,
                    new int[]{Color.parseColor("#FF512F"), Color.parseColor("#DD2476")},
                    null,
                    Shader.TileMode.CLAMP
            );
            tp.setShader(shader);
        }
    }
}
