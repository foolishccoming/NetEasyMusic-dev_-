package t3.henu.neteasymusic.appMain_drawerlayout_start;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import t3.henu.neteasymusic.R;



public class RecyclerviewAdapter extends BaseMultiItemQuickAdapter<RecyclerViewData> {


    public RecyclerviewAdapter(List<RecyclerViewData> data) {
        super(data);
        addItemType(RecyclerViewData.TYPE1,R.layout.item_menu);
        addItemType(RecyclerViewData.TYPE2,R.layout.item_divider);
        addItemType(RecyclerViewData.TYPE3,R.layout.activity_main_drawerlayout_left_header);
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return RecyclerViewData.TYPE3;
        }
        if(position==5||position==8){
            return RecyclerViewData.TYPE2;
        }else{
            return RecyclerViewData.TYPE1;
        }
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, RecyclerViewData recyclerData) {
        switch (baseViewHolder.getItemViewType()){
            case RecyclerViewData.TYPE1:
                baseViewHolder.setImageResource(R.id.id_left_item_imageView,recyclerData.Image);
                baseViewHolder.setText(R.id.id_left_item_textView,recyclerData.Text);
                break;
            case RecyclerViewData.TYPE3:
                baseViewHolder.setOnClickListener(R.id.id_appmain_drawelayout_left_btn_signin, new OnItemChildClickListener());
                baseViewHolder.setOnClickListener(R.id.id_user_roundedImageView, new OnItemChildClickListener());
                break;
        }
    }
}
