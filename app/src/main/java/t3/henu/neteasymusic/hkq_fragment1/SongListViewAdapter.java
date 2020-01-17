package t3.henu.neteasymusic.hkq_fragment1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

import t3.henu.neteasymusic.R;

/**
 * Created by Android on 2017/5/28.
 */

public class SongListViewAdapter extends RecyclerView.Adapter<SongListViewAdapter.ViewHolder> {

    private List<SelectItems> mSelectItems;

    public SongListViewAdapter(List<SelectItems> selectItems) {
        mSelectItems = selectItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_type, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.selectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                SelectItems si = mSelectItems.get(position);
                Toast.makeText(view.getContext(), "你点击了:" + si.getItemTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                SelectItems si = mSelectItems.get(position);
                Toast.makeText(view.getContext(), "你点击了" + si.getText1(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                SelectItems si = mSelectItems.get(position);
                Toast.makeText(view.getContext(), "你点击了" + si.getText2(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                SelectItems si = mSelectItems.get(position);
                Toast.makeText(view.getContext(), "你点击了" + si.getText3(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SelectItems selectItems = mSelectItems.get(position);
        holder.tv_name.setText(selectItems.getItemTitle());
        holder.tv1.setText(selectItems.getText1());
        holder.tv2.setText(selectItems.getText2());
        holder.tv3.setText(selectItems.getText3());
        holder.img1.setImageResource(selectItems.getImg1());
        holder.img2.setImageResource(selectItems.getImg2());
        holder.img3.setImageResource(selectItems.getImg3());
    }

    @Override
    public int getItemCount() {
        return mSelectItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View selectView;
        LinearLayout ll1, ll2, ll3;
        TextView tv_name, tv1, tv2, tv3;
        ImageView img1, img2, img3;

        public ViewHolder(View view) {
            super(view);
            selectView = view;
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv1 = (TextView) view.findViewById(R.id.tv1);
            tv2 = (TextView) view.findViewById(R.id.tv2);
            tv3 = (TextView) view.findViewById(R.id.tv3);
            img1 = (ImageView) view.findViewById(R.id.img1);
            img2 = (ImageView) view.findViewById(R.id.img2);
            img3 = (ImageView) view.findViewById(R.id.img3);
            ll1 = (LinearLayout) view.findViewById(R.id.ll1);
            ll2 = (LinearLayout) view.findViewById(R.id.ll2);
            ll3 = (LinearLayout) view.findViewById(R.id.ll3);
        }
    }

}
