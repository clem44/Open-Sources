package com.codeogenic.opensources;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clem_gumbs on 2/10/17.
 */

public class OSOptions {
    public static final String TITLE_COLOR = "title_color";
    public static final String SUMMARY = "summary";
    public static final String LOGO = "LOGO";
    public static final String BOLD_NAME = "bold";
    public static final String REGULAR = "regular";
    public static final String ITALIC = "italic";
    public static final int STYLE_1 = 0;
    public static final int STYLE_2 = 1;
    public static final String STYLE = "style";
    public static final String TOOLBAR_ID = "toolbar";
    private static final String TAG = OSOptions.class.getSimpleName();
    public static final String WITH_HEADER = "with_header";
    Bundle options;
    ArrayList<ListItem> items;
    int mStyle = STYLE_1;
    public static final String TITLE = "title";
    public static final String HEADING_TEXT = "heading";
    public static final String ITEMS = "items";
    public static final String HEADVIEW = "headerView";
    public static final String THEME = "theme";
    public static final int DARK_THEME = 1032;
    public static final int LIGHT_THEME = 1168;
    private boolean withHeaderImg = true;


    public OSOptions() {
        options = new Bundle();
        items = new ArrayList<>();
        setToolbarId(R.id.toolbar);
    }

    /**
     * Set Activit Title
     *
     * @param title
     */
    public void setToolbarTitle(String title) {
        options.putString(TITLE, title);
    }

    public void setHeaderText(String s) {options.putString(HEADING_TEXT, s); }

    public void setItems(ArrayList<ListItem> items) {
        this.items = items;

    }

    public void addItem(ListItem item) {
        this.items.add(item);
    }

    public void setItem(int index, ListItem item) {
        this.items.set(index, item);
    }

    /**
     * Return bundle Extras
     *
     * @return
     */
    public Bundle getOptions() {

        options.putBoolean(WITH_HEADER, withHeaderImg);
        Log.w(TAG, "item size on getOptions:" + items.size());

        ArrayList<ListItem> clone = new ArrayList<>(items);
        clone.add(0, null);
        options.putParcelableArrayList(ITEMS, clone);
        return options;
    }

    public void setHeaderView(@LayoutRes int headerView) {
        this.options.putInt(HEADVIEW, headerView);
    }

    /**
     * Set either Dark or Light Theme
     * use OSOptions.DARK_THEME or OSOptions.LIGHT_THEME
     *
     * @param theme
     */
    public void setTheme(int theme) {
        this.options.putInt(THEME, theme);
    }

    public void setTitleColor(int color) {
        this.options.putInt(TITLE_COLOR, color);
    }

    public void setSummary(String summary) {
        this.options.putString(SUMMARY, summary);
    }

    public void setLogoResource(int logoImage) {
        this.options.putInt(LOGO, logoImage);
    }

    /**
     * Takes the full font path
     * fonts/fontname.tff
     *
     * @param name
     */
    public void setTypefaceBold(String name) {
        options.putString(BOLD_NAME, name);
    }

    public void setTypefaceRegular(String name) {
        options.putString(REGULAR, name);
    }

    public void setTypefaceItalic(String name) {
        options.putString(ITALIC, name);
    }

    public void setStyle(int style) {
        this.mStyle = style;
        options.putInt(STYLE, mStyle);
    }

    public void removeItem(int position) {
        try {
            this.items.remove(position);
        } catch (IndexOutOfBoundsException ie) {
            ie.printStackTrace();
        }
    }

    public void withHeaderImage(boolean b) {
        withHeaderImg = b;
    }

    public void removeItem(ListItem item) {
        try {
            int i = this.items.indexOf(item);
            Log.w(TAG, "index of item:" + i);
            this.items.remove(i);
        } catch (IndexOutOfBoundsException ie) {
            ie.printStackTrace();
        }
    }

    private void setToolbarId(@IdRes int toolbarId) {

        options.putInt(TOOLBAR_ID, toolbarId);
    }


}
