package com.thadocizn.blacklivesmatter;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ArticleActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>>, ItemClickListener {

    private static final String LOG_TAG = ArticleActivity.class.getName();
    private static final String USGS_REQUEST_URL =
            "https://content.guardianapis.com/search?q=black%20lives%20matter%20&api-key=0d50943e-a985-4eae-9e6b-1ec667c3e829";
    private static final int Article_LOADER_ID = 1;
    private ArticleAdapter adapter;
    private List<Article> articlesList = new ArrayList<>();
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_activity);

        RecyclerView articleListView = (RecyclerView) findViewById(R.id.recyclerView);
        articleListView.setLayoutManager(new LinearLayoutManager(this));
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        adapter = new ArticleAdapter(this, articlesList);
        adapter.setClickListener(this);
        articleListView.setAdapter(adapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        articleListView.addItemDecoration(itemDecoration);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(Article_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

    }

    @Override
    public void onClick(View view, int position) {
        // Find the current article that was clicked on
        final Article currentArticle = articlesList.get(position);
        // Convert the String URL into a URI object (to pass into the Intent constructor)
        Uri articleUri = Uri.parse(currentArticle.getWebLink());
        // Create a new intent to view the article URI
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, articleUri);
        // Send the intent to launch a new activity
        startActivity(websiteIntent);
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        return new ArticleLoader(this, USGS_REQUEST_URL);    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        adapter.setArticleInfoList(null);

        // If there is a valid list of {@link Article}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (articles != null && !articles.isEmpty()) {
            adapter.setArticleInfoList(articles);
            articlesList = new ArrayList<>(articles);
        }else {
            // Set empty state text to display "No articles found."
            mEmptyStateTextView.setText(R.string.no_articles);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        adapter.setArticleInfoList(null);
    }
}
