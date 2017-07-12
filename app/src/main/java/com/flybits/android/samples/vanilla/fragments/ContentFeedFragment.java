package com.flybits.android.samples.vanilla.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.flybits.android.samples.vanilla.interfaces.IProgressDialog;
import com.flybits.commons.library.api.results.callbacks.PagedResultCallback;
import com.flybits.commons.library.exceptions.FlybitsException;

import java.util.ArrayList;

public class ContentFeedFragment extends Fragment {

    private ArrayList<Content> listOfContent;
    private ContentFeedAdapter adapter;
    private ContentResult result;
    private IProgressDialog callbackProgress;
    private SwipeRefreshLayout swipeContainer;

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
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rcView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        listOfContent = new ArrayList<>();
        adapter = new ContentFeedAdapter(getContext(), listOfContent);
        rcView.setAdapter(adapter);

        callbackProgress.onStartProgress(getString(R.string.loadingContent), true);
        displayContent();

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                displayContent();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

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
                    listOfContent.clear();
                    listOfContent.addAll(items);
                    adapter.notifyDataSetChanged();
                    callbackProgress.onStopProgress();
                    swipeContainer.setRefreshing(false);

                }
            }

            @Override
            public void onException(FlybitsException exception) {
                if (isAdded()){
                    swipeContainer.setRefreshing(false);
                }
            }

            @Override
            public void onLoadedAllItems() {
                if (isAdded()){
                    swipeContainer.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callbackProgress = (IProgressDialog) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        callbackProgress.onStopProgress();
    }
}