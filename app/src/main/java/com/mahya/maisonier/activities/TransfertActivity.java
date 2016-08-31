package com.mahya.maisonier.activities;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.mahya.maisonier.R;
import com.mahya.maisonier.adapter.DividerItemDecoration;
import com.mahya.maisonier.adapter.model.OccupationAdapter;
import com.mahya.maisonier.entites.Compte;
import com.mahya.maisonier.entites.Habitant;
import com.mahya.maisonier.entites.Occupation;
import com.mahya.maisonier.interfaces.CrudActivity;
import com.mahya.maisonier.interfaces.OnItemClickListener;
import com.mahya.maisonier.utils.Constants;
import com.mahya.maisonier.utils.MyRecyclerScroll;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;


public class TransfertActivity extends BaseActivity implements CrudActivity, SearchView.OnQueryTextListener,
        OnItemClickListener {


    protected RecyclerView mRecyclerView;
    DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    OccupationAdapter mAdapter;
    FrameLayout fab;
    FloatingActionButton myfab_main_btn;
    Animation animation;
    ImageView photo;
    Button changeDate;
    Bitmap thePic = null;
    Habitant h = null;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private android.support.v7.view.ActionMode actionMode;
    private android.content.Context context = this;
    private TextView tvEmptyView;
    private int page = 0;
    private Uri picUri;
    private int month;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setAllowReturnTransitionOverlap(true);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setSharedElementExitTransition(new ChangeTransform());
        animation = AnimationUtils.loadAnimation(this, R.anim.simple_grow);
        super.setContentView(R.layout.activity_model1);

        setTitle(context.getString(R.string.Transfertintercompte));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initView();
        fab.startAnimation(animation);
        mAdapter = new OccupationAdapter(this, (ArrayList<Occupation>) Occupation.findAll(), this);
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
        if (Occupation.findAll().isEmpty()) {
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
        dialog.setContentView(R.layout.trqnsfert_compte);
        // Initialisation du formulaire
        final Spinner habitant;
        final ImageView imgBailleur;
        final TextView Nom;
        final TextView Prenom;
        final TextView Titre;
        final Spinner logement;
        final Spinner comptesource;
        final Spinner comptedest;
        final EditText montant;

        habitant = (Spinner) dialog.findViewById(R.id.habitant);
        imgBailleur = (ImageView) dialog.findViewById(R.id.imgBailleur);
        Nom = (TextView) dialog.findViewById(R.id.Nom);
        Prenom = (TextView) dialog.findViewById(R.id.Prenom);
        Titre = (TextView) dialog.findViewById(R.id.Titre);
        logement = (Spinner) dialog.findViewById(R.id.logement);
        comptesource = (Spinner) dialog.findViewById(R.id.comptesource);
        comptedest = (Spinner) dialog.findViewById(R.id.comptedest);
        montant = (EditText) dialog.findViewById(R.id.montant);

        final Button valider = (Button) dialog.findViewById(R.id.valider);
        final Button annuler = (Button) dialog.findViewById(R.id.annuler);


        final HintSpinner habitantHint = new HintSpinner<>(
                habitant,
                new HintAdapter<Habitant>(context, "Habitant ", Habitant.findAll()),
                new HintSpinner.Callback<Habitant>() {


                    @Override
                    public void onItemSelected(int position, Habitant hab) {

                        h = hab;
                        Nom.setText(h.getNom());
                        Prenom.setText(h.getPrenom());
                        Titre.setText(h.getTitre());
                        if (h.getPhoto() != null) {

                            imgBailleur.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.patch + "" + h.getPhoto()));
                        }

                        final HintSpinner logementHint = new HintSpinner<>(
                                logement,
                                new HintAdapter<Occupation>(context, "Logement ", hab.getOccupationList()),
                                new HintSpinner.Callback<Occupation>() {


                                    @Override
                                    public void onItemSelected(int position, final Occupation occ) {
                                        final HintSpinner depatHint = new HintSpinner<>(
                                                comptesource,
                                                new HintAdapter<Compte>(context, "Compte source ", occ.getCompteList()),
                                                new HintSpinner.Callback<Compte>() {


                                                    @Override
                                                    public void onItemSelected(int position, Compte dep) {

                                                        List<Compte> comptes = occ.getCompteList();
                                                        comptes.remove(dep);
                                                        final HintSpinner depatHint = new HintSpinner<>(
                                                                comptedest,
                                                                new HintAdapter<Compte>(context, "Compte de destination ", comptes),
                                                                new HintSpinner.Callback<Compte>() {


                                                                    @Override
                                                                    public void onItemSelected(int position, Compte itemAtPosition) {


                                                                    }
                                                                });
                                                        depatHint.init();
                                                    }
                                                });
                                        depatHint.init();

                                    }
                                });
                        logementHint.init();
                    }
                });
        habitantHint.init();
        valider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                if (montant.getText().toString().trim().isEmpty() || Integer.parseInt(montant.getText().toString().trim()) < 1) {
                    montant.setError("Verifier la valeur du montant");
                    return;
                }


                try {
                    Snackbar.make(view, "l'occupation a été correctement crée", Snackbar.LENGTH_LONG)

                            .setAction("Action", null).show();
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
        // Delete.tables(Occupation.class);
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

                            Occupation occupation = new Occupation();
                            occupation.setId(id);
                            occupation.delete();

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
        final List<Occupation> filteredModelList = filter(Occupation.findAll(), query);
        mAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<Occupation> filter(List<Occupation> models, String query) {
        query = query.toLowerCase();
        System.out.println(models);
        final List<Occupation> filteredModelList = new ArrayList<>();
        for (Occupation model : models) {
            final String text = model.getHabitant().load().getNom().toLowerCase();
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
