package com.byreytiz.game.gui;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.byreytiz.game.R;
import com.byreytiz.game.gui.util.Utils;
import com.byreytiz.weikton.reg.Preferences;
import com.nvidia.devtech.NvEventQueueActivity;

public class BrGraphicSettings {
    public Activity activity;
    public LinearLayout main_layout;

    public Switch switch_dialog, s_skybox;
    public Button close, ok;
    public SeekBar snow_bar;

    public BrGraphicSettings(Activity aactivity) {
        activity = aactivity;
        main_layout = aactivity.findViewById(R.id.gs_root);
        main_layout.setVisibility(View.GONE);

        ok = aactivity.findViewById(R.id.graphic_apply);
        close = aactivity.findViewById(R.id.graphic_close);

        snow_bar = aactivity.findViewById(R.id.water_progress);

        s_skybox = aactivity.findViewById(R.id.switch_sky);
        switch_dialog = aactivity.findViewById(R.id.switch_aa);

        snow_bar.setProgress(Preferences.snowget());
        s_skybox.setChecked(NvEventQueueActivity.getInstance().getNativeSkyBox());
        switch_dialog.setChecked(NvEventQueueActivity.getInstance().getNativeDialog());

        snow_bar.setOnSeekBarChangeListener(seekBarChangeListener);

        s_skybox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                NvEventQueueActivity.getInstance().setNativeSkyBox(b);
            }
        });

        switch_dialog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                NvEventQueueActivity.getInstance().setNativeDialog(b);
            }
        });

        ok.setOnClickListener( view -> {
            HideSettings();
        });

        close.setOnClickListener( view -> {
            HideSettings();
        });
    }

    public void ShowSettings()
    {
        Utils.ShowLayout(main_layout, true);
    }

    public void HideSettings()
    {
        Utils.HideLayout(main_layout, true);
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            NvEventQueueActivity.getInstance().setNativeHudElementPosition(999, progress, 0);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
