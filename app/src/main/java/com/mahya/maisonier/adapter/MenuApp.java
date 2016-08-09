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
import com.mahya.maisonier.activities.ArticleActivity;
import com.mahya.maisonier.activities.BailleurActivity;
import com.mahya.maisonier.activities.BatimentActivity;
import com.mahya.maisonier.activities.CaracteristiqueActivity;
import com.mahya.maisonier.activities.CiteActivity;
import com.mahya.maisonier.activities.LogementActivity;
import com.mahya.maisonier.activities.TypePenaliteActivity;
import com.mahya.maisonier.activities.TypedeChargeActivity;
import com.mahya.maisonier.activities.TypelogementActivity;
import com.mahya.maisonier.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MenuApp extends ExpandableRecyclerAdapter<MenuApp.ItemMenu> implements OnItemClickListener {
    public static final int sub_menu = 1001;
    Context context;
    Intent[] intents;

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
                        TextView name = (TextView) view.findViewById(R.id.item_name);
                        if (name.getText().toString().trim().equals(context.getString(R.string.typelogement))) {

                            context.startActivity(new Intent(context, TypelogementActivity.class));

                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Caracteristiques))) {
                            context.startActivity(new Intent(context, CaracteristiqueActivity.class));

                        } else if (name.getText().toString().trim().equals(context.getString(R.string.typedecharge))) {
                            context.startActivity(new Intent(context, TypedeChargeActivity.class));

                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Cite))) {
                            context.startActivity(new Intent(context, CiteActivity.class));

                        } else if (name.getText().toString().trim().equals(context.getString(R.string.batiment))) {
                            context.startActivity(new Intent(context, BatimentActivity.class));

                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Logements))) {
                            context.startActivity(new Intent(context, LogementActivity.class));

                        } else if (name.getText().toString().trim().equals(context.getString(R.string.typepenalite).trim())) {
                            context.startActivity(new Intent(context, TypePenaliteActivity.class));


                        } else if (name.getText().toString().trim().equals(context.getString(R.string.article).trim())) {
                            context.startActivity(new Intent(context, ArticleActivity.class));


                        } else if (name.getText().toString().trim().equals(context.getString(R.string.Bailleur).trim())) {
                            context.startActivity(new Intent(context, BailleurActivity.class));


                        }


                    }
                });

                break;
        }


    }


    List<ItemMenu> getSampleItems() {
        List<ItemMenu> items = new ArrayList<>();

        items.add(new ItemMenu(null, context.getText(R.string.accueilmaisonier).toString()));
        items.add(new ItemMenu(null, context.getText(R.string.gererlogement).toString()));
        items.add(new ItemMenu(null, context.getText(R.string.typelogement).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.batiment).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Logements).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Caracteristiques).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.disponibles).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Cite).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.GererdesLoyers).toString()));
        items.add(new ItemMenu(null, context.getText(R.string.Occupations).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Loyers).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Caution).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Correspondance).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Depenses).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Gérerleshabitants).toString()));
        items.add(new ItemMenu(null, context.getText(R.string.Enregistrement).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Listedeshablitants).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Dossierdunhabitant).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Etatdesoccupations).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Gérerlescontrats).toString()));
        items.add(new ItemMenu(null, context.getText(R.string.Bailleur).toString(), " "));
        items.add(new ItemMenu(null, context.getString(R.string.article), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Contratdebail).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Rubriquedecontrat).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Rubrique).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.ChargerRibriqueduncontrat).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Gérerlescomptes).toString()));
        items.add(new ItemMenu(null, context.getText(R.string.Typedecompte).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Compte).toString() , " "));
        items.add(new ItemMenu(null, context.getText(R.string.Dépot).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.ChargerDépot).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Transfertintercompte).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Gérerlespénalités).toString()));
        items.add(new ItemMenu(null, context.getString(R.string.typepenalite).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Pénalités).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Charges).toString(), " "));
        items.add(new ItemMenu(null, context.getString(R.string.typedecharge).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Gestiondesindex).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Consommationeneau).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Consommationenélectricité).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Indexeneau).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Indexenéléctricité).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Gérerlecable).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Etats).toString()));
        items.add(new ItemMenu(null, context.getText(R.string.Depenses).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Historiquedeparametrage).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Mois).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Année).toString(), " "));
        items.add(new ItemMenu(null, context.getText(R.string.Parametres).toString(), " "));

        return items;
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

    public static class ItemMenu extends ExpandableRecyclerAdapter.ListItem {
        public String Text;
        ImageView icon;

        public ItemMenu(ImageView icon, String group) {
            super(menu);
            this.icon = icon;
            Text = group;
        }

        public ItemMenu(ImageView icon, String first, String last) {
            super(sub_menu);

            this.icon = icon;
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
