package com.mbds.livreenfolie.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.mbds.livreenfolie.MainActivity;
import com.mbds.livreenfolie.R;
import com.mbds.livreenfolie.model.Livre;
import com.orhanobut.hawk.Hawk;

import java.util.Locale;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LivreDetailsActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    Livre article;
    private TextToSpeech textToSpeech;

    @BindView(R.id.tts_btn)
    ImageButton ttsBtn;

    String[] currentFileChunks;

    @BindView(R.id.article_image)
    ImageView articleImage;

    @BindView(R.id.category)
    TextView category;

    @BindView(R.id.puzzle)
    Button puzzle;


//    @BindView(R.id.summary_content)
//    TextView summary;

    private boolean isFr;
    private boolean isSame;
    private boolean imageFound;

    String SUMMARY_RESULT = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_article_details);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Hawk.init(this).build();

        textToSpeech = new TextToSpeech(this, this);

        if (savedInstanceState == null) {
            String rs = getIntent().getStringExtra("item_id");
            Gson gs = new Gson();
            article = gs.fromJson(rs, Livre.class);
            toolbar.setTitle(article.getTitle());
            isFr = article.getLang().toLowerCase().contains("fr");

            TextView rName = findViewById(R.id.title);
            rName.setText(article.getTitle());

            TextView rDesc2 = findViewById(R.id.article_body);
            String body = article.getBody(); //.replace("\\n\\n", "\n\n");


            try {
                String allcat = article.getCategories();
                Resources res = getResources();
                String[] categories = res.getStringArray(R.array.article_categories);

                if (allcat != null && allcat.length() > 6) {
                    String catg = "";

                    for (int i = 0; i < categories.length; ++i) {
                        if (allcat.contains(categories[i].substring(0, 3))) {
                            catg += categories[i] + " ";
                        }
                    }

                    category.setText(catg.replace(",", "").trim().replace(" ", " - "));
                } else {
                    category.setText("Actus");
                }
            } catch (Exception exc) {
                category.setText("News");
            }


            TextView rContact = findViewById(R.id.source);
            rContact.setText("Source: " + article.getSourceUri()+ ", Autheur: " + article.getTitle());

            final String url = article.getImage() + "";
            Glide.with(articleImage.getContext())
                    .asBitmap()
                    .load(url.trim())
                    .into(new BitmapImageViewTarget(articleImage) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                            super.onResourceReady(bitmap, transition);
                            puzzle.setVisibility(View.VISIBLE);
                            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                                public void onGenerated(Palette p) {
                                    setBackgroundColor(p);
                                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(p.getDominantColor(0)));
                                }
                            });
                        }

                        @Override
                        public void onLoadFailed(Drawable drawable) {
                            articleImage.setImageDrawable(getResources().getDrawable(R.drawable.article));
                            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.article);
                            Palette.from(bm).generate(new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(@Nullable Palette p) {
                                    setBackgroundColor(p);
                                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(p.getDominantColor(0)));
                                }
                            });
                        }
                    });
        }

        FloatingActionButton share = findViewById(R.id.fab);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareThing();
            }
        });

        Button read = findViewById(R.id.button_read);
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(context, Uri.parse(article.getUrl()));
            }
        });

//        Button share = findViewById(R.id.fab);
//        share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                shareArt();
//            }
//        });

        ttsBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

            }

        });

    }

    private void setBackgroundColor(Palette palette) {
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(palette.getVibrantSwatch().getRgb());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void shareArt() {
        String temp = article.getTitle() + "\n\n" + article.getBody();
        final String jn = temp + "\n\nPlus d'articles sur Livre En Folie : https://play.google.com/store/apps/details?id=com.mbds.livreenfolie.";

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, jn);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share Livre En Folie"));
    }

    @Override
    public void onResume() {
        super.onResume();

        if (article == null) {
            article = Hawk.get("CURRENT_ARTICLE");
        }
    }

    private void shareThing() {
        String body = article.getBody();
        if (body.length() > 250) {
            body = body.substring(0, 249) + "...";
        }
        final String data = "\n\n" + article.getTitle() + "\n\n" + body
                + "\n\n" + "Source: " + article.getSourceUri() + ", Autheur: " + article.getTitle()
                + "\n\n\n Ecoutez cet article sur l'application Livre En Folie: https://play.google.com/store/apps/details?id=com.mbds.livreenfolie";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, data);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share Livre En Folie"));
    }

    @Override
    public void onPause() {

        textToSpeech.stop();
        textToSpeech.shutdown();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        textToSpeech.stop();
        textToSpeech.shutdown();
        super.onDestroy();
    }

    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {
            if (isFr) {
                textToSpeech.setLanguage(Locale.CANADA_FRENCH);
            } else {
                textToSpeech.setLanguage(Locale.US);
            }

//
//            String start_sum;
//            if (isFr) {
//                start_sum = "Lecture de l'article " + article.getTitle() + " sur l'application Livre En Folie\n\n ";
//            } else {
//                start_sum = "Reading of the article " + article.getTitle() + " on the application Livre En Folie\n\n ";
//                textToSpeech.setSpeechRate(0.5f);
//            }

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public static String[] convertFileToParagraph(String fileContent) {
        String pattern = "([ \\t\\r]*\\n[ \\t\\r]*)+";
        return Pattern.compile(pattern, Pattern.MULTILINE).split(fileContent);
    }

}