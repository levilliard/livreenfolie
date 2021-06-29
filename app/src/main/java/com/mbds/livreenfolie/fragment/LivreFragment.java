package com.mbds.livreenfolie.fragment;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import com.google.gson.Gson;
import com.mbds.livreenfolie.R;
import com.mbds.livreenfolie.activity.LivreDetailsActivity;
import com.mbds.livreenfolie.adapter.LivreAdapter;
import com.mbds.livreenfolie.model.Livre;
import com.mbds.livreenfolie.util.GlobalVar;
import com.orhanobut.hawk.Hawk;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

//import static android.support.constraint.Constraints.TAG;

public class LivreFragment extends Fragment implements SearchView.OnQueryTextListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    public static final int ITEMS_PER_AD = 5;

    private int mColumnCount = 1;
    List<Livre> articles;
    List<Livre> _articles;
    Livre article;
    Livre _article;
    LivreAdapter mAdapter;
    SearchView searchView;

    private final String TAG = "LivreFragment";

    private RecyclerViewHeader recyclerHeader;
    private RecyclerView recycler;
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/4177191030";

    public LivreFragment() {

    }

    public static LivreFragment newInstance(int columnCount) {
        LivreFragment fragment = new LivreFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        article = new Livre();
        _article = new Livre();
        articles = new ArrayList<>();
        _articles = new ArrayList<>();
        mAdapter = new LivreAdapter(articles);

        //final ProgressDialog progress = ProgressDialog.show(getContext(), "Livre En Folie", "Initialisation ...");
        final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("EST"));
        final Date date = cal.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        final String temp = format1.format(date);
        final String year = temp.split("-")[0];
        final String mt = temp.split("-")[1];
        final String collection = "NEWS" + year + "-" + mt;

        final int CURRENT_MONTH = cal.get(Calendar.MONTH);
        cal.add(Calendar.DATE, 1);
        final int NEXT_MONTH = cal.get(Calendar.MONTH);
        final String test = "TEST_NEWS";

        //offline mode

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void setupViews(View view) {
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);

        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(mAdapter);
        recyclerHeader = (RecyclerViewHeader) view.findViewById(R.id.header);
        recyclerHeader.attachTo(recycler);
    }

    private void filterCategories(String category) {

        _articles.clear();

        for (int i = 0; i < articles.size(); ++i) {
            if (articles.get(i).getCategories() != null && articles.get(i).getCategories().toLowerCase().contains(category)) {
                _articles.add(articles.get(i));
            }
        }

        if (_articles.size() >= 1) {
            article = _articles.remove(0);
            updateHData();
        } else {
            article = new Livre();
            updateHData();
        }

        Collections.sort(_articles);
        this.mAdapter.setData(_articles);
    }

    public void updateHData() {
        if (recyclerHeader != null) {
            ((TextView) recyclerHeader.findViewById(R.id.article_title)).setText(article.getTitle());
            ((TextView) recyclerHeader.findViewById(R.id.article_body)).setText(article.getBody());

            if (article.getAltDateTime() != null && article.getTitle().trim().length() > 1) {
                ((TextView) recyclerHeader.findViewById(R.id.article_date)).setText("Date: " + article.getAltDateTime());
            } else {
                ((TextView) recyclerHeader.findViewById(R.id.article_date)).setText("");
            }

            final ImageView articleImage = (ImageView) recyclerHeader.findViewById(R.id.article_image);

            if (article.getTitle() != null && article.getTitle().trim().length() > 1) {
//                RequestOptions options = new RequestOptions()
//                        .centerCrop()
//                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                        .placeholder(R.drawable.article)
//                        .dontTransform()
//                        .priority(Priority.HIGH);
//
//                Glide.with(recyclerHeader.getContext())
//                        .load((article.getImage() + "").trim())
//                        .apply(options)
//                        .into(articleImage)
//                        .onLoadFailed();
                Button puzzle = recyclerHeader.findViewById(R.id.puzzle);

                Glide.with(recyclerHeader.getContext())
                        .asBitmap()
                        .load(article.getImage())
                        .into(new BitmapImageViewTarget(articleImage) {
                            @Override
                            public void onResourceReady(Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                                super.onResourceReady(bitmap, transition);
                                puzzle.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onLoadFailed(Drawable drawable) {
                                articleImage.setImageDrawable(getResources().getDrawable(R.drawable.article));
                            }
                        });

            } else {
                articleImage.setImageDrawable(getResources().getDrawable(R.drawable.notfound));
            }

            CardView card = recyclerHeader.findViewById(R.id.article_view);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (article.getTitle() != null && article.getTitle().trim().length() > 0) {
                        Gson gson = new Gson();
                        String obj = gson.toJson(article);

                        Context context = view.getContext();
                        Intent intent = new Intent(context, LivreDetailsActivity.class);
                        intent.putExtra("item_id", obj);

                        context.startActivity(intent);
                    }
                }
            });
        }
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();

        super.onPrepareOptionsMenu(menu);
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        setupViews(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        filter(query);
        return false;
    }

    public void filter(String text) {
        _articles.clear();

        if (text.isEmpty()) {
            _articles.addAll(articles);
        } else {
            for (Livre art : articles) {
                if (art.getTitle().toLowerCase().contains(text.toLowerCase())) {
                    _articles.add(art);
                }
            }
        }

        if (_articles.size() > 1) {
            article = _articles.remove(0);
            updateHData();
        } else {
            article = _article;
            updateHData();
        }

        mAdapter.setData(_articles);
    }

}
