package com.flybits.android.samples.vanilla.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flybits.android.kernel.models.Content;
import com.flybits.android.kernel.models.results.ContentResult;
import com.flybits.android.kernel.utilities.ContentParameters;
import com.flybits.android.samples.vanilla.R;
import com.flybits.android.samples.vanilla.adapters.ContentFeedAdapter;
import com.flybits.commons.library.api.results.callbacks.PagedResultCallback;
import com.flybits.commons.library.exceptions.FlybitsException;

import java.util.ArrayList;

public class ContentFeedFragment extends Fragment {

    private ArrayList<Content> listOfContent;
    private ContentFeedAdapter adapter;
    private ContentResult result;

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

        displayContent();
        return view;
    }

    public void displayContent() {

        ContentParameters params = new ContentParameters.Builder()
                .addPaging(999, 0)
                .build();

        result = Content.get(getContext(), params, new PagedResultCallback<Content>() {
            @Override
            public void onSuccess(ArrayList<Content> items) {
                if (isAdded()){
                    listOfContent.addAll(items);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onException(FlybitsException exception) {

            }

            @Override
            public void onLoadedAllItems() {

            }
        });
    }
}