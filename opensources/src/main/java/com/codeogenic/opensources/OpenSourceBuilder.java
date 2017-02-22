package com.codeogenic.opensources;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by clem_gumbs on 2/15/17.
 */

public final class OpenSourceBuilder {

    public OpenSourceBuilder() {
    }

    public static ActivityBuilder activity(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        } else {
            return new ActivityBuilder(context);
        }

    }

    public static final class ActivityBuilder {

        private Context context;
        private OSOptions options;

        public ActivityBuilder(Context context) {
            this.context = context;
            options = new OSOptions();
        }

        public void start(@NonNull OSOptions bundle) {
            context.startActivity(getIntent(bundle));
        }

        public Intent getIntent(@NonNull OSOptions bundle) {
            return getIntent(bundle, OpenSources.class);
        }

        public Intent getIntent(@NonNull OSOptions bundle, @Nullable Class<?> cls) {

            Intent intent = new Intent();
            intent.setClass(context, cls);
            if (bundle != null)
                intent.putExtras(bundle.getOptions());
            else {
                intent.putExtras(this.options.getOptions());
            }
            return intent;
        }

        /**
         * Set Activit Title
         *
         * @param title
         */
        public void setTitle(String title) {
            options.setTitle(title);
        }

        public void setItems(ArrayList<ListItem> items) {
            options.setItems(items);
        }

        public void setHeaderView(int hd) {
            options.setHeaderView(hd);
        }

        public void setTitleColor(int color){
           options.setTitleColor(color);

        }
        public void setHeaderSummary(String summary){
            options.setSummary(summary);
        }
        public void setLogoImage(@DrawableRes int image){
            options.setLogoResource(image);
        }

        /**
         * Set either Dark or Light Theme
         * use OSOptions.DARK_THEME or OSOptions.LIGHT_THEME
         *
         * @param theme
         */
        public void setTheme(int theme) {
            options.setTheme(theme);

        }
    }


    public static final class FragmentBuilder {

        private Context context;
        private OSOptions options;

        public FragmentBuilder(@IdRes int id, Context context) {
            this.context = context;
            options = new OSOptions();
        }

        public void start(@NonNull OSOptions bundle) {
            context.startActivity(getIntent(bundle));
        }

        public Intent getIntent(@NonNull OSOptions bundle) {
            return getIntent(bundle, OpenSources.class);
        }

        public Intent getIntent(@NonNull OSOptions bundle, @Nullable Class<?> cls) {

            Intent intent = new Intent();
            intent.setClass(context, cls);
            if (bundle != null)
                intent.putExtras(bundle.getOptions());
            else {
                intent.putExtras(this.options.getOptions());
            }
            return intent;
        }

        /**
         * Set Activit Title
         *
         * @param title
         */
        public void setTitle(String title) {
            options.setTitle(title);
        }

        public void setItems(ArrayList<ListItem> items) {
            options.setItems(items);
        }

        public void setHeaderView(int hd) {
            options.setHeaderView(hd);
        }

        public void setTitleColor(int color){
            options.setTitleColor(color);

        }
        public void setHeaderSummary(String summary){
            options.setSummary(summary);
        }
        public void setLogoImage(@DrawableRes int image){
            options.setLogoResource(image);
        }

        /**
         * Set either Dark or Light Theme
         * use OSOptions.DARK_THEME or OSOptions.LIGHT_THEME
         *
         * @param theme
         */
        public void setTheme(int theme) {
            options.setTheme(theme);

        }

    }
}
