package com.codeogenic.opensource_libraries;

import android.content.DialogInterface;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.codeogenic.opensources.ListItem;
import com.codeogenic.opensources.OSOptions;
import com.codeogenic.opensources.OpenSources;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    Button btn_sources, btn_frag;
    CheckBox checkBox0, checkBox1, checkBox2, checkBox3, checkBox4, checkBox5,checkBox6,checkBox7,checkBox8;
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
        checkBox6 = (CheckBox) findViewById(R.id.checkBox6);
        checkBox7 = (CheckBox) findViewById(R.id.checkBox7);
        checkBox8 = (CheckBox) findViewById(R.id.checkBox8);

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
        checkBox6.setOnCheckedChangeListener(new MyCheckListener());
        checkBox7.setOnCheckedChangeListener(new MyCheckListener());
        checkBox8.setOnCheckedChangeListener(new MyCheckListener());

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
        data.add(new ListItem("Glide", getString(R.string.MIT_Licence), ""));
        data.add(new ListItem("Gson", getString(R.string.MIT_Licence), ""));
        data.add(new ListItem("RxJava", getString(R.string.MIT_Licence), ""));

        options.addItem(data.get(0));
        options.addItem(data.get(1));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        // myMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idt = item.getItemId();
        if (idt == R.id.about) {

           showAboutDialog();
        }

        if (idt == android.R.id.home) {

            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialogTheme);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.about_layout, null);
        TextView textView= (TextView) layout.findViewById(R.id.textView4);
        textView.setText(Html.fromHtml(getResources().getString(R.string.aboutMe)));
        builder.setView(layout);
        builder.setTitle("About OSL");
        //builder.setMessage();
        builder.setCancelable(false);
        builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                // Intent intent = new Intent(ProfileActivity.this, UserLoginActivity.class);
                //startActivity(intent);
                //ActivityCompat.finishAffinity(PlayScreen.this);

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.btn_sources:
                    Log.w(TAG, "open sources clicked");
                    options.setToolbarTitle("Open Sources");
                    options.setHeaderText(heading.getText().toString());
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
                case R.id.checkBox6:
                    if (checked)
                        options.addItem(data.get(6));
                    else
                        options.removeItem(data.get(6));
                    break;
                case R.id.checkBox7:
                    if (checked)
                        options.addItem(data.get(7));
                    else
                        options.removeItem(data.get(7));
                    break;
                case R.id.checkBox8:
                    if (checked)
                        options.addItem(data.get(8));
                    else
                        options.removeItem(data.get(8));
                    break;

            }
        }
    }
}
