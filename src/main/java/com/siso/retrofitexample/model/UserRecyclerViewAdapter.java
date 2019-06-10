package com.siso.retrofitexample.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.siso.retrofitexample.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.UserRecyclerViewHolder> implements Filterable {
    private List<User> userList;
    private List<User> filterList;
    private OnItemClickListener listener;
    public void setUserList(List<User> userList) {
        this.userList = userList;
        this.filterList = new ArrayList<>(userList) ;
        notifyDataSetChanged();
    }

    public UserRecyclerViewAdapter(final  ArrayList<User> userList1) {
        this.userList = userList1;
        filterList = new ArrayList<>(this.userList);

        filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<User> filteredList = new ArrayList<>();


                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(userList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (User item : userList) {
                        if (item.getFirst_name().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterList.clear();
                filterList.addAll((ArrayList) results.values);
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public UserRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_user, viewGroup, false);
        UserRecyclerViewHolder evh = new UserRecyclerViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserRecyclerViewHolder holder, int i) {
        User  user = filterList.get(i);
        holder.getFirstNameView().setText(user.getFirst_name());
        holder.getLastNameView().setText(user.getLast_name());
        holder.getEmailView().setText(user.getEmail());
       // Context context = holder.getUserImageView().getContext();
        Picasso.get()
                .load(user.getAvatar())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.getUserImageView());


    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public  class UserRecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView userImageView;
        private TextView firstNameView;
        private TextView lastNameView;
        private TextView emailView;

        public UserRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            userImageView = itemView.findViewById(R.id.userImage);
            firstNameView = itemView.findViewById(R.id.firstNameUserText);
            lastNameView = itemView.findViewById(R.id.lastNameUserText);
            emailView = itemView.findViewById(R.id.emailUsetText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(filterList.get(position));
                    }
                }
            });
        }

        public ImageView getUserImageView() {
            return userImageView;
        }

        public TextView getFirstNameView() {
            return firstNameView;
        }

        public TextView getLastNameView() {
            return lastNameView;
        }

        public TextView getEmailView() {
            return emailView;
        }
    }

    private Filter filter;


    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }



}
