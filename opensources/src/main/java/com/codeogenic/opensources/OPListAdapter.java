package com.codeogenic.opensources;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeogenic.view.CollapsingLayout;
import com.codeogenic.view.SwellCheckBox;

import java.util.ArrayList;

/**
 * Created by clem_gumbs on 2/21/17.
 */

public class OPListAdapter extends BaseAdapter {

    private static final String TAG = OPListAdapter.class.getSimpleName();
    private String heading;
    private int logo;
    private String summary;
    private Context context;
    private ArrayList<ListItem> items;
    LayoutInflater layoutInflater;
    RotateAnimation rotate;
    private boolean expanded;
    private boolean isAnimating;
    private int mStyle = OSOptions.STYLE_1;
    private String wrap_style;
    private boolean withHeader = true;
    private boolean withHeaderImg = true;
    private String wrap_end;


    public OPListAdapter(Context context, ArrayList<ListItem> items, String summary, String heading, int logo) {
        this.heading = !TextUtils.isEmpty(heading) ? heading : "Open Source Libraries";
        this.logo = logo;
        this.summary = summary;
        this.context = context;
        this.items = items;
        layoutInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.w(TAG, "item size on init:" + items.size());
        init();
    }

    private void init() {
        rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setInterpolator(new LinearInterpolator());
        //rotate.setRepeatMode(Animation.REVERSE);
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                expanded = !expanded;
                isAnimating = false;
            }
        });
        wrap_style = "<html><body style=\"text-align:justify\">";
        wrap_end ="</body></html>";
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListItem current = (ListItem) getItem(position);
        View rowView = convertView;
        ViewHolder holder;

        if (withHeader && position == 0) {
            if (rowView == null) {
                rowView= layoutInflater.inflate(R.layout.item_header,parent, false);
                holder = new HeaderHolder(rowView, current);
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }
        }

        switch (mStyle) {
            case OSOptions.STYLE_1:
                //Log.w(TAG, "getView position: " + position);
                if (position != 0) {
                    try {

                        if (rowView == null) {
                            rowView = layoutInflater.inflate(R.layout.list_item_body2, parent, false);
                            holder = new HolderOne(rowView, current);
                            rowView.setTag(holder);
                        } else {
                            holder = (ViewHolder) rowView.getTag();
                        }

                    } catch (NullPointerException ne) {
                        ne.printStackTrace();
                    }
                }

                break;

            case OSOptions.STYLE_2:
                if (position != 0) {
                    try {

                        if (rowView == null) {
                            rowView = layoutInflater.inflate(R.layout.list_item_body, parent,false);
                            holder = new HolderTwo(rowView, current);
                            rowView.setTag(holder);
                        } else {
                            holder = (ViewHolder) rowView.getTag();
                        }

                    } catch (NullPointerException ne) {
                        ne.printStackTrace();
                    }
                }
                break;

            default:

        }
        return rowView;
    }

    public void setStyle(int style) {
        this.mStyle = style;
    }

    public void showHeaderImage(boolean withH) {
        this.withHeaderImg = withH;
    }

    public void showHeader(boolean withH) {
        this.withHeader = withH;
    }

    class HeaderHolder extends ViewHolder {
        ImageView logoImage;
        TextView mHeading, summaryText;

        public HeaderHolder(View itemView, ListItem current) {
            super(itemView);
            init(current);
        }

        @Override
        public void init(Object obj) {
            logoImage = (ImageView) convertView.findViewById(R.id.logo);
            mHeading = (TextView) convertView.findViewById(R.id.title);
            summaryText = (TextView) convertView.findViewById(R.id.summary);
            try {
                mHeading.setText(heading);
                summary = !TextUtils.isEmpty(summary) ? wrap_style + summary+wrap_end : "";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    summaryText.setText(Html.fromHtml(summary, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    summaryText.setText(Html.fromHtml(summary));
                }
                if (!withHeaderImg) {
                    logoImage.setVisibility(View.GONE);
                    return;
                }
                logoImage.setImageResource(logo);
            } catch (NullPointerException ne) {
                ne.printStackTrace();
            }
        }
    }

    class HolderOne extends ViewHolder {

        public TextView mTextView;
        public TextView listTitleTextView;
        public CollapsingLayout cl;
        public View headerView;
        public ImageView img_button;

        public HolderOne(View convertView, ListItem current) {
            super(convertView);
            init(current);

        }

        @Override
        public void init(Object obj) {
            ListItem current = (ListItem) obj;
            cl = (CollapsingLayout) convertView.findViewById(R.id.collapse_panel);
            String listTitle = current.getHeading();

            listTitleTextView = (TextView) convertView
                    .findViewById(R.id.heading);
            listTitleTextView.setTypeface(null, Typeface.BOLD);
            listTitleTextView.setText(listTitle);

            headerView = convertView.findViewById(R.id.title_handle);
            img_button = (ImageView) headerView.findViewById(R.id.btn_expand);

            mTextView = (TextView) convertView.findViewById(R.id.body);
            String body = !TextUtils.isEmpty(current.getBody()) ? current.getBody() : "";

            body += !TextUtils.isEmpty(current.getUrl()) ? "\n Source -> " + current.getUrl() : "";

            body = wrap_style + body +wrap_end;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mTextView.setText(Html.fromHtml(body, Html.FROM_HTML_MODE_COMPACT));
            } else {
                mTextView.setText(Html.fromHtml(body));
            }

            final long time = cl.getDuration();
            headerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if ( !cl.isAnimating()) {
                        cl.toggle();
                        img_button.animate().rotationBy(180f).setDuration(time*2)
                                .setInterpolator(new LinearInterpolator()).setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                isAnimating = false;
                                Log.w(TAG, "rotating arrow");
                            }

                            @Override
                            public void onAnimationStart(Animator animation) {
                                expanded = !expanded;
                                isAnimating = true;
                            }
                        }).start();
                    }

                }
            });
        }
    }

    class HolderTwo extends ViewHolder {

        public TextView mTextView;
        public TextView listTitleTextView;
        public CollapsingLayout cl;
        public View headerView;
        public SwellCheckBox img_button;

        public HolderTwo(View convertView, ListItem current) {
            super(convertView);
            init(current);
        }

        @Override
        public void init(Object obj) {
            ListItem current = (ListItem) obj;
            cl = (CollapsingLayout) convertView.findViewById(R.id.collapse_panel);
            String listTitle = current.getHeading();

            listTitleTextView = (TextView) convertView
                    .findViewById(R.id.heading);
            listTitleTextView.setTypeface(null, Typeface.BOLD);
            listTitleTextView.setText(listTitle);

            headerView = convertView.findViewById(R.id.title_handle);
            img_button = (SwellCheckBox) headerView.findViewById(R.id.btn_expand);

            /*img_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!cl.isAnimating()) {
                        img_button.toggle();
                        cl.toggle();
                    }
                }
            });*/
            img_button.setFocusable(false);
            img_button.setClickable(false);

            mTextView = (TextView) convertView.findViewById(R.id.body);
            String body = !TextUtils.isEmpty(current.getBody()) ? current.getBody() : "";
            body += !TextUtils.isEmpty(current.getUrl()) ? "\n Source -> " + current.getUrl() : "";
            body = wrap_style + body+wrap_end;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mTextView.setText(Html.fromHtml(body, Html.FROM_HTML_MODE_COMPACT));
            } else {
                mTextView.setText(Html.fromHtml(body));
            }

            headerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Log.w(TAG, "item headerView clicked");
                    if (!cl.isAnimating()) {
                        img_button.toggle();
                        cl.toggle();
                    }
                }
            });
        }
    }

    protected abstract class ViewHolder {

        View convertView;

        public ViewHolder(View itemView) {
            this.convertView = itemView;
        }


        public abstract void init(Object obj);
    }


}
