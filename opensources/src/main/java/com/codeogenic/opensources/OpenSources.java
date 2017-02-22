package com.codeogenic.opensources;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class OpenSources extends AppCompatActivity {

    private static final String TAG = OpenSources.class.getSimpleName();
    // ExpandableListView listView;
    ListView listView;
    ArrayList<ListItem> mList;
    TextView heading, summary;
    ImageView logo;
    OPListAdapter mAdapter;
    View root;
    String font_bold,font_regular,font_italic;
    private static Map<String, Typeface> cachedFont = new HashMap<String, Typeface>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_sources);
        Bundle options = getIntent().getExtras();
        String title = options.getString(OSOptions.TITLE);
        String sum = options.getString(OSOptions.SUMMARY);
       // fontpath = options.getString(OSOptions.FONT_PATH);
        font_bold = options.getString(OSOptions.BOLD_NAME);
        font_regular = options.getString(OSOptions.REGULAR);
        font_italic = options.getString(OSOptions.ITALIC);

        root = findViewById(R.id.activity_open_sources);


        if(getSupportActionBar() ==null && getActionBar()==null)
            try {
                setSupportActionBar((Toolbar) findViewById(options.getInt(OSOptions.TOOLBAR_ID)));
            }catch (Resources.NotFoundException nf){
                nf.printStackTrace();
            }
        else{
            findViewById(R.id.toolbar).setVisibility(View.GONE);
        }


        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {

            actionBar.setTitle(!TextUtils.isEmpty(title) ? title : "");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);

        }
        /*heading = (TextView) findViewById(R.id.title);
        summary = (TextView) findViewById(R.id.summary);
        summary.setText(!TextUtils.isEmpty(sum) ? sum : "");

        logo = (ImageView) findViewById(R.id.logo);
        try {
            logo.setImageResource(options.getInt(OSOptions.LOGO));
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        }*/

        // getSupportActionBar().setHomeAsUpIndicator();
        mList = options.getParcelableArrayList(OSOptions.ITEMS);
        //mList = (ArrayList<ListItem>) options.getSerializable(OSOptions.ITEMS);
        listView = (ListView) findViewById(R.id.mylistview);
        mAdapter = new OPListAdapter(this, mList,sum,options.getString(OSOptions.HEADING_TEXT),options.getInt(OSOptions.LOGO));
        mAdapter.setStyle(options.getInt(OSOptions.STYLE));
        mAdapter.showHeaderImage(options.getBoolean(OSOptions.WITH_HEADER));

        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.w(TAG, "listview item clicked");
            }
        });

       /* listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {

            }
        });*/

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        overrideFonts(this, root);
    }

    public static OpenSourceBuilder.ActivityBuilder Builder(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        } else {
            return new OpenSourceBuilder.ActivityBuilder(context);
        }

    }

    public static OpenSourceBuilder.FragmentBuilder FragmentBuilder(@IdRes int container, Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        } else {
            return new OpenSourceBuilder.FragmentBuilder(container, context);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //getMenuInflater().inflate(R.menu.menu_settings, menu);
        // myMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idt = item.getItemId();

        if (idt == android.R.id.home) {

            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private void overrideFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView ) {
               // ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "font.ttf"));
                TextView tv =   ((TextView) v);
                switch (tv.getTypeface().getStyle()) {
                    case Typeface.BOLD:
                        tv.setTypeface(findFont(this,font_bold,""));
                        break;

                    case Typeface.ITALIC:
                        tv.setTypeface(findFont(this,font_italic,""));
                        break;

                    case Typeface.NORMAL:
                        tv.setTypeface(findFont(this,font_regular,""));
                        break;

                    default:
                        tv.setTypeface(findFont(this,"",""));
                        break;
                }

            }
        } catch (Exception e) {
        }
    }

    public static Typeface findFont(Context context, String fontPath, String defaultFontPath){

        if (fontPath == null){
            return Typeface.DEFAULT;
        }

        String fontName = new File(fontPath).getName();
        String defaultFontName = "";
        if (!TextUtils.isEmpty(defaultFontPath)){
            defaultFontName = new File(defaultFontPath).getName();
        }

        if (cachedFont.containsKey(fontName)){
            return cachedFont.get(fontName);
        }else{
            try{
                AssetManager assets = context.getResources().getAssets();

                if (Arrays.asList(assets.list("")).contains(fontPath)){
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
                    cachedFont.put(fontName, typeface);
                    return typeface;
                }else if (Arrays.asList(assets.list("fonts")).contains(fontName)){
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), String.format("fonts/%s",fontName));
                    cachedFont.put(fontName, typeface);
                    return typeface;
                }else if (!TextUtils.isEmpty(defaultFontPath) && Arrays.asList(assets.list("")).contains(defaultFontPath)){
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), defaultFontPath);
                    cachedFont.put(defaultFontName, typeface);
                    return typeface;
                } else {
                    throw new Exception("Font not Found");
                }

            }catch (Exception e){
                return Typeface.DEFAULT;
            }
        }

    }
}
