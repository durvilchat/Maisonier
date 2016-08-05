package com.mahya.maisonier.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mahya.maisonier.Profil_User;
import com.mahya.maisonier.R;
import com.mahya.maisonier.adapter.ExpandableRecyclerAdapter;
import com.mahya.maisonier.adapter.MenuApp;
import com.mahya.maisonier.entites.Bailleur;
import com.mahya.maisonier.interfaces.OnItemClickListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    protected TextView itemFooterName;
    RecyclerView recycler;
    MenuApp adapter;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recycler = (RecyclerView) findViewById(R.id.main_recycler);
        adapter = new MenuApp(this);
        adapter.setMode(ExpandableRecyclerAdapter.MODE_ACCORDION);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        recycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView text = (TextView) view.findViewById(R.id.item_name);
                Toast.makeText(context, " tres " + text.getText(), Toast.LENGTH_SHORT).show();
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initView();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public void bien(View view) {

        Bailleur habitant = new Bailleur();
        habitant.setNom("franck");
        habitant.setGenre("Masculin");
        habitant.setTel1("698939289");
        habitant.setTitre("Bien");
        habitant.save();

    }

    /**
     * deconnexion de l'utilisateur
     *
     * @param
     * @return
     */
    public void dcnx(View view) {
        Snackbar.make(view, "deconnexion a été validé", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_user) {

            startActivity(new Intent(this, Profil_User.class));

        } else if (id == R.id.action_option) {


        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initView() {
        itemFooterName = (TextView) findViewById(R.id.item_footer_name);
    }


    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public boolean onItemLongClicked(int position) {
        return false;
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
