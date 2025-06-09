package com.byreytiz.game.gui;

import android.app.Activity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.nvidia.devtech.NvEventQueueActivity;
import com.byreytiz.game.R;
import com.byreytiz.game.gui.util.Utils;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Formatter;

public class HudManager {
    public Activity activity;
    public ConstraintLayout hud_layout;

    private final NvEventQueueActivity mContext = null;

    public TextView hud_money;

    public ImageView hud_weapon;

    public ImageView hud_radar;

    public ImageView hud_x2;

    public ImageView hud_donatik;

    public ImageView hud_menu;
    public ImageView hud_micro;

    public ImageView hud_gps;

    public ImageView hud_zona, hud_seat;

    public ArrayList<ImageView> hud_wanted;

    public ProgressBar progressHP;
    public ProgressBar progressArmor;

    private boolean isHudSetPos = false;

    public HudManager(Activity aactivity) {
        activity = aactivity;

        hud_layout = aactivity.findViewById(R.id.hud_main);
        hud_layout.setVisibility(View.GONE);

        progressArmor = aactivity.findViewById(R.id.hud_armor_pb);
        progressHP = aactivity.findViewById(R.id.hud_health_pb);

        hud_radar = aactivity.findViewById(R.id.radar_hude);

        hud_micro = aactivity.findViewById(R.id.imageView14);

        hud_gps = aactivity.findViewById(R.id.imageView16);
        hud_zona = aactivity.findViewById(R.id.grzona);
        hud_x2 = aactivity.findViewById(R.id.imageView17);

        hud_money = aactivity.findViewById(R.id.hud_money);
        hud_weapon = aactivity.findViewById(R.id.hud_weapon);
        hud_menu = aactivity.findViewById(R.id.hud_menu);

        hud_wanted = new ArrayList<>();
        hud_wanted.add(activity.findViewById(R.id.hud_star_1));
        hud_wanted.add(activity.findViewById(R.id.hud_star_2));
        hud_wanted.add(activity.findViewById(R.id.hud_star_3));
        hud_wanted.add(activity.findViewById(R.id.hud_star_4));
        hud_wanted.add(activity.findViewById(R.id.hud_star_5));
        hud_wanted.add(activity.findViewById(R.id.hud_star_6));
        hud_micro.setOnClickListener( view -> {
            view.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
        });

        hud_donatik = aactivity.findViewById(R.id.imageView30);
        hud_donatik.setOnClickListener(v -> {
            try {
                NvEventQueueActivity.getInstance().sendCommand("/donate".getBytes("windows-1251"));
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
        });

        hud_menu.setOnClickListener( view -> {
            view.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            NvEventQueueActivity.getInstance().showMenuu();
            NvEventQueueActivity.getInstance().togglePlayer(1);
        });

        hud_seat = aactivity.findViewById(R.id.imageView29);
        hud_seat.setVisibility(View.GONE); //fix cplayerped spawn after show
        hud_seat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NvEventQueueActivity.getInstance().sendG();
            }
        });
    }

    public void ShowVehButtonG() {
        hud_seat.setVisibility(View.VISIBLE);
    }

    public void HideVehButtonG() {
        hud_seat.setVisibility(View.GONE);
    }

    public void UpdateHudInfo(int health, int armour, int hunger, int weaponidweik, int ammo, int playerid, int money, int wanted)
    {
        progressHP.setProgress(health);
        progressArmor.setProgress(armour);

        DecimalFormat formatter=new DecimalFormat();
        DecimalFormatSymbols symbols= DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);
        String s= formatter.format(money);
        hud_money.setText(s);

        int id = activity.getResources().getIdentifier(new Formatter().format("weapon_%d", Integer.valueOf(weaponidweik)).toString(), "drawable", activity.getPackageName());
        hud_weapon.setImageResource(id);

        hud_weapon.setOnClickListener(v -> NvEventQueueActivity.getInstance().onWeaponChanged());
        if(wanted > 6) wanted = 6;
        for (int i2 = 0; i2 < wanted; i2++) {
            hud_wanted.get(i2).setBackgroundResource(R.drawable.ic_y_star);
        }


        hud_radar.setOnClickListener(view -> {
            NvEventQueueActivity.getInstance().showPausMenu();

        });



        //Toast.makeText(aactivity, "X:" + (int)hud_radar.getX() + " Y:" + (int)hud_radar.getY(), Toast.LENGTH_LONG).show();
        //System by Weikton, ALL REVERS: F*UCK YOU
       // NvEventQueueActivity.setNativeHudElementPosition(6, (int)hud_radar.getX(), (int)hud_radar.getY());
       // NvEventQueueActivity.setNativeHudElementScale(6, (int)hud_radar.getX(), (int)hud_radar.getY())/*, (int)hud_radar.getScaleType()*/;
    }

    public void ShowHud()
    {
        Utils.ShowLayout(hud_layout, true);
        hud_radar.post(() -> {
            if(!isHudSetPos) { // проверка чтобы не было краша при новом показе худа
                // отправка в CHUD::Rect(), кастомный bg
                NvEventQueueActivity.getInstance().SetRadarBgPos(hud_radar.getX(), hud_radar.getY(), hud_radar.getX() + hud_radar.getWidth(), hud_radar.getY() + hud_radar.getHeight());

                // все координаты
                int screenwidth = hud_layout.getWidth();
                int screenheight = hud_layout.getHeight();

                float real_prcX = ((hud_radar.getX() + (hud_radar.getWidth() / 2)) / screenwidth) * 100;
                float real_prcY = ((hud_radar.getY() + (hud_radar.getHeight() / 2.23f)) / screenheight) * 100; // fix
                float gtaX = (640 * (real_prcX / 100f));
                float gtaY = (480 * (real_prcY / 100f));

                // позиция радара GTA (CHud::DrawRadar())
                NvEventQueueActivity.getInstance().SetRadarPos(gtaX, gtaY);
                // убираем из xml после того как взяли координаты
                activity.runOnUiThread(() -> {
                    hud_radar.setVisibility(View.INVISIBLE);
                });

                // показываем что все готово для хука CSprite2d__Draw
                NvEventQueueActivity.getInstance().SetRadarEnabled(true);
                // обозначаем что больше находить координаты не нужно
                isHudSetPos = true;
            }});
    }

    public void ShowGps()
    {
        Utils.ShowLayout(hud_gps, false);
    }

    public void HideGps()
    {
        Utils.HideLayout(hud_gps, false);
    }

    public void ShowX2()
    {
        Utils.ShowLayout(hud_x2, false);
    }

    public void HideX2()
    {
        Utils.HideLayout(hud_x2, false);
    }

    public void ShowZona()
    {
        Utils.ShowLayout(hud_zona, false);
    }

    public void HideZona()
    {
        Utils.HideLayout(hud_zona, false);
    }

    public void ShowRadar()
    {
        Utils.ShowLayout(hud_radar, true);
    }

    public void HideRadar()
    {
        Utils.HideLayout(hud_radar, false);
    }

    public void HideHud()
    {
        Utils.HideLayout(hud_layout, false);
        //Utils.HideLayout(hud_micro, false);
    }

}
