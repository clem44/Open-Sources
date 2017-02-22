package com.codeogenic.opensource_libraries;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.codeogenic.opensources.ListItem;
import com.codeogenic.opensources.OSOptions;
import com.codeogenic.opensources.OpenSources;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    Button btn_sources, btn_frag;
    CheckBox checkBox0, checkBox1, checkBox2, checkBox3, checkBox4, checkBox5;
    RadioGroup radioGroup;
    EditText heading;
    OSOptions options = new OSOptions();
    ArrayList<ListItem> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_sources = (Button) findViewById(R.id.btn_sources);
        btn_frag = (Button) findViewById(R.id.btn_frag);

        heading = (EditText) findViewById(R.id.title_editText);
        checkBox0 = (CheckBox) findViewById(R.id.checkBox0);
        checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
        checkBox5 = (CheckBox) findViewById(R.id.checkBox5);

        radioGroup = (RadioGroup) findViewById(R.id.style_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {

                if (id == R.id.style_1) {
                    options.setStyle(OSOptions.STYLE_1);

                } else if (id == R.id.style_2) {
                    options.setStyle(OSOptions.STYLE_2);
                }

            }
        });

        checkBox0.setOnCheckedChangeListener(new MyCheckListener());
        checkBox1.setOnCheckedChangeListener(new MyCheckListener());
        checkBox2.setOnCheckedChangeListener(new MyCheckListener());
        checkBox3.setOnCheckedChangeListener(new MyCheckListener());
        checkBox4.setOnCheckedChangeListener(new MyCheckListener());
        checkBox5.setOnCheckedChangeListener(new MyCheckListener());

        btn_sources.setOnClickListener(new ButtonListener());
        btn_frag.setOnClickListener(new ButtonListener());

        initData();
        options.setStyle(OSOptions.STYLE_1);

    }

    private void initData() {

        data.add(new ListItem("Android Iconics", getString(R.string.mike_penz), ""));
        data.add(new ListItem("Butterknife", getString(R.string.MIT_Licence), ""));
        data.add(new ListItem("CircleImageView", getString(R.string.MIT_Licence), "https://github.com/hdodenhof/CircleImageView"));
        data.add(new ListItem("Fancy Button", getString(R.string.fancy_button), "https://github.com/medyo/Fancybuttons"));
        data.add(new ListItem("Material Dialogs", getString(R.string.fancy_button), ""));
        data.add(new ListItem("Retrofit", getString(R.string.MIT_Licence), ""));

        options.addItem(data.get(0));
        options.addItem(data.get(1));
    }


    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.btn_sources:
                    Log.w(TAG, "open sources clicked");
                    options.setToolbarTitle("Open Sources");
                    options.setHeaderText("Libraries We Use");
                    options.setTypefaceBold("fonts/ClanPro-Medium.otf");
                    options.setTypefaceRegular("fonts/ClanPro-Book.otf");
                    options.setLogoResource(R.mipmap.ic_launcher);
                    options.setSummary("The listed following are external libraries we have included in this application. We thank the open source community for all of their contributions.");
                    options.setTheme(OSOptions.DARK_THEME);
                    OpenSources.Builder(MainActivity.this).start(options);
                    break;

                case R.id.btn_frag:
                    break;
            }
        }
    }


    private class MyCheckListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
            switch (compoundButton.getId()) {
                case R.id.checkBox0:
                    if (checked)
                        options.addItem(data.get(0));
                    else
                        options.removeItem(data.get(0));
                    break;
                case R.id.checkBox1:
                    if (checked)
                        options.addItem(data.get(1));
                    else
                        options.removeItem(data.get(1));
                    break;
                case R.id.checkBox2:
                    if (checked)
                        options.addItem(data.get(2));
                    else
                        options.removeItem(data.get(2));
                    break;
                case R.id.checkBox3:
                    if (checked)
                        options.addItem(data.get(3));
                    else
                        options.removeItem(data.get(3));
                    break;
                case R.id.checkBox4:
                    if (checked)
                        options.addItem(data.get(4));
                    else
                        options.removeItem(data.get(4));
                    break;
                case R.id.checkBox5:
                    if (checked)
                        options.addItem(data.get(5));
                    else
                        options.removeItem(data.get(5));
                    break;

            }
        }
    }
}
