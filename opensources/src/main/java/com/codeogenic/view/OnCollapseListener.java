package com.codeogenic.view;

import android.view.View;

/**
 * Created by clem_gumbs on 2/17/17.
 */
public interface OnCollapseListener {

    public void onCollapse(View handle, View contentView);
    public void onExpand(View handle, View content);
}
