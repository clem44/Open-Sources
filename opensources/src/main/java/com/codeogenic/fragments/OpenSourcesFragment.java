package com.codeogenic.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codeogenic.opensources.ListItem;
import com.codeogenic.opensources.OPListAdapter;
import com.codeogenic.opensources.OSOptions;
import com.codeogenic.opensources.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OpenSourcesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OpenSourcesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = OpenSourcesFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View layout;
    ListView listView;
    ArrayList<ListItem> mList;
    OPListAdapter mAdapter;
    String font_bold,font_regular,font_italic;
    private static Map<String, Typeface> cachedFont = new HashMap<String, Typeface>();

    public OpenSourcesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param2 Parameter 2.
     * @return A new instance of fragment OpenSourcesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OpenSourcesFragment newInstance( Bundle param2) {
        OpenSourcesFragment fragment = new OpenSourcesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(param2);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle options = getArguments();
           // mParam2 = getArguments().getString(ARG_PARAM2);
            String title = options.getString(OSOptions.TITLE);
            String sum = options.getString(OSOptions.SUMMARY);
            // fontpath = options.getString(OSOptions.FONT_PATH);
            font_bold = options.getString(OSOptions.BOLD_NAME);
            font_regular = options.getString(OSOptions.REGULAR);
            font_italic = options.getString(OSOptions.ITALIC);
            mList = options.getParcelableArrayList(OSOptions.ITEMS);
            mAdapter = new OPListAdapter(getContext(), mList,sum,
                    options.getString(OSOptions.HEADING_TEXT),options.getInt(OSOptions.LOGO));
            mAdapter.setStyle(options.getInt(OSOptions.STYLE));
            mAdapter.showHeaderImage(options.getBoolean(OSOptions.WITH_HEADER));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_open_sources, container, false);
        //mList = (ArrayList<ListItem>) options.getSerializable(OSOptions.ITEMS);
        listView = (ListView) layout.findViewById(R.id.mylistview);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.w(TAG, "listview item clicked");
            }
        });


        return layout;
    }

}
