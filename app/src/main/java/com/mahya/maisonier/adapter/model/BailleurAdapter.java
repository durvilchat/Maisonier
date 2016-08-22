package com.mahya.maisonier.adapter.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.mahya.maisonier.R;
import com.mahya.maisonier.activities.BailleurActivity;
import com.mahya.maisonier.activities.detail.Aff_BailleurActivity;
import com.mahya.maisonier.entites.Bailleur;
import com.mahya.maisonier.entites.Bailleur_Table;
import com.mahya.maisonier.interfaces.OnItemClickListener;
import com.mahya.maisonier.utils.Constants;
import com.mahya.maisonier.utils.ImageShow;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BailleurAdapter extends RecyclerSwipeAdapter<BailleurAdapter.SimpleViewHolder> {


    private static final String TAG = BailleurAdapter.class.getSimpleName();
    public static int idSelect;
    Context mContext;
    int selectposition;
    private List<Bailleur> bailleurs;
    private View vue;
    private SparseBooleanArray selectedItems;
    private OnItemClickListener clickListener;


    public BailleurAdapter(Context context, List<Bailleur> bailleurs, OnItemClickListener clickListener) {
        this.mContext = context;
        this.bailleurs = bailleurs;
        this.clickListener = clickListener;
        selectedItems = new SparseBooleanArray();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        vue = view;
        return new SimpleViewHolder(view, clickListener);
    }


    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {

        try {

            if (bailleurs.get(position).getPhoto() != null) {

                viewHolder.img.setImageURI(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.patch + "" + bailleurs.get(position).getPhoto()));
            } else {
                viewHolder.img.setImageResource(R.drawable.avatar);

            }
            viewHolder.nom.setText(bailleurs.get(position).getNom() + " " + bailleurs.get(position).getPrenom());
            viewHolder.prenom.setText(bailleurs.get(position).getTitre());
            viewHolder.tel.setText(bailleurs.get(position).getTel1());
            viewHolder.id.setText(String.valueOf(bailleurs.get(position).getId()));

            viewHolder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mContext instanceof BailleurActivity) {
                        ((BailleurActivity) mContext).onItemClicked(position);
                        Intent intent = new Intent(mContext, ImageShow.class);
                        intent.putExtra("image", bailleurs.get(position).getPhoto());
                        if (bailleurs.get(position).getPhoto() == null)
                            return;
                        mContext.startActivity(intent);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
// Span the item if active
        final ViewGroup.LayoutParams lp = viewHolder.itemView.getLayoutParams();
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams sglp = (StaggeredGridLayoutManager.LayoutParams) lp;

            viewHolder.itemView.setLayoutParams(sglp);
        }

        // Highlight the item if it's selected
        viewHolder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);

        // Drag From Left
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        // Drag From Right
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));
        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView id = (TextView) view.findViewById(R.id.idItem);
                idSelect = Integer.parseInt(id.getText().toString());
                if (mContext instanceof BailleurActivity) {
                    Intent intent = new Intent(mContext, Aff_BailleurActivity.class);
                    intent.putExtra("id", idSelect);
                    mContext.startActivity(intent);
                }
            }
        });

        viewHolder.swipeLayout.getSurfaceView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                ((BailleurActivity) mContext).onItemLongClicked(position);
                TextView id = (TextView) view.findViewById(R.id.idItem);
                idSelect = Integer.parseInt(id.getText().toString());
                return true;
            }


        });

        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

                mItemManger.closeAllExcept(layout);

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                selectposition = position;
                TextView id = (TextView) layout.findViewById(R.id.idItem);
                idSelect = Integer.parseInt(id.getText().toString());
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });


        viewHolder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((BailleurActivity) mContext).detail(idSelect);
                mItemManger.closeAllExcept(null);
            }
        });


        viewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BailleurActivity) mContext).modifier(idSelect);
                mItemManger.closeAllExcept(null);


            }
        });
        viewHolder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> contqct = new ArrayList<String>();
                Bailleur bailleur = SQLite.select().from(Bailleur.class).where(Bailleur_Table.id.eq(idSelect)).querySingle();
                if (!bailleur.getTel1().isEmpty()) {
                    contqct.add(bailleur.getTel1());
                } else if (!bailleur.getTel2().isEmpty()) {
                    contqct.add(bailleur.getTel2());
                } else if (!bailleur.getTel3().isEmpty()) {
                    contqct.add(bailleur.getTel3());
                } else if (!bailleur.getTel4().isEmpty()) {
                    contqct.add(bailleur.getTel4());
                }

                ((BailleurActivity) mContext).call(contqct);
                mItemManger.closeAllExcept(null);
            }
        });


        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BailleurActivity) mContext).supprimer(idSelect);
                mItemManger.closeAllExcept(null);

            }
        });


        mItemManger.bindView(viewHolder.itemView, position);


    }

    public void animateTo(List<Bailleur> typeLogements) {
        applyAndAnimateRemovals(typeLogements);
        applyAndAnimateAdditions(typeLogements);
        applyAndAnimateMovedItems(typeLogements);
    }

    private void applyAndAnimateRemovals(List<Bailleur> typeLogements) {
        for (int i = this.bailleurs.size() - 1; i >= 0; i--) {
            final Bailleur model = this.bailleurs.get(i);
            if (!typeLogements.contains(model)) {
                deleteItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Bailleur> typeLogements) {
        for (int i = 0, count = typeLogements.size(); i < count; i++) {
            final Bailleur typeLogement = typeLogements.get(i);
            if (!this.bailleurs.contains(typeLogement)) {
                addItem(i, typeLogement);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Bailleur> typeLogements) {
        for (int toPosition = typeLogements.size() - 1; toPosition >= 0; toPosition--) {
            final Bailleur model = typeLogements.get(toPosition);
            final int fromPosition = this.bailleurs.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }


    public void addItem(int position, Bailleur model) {
        bailleurs.add(position, model);
        notifyItemInserted(position);
    }


    public void removeItems(List<Integer> positions) {
        // Reverse-sort the list
        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });

        // Split the list in ranges
        while (!positions.isEmpty()) {
            if (positions.size() == 1) {
                deleteItem(positions.get(0));
                positions.remove(0);
            } else {
                int count = 1;
                while (positions.size() > count && positions.get(count).equals(positions.get(count - 1) - 1)) {
                    ++count;
                }

                if (count == 1) {
                    deleteItem(positions.get(0));
                } else {
                    removeRange(positions.get(count - 1), count);
                }

                for (int i = 0; i < count; ++i) {
                    positions.remove(0);
                }
            }
        }
    }

    private void removeRange(int positionStart, int itemCount) {
        for (int i = 0; i < itemCount; ++i) {
            bailleurs.remove(positionStart);
        }
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    @Override
    public int getItemViewType(int position) {
        final Bailleur item = bailleurs.get(position);

        return 0;
    }


    public void add(List<Bailleur> items) {
        int previousDataSize = this.bailleurs.size();
        this.bailleurs.addAll(items);
        notifyItemRangeInserted(previousDataSize, items.size());
    }

    public Integer getId() {
        return idSelect;
    }

    public int getSelectposition() {
        return selectposition;
    }

    public Bailleur getItem(int idx) {
        return bailleurs.get(idx);
    }


    public void deleteItem(int index) {
        bailleurs.remove(index);
        notifyItemRemoved(index);
    }

    public void actualiser(List<Bailleur> cites) {
        this.bailleurs.clear();
        this.bailleurs.addAll(cites);
        notifyDataSetChanged();
    }


    public void moveItem(int fromPosition, int toPosition) {
        final Bailleur model = bailleurs.remove(fromPosition);
        bailleurs.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }


    /**
     * Indicates if the item at position position is selected
     *
     * @param position Position of the item to check
     * @return true if the item is selected, false otherwise
     */
    public boolean isSelected(int position) {
        return getSelectedItems().contains(position);
    }

    /**
     * Toggle the selection status of the item at a given position
     *
     * @param position Position of the item to toggle the selection status for
     */
    public void toggleSelection(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }


    /**
     * Clear the selection status for all items
     */
    public void clearSelection() {
        List<Integer> selection = getSelectedItems();
        selectedItems.clear();
        for (Integer i : selection) {
            notifyItemChanged(i);
        }
    }

    /**
     * Count the selected items
     *
     * @return Selected items count
     */
    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    /**
     * Indicates the list of selected items
     *
     * @return List of selected items ids
     */
    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); ++i) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }


    @Override
    public int getItemCount() {
        return bailleurs.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


    //  ViewHolder Class

    public static class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {
        private static final String TAG = RecyclerView.ViewHolder.class.getSimpleName();
        public ImageButton call;
        SwipeLayout swipeLayout;
        ImageButton tvDelete;
        ImageButton tvEdit;
        TextView prenom;
        TextView tel;
        TextView id;
        CircleImageView img;
        ImageButton detail;
        TextView nom;
        View selectedOverlay;
        private OnItemClickListener listener;

        public SimpleViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            tel = (TextView) itemView.findViewById(R.id.tel);
            nom = (TextView) itemView.findViewById(R.id.nom);
            prenom = (TextView) itemView.findViewById(R.id.titre);
            id = (TextView) itemView.findViewById(R.id.idItem);
            img = (CircleImageView) itemView.findViewById(R.id.useimg);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            tvDelete = (ImageButton) itemView.findViewById(R.id.tvDelete);
            tvEdit = (ImageButton) itemView.findViewById(R.id.tvEdit);
            call = (ImageButton) itemView.findViewById(R.id.call);
            detail = (ImageButton) itemView.findViewById(R.id.detail);
            selectedOverlay = itemView.findViewById(R.id.selected_overlay);
            this.listener = listener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemView.setLongClickable(true);

        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClicked(getPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (listener != null) {
                return listener.onItemLongClicked(getPosition());
            }

            return false;
        }


    }
}
