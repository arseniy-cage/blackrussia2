package com.byreytiz.launcher.activity;

import static com.byreytiz.game.core.Config.GAME_PATH;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.byreytiz.game.R;

import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "SettingsPrefs";
    private static final String KEY_RESOLUTION_LEVEL = "Resolution";
    private static final String KEY_REFLECTIONS_LEVEL = "ReflectionsLevel";
    private static final String KEY_EFFECTS_LEVEL = "EffectsLevel";
    private static final String KEY_FPS = "Fps";
    private static final String KEY_FPS_COUNTER = "FpsCounter";
    private static final String GRAPHICS_INI_FILE = "SAMP/graphics.ini";
    private static final String GRAPHICS_INI_PATH = GAME_PATH + GRAPHICS_INI_FILE;
    private static final String SETTINGS_INI_FILE = "SAMP/settings.ini";
    private static final String SETTINGS_INI_PATH = GAME_PATH + SETTINGS_INI_FILE;

    private ConstraintLayout graph, main, reinstallDialog, nodialog;
    private Toast currentToast;
    private SeekBar seekBarRes, seekBarReflections, seekBarEffects, seekBarFps;
    private TextView resValue, reflectionsValue, effectsValue, fpsValue, mainBth, graphBth, editbth, soundBth, reinstallgame;
    private Switch switchDebug;
    private AppCompatButton reinstallbth, reinstallno;
    private Button bthno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        loadSettings();
        setupListeners();
    }

    private void initViews() {
        findViewById(R.id.back).setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        seekBarRes = findViewById(R.id.seekBar_res);
        resValue = findViewById(R.id.res_value);
        seekBarReflections = findViewById(R.id.seekBar_reflections);
        reflectionsValue = findViewById(R.id.reflections_value);
        seekBarEffects = findViewById(R.id.seekBar_effects);
        effectsValue = findViewById(R.id.effects_value);
        seekBarFps = findViewById(R.id.seekBar_fps);
        fpsValue = findViewById(R.id.fps_value);
        switchDebug = findViewById(R.id.switch_debug);
        graph = findViewById(R.id.graph_settings);
        main = findViewById(R.id.mainsettings);
        mainBth = findViewById(R.id.textView29);
        graphBth = findViewById(R.id.textView291);
        soundBth = findViewById(R.id.textView292);
        reinstallgame = findViewById(R.id.button_clean_game2);
        reinstallDialog = findViewById(R.id.dialogclean);
        reinstallbth = findViewById(R.id.button_clean_game);
        reinstallno = findViewById(R.id.button_clean_game3);
        nodialog = findViewById(R.id.dialog);
        bthno = findViewById(R.id.button12);
        editbth = findViewById(R.id.imageView30);

        graph.setVisibility(View.GONE);
        main.setVisibility(View.VISIBLE);
        reinstallDialog.setVisibility(View.GONE);
        reinstallno.setVisibility(View.GONE);
    }

    private void setupListeners() {
        setupSeekBarListener(seekBarRes, resValue, null, this::saveResolution);
        setupSeekBarListener(seekBarReflections, reflectionsValue, new String[]{"Отключены", "Низкие", "Детализированные", "Максимальные"}, this::saveReflections);
        setupSeekBarListener(seekBarEffects, effectsValue, new String[]{"Низкое", "Среднее", "Высокое"}, this::saveEffects);
        setupSeekBarListener(seekBarFps, fpsValue, null, this::saveFPS);
        mainBth.setOnClickListener(view -> ShowMain());
        graphBth.setOnClickListener(view -> ShowGraph());
        soundBth.setOnClickListener(view -> ShowSound());
        reinstallgame.setOnClickListener(view -> reinstallDialog.setVisibility(View.VISIBLE));
        nodialog.setOnClickListener(view -> reinstallDialog.setVisibility(View.VISIBLE));
        reinstallbth.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), LoaderActivity.class)));
        reinstallno.setOnClickListener(view -> reinstallDialog.setVisibility(View.GONE));
        bthno.setOnClickListener(view -> nodialog.setVisibility(View.GONE));
        editbth.setOnClickListener(view -> nodialog.setVisibility(View.VISIBLE));

        switchDebug.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveSettings();
            saveFPSCounter();
        });
    }

    private void setupSeekBarListener(SeekBar seekBar, TextView valueText, String[] labels, Runnable onChange) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (labels != null) {
                    valueText.setText(labels[progress >= 0 && progress < labels.length ? progress : 0]);
                } else {
                    valueText.setText(String.valueOf(progress));
                }
                saveSettings();
                onChange.run();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void saveSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_RESOLUTION_LEVEL, seekBarRes.getProgress());
        editor.putInt(KEY_REFLECTIONS_LEVEL, seekBarReflections.getProgress());
        editor.putInt(KEY_EFFECTS_LEVEL, seekBarEffects.getProgress());
        editor.putInt(KEY_FPS, seekBarFps.getProgress());
        editor.putBoolean(KEY_FPS_COUNTER, switchDebug.isChecked());
        editor.apply();
    }

    private void ShowMain() {
        graph.animate().alpha(0f).setDuration(300).withEndAction(() -> {
            mainBth.setTextColor(Color.WHITE);
            graphBth.setTextColor(Color.GRAY);
            soundBth.setTextColor(Color.GRAY);
            graph.setVisibility(View.GONE);
            main.setAlpha(0f);
            main.setVisibility(View.VISIBLE);
            main.animate().alpha(1f).setDuration(300).start();
        });
    }

    private void ShowGraph() {
        main.animate().alpha(0f).setDuration(300).withEndAction(() -> {
            graphBth.setTextColor(Color.WHITE);
            mainBth.setTextColor(Color.GRAY);
            soundBth.setTextColor(Color.GRAY);
            main.setVisibility(View.GONE);
            graph.setAlpha(0f);
            graph.setVisibility(View.VISIBLE);
            graph.animate().alpha(1f).setDuration(300).start();
        });
    }

    private void ShowSound() {
        tost("В разработке...");
    }

    private void loadSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int resolution = sharedPreferences.getInt(KEY_RESOLUTION_LEVEL, 100);
        int reflectionsLevel = sharedPreferences.getInt(KEY_REFLECTIONS_LEVEL, 0);
        int effectsLevel = sharedPreferences.getInt(KEY_EFFECTS_LEVEL, 0);
        int fps = sharedPreferences.getInt(KEY_FPS, 60);
        boolean fpsCounter = sharedPreferences.getBoolean(KEY_FPS_COUNTER, false);

        seekBarRes.setProgress(resolution);
        seekBarReflections.setProgress(reflectionsLevel);
        seekBarEffects.setProgress(effectsLevel);
        seekBarFps.setProgress(fps);
        switchDebug.setChecked(fpsCounter);

        updateUI(resolution, reflectionsLevel, effectsLevel, fps);
    }

    private void updateUI(int resolution, int reflectionsLevel, int effectsLevel, int fps) {
        String[] reflectionsLabels = {"Отключены", "Низкие", "Детализированные", "Максимальные"};
        String[] effectsLabels = {"Низкое", "Среднее", "Высокое"};

        resValue.setText(String.valueOf(resolution));
        reflectionsValue.setText(reflectionsLabels[reflectionsLevel]);
        effectsValue.setText(effectsLabels[effectsLevel]);
        fpsValue.setText(String.valueOf(fps));
    }

    private void saveResolution() {
        saveToIniFile(GRAPHICS_INI_PATH, "graphics", "resolution", resValue.getText().toString());
    }

    private void saveFPS() {
        saveToIniFile(SETTINGS_INI_PATH, "gui", "fps", fpsValue.getText().toString());
    }

    private void saveFPSCounter() {
        saveToIniFile(SETTINGS_INI_PATH, "gui", "fpscounter", switchDebug.isChecked() ? "1" : "0");
    }

    private void saveReflections() {
        saveToIniFile(GRAPHICS_INI_PATH, "graphics", "reflections", String.valueOf(seekBarReflections.getProgress()));
    }

    private void saveEffects() {
        saveToIniFile(GRAPHICS_INI_PATH, "graphics", "effects", String.valueOf(seekBarEffects.getProgress()));
    }

    private void saveToIniFile(String filePath, String section, String key, String value) {
        File iniFile = new File(filePath);
        try {
            if (!iniFile.exists()) {
                iniFile.getParentFile().mkdirs();
                iniFile.createNewFile();
            }
            Wini ini = new Wini(iniFile);
            ini.put(section, key, value);
            ini.store();
        } catch (IOException e) {
            e.printStackTrace();
            showToast("Ошибка при сохранении настроек.");
        }
    }

    private void showToast(String message) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        currentToast.show();
    }

    private void tost(String pon)
    {
        Toast.makeText(this, pon, Toast.LENGTH_SHORT).show();
    }
}