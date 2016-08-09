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
import android.text.Html;
import android.transition.ChangeTransform;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.daimajia.swipe.util.Attributes;
import com.mahya.maisonier.R;
import com.mahya.maisonier.adapter.DividerItemDecoration;
import com.mahya.maisonier.adapter.model.CiteAdapter;
import com.mahya.maisonier.entites.Bailleur;
import com.mahya.maisonier.entites.Cite;
import com.mahya.maisonier.entites.Cite_Table;
import com.mahya.maisonier.entites.TypeLogement_Table;
import com.mahya.maisonier.interfaces.CrudActivity;
import com.mahya.maisonier.interfaces.OnItemClickListener;
import com.mahya.maisonier.utils.CustomLoadingListItemCreator;
import com.paginate.Paginate;
import com.paginate.recycler.LoadingListItemSpanLookup;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

public class CiteActivity extends BaseActivity implements Paginate.Callbacks, CrudActivity, SearchView.OnQueryTextListener,
        OnItemClickListener {

    private static final int GRID_SPAN = 3;
    private static final String TAG = CiteActivity.class.getSimpleName();
    protected RecyclerView mRecyclerView;
    CiteAdapter mAdapter;
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
            mAdapter.add(Cite.getInitData(initItem));
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
        Cite.cites.clear();
        Cite.cites = Cite.findAll();
        if (Cite.cites.size() < 50) {
            initItem = Cite.cites.size();
        }
        totalPages = Cite.cites.size() / initItem;
        if (totalPages < 0) {

            totalPages = 1;
        }
        setTitle(context.getString(R.string.Cite));
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
        if (Cite.findAll().isEmpty()) {
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
        dialog.setContentView(R.layout.add_cite);
        // Initialisation du formulaire

        final TextView option = (TextView) dialog.findViewById(R.id.optionCite);
        final EditText nom = (EditText) dialog.findViewById(R.id.NomCite);
        final EditText tel = (EditText) dialog.findViewById(R.id.Telephone);
        final EditText email = (EditText) dialog.findViewById(R.id.Email);
        final EditText siege = (EditText) dialog.findViewById(R.id.Siege);
        final EditText Pcite = (EditText) dialog.findViewById(R.id.PoliceDeLaCite);
        final EditText Pcont = (EditText) dialog.findViewById(R.id.PoliceDesContacts);
        final EditText Pdesc = (EditText) dialog.findViewById(R.id.PoliceDeLaDescription);
        final Spinner bailleur = (Spinner) dialog.findViewById(R.id.Bailleur);
        final EditText desc = (EditText) dialog.findViewById(R.id.Description);
        option.setText(context.getText(R.string.ajouterCite));
        ArrayAdapter<Bailleur> adapter =
                new ArrayAdapter<Bailleur>(this, android.R.layout.simple_spinner_dropdown_item, Bailleur.findAll());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        bailleur.setAdapter(adapter);

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
                if (siege.getText().toString().trim().equals("")) {
                    desc.setError("Velliez remplir le siège");
                    return;

                }
                if (bailleur.getSelectedItem().toString().trim().equals("")) {
                    // bailleur.setEr("Velliez remplir le code");
                    return;

                }

                Cite cite = new Cite();
                cite.setNomCite(nom.getText().toString().trim());
                cite.setTels(tel.getText().toString().trim());
                cite.setDescription(desc.getText().toString().trim());
                cite.setSiege(siege.getText().toString().trim());
                cite.setEmail(email.getText().toString().trim());
                cite.assoBailleur((Bailleur) bailleur.getSelectedItem());
                cite.setPoliceCite(Double.parseDouble(Pcite.getText().toString().trim()));
                cite.setPoliceContact(Double.parseDouble(Pcont.getText().toString().trim()));
                cite.setPoliceDescription(Double.parseDouble(Pdesc.getText().toString().trim()));
                try {
                    cite.save();
                    if (Cite.findAll().isEmpty()) {
                        mRecyclerView.setVisibility(View.GONE);
                        tvEmptyView.setVisibility(View.VISIBLE);

                    } else {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        tvEmptyView.setVisibility(View.GONE);
                    }

                    Snackbar.make(view, "la cite a été correctement crée", Snackbar.LENGTH_LONG)

                            .setAction("Action", null).show();
                    mAdapter.addItem(0, cite);
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
    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null;
        //FlowManager.destroy();
        // Delete.tables(Cite.class);
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

                            Cite cite = new Cite();
                            cite.setId(id);
                            cite.delete();

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
        final Cite typeLogement = SQLite.select().from(Cite.class).where(TypeLogement_Table.id.eq(id)).querySingle();

        AlertDialog detail = new AlertDialog.Builder(this)
                .setMessage(Html.fromHtml("<b>" + "Code: " + "</b> ") + typeLogement.getEmail() + "\n" + "\n " + Html.fromHtml("<b>" + "Description: " + "</b> ") + typeLogement.getDescription())
                .setIcon(R.drawable.ic_info_indigo_900_18dp)
                .setTitle("Detail " + typeLogement.getNomCite())
                .setNeutralButton("OK", null)
                .setCancelable(false)
                .create();
        detail.show();

    }

    @Override
    public void modifier(final int id) {

        final Cite cite = SQLite.select().from(Cite.class).where(Cite_Table.id.eq(id)).querySingle();
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_cite);
        // Initialisation du formulaire

        final TextView option = (TextView) dialog.findViewById(R.id.optionCite);
        final EditText nom = (EditText) dialog.findViewById(R.id.NomCite);
        final EditText tel = (EditText) dialog.findViewById(R.id.Telephone);
        final EditText email = (EditText) dialog.findViewById(R.id.Email);
        final EditText Pcite = (EditText) dialog.findViewById(R.id.PoliceDeLaCite);
        final EditText Pcont = (EditText) dialog.findViewById(R.id.PoliceDesContacts);
        final EditText Pdesc = (EditText) dialog.findViewById(R.id.PoliceDeLaDescription);
        final Spinner bailleur = (Spinner) dialog.findViewById(R.id.Bailleur);
        final EditText desc = (EditText) dialog.findViewById(R.id.Description);

        option.setText(context.getText(R.string.modifierCite));
        ArrayAdapter<Bailleur> adapter =
                new ArrayAdapter<Bailleur>(this, R.layout.spinner_item, Bailleur.findAll());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        bailleur.setAdapter(adapter);
        nom.setText(cite.getNomCite());
        tel.setText(cite.getTels());
        email.setText(cite.getEmail());
        Pcite.setText(String.valueOf(cite.getPoliceCite()));
        Pdesc.setText(String.valueOf(cite.getPoliceDescription()));
        Pcont.setText(String.valueOf(cite.getPoliceContact()));
        desc.setText(cite.getDescription());

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

                Cite cite = new Cite();
                cite.setNomCite(nom.getText().toString().trim());
                cite.setTels(tel.getText().toString().trim());
                cite.setDescription(desc.getText().toString().trim());
                cite.setEmail(email.getText().toString().trim());
                cite.assoBailleur((Bailleur) bailleur.getSelectedItem());
                cite.setPoliceCite(Double.parseDouble(Pcite.getText().toString().trim()));
                cite.setPoliceContact(Double.parseDouble(Pcont.getText().toString().trim()));
                cite.setPoliceDescription(Double.parseDouble(Pdesc.getText().toString().trim()));
                try {
                    cite.save();
                    Snackbar.make(view, "le cite a été correctement modifier", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mAdapter.actualiser(Cite.findAll());
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
    protected void setupPagination() {
        // If RecyclerView was recently bound, unbind
        if (paginate != null) {
            paginate.unbind();
        }
        handler.removeCallbacks(fakeCallback);
        //  mAdapter = new CiteAdapter(this, (ArrayList<Cite>) Cite.findAll(), this);
        loading = false;
        page = 0;

        mAdapter = new CiteAdapter(this, Cite.getInitData(initItem), this);
        mRecyclerView.setAdapter(mAdapter);


        ((CiteAdapter) mAdapter).setMode(Attributes.Mode.Single);
        paginate = Paginate.with(mRecyclerView, this)
                .setLoadingTriggerThreshold(threshold)
                .addLoadingListItem(addLoadingRow)
                .setLoadingListItemCreator(customLoadingListItem ? new CustomLoadingListItemCreator(mRecyclerView) : null)
                .setLoadingListItemSpanSizeLookup(new LoadingListItemSpanLookup() {
                    @Override
                    public int getSpanSize() {
                        return 0;
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
        final List<Cite> filteredModelList = filter(Cite.findAll(), query);
        mAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private List<Cite> filter(List<Cite> models, String query) {
        query = query.toLowerCase();
        System.out.println(models);
        final List<Cite> filteredModelList = new ArrayList<>();
        for (Cite model : models) {
            final String text = model.getNomCite().toLowerCase();
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
