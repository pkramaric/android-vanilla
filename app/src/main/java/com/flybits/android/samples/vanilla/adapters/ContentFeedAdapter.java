package com.flybits.android.samples.vanilla.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flybits.android.kernel.models.Content;
import com.flybits.android.samples.vanilla.Constants;
import com.flybits.android.samples.vanilla.R;
import com.flybits.android.samples.vanilla.templates.ImageOnly;
import com.flybits.android.samples.vanilla.templates.TextOnly;
import com.flybits.android.samples.vanilla.templates.TextWithImage;
import com.flybits.commons.library.exceptions.FlybitsException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ContentFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_MODULE_TEXT_WITH_IMAGE   = 0;
    private final int VIEW_MODULE_TEXT              = 1;
    private final int VIEW_MODULE_IMAGE             = 2;

    private ArrayList<Content> items;
    private Context context;

    public ContentFeedAdapter(Context context, ArrayList<Content> items){
        this.context        = context;
        this.items          = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int code) {

        switch ( code ) {
            case VIEW_MODULE_TEXT_WITH_IMAGE:
                View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_with_img, parent, false);
                return new TextWithImageViewHolder(v1);
            case VIEW_MODULE_TEXT:
                View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_only, parent, false);
                return new TextOnlyViewHolder(v2);
            case VIEW_MODULE_IMAGE:
                View v3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img_only, parent, false);
                return new ImageOnlyViewHolder(v3);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder customViewHolder, int position) {

        switch (customViewHolder.getItemViewType()) {
            case VIEW_MODULE_TEXT_WITH_IMAGE:

                try {
                    TextWithImage obj = (TextWithImage) items.get(position).getData(context, TextWithImage.class);
                    ((TextWithImageViewHolder)customViewHolder).txtTitle.setText(obj.title);
                    ((TextWithImageViewHolder)customViewHolder).txtDescription.setText(obj.description);
                    if (obj.url != null) {
                        Picasso.with(context).load(obj.url).into(((TextWithImageViewHolder) customViewHolder).img);
                    }
                }catch (FlybitsException e){}
                break;
            case VIEW_MODULE_TEXT:
                try {
                    TextOnly obj = (TextOnly) items.get(position).getData(context, TextOnly.class);
                    ((TextOnlyViewHolder)customViewHolder).txtTitle.setText(obj.title);
                    ((TextOnlyViewHolder)customViewHolder).txtDescription.setText(obj.description);

                }catch (FlybitsException e){}
                 break;
            case VIEW_MODULE_IMAGE:
                try {
                    ImageOnly obj = (ImageOnly) items.get(position).getData(context, ImageOnly.class);
                    if (obj.url != null) {
                        Picasso.with(context).load(obj.url).into(((ImageOnlyViewHolder) customViewHolder).img);
                    }

                }catch (FlybitsException e){}
               break;
        }
    }

    @Override
    public int getItemViewType(int position) {

        switch (items.get(position).getTemplateId()){
            case Constants.TEMPLATE_ID_TEXT_AND_IMG:
                return VIEW_MODULE_TEXT_WITH_IMAGE;
            case Constants.TEMPLATE_ID_TEXT_ONLY:
                return VIEW_MODULE_TEXT;
            case Constants.TEMPLATE_ID_IMAGE_ONLY:
                return VIEW_MODULE_IMAGE;
            default:
                return VIEW_MODULE_TEXT;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class TextWithImageViewHolder extends RecyclerView.ViewHolder  {

        public TextView txtTitle;
        public TextView txtDescription;
        public ImageView img;

        public TextWithImageViewHolder(View v) {
            super(v);
            txtTitle        = (TextView) v.findViewById(R.id.txtTitle);
            txtDescription  = (TextView) v.findViewById(R.id.txtDescription);
            img             = (ImageView) v.findViewById(R.id.imgPic);
        }
    }

    public class TextOnlyViewHolder extends RecyclerView.ViewHolder  {

        public TextView txtTitle;
        public TextView txtDescription;

        public TextOnlyViewHolder(View v) {
            super(v);
            txtTitle        = (TextView) v.findViewById(R.id.txtTitle);
            txtDescription  = (TextView) v.findViewById(R.id.txtDescription);
        }
    }

    public class ImageOnlyViewHolder extends RecyclerView.ViewHolder  {

        public ImageView img;

        public ImageOnlyViewHolder(View v) {
            super(v);
            img             = (ImageView) v.findViewById(R.id.imgPic);
        }
    }

}
