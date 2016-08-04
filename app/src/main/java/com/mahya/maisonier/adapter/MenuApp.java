package com.mahya.maisonier.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mahya.maisonier.R;
import com.mahya.maisonier.activities.MainActivity;
import com.mahya.maisonier.activities.TypelogementActivity;
import com.mahya.maisonier.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MenuApp extends ExpandableRecyclerAdapter<MenuApp.ItemMenu> implements OnItemClickListener {
    public static final int sub_menu = 1001;
    Context context;

    public MenuApp(Context context) {
        super(context);
        this.context = context;
        setItems(getSampleItems());

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case menu:
                return new MenuHolder(inflate(R.layout.item_menu, parent));
            case sub_menu:
            default:
                return new SubmenuHolder(inflate(R.layout.item_submenu, parent), this);
        }
    }

    @Override
    public void onBindViewHolder(final ExpandableRecyclerAdapter.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case menu:
                ((MenuHolder) holder).bind(position);
                if (((MenuHolder) holder).name.getText().equals(context.getString(R.string.accueilmaisonier))) {
                    ((MenuHolder) holder).arrow.setVisibility(View.GONE);
                    ((MenuHolder) holder).tittre.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(context, "koool" + position, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    ((MenuHolder) holder).arrow.setVisibility(View.VISIBLE);
                }


                break;
            case sub_menu:
            default:
                ((SubmenuHolder) holder).bind(position);

                ((SubmenuHolder) holder).sous_titre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            switch(((SubmenuHolder) holder).name.getText().toString()) {
                            case  "Type de logement":
                                Intent i =new Intent(context, TypelogementActivity.class);
                                 context.startActivity(i);
                                break;
                            case "":

                                break;
                            default:

                        }


                    }
                });

                break;
        }


    }


    List<ItemMenu> getSampleItems() {
        List<ItemMenu> items = new ArrayList<>();

        items.add(new ItemMenu(context.getText(R.string.accueilmaisonier).toString()));
        items.add(new ItemMenu(context.getText(R.string.gererlogement).toString()));
        items.add(new ItemMenu(context.getText(R.string.typelogement).toString(), " "));
        items.add(new ItemMenu("Batiments", " "));
        items.add(new ItemMenu("Logements", " "));
        items.add(new ItemMenu("Caracteristiques", " "));
        items.add(new ItemMenu("Logements disponibles", " "));
        items.add(new ItemMenu("Cite", " "));
        items.add(new ItemMenu("Gérer des Loyers"));
        items.add(new ItemMenu("Occupations", " "));
        items.add(new ItemMenu("Loyers", " "));
        items.add(new ItemMenu("Caution", " "));
        items.add(new ItemMenu("Correspondances", " "));
        items.add(new ItemMenu("Depenses", " "));
        items.add(new ItemMenu("Gérer les habitants"));
        items.add(new ItemMenu("Enregistrement", " "));
        items.add(new ItemMenu("Liste des hablitants", " "));
        items.add(new ItemMenu("Dossier d'un habitant", " "));
        items.add(new ItemMenu("Etat des occupations", " "));
        items.add(new ItemMenu("Gérer les contrats"));
        items.add(new ItemMenu("Bailleur", " "));
        items.add(new ItemMenu("Articles", " "));
        items.add(new ItemMenu("Contrat de bail", " "));
        items.add(new ItemMenu("Rubrique de contrat", " "));
        items.add(new ItemMenu("Rubrique", " "));
        items.add(new ItemMenu("Charger Ribrique d'un contrat", " "));
        items.add(new ItemMenu("Gérer les comptes"));
        items.add(new ItemMenu("Type de compte", " "));
        items.add(new ItemMenu("Compte", " "));
        items.add(new ItemMenu("Dépot", " "));
        items.add(new ItemMenu("Charger Dépot", " "));
        items.add(new ItemMenu("Transfert inter-compte", " "));
        items.add(new ItemMenu("Gérer les pénalités"));
        items.add(new ItemMenu("Types de pénalité", " "));
        items.add(new ItemMenu("Pénalités", " "));
        items.add(new ItemMenu("Charges", " "));
        items.add(new ItemMenu("Types de charges", " "));
        items.add(new ItemMenu("Gestion des index"));
        items.add(new ItemMenu("Consommation en eau", " "));
        items.add(new ItemMenu("Consommation en électricité", " "));
        items.add(new ItemMenu("Index en eau", " "));
        items.add(new ItemMenu("Index en éléctricité", " "));
        items.add(new ItemMenu("Gérer le cable", " "));
        items.add(new ItemMenu("Etats"));
        items.add(new ItemMenu("Depenses", " "));
        items.add(new ItemMenu("Depenses", " "));
        items.add(new ItemMenu("Historique de parametrage", " "));
        items.add(new ItemMenu("Mois", " "));
        items.add(new ItemMenu("Année", " "));
        items.add(new ItemMenu("Parametres"));

        return items;
    }

    @Override
    public void onItemClicked(View view, int position) {
        Toast.makeText(context, " okkkk", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onLongClick(View view, int position) {

    }

    public static class ItemMenu extends ExpandableRecyclerAdapter.ListItem {
        public String Text;

        public ItemMenu(String group) {
            super(menu);

            Text = group;
        }

        public ItemMenu(String first, String last) {
            super(sub_menu);

            Text = first + " " + last;
        }
    }

    public class MenuHolder extends ExpandableRecyclerAdapter.HeaderViewHolder {
        TextView name;
        LinearLayout tittre;

        public MenuHolder(View view) {
            super(view, (ImageView) view.findViewById(R.id.item_arrow));
            tittre = (LinearLayout) view.findViewById(R.id.titremenu);
            name = (TextView) view.findViewById(R.id.item_header_name);

        }


        public void bind(int position) {
            super.bind(position);
            name.setText(visibleItems.get(position).Text);
        }
    }

    public class SubmenuHolder extends ExpandableRecyclerAdapter.ViewHolder {
        TextView name;
        LinearLayout sous_titre;

        public SubmenuHolder(View view, final OnItemClickListener onItemClickListener) {
            super(view);

            name = (TextView) view.findViewById(R.id.item_name);
            sous_titre = (LinearLayout) view.findViewById(R.id.sous_titre);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onLongClick(view, getAdapterPosition());
                    }
                    Toast.makeText(context, "koool", Toast.LENGTH_SHORT).show();
                }

            });

        }

        public void bind(int position) {
            name.setText(visibleItems.get(position).Text);
        }
    }
}
