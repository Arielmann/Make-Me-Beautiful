package com.example.home.makemebeautiful.choose_stylist.choosing_screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.choose_stylist.model.ChooseStylistModel;

import java.util.concurrent.ExecutionException;

/**
 * Created by home on 8/13/2016.
 */
public class ChooseStylistViewFrag extends Fragment {

    private RecyclerView recyclerView;
    private ChooseStylistModel model;


    public void setFragData() throws ExecutionException, InterruptedException {
        model = ChooseStylistModel.getInstance(getActivity());
        recyclerView.setLayoutManager(model.getLayoutManager());
        recyclerView.setAdapter(model.getAdapter());
        model.getAdapter().setContext(getContext());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View recyclerViewLayout = inflater.inflate(R.layout.frag_recycler_view, null);
        recyclerView = (RecyclerView) recyclerViewLayout.findViewById(R.id.recyclerView);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        return recyclerViewLayout;
    }
}
