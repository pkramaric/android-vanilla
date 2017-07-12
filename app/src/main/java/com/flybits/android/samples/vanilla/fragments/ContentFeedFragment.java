package com.flybits.android.samples.vanilla.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flybits.android.kernel.models.Content;
import com.flybits.android.samples.vanilla.R;
import com.flybits.android.samples.vanilla.adapters.ContentFeedAdapter;

import java.util.ArrayList;

public class ContentFeedFragment extends Fragment {

    private ArrayList<Content> listOfContent;
    private ContentFeedAdapter adapter;

    public static ContentFeedFragment newInstance(){

        Bundle bundle           = new Bundle();
        ContentFeedFragment fragment  = new ContentFeedFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view   = inflater.inflate(R.layout.fragment_feed, container, false);
        RecyclerView  rcView    = (RecyclerView) view.findViewById(R.id.list_feed);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rcView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        listOfContent = new ArrayList<>();
        adapter = new ContentFeedAdapter(getContext(), listOfContent);
        rcView.setAdapter(adapter);

        getContent();
        return view;
    }

}