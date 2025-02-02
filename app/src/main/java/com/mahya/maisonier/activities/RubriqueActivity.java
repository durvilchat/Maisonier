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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.mahya.maisonier.R;
import com.mahya.maisonier.adapter.DividerItemDecoration;
import com.mahya.maisonier.adapter.model.RubriqueAdapter;
import com.mahya.maisonier.entites.ArticleBail;
import com.mahya.maisonier.entites.Bailleur;
import com.mahya.maisonier.entites.Cite_Table;
import com.mahya.maisonier.entites.Rubrique;
import com.mahya.maisonier.interfaces.CrudActivity;
import com.mahya.maisonier.interfaces.OnItemClickListener;
import com.mahya.maisonier.utils.MyRecyclerScroll;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

public class RubriqueActivity extends BaseActivity implements CrudActivity, SearchView.OnQueryTextListener,
        OnItemClickListener {

    private static final String TAG = RubriqueActivity.class.getSimpleName();
    protected RecyclerView mRecyclerView;
    RubriqueAdapter mAdapter;
    FrameLayout fab;
    FloatingActionButton myfab_main_btn;
    Animation animation;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private android.support.v7.view.ActionMode actionMode;
    private android.content.Context context = this;
    private TextView tvEmptyView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setAllowReturnTransitionOverlap(true);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setSharedElementExitTransition(new ChangeTransform());
        animation = AnimationUtils.loadAnimation(this, R.anim.simple_grow);
        super.setContentView(R.layout.activity_model1);
        Rubrique.rubriques.clear();
        Rubrique.rubriques = Rubrique.findAll();

        setTitle(context.getString(R.string.Rubrique));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initView();
        fab.startAnimation(animation);
        mAdapter = new RubriqueAdapter(this, (ArrayList<Rubrique>) Rubrique.findAll(), this);
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
        if (Rubrique.findAll().isEmpty()) {
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
        dialog.setContentView(R.layout.add_rubrique);
        // Initialisation du formulaire

        TextView mOperation = (TextView) dialog.findViewById(R.id.operation);
        final EditText mLibelle = (EditText) dialog.findViewById(R.id.Libelle);
        final CheckBox mValeur = (CheckBox) dialog.findViewById(R.id.Valeur);
        final EditText mNumero = (EditText) dialog.findViewById(R.id.Numero);
        final Spinner mArticle = (Spinner) dialog.findViewById(R.id.Article);
        final HintSpinner contratHint = new HintSpinner<>(
                mArticle,
                new HintAdapter<ArticleBail>(this, "Article  ", ArticleBail.findAll()),
                new HintSpinner.Callback<ArticleBail>() {


                    @Override
                    public void onItemSelected(int position, ArticleBail itemAtPosition) {
                    }
                });
        contratHint.init();

        final Button valider = (Button) dialog.findViewById(R.id.valider);
        final Button annuler = (Button) dialog.findViewById(R.id.annuler);
        // Click cancel to dismiss android custom dialog box
        valider.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (mLibelle.getText().toString().trim().equals("")) {
                    mLibelle.setError("Velliez remplir le libellé");
                    return;

                }

                Rubrique rubrique = new Rubrique();
                rubrique.setLibelle(mLibelle.getText().toString().trim());
                rubrique.setValeur(mValeur.isChecked());
                rubrique.setNumero(Integer.parseInt(mNumero.getText().toString().trim()));
                rubrique.assocArticleBail((ArticleBail) mArticle.getSelectedItem());

                try {
                    rubrique.save();
                    if (Rubrique.findAll().isEmpty()) {
                        mRecyclerView.setVisibility(View.GONE);
                        tvEmptyView.setVisibility(View.VISIBLE);

                    } else {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        tvEmptyView.setVisibility(View.GONE);
                    }

                    Snackbar.make(view, "la Rubrique a été correctement crée", Snackbar.LENGTH_LONG)

                            .setAction("Action", null).show();
                    mAdapter.addItem(0, rubrique);
                } catch (android.database.sqlite.SQLiteConstraintException e) {


                    Snackbar.make(v, "Rubrique déja existante", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } catch (Exception e) {
                    Snackbar.make(view, "echec d'enregistremment", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();;
                }


                dialog.dismiss();
            }
        });

        // Your android custom dialog ok action
        // Action for custom dialog ok button click
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLibelle.setText("");

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
        // Delete.tables(Rubrique.class);
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

                            Rubrique Rubrique = new Rubrique();
                            Rubrique.setId(id);
                            Rubrique.delete();

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

        final Rubrique Rubrique = SQLite.select().from(Rubrique.class).where(Cite_Table.id.eq(id)).querySingle();
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_cite);
        // Initialisation du formulaire

        final TextView option = (TextView) dialog.findViewById(R.id.operation);
        final EditText nom = (EditText) dialog.findViewById(R.id.NomCite);
        final EditText tel = (EditText) dialog.findViewById(R.id.Telephone);
        final EditText email = (EditText) dialog.findViewById(R.id.Email);
        final EditText Pcite = (EditText) dialog.findViewById(R.id.PoliceDeLaCite);
        final EditText siege = (EditText) dialog.findViewById(R.id.Siege);
        final EditText Pcont = (EditText) dialog.findViewById(R.id.PoliceDesContacts);
        final EditText Pdesc = (EditText) dialog.findViewById(R.id.PoliceDeLaDescription);
        final Spinner bailleur = (Spinner) dialog.findViewById(R.id.Bailleur);
        final EditText desc = (EditText) dialog.findViewById(R.id.Description);

        option.setText(context.getText(R.string.modifierCite));
        ArrayAdapter<Bailleur> adapter =
                new ArrayAdapter<Bailleur>(this, R.layout.spinner_item, Bailleur.findAll());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        bailleur.setAdapter(adapter);


        final Button valider = (Button) dialog.findViewById(R.id.valider);
        final Button annuler = (Button) dialog.findViewById(R.id.annuler);
        // Click cancel to dismiss android custom dialog box
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nom.getText().toString().trim().equals("")) {
                    nom.setError("Velliez remplir le nom");
                    return;

                }
                if (tel.getText().toString().trim().equals("")) {
                    tel.setError("Velliez remplir le telephone");
                    return;

                }
                if (email.getText().toString().trim().equals("")) {
                    email.setError("Velliez remplir le email");
                    return;

                }
                if (desc.getText().toString().trim().equals("")) {
                    desc.setError("Velliez remplir la description");
                    return;

                }
                if (bailleur.getSelectedItem().toString().trim().equals("")) {
                    // bailleur.setEr("Velliez remplir le code");
                    return;

                }


                try {
                    Rubrique.save();
                    Snackbar.make(view, "le Rubrique a été correctement modifier", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mAdapter.actualiser(Rubrique.findAll());
                }  catch (android.database.sqlite.SQLiteConstraintException e) {


                    Snackbar.make(view, "Rubrique déjà existanet", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
                catch (Exception e) {
                    Snackbar.make(view, "echec de la modification", Snackbar.LENGTH_LONG)
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

                nom.setText("");
                desc.setText("");
                tel.setText("");
                email.setText("");
                Pcite.setText("");
                Pdesc.setText("");
                Pcont.setText("");

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
        final List<Rubrique> filteredModelList = filter(Rubrique.findAll(), query);
        mAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private List<Rubrique> filter(List<Rubrique> models, String query) {
        query = query.toLowerCase();
        System.out.println(models);
        final List<Rubrique> filteredModelList = new ArrayList<>();
        for (Rubrique model : models) {
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
