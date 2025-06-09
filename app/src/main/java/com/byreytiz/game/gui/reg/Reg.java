package com.byreytiz.game.gui.reg;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;


/*

         - Made by Weikton (tapy.me/weikton)

*/

import androidx.constraintlayout.widget.ConstraintLayout;

import com.byreytiz.weikton.reg.Preferences;
import com.nvidia.devtech.NvEventQueueActivity;
import com.byreytiz.game.R;
import com.byreytiz.game.gui.util.Utils;

import java.io.UnsupportedEncodingException;

public class Reg {
    public Activity activity;
    public ConstraintLayout auth_layout;
    public ConstraintLayout reg_layout;
    public ConstraintLayout reg_layout_sx;
    public ConstraintLayout reg_person;

    public Button resume;
    public TextView nickname;
    public EditText input;
    public TextView cancel;

    public Button rc2;
    public ToggleButton male;
    public ToggleButton female;

    public Button resume2;
    public TextView nickname2;
    public ImageView person_left;
    public ImageView person_right;
    public ImageView person_close_h;
    public Button play_person;
    public EditText input2;
    public EditText input3;
    public TextView cancel2;
    public TextView hint_person;

    public Switch autoreg;
    static Boolean isTurned = false;
    static Boolean isReg = false;
    static int isAL = 0;

