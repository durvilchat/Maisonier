package com.mahya.maisonier.activities;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.transition.ChangeTransform;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.swipe.util.Attributes;
import com.mahya.maisonier.R;
import com.mahya.maisonier.adapter.DividerItemDecoration;
import com.mahya.maisonier.adapter.model.ArticleAdapter;
import com.mahya.maisonier.entites.ArticleBail;
import com.mahya.maisonier.entites.ArticleBail_Table;
import com.mahya.maisonier.interfaces.CrudActivity;
import com.mahya.maisonier.interfaces.OnItemClickListener;
import com.mahya.maisonier.utils.CustomLoadingListItemCreator;
import com.paginate.Paginate;
import com.paginate.recycler.LoadingListItemSpanLookup;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

public class ArticleActivity extends BaseActivity implements Paginate.Callbacks, CrudActivity, SearchView.OnQueryTextListener,
        OnItemClickListener {

    private static final int GRID_SPAN = 3;
    private static final String TAG = ArticleActivity.class.getSimpleName();
    protected RecyclerView mRecyclerView;
    ArticleAdapter mAdapter;
    FrameLayout fab;
    ImageButton myfab_main_btn;
    Animation animation;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private android.support.v7.view.ActionMode actionMode;
    private android.content.Context context = this;
    private TextView tvEmptyView;
    private boolean loading = false;
    private int page = 0;
    private Handler handler;
    private Paginate paginate;
    private Runnable fakeCallback = new Runnable() {
        @Override
        public void run() {
            page++;
            mAdapter.add(ArticleBail.getInitData(1));
            loading = false;
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setAllowReturnTransitionOverlap(true);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setSharedElementExitTransition(new ChangeTransform());
        animation = AnimationUtils.loadAnimation(this, R.anim.simple_grow);
        super.setContentView(R.layout.activity_model1);
        ArticleBail.articleBails.clear();
        ArticleBail.articleBails = ArticleBail.findAll();
        setTitle(context.getString(R.string.article));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initView();
        fab.startAnimation(animation);
        handler = new Handler();
        setupPagination();
    }

    private void initView() {

        fab = (FrameLayout) findViewById(R.id.myfab_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_item);
        tvEmptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView.setFilterTouchesWhenObscured(true);
        myfab_main_btn = (ImageButton) findViewById(R.id.myfab_main_btn);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        if (new ArticleBail().findAll().isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            tvEmptyView.setVisibility(View.VISIBLE);

        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            tvEmptyView.setVisibility(View.GONE);
        }

    }

    public void add(final View view) {
        switch (view.getId()) {
            case R.id.myfab_main_btn:
                ajouter(view);
                break;
        }
    }

    @Override
    public void ajouter(final View view) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_articles);
        // Initialisation du formulaire

        final EditText num = (EditText) dialog.findViewById(R.id.Numero);
        final EditText libelle = (EditText) dialog.findViewById(R.id.Libelle);

        final Button valider = (Button) dialog.findViewById(R.id.valider);
        final Button annuler = (Button) dialog.findViewById(R.id.annuler);
        // Click cancel to dismiss android custom dialog box
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (libelle.getText().toString().trim().equals("")) {
                    libelle.setError("Velliez remplir le libelle");
                    return;

                }
                if (num.getText().toString().trim().equals("")) {
                    num.setError("Velliez remplir le numéro");
                    return;

                }
                ArticleBail articleBail = new ArticleBail();
                articleBail.setLibelle(libelle.getText().toString().trim());
                articleBail.setNumero(Integer.parseInt(num.getText().toString().trim()));
                try {
                    articleBail.save();
                    Snackbar.make(view, "le type de penalité a été correctement crée", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mAdapter.addItem(0, articleBail);
                } catch (Exception e) {
                    Snackbar.make(view, "echec", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }


                dialog.dismiss();
            }
        });

        // Your android custom dialog ok action
        // Action for custom dialog ok button click
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText("");
                libelle.setText("");

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null;
        //FlowManager.destroy();
        // Delete.tables(ArticleBail.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void supprimer(final int id) {

        new AlertDialog.Builder(this)
                .setTitle("Avertissement")
                .setMessage("Voulez vous vraimment supprimer ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {

                            ArticleBail typeLogement = new ArticleBail();
                            typeLogement.setId(id);
                            typeLogement.delete();

                        } catch (Exception e) {

                        }

                        mAdapter.deleteItem(mAdapter.getSelectposition());


                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        int count = mAdapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    @Override
    public void onItemClicked(int position) {
        if (actionMode != null) {
            toggleSelection(position);
        } else {

        }
    }

    @Override
    public boolean onItemLongClicked(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }

        toggleSelection(position);

        return true;
    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public void detail(final int id) {


    }

    @Override
    public void modifier(final int id) {

        final ArticleBail typePenalite = SQLite.select().from(ArticleBail.class).where(ArticleBail_Table.id.eq(id)).querySingle();
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_articles);
        // Initialisation du formulaire

        final EditText num = (EditText) dialog.findViewById(R.id.Numero);
        final EditText libelle = (EditText) dialog.findViewById(R.id.Libelle);

        final Button valider = (Button) dialog.findViewById(R.id.valider);
        final Button annuler = (Button) dialog.findViewById(R.id.annuler);
        num.setText(typePenalite.getNumero());
        libelle.setText(typePenalite.getLibelle());
        // Click cancel to dismiss android custom dialog box
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (libelle.getText().toString().trim().equals("")) {
                    libelle.setError("Velliez remplir le libelle");
                    return;

                }
                if (num.getText().toString().trim().equals("")) {
                    num.setError("Velliez remplir le numéro");
                    return;

                }
                ArticleBail articleBail = new ArticleBail();
                articleBail.setId(id);
                articleBail.setLibelle(libelle.getText().toString().trim());
                articleBail.setNumero(Integer.parseInt(num.getText().toString().trim()));
                try {
                    articleBail.save();
                    Snackbar.make(v, "le type de penalité a été correctement modifié", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mAdapter.addItem(0, articleBail);
                } catch (Exception e) {
                    Snackbar.make(v, "echec", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }


                dialog.dismiss();
            }
        });

        // Your android custom dialog ok action
        // Action for custom dialog ok button click
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText("");
                libelle.setText("");

                dialog.dismiss();
            }

        });

        // Your android custom dialog ok action
        // Action for custom dialog ok button click
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                num.setText("");
                libelle.setText("");

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void setupPagination() {
        // If RecyclerView was recently bound, unbind
        if (paginate != null) {
            paginate.unbind();
        }
        handler.removeCallbacks(fakeCallback);
        mAdapter = new ArticleAdapter(this, (ArrayList<ArticleBail>) ArticleBail.findAll(), this);
        loading = false;
        page = 0;

        mAdapter = new ArticleAdapter(this, ArticleBail.getInitData(initItem), this);
        mRecyclerView.setAdapter(mAdapter);


        ((ArticleAdapter) mAdapter).setMode(Attributes.Mode.Single);
        paginate = Paginate.with(mRecyclerView, this)
                .setLoadingTriggerThreshold(threshold)
                .addLoadingListItem(addLoadingRow)
                .setLoadingListItemCreator(customLoadingListItem ? new CustomLoadingListItemCreator(mRecyclerView) : null)
                .setLoadingListItemSpanSizeLookup(new LoadingListItemSpanLookup() {
                    @Override
                    public int getSpanSize() {
                        return GRID_SPAN;
                    }
                })
                .build();
    }

    @Override
    public synchronized void onLoadMore() {
        Log.d("Paginate", "onLoadMore");
        loading = true;
        // Fake asynchronous loading that will generate page of random data after some delay
        handler.postDelayed(fakeCallback, networkDelay);
    }

    @Override
    public synchronized boolean isLoading() {
        return loading; // Return boolean weather data is already loading or not
    }

    @Override
    public boolean hasLoadedAllItems() {
        return page == totalPages; // If all pages are loaded return true
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.model, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return true;
    }


    @Override
    public boolean onQueryTextChange(String query) {
        final List<ArticleBail> filteredModelList = filter(ArticleBail.findAll(), query);
        mAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private List<ArticleBail> filter(List<ArticleBail> models, String query) {
        query = query.toLowerCase();
        System.out.println(models);
        final List<ArticleBail> filteredModelList = new ArrayList<>();
        for (ArticleBail model : models) {
            final String text = model.getLibelle().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


    private class ActionModeCallback implements android.support.v7.view.ActionMode.Callback {
        @SuppressWarnings("unused")
        private final String TAG = ActionModeCallback.class.getSimpleName();

        @Override
        public boolean onCreateActionMode(android.support.v7.view.ActionMode mode, Menu menu) {

            mode.getMenuInflater().inflate(R.menu.menu_supp, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(android.support.v7.view.ActionMode mode, Menu menu) {
            return false;
        }


        @Override
        public boolean onActionItemClicked(final android.support.v7.view.ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_supp:
                    new AlertDialog.Builder(context)
                            .setTitle("Avertissement")
                            .setMessage("Voulez vous vraimment supprimer ?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    try {

                                        mAdapter.removeItems(mAdapter.getSelectedItems());
                                        mode.finish();

                                    } catch (Exception e) {

                                    }


                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();

                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(android.support.v7.view.ActionMode mode) {
            mAdapter.clearSelection();
            actionMode = null;
        }
    }
}
