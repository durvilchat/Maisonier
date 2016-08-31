package com.mahya.maisonier.activities;


import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.mahya.maisonier.R;
import com.mahya.maisonier.activities.detail.Aff_HabitantActivity;
import com.mahya.maisonier.adapter.DividerItemDecoration;
import com.mahya.maisonier.adapter.model.HabitantAdapter;
import com.mahya.maisonier.entites.Caracteristique;
import com.mahya.maisonier.entites.Habitant;
import com.mahya.maisonier.entites.Habitant_Table;
import com.mahya.maisonier.fragments.AffHabitantFragment;
import com.mahya.maisonier.interfaces.CrudActivity;
import com.mahya.maisonier.interfaces.OnItemClickListener;
import com.mahya.maisonier.utils.Constants;
import com.mahya.maisonier.utils.MyRecyclerScroll;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.mahya.maisonier.utils.Utils.saveToInternalStorage;

public class HabitantActivity extends BaseActivity implements CrudActivity, SearchView.OnQueryTextListener,
        OnItemClickListener {

    //keep track of camera capture intent
    static final int CAMERA_CAPTURE = 1;
    private static final String TAG = HabitantActivity.class.getSimpleName();
    //keep track of cropping intent
    final int PIC_CROP = 3;
    //keep track of gallery intent
    final int PICK_IMAGE_REQUEST = 2;
    protected RecyclerView mRecyclerView;
    boolean mTwoPane;
    HabitantAdapter mAdapter;
    FrameLayout fab;
    FloatingActionButton myfab_main_btn;
    Animation animation;
    ImageView photo;
    DatePicker datePicker;
    Bitmap thePic = null;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private android.support.v7.view.ActionMode actionMode;
    private android.content.Context context = this;
    private TextView tvEmptyView;
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
        super.setContentView(R.layout.activity_list_user);


        setTitle(context.getString(R.string.Enregistrement));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initView();
        fab.startAnimation(animation);
        mAdapter = new HabitantAdapter(this, Habitant.findAll(), this);
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

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }
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
        if (Habitant.findAll().isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            tvEmptyView.setVisibility(View.VISIBLE);

        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            tvEmptyView.setVisibility(View.GONE);
        }

    }

    public void call(List<String> contact) {
        ArrayAdapter<String> stringArrayAdapter;
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.call);
        final ListView listView = (ListView) dialog.findViewById(R.id.call);
        stringArrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, contact);
        listView.setAdapter(stringArrayAdapter);
        dialog.show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + listView.getItemAtPosition(i).toString()));//change the number
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }
        });


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
        dialog.setContentView(R.layout.add_enregistrement);
        // Initialisation du formulaire

        final TextView operation = (TextView) dialog.findViewById(R.id.operation);
        final EditText Nom = (EditText) dialog.findViewById(R.id.Nom);
        final EditText Prenom = (EditText) dialog.findViewById(R.id.Prenom);
        final EditText NumeroCni = (EditText) dialog.findViewById(R.id.NumeroCni);
        final MaterialBetterSpinner Genre = (MaterialBetterSpinner) dialog.findViewById(R.id.Genre);
        final MaterialBetterSpinner Titre = (MaterialBetterSpinner) dialog.findViewById(R.id.Titre);
        final EditText Tel1 = (EditText) dialog.findViewById(R.id.Tel1);
        photo = (ImageView) dialog.findViewById(R.id.imghabitant);

        List<String> titres = new ArrayList<>();
        titres.add("Monsieur");
        titres.add("Madame");
        titres.add("Docteur");
        titres.add("Professeur");
        List<String> genres = new ArrayList<>();
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.spinner_item, titres);

        Titre.setAdapter(adapter);

        genres.add("Homme");
        genres.add("Femme");
        ArrayAdapter<String> adapter1 =
                new ArrayAdapter<String>(this, R.layout.spinner_item, genres);

        Genre.setAdapter(adapter1);

        final Button camera = (Button) dialog.findViewById(R.id.capturer);
        final Button gallery = (Button) dialog.findViewById(R.id.choisirImg);
        final Button valider = (Button) dialog.findViewById(R.id.valider);
        final Button annuler = (Button) dialog.findViewById(R.id.annuler);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraOption(0);
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraOption(1);
            }
        });
        // Click cancel to dismiss android custom dialog box
        dialog.show();

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Nom.getText().toString().trim().isEmpty()) {
                    Nom.setError("Veillez saisir votre nom");
                    return;
                }


                if (Genre.getText().toString().trim().isEmpty()) {
                    Genre.setError("Veillez saisir votre numero de le genre");
                    return;
                }
                if (Titre.getText().toString().trim().isEmpty()) {
                    Titre.setError("Veillez saisir votre numero de le titre");
                    return;
                }
                if (Tel1.getText().toString().trim().isEmpty()) {
                    Tel1.setError("Veillez saisir votre numero de le telephone");
                    return;
                }
                DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Habitant bailleur = new Habitant();
                bailleur.setNom(Nom.getText().toString().trim());
                bailleur.setPrenom(Prenom.getText().toString().trim());
                bailleur.setGenre(Genre.getText().toString());
                bailleur.setNumeroCNI(NumeroCni.getText().toString());
                bailleur.setTitre(Titre.getText().toString());
                bailleur.setTel1(Tel1.getText().toString().trim());

                try {
                    bailleur.setPhoto(saveToInternalStorage(thePic, Nom.getText().toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bailleur.save();
                Snackbar.make(view, "le type de penalité a été correctement crée", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mAdapter.addItem(0, bailleur);
                if (new Caracteristique().findAll().isEmpty()) {
                    mRecyclerView.setVisibility(View.GONE);
                    tvEmptyView.setVisibility(View.VISIBLE);

                } else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    tvEmptyView.setVisibility(View.GONE);
                }
                dialog.dismiss();
            }
        });

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
        // Delete.tables(Habitant.class);
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


    public void start(int id) {
        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putInt("id", id);
            AffHabitantFragment fragment = new AffHabitantFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();

        } else {
            Intent intent = new Intent(context, Aff_HabitantActivity.class);
            intent.putExtra("id", id);

            startActivity(intent);
        }
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

                            Habitant cite = new Habitant();
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


    }

    @Override
    public void modifier(final int id) {

        final Habitant habitant = SQLite.select().from(Habitant.class).where(Habitant_Table.id.eq(id)).querySingle();
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_enregistrement);
        // Initialisation du formulaire


        final TextView operation = (TextView) dialog.findViewById(R.id.operation);
        final EditText Nom = (EditText) dialog.findViewById(R.id.Nom);
        final EditText Prenom = (EditText) dialog.findViewById(R.id.Prenom);
        final EditText NumeroCni = (EditText) dialog.findViewById(R.id.NumeroCni);
        final MaterialBetterSpinner Genre = (MaterialBetterSpinner) dialog.findViewById(R.id.Genre);
        final MaterialBetterSpinner Titre = (MaterialBetterSpinner) dialog.findViewById(R.id.Titre);
        final EditText Tel1 = (EditText) dialog.findViewById(R.id.Tel1);
        photo = (ImageView) dialog.findViewById(R.id.imghabitant);
        operation.setText("Modifier un habitant");
        Nom.setText(habitant.getNom());
        Prenom.setText(habitant.getPrenom());
        NumeroCni.setText(habitant.getNumeroCNI());
        Genre.setText(habitant.getGenre());
        Titre.setText(habitant.getTitre());
        Tel1.setText(habitant.getTel1());
        if (habitant.getPhoto() != null) {

            photo.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.patch + "" + habitant.getPhoto()));
        }


        List<String> titres = new ArrayList<>();
        titres.add("Monsieur");
        titres.add("Madame");
        titres.add("Docteur");
        titres.add("Professeur");
        List<String> genres = new ArrayList<>();
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.spinner_item, titres);

        Titre.setAdapter(adapter);

        genres.add("Homme");
        genres.add("Femme");
        ArrayAdapter<String> adapter1 =
                new ArrayAdapter<String>(this, R.layout.spinner_item, genres);

        Genre.setAdapter(adapter1);

        final Button camera = (Button) dialog.findViewById(R.id.capturer);
        final Button gallery = (Button) dialog.findViewById(R.id.choisirImg);
        final Button valider = (Button) dialog.findViewById(R.id.valider);
        final Button annuler = (Button) dialog.findViewById(R.id.annuler);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraOption(0);
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraOption(1);
            }
        });
        // Click cancel to dismiss android custom dialog box
        dialog.show();

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Nom.getText().toString().trim().isEmpty()) {
                    Nom.setError("Veillez saisir votre nom");
                    return;
                }


                if (Genre.getText().toString().trim().isEmpty()) {
                    Genre.setError("Veillez saisir votre numero de le genre");
                    return;
                }
                if (Titre.getText().toString().trim().isEmpty()) {
                    Titre.setError("Veillez saisir votre numero de le titre");
                    return;
                }
                if (Tel1.getText().toString().trim().isEmpty()) {
                    Tel1.setError("Veillez saisir votre numero de le telephone");
                    return;
                }
                DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Habitant habitant1 = new Habitant();
                habitant1.setId(id);
                habitant1.setNom(Nom.getText().toString().trim());
                habitant1.setPrenom(Prenom.getText().toString().trim());
                habitant1.setGenre(Genre.getText().toString());
                habitant1.setNumeroCNI(NumeroCni.getText().toString());
                habitant1.setTitre(Titre.getText().toString());
                habitant1.setTel1(Tel1.getText().toString().trim());

                try {
                    habitant1.setPhoto(saveToInternalStorage(thePic, Nom.getText().toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                habitant1.save();
                Snackbar.make(v, "l'habitant a été modifié", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mAdapter.actualiser(Habitant.findAll());

                dialog.dismiss();
            }
        });

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
        final List<Habitant> filteredModelList = filter(Habitant.findAll(), query);
        mAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<Habitant> filter(List<Habitant> models, String query) {
        query = query.toLowerCase();
        System.out.println(models);
        final List<Habitant> filteredModelList = new ArrayList<>();
        for (Habitant model : models) {
            final String text = model.getNom().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    public void cameraOption(int i) {
        switch (i) {
            case 0:
                try {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/maisonier/picture.jpg";
                    File imageFile = new File(imageFilePath);
                    picUri = Uri.fromFile(imageFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                    startActivityForResult(takePictureIntent, CAMERA_CAPTURE);

                } catch (ActivityNotFoundException anfe) {
                    //display an error message
                    String errorMessage = "Image non supportée";
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
                }

                break;

            case 1:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
                break;

            default:
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            //user is returning from capturing an image using the camera
            if (requestCode == CAMERA_CAPTURE) {
                //get the Uri for the captured image
                Uri uri = picUri;
                //carry out the crop operation
                performCrop();
                Log.d("picUri", uri.toString());

            } else if (requestCode == PICK_IMAGE_REQUEST) {
                picUri = data.getData();
                Log.d("uriGallery", picUri.toString());
                performCrop();
            }

            //user is returning from cropping the image
            else if (requestCode == PIC_CROP) {
                //get the returned data
                Bundle extras = data.getExtras();
                //get the cropped bitmap
                thePic = (Bitmap) extras.get("data");
                //display the returned cropped image
                photo.setImageBitmap(thePic);

            }

        }
    }

    private void performCrop() {
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        } catch (ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "erreur";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
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