    public Reg(Activity aactivity) {
        activity = aactivity;
        auth_layout = aactivity.findViewById(R.id.linearLayout4);
        auth_layout.setVisibility(View.GONE);
        reg_layout = aactivity.findViewById(R.id.linearLayout4reg);
        reg_layout.setVisibility(View.GONE);
        reg_layout_sx = aactivity.findViewById(R.id.linearLayout4regsx);
        reg_layout_sx.setVisibility(View.GONE);
        reg_person = aactivity.findViewById(R.id.reg_person);
        reg_person.setVisibility(View.GONE);
        person_left = aactivity.findViewById(R.id.arrow_left);
        person_right = aactivity.findViewById(R.id.arrow_right);
        play_person = aactivity.findViewById(R.id.play_buttonnon);
        person_close_h = aactivity.findViewById(R.id.person_close_h);
        hint_person = aactivity.findViewById(R.id.hint_person);

        person_close_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hint_person.setVisibility(View.GONE);
                person_close_h.setVisibility(View.GONE);
            }
        });

        person_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NvEventQueueActivity.getInstance().personclick(2);
            }
        });
        person_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NvEventQueueActivity.getInstance().personclick(1);
            }
        });
        play_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NvEventQueueActivity.getInstance().personclick(0);
            }
        });

        autoreg = aactivity.findViewById(R.id.auto_switch);
        autoreg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked) {
                    isTurned = true;
                    Preferences.setAL(true);
                    NvEventQueueActivity.getInstance().showNotification(2, "Недоступно", 4, "", "");
                } else {
                    isTurned = false;
                    Preferences.setAL(false);
                }
            }
        });

        //if(Preferences.GetAutoLoginStatus() == true && isReg == false) {
        //   autoreg.setChecked(true);
        //   try {
        //       NvEventQueueActivity.getInstance().sendDialogResponse(1, /*Preferences.GetCurrentDialogId()*/1, Preferences.GetListitemToSend(), Preferences.getPassword().getBytes("windows-1251"));
        //   } catch (UnsupportedEncodingException e) {
        //       e.printStackTrace();
        //   }
        //       HideAuth();
        //   } else {
        //    autoreg.setChecked(false);
        //}

        nickname = aactivity.findViewById(R.id.textView79);
        nickname.setText(Preferences.getNick() + " " + "[" + Preferences.GetID() + "]");
        nickname2 = aactivity.findViewById(R.id.textView9999);
        nickname2.setText(Preferences.getNick() + " " + "[" + Preferences.GetID() + "]");

        input = aactivity.findViewById(R.id.password_enter);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    Preferences.setPassword(String.valueOf(input.getText()));
                } catch (Exception e) {
                    // error
                    e.printStackTrace();
                }
            }
        });

        input2 = aactivity.findViewById(R.id.password_enter3);
        input2.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {

                } catch (Exception e) {
                    // error
                    e.printStackTrace();
                }
            }
        });

        input3 = aactivity.findViewById(R.id.password_enter2);
        input3.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {

                } catch (Exception e) {
                    // error
                    e.printStackTrace();
                }
            }
        });

        cancel = aactivity.findViewById(R.id.textView3);
        cancel.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            try {
                NvEventQueueActivity.getInstance().sendDialogResponse(0, /*Preferences.GetCurrentDialogId()*/1, Preferences.GetListitemToSend(), input.getText().toString().getBytes("windows-1251"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            HideAuth();
        });

        //    TYPE_TEXT_GREEN = 3;
        //    TYPE_TEXT_RED = 2;
        resume = aactivity.findViewById(R.id.play_but);
        resume.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
            try {
                if(checkValid()) {
                    NvEventQueueActivity.getInstance().sendDialogResponse(1, /*Preferences.GetCurrentDialogId()*/1, Preferences.GetListitemToSend(), input.getText().toString().getBytes("windows-1251"));
                    HideAuth();
                }
            } catch (UnsupportedEncodingException e) {
                NvEventQueueActivity.getInstance().showNotification(2, "Произошла неизвестная ошибка", 5, "", "");
                e.printStackTrace();
            }
        });

        male = aactivity.findViewById(R.id.toggleButton);
        female = aactivity.findViewById(R.id.toggleButton2);

        rc2 = aactivity.findViewById(R.id.play_but23);
        resume2 = aactivity.findViewById(R.id.play_but222);
        resume2.setOnClickListener(view -> {
            try {
                if(checkValidReg() && input2.getText().toString().equals(input3.getText().toString())) {
                    NvEventQueueActivity.getInstance().sendDialogResponse(1, /*Preferences.GetCurrentDialogId()*/2, Preferences.GetListitemToSend(), input2.getText().toString().getBytes("windows-1251"));
                    resume2.setText("ПРОДОЛЖИТЬ");
                    input3.setInputType(View.AUTOFILL_TYPE_TEXT);
                    input3.setHint("Никнейм пригласившего игрока");
                    NvEventQueueActivity.getInstance().showNotification(2, "Необязательный пункт регистрации, можете пропустить", 5, "", "");
                    input3.setText("");
                    input2.setVisibility(View.GONE);

                    rc2.setVisibility(View.VISIBLE);
                    rc2.setOnClickListener(view3 -> {
                        view3.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
                        try {
                            NvEventQueueActivity.getInstance().sendDialogResponse(0, /*Preferences.GetCurrentDialogId()*/2, Preferences.GetListitemToSend(), input3.getText().toString().getBytes("windows-1251"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        HideReg();
                        ShowRegSX();
                        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                buttonView.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
                                if (buttonView.isChecked()) {
                                    try {
                                        NvEventQueueActivity.getInstance().sendDialogResponse(1, /*Preferences.GetCurrentDialogId()*/2, Preferences.GetListitemToSend(), input3.getText().toString().getBytes("windows-1251"));
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    HideRegSX();

                                } else {

                                }
                            }
                        });
                        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                buttonView.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
                                if (buttonView.isChecked()) {
                                    try {
                                        NvEventQueueActivity.getInstance().sendDialogResponse(0, /*Preferences.GetCurrentDialogId()*/2, Preferences.GetListitemToSend(), input3.getText().toString().getBytes("windows-1251"));
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    HideRegSX();
                                } else {

                                }
                            }
                        });
                    });

                    resume2.setOnClickListener(view2 -> {
                        view2.startAnimation(AnimationUtils.loadAnimation(aactivity, R.anim.button_click));
                        try {
                            NvEventQueueActivity.getInstance().sendDialogResponse(1, /*Preferences.GetCurrentDialogId()*/2, Preferences.GetListitemToSend(), input3.getText().toString().getBytes("windows-1251"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        HideReg();
                        //ShowRegSX();
                    });
                } else NvEventQueueActivity.getInstance().showNotification(4, "Пароли не совпадают", 5, "", "");
            } catch (UnsupportedEncodingException e) {
                NvEventQueueActivity.getInstance().showNotification(4, "Произошла неизвестная ошибка", 5, "", "");
                e.printStackTrace();
            }
        });
    }

    public void ShowAuth()
    {
        Utils.ShowLayout(auth_layout, true);
        isReg = false;
    }

    public void HideAuth()
    {
        Utils.HideLayout(auth_layout, true);
    }

    public void ShowReg()
    {
        isReg = true;
        Utils.ShowLayout(reg_layout, true);
    }

    public void HideReg()
    {
        isReg = false;
        Utils.HideLayout(reg_layout, true);
    }

    public void ShowRegSX()
    {
        isReg = true;
        Utils.ShowLayout(reg_layout_sx, true);
    }

    public void ShowPerson(){
        Utils.ShowLayout(reg_person, true);
    }
    public void HidePerson(){
        Utils.HideLayout(reg_person, false);
    }

    public void HideRegSX()
    {
        Utils.HideLayout(reg_layout_sx, true);
        NvEventQueueActivity.getInstance().showNotification(5, "Добро пожаловать!", 4, "", "");
    }

    public boolean checkValid(){
        EditText nick = activity.findViewById(R.id.password_enter);
        if(nick.getText().toString().isEmpty()) {
            NvEventQueueActivity.getInstance().showNotification(4, "Введите пароль", 5, "", "");
            return false;
        }
        if(nick.getText().toString().length() < 6) {
            NvEventQueueActivity.getInstance().showNotification(4, "Длина пароля должна быть не менее 6 символов", 5, "", "");
            return false;
        }
        return true;
    }

    public boolean checkValidReg() {
        EditText nick2 = activity.findViewById(R.id.password_enter2);
        EditText nick3 = activity.findViewById(R.id.password_enter3);
        if(nick2.getText().toString().isEmpty()) {
            NvEventQueueActivity.getInstance().showNotification(4, "Введите пароль", 5, "", "");
            return false;
        }
        if(nick2.getText().toString().length() < 6 && !nick2.getText().toString().isEmpty()) {
            NvEventQueueActivity.getInstance().showNotification(4, "Длина пароля должна быть не менее 6 символов", 5, "", "");
            return false;
        }
        if(nick3.getText().toString().isEmpty()) {
            NvEventQueueActivity.getInstance().showNotification(4, "Повторите пароль", 5, "", "");
            return false;
        }
        return true;
    }
}
