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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.Spinner;
import android.widget.TextView;

import com.etiennelawlor.imagegallery.library.enums.PaletteColorType;
import com.github.clans.fab.FloatingActionButton;
import com.mahya.maisonier.GalleryDemoActivity;
import com.mahya.maisonier.R;
import com.mahya.maisonier.adapter.DividerItemDecoration;
import com.mahya.maisonier.adapter.GalleryImageAdapter;
import com.mahya.maisonier.adapter.model.BatimentAdapter;
import com.mahya.maisonier.entites.Batiment;
import com.mahya.maisonier.entites.Batiment_Table;
import com.mahya.maisonier.entites.Caracteristique;
import com.mahya.maisonier.entites.Cite;
import com.mahya.maisonier.entites.PhotoBatiment;
import com.mahya.maisonier.interfaces.CrudActivity;
import com.mahya.maisonier.interfaces.OnItemClickListener;
import com.mahya.maisonier.utils.MyRecyclerScroll;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

public class BatimentActivity extends BaseActivity implements CrudActivity, SearchView.OnQueryTextListener,
        OnItemClickListener {


    private static final String TAG = BatimentActivity.class.getSimpleName();
    protected RecyclerView mRecyclerView;
    BatimentAdapter mAdapter;
    FrameLayout fab;
    FloatingActionButton myfab_main_btn;
    Animation animation;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private android.support.v7.view.ActionMode actionMode;
    private android.content.Context context = this;
    private TextView tvEmptyView;
    private PaletteColorType paletteColorType;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setAllowReturnTransitionOverlap(true);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setSharedElementExitTransition(new ChangeTransform());
        animation = AnimationUtils.loadAnimation(this, R.anim.simple_grow);
        super.setContentView(R.layout.activity_model1);

        setTitle("Batiment");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initView();

        fab.startAnimation(animation);
        mAdapter = new BatimentAdapter(this, Batiment.findAll(), (OnItemClickListener) this);
        myfab_main_btn.hide(false);
        mRecyclerView.setAdapter(mAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myfab_main_btn.show(true);
                myfab_main_btn.setShowAnimation(AnimationUtils.loadAnimation(context, R.anim.show_from_bottom));
                myfab_main_btn.setHideAnimation(AnimationUtils.loadAnimation(context, R.anim.hide_to_bottom));
            }
        }, 300);
        mRecyclerView.addOnScrollListener(new MyRecyclerScroll() {
            @Override
            public void show() {
                myfab_main_btn.show(true);
            }

            @Override
            public void hide() {
                myfab_main_btn.hide(true);
            }
        });

    }


    private void initView() {

        fab = (FrameLayout) findViewById(R.id.myfab_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_item);
        tvEmptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView.setFilterTouchesWhenObscured(true);
        myfab_main_btn = (FloatingActionButton) findViewById(R.id.myfab_main_btn);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        if (new Batiment().findAll().isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            tvEmptyView.setVisibility(View.VISIBLE);

        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            tvEmptyView.setVisibility(View.GONE);
        }

    }

    public void action(final View view) {
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
        dialog.setContentView(R.layout.add_batiment);
        // Initialisation du formulaire

        final EditText code = (EditText) dialog.findViewById(R.id.code);
        final EditText nom = (EditText) dialog.findViewById(R.id.nom);
        final Spinner cite = (Spinner) dialog.findViewById(R.id.cite);
        final HintSpinner citeHint = new HintSpinner<>(
                cite,
                new HintAdapter<Cite>(this, "Cite", Cite.findAll()),
                new HintSpinner.Callback<Cite>() {


                    @Override
                    public void onItemSelected(int position, Cite itemAtPosition) {
                    }
                });
        citeHint.init();

        final Button valider = (Button) dialog.findViewById(R.id.valider);
        final Button annuler = (Button) dialog.findViewById(R.id.annuler);
        // Click cancel to dismiss android custom dialog box
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nom.getText().toString().trim().equals("")) {
                    nom.setError("Velliez remplir le nom");
                    return;

                }
                if (code.getText().toString().trim().equals("")) {
                    code.setError("Velliez remplir le code");
                    return;

                }
                Batiment batiment = new Batiment();
                batiment.setCode(code.getText().toString().trim());
                batiment.setNom(nom.getText().toString().trim());
                batiment.assoCite((Cite) cite.getSelectedItem());
                try {
                    batiment.save();
                    Snackbar.make(view, "le batiment a été correctement crée", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mAdapter.addItem(batiment, 0);
                    if (new Caracteristique().findAll().isEmpty()) {
                        mRecyclerView.setVisibility(View.GONE);
                        tvEmptyView.setVisibility(View.VISIBLE);

                    } else {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        tvEmptyView.setVisibility(View.GONE);
                    }
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

                code.setText("");
                nom.setText("");

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void detail(int i) {
        {
            TextView Code;
            TextView Nom;
            TextView Cite;
            CheckBox Etat;
            final Batiment batiment = SQLite.select().from(Batiment.class).where(Batiment_Table.id.eq(i)).querySingle();
            final Dialog dialog = new Dialog(context);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.aff_batiment);
            // Initialisation du formulaire
            List<String> urls = new ArrayList<>();
            for (PhotoBatiment image1 : batiment.getPhotoBatimentList()
                    ) {
                urls.add(image1.getNom());

            }
            Gallery gallery = (Gallery) dialog.findViewById(R.id.gal);
            gallery.setAdapter(new GalleryImageAdapter(this, urls, 1));

            Code = (TextView) dialog.findViewById(R.id.Code);
            Nom = (TextView) dialog.findViewById(R.id.Nom);
            Cite = (TextView) dialog.findViewById(R.id.Cite);
            Etat = (CheckBox) dialog.findViewById(R.id.Etat);
           final Button fermer  = (Button) dialog.findViewById(R.id.fermer);


            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Code.setText(batiment.getCode());
            Nom.setText(batiment.getNom());
            Cite.setText(batiment.getCite().load().getNomCite());
            dialog.show();
            fermer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dialog.dismiss();
                }
            });
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null;
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

                            Batiment Batiment = new Batiment();
                            Batiment.setId(id);
                            Batiment.delete();

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
            if (count == 1) {

                actionMode.getMenu().findItem(R.id.pic).setVisible(true);

            } else {

                actionMode.getMenu().findItem(R.id.pic).setVisible(false);
            }
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
    public void modifier(final int id) {

        final Batiment batiment = SQLite.select().from(Batiment.class).where(Batiment_Table.id.eq(id)).querySingle();
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_batiment);
        // Initialisation du formulaire

        final EditText code = (EditText) dialog.findViewById(R.id.code);
        final EditText nom = (EditText) dialog.findViewById(R.id.nom);
        final Spinner cite = (Spinner) dialog.findViewById(R.id.cite);
        final HintSpinner citeHint = new HintSpinner<>(
                cite,
                new HintAdapter<Cite>(this, "Cite", Cite.findAll()),
                new HintSpinner.Callback<Cite>() {


                    @Override
                    public void onItemSelected(int position, Cite itemAtPosition) {
                    }
                });
        citeHint.init();

        nom.setText(batiment.getNom());
        code.setText(batiment.getCode());
        final Button valider = (Button) dialog.findViewById(R.id.valider);
        final Button annuler = (Button) dialog.findViewById(R.id.annuler);
        // Click cancel to dismiss android custom dialog box
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nom.getText().toString().trim().equals("")) {
                    nom.setError("Velliez remplir le nom");
                    return;

                }
                if (code.getText().toString().trim().equals("")) {
                    code.setError("Velliez remplir le code");
                    return;

                }
                try {

                    batiment.setId(batiment.getId());
                    batiment.setCode(code.getText().toString().trim());
                    batiment.setNom(nom.getText().toString().trim());
                    batiment.assoCite((Cite) cite.getSelectedItem());
                    batiment.save();
                    mAdapter.actualiser(Batiment.findAll());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }


                dialog.dismiss();
            }
        });
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code.setText("");
                nom.setText("");

                dialog.dismiss();
            }
        });
        dialog.show();
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
        final List<Batiment> filteredModelList = filter(Batiment.findAll(), query);
        mAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private List<Batiment> filter(List<Batiment> models, String query) {
        query = query.toLowerCase();
        System.out.println(models);
        final List<Batiment> filteredModelList = new ArrayList<>();
        for (Batiment model : models) {
            final String text = model.getNom().toLowerCase();
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
                case R.id.pic:

                    new GalleryDemoActivity().film(1);

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
