package com.mahya.maisonier.adapter.model;

import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import com.mahya.maisonier.activities.CiteActivity;
import com.mahya.maisonier.entites.Depense;
import com.mahya.maisonier.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DepenseAdapter extends RecyclerSwipeAdapter<DepenseAdapter.SimpleViewHolder> {


    private static final String TAG = DepenseAdapter.class.getSimpleName();
    Context mContext;
    int idSelect;
    int selectposition;
    private List<Depense> depots;
    private SparseBooleanArray selectedItems;
    private OnItemClickListener clickListener;

    public DepenseAdapter(Context context, List<Depense> depots, OnItemClickListener clickListener) {
        this.mContext = context;
        this.depots = depots;
        this.clickListener = clickListener;
        selectedItems = new SparseBooleanArray();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_compose, parent, false);

        return new SimpleViewHolder(view, clickListener);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {

        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            viewHolder.montant.setText(String.valueOf(depots.get(position).getMontant()));
            //viewHolder.logement.setText(depots.get(position).get().load().getLogement().load().getDescription());
            //viewHolder.contratBailleur.setText(sdf.format(depots.get(position).getOccupation().load().getHabitant().load().getNom()));
            viewHolder.id.setText(String.valueOf(depots.get(position).getId()));
        } catch (Exception e) {

            return;
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
            public void onClick(View v) {

                if (mContext instanceof CiteActivity) {
                    ((CiteActivity) mContext).onItemClicked(position);
                    TextView id = (TextView) v.findViewById(R.id.idItem);
                    idSelect = Integer.parseInt(id.getText().toString());
                    ((CiteActivity) mContext).detail(idSelect);
                }
            }
        });

        viewHolder.swipeLayout.getSurfaceView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                ((CiteActivity) mContext).onItemLongClicked(position);

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

                TextView id = (TextView) view.findViewById(R.id.idItem);
                idSelect = Integer.parseInt(id.getText().toString());
                ((CiteActivity) mContext).detail(idSelect);
                mItemManger.closeAllExcept(null);
            }
        });


        viewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CiteActivity) mContext).modifier(idSelect);
                mItemManger.closeAllExcept(null);


            }
        });


        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CiteActivity) mContext).supprimer(idSelect);
                mItemManger.closeAllExcept(null);

            }
        });


        mItemManger.bindView(viewHolder.itemView, position);


    }

    public void animateTo(List<Depense> typeLogements) {
        applyAndAnimateRemovals(typeLogements);
        applyAndAnimateAdditions(typeLogements);
        applyAndAnimateMovedItems(typeLogements);
    }

    private void applyAndAnimateRemovals(List<Depense> typeLogements) {
        for (int i = this.depots.size() - 1; i >= 0; i--) {
            final Depense model = this.depots.get(i);
            if (!typeLogements.contains(model)) {
                deleteItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Depense> typeLogements) {
        for (int i = 0, count = typeLogements.size(); i < count; i++) {
            final Depense typeLogement = typeLogements.get(i);
            if (!this.depots.contains(typeLogement)) {
                addItem(i, typeLogement);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Depense> typeLogements) {
        for (int toPosition = typeLogements.size() - 1; toPosition >= 0; toPosition--) {
            final Depense model = typeLogements.get(toPosition);
            final int fromPosition = this.depots.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }


    public void addItem(int position, Depense model) {
        depots.add(position, model);
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
            depots.remove(positionStart);
        }
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    @Override
    public int getItemViewType(int position) {
        final Depense item = depots.get(position);

        return 0;
    }


    public void add(List<Depense> items) {
        int previousDataSize = this.depots.size();
        this.depots.addAll(items);
        notifyItemRangeInserted(previousDataSize, items.size());
    }

    public Integer getId() {
        return idSelect;
    }

    public int getSelectposition() {
        return selectposition;
    }

    public Depense getItem(int idx) {
        return depots.get(idx);
    }


    public void deleteItem(int index) {
        depots.remove(index);
        notifyItemRemoved(index);
    }

    public void actualiser(List<Depense> cites) {
        this.depots.clear();
        this.depots.addAll(cites);
        notifyDataSetChanged();
    }


    public void moveItem(int fromPosition, int toPosition) {
        final Depense model = depots.remove(fromPosition);
        depots.add(toPosition, model);
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
        return depots.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }


    //  ViewHolder Class

    public static class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {
        private static final String TAG = RecyclerView.ViewHolder.class.getSimpleName();
        SwipeLayout swipeLayout;
        ImageButton tvDelete;
        ImageButton tvEdit;
        TextView montant;
        TextView logement;
        TextView id;
        ImageButton detail;
        TextView habitant;
        View selectedOverlay;
        private OnItemClickListener listener;

        public SimpleViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            montant = (TextView) itemView.findViewById(R.id.titre);
            habitant = (TextView) itemView.findViewById(R.id.libelle);
            logement = (TextView) itemView.findViewById(R.id.desc);
            id = (TextView) itemView.findViewById(R.id.idItem);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            tvDelete = (ImageButton) itemView.findViewById(R.id.tvDelete);
            tvEdit = (ImageButton) itemView.findViewById(R.id.tvEdit);
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
