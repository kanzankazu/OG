package com.gandsoft.openguide.activity.infomenu.gallery2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gandsoft.openguide.R;
import com.jsibbold.zoomage.ZoomageView;

public class GalleryDetailPagerFragment extends Fragment {

    private int position;
    private String like;
    private String account_id;
    private String total_comment;
    private String status_like;
    private String username;
    private String caption;
    private String image_posted;
    private String image_icon;
    private String image_postedLocal;
    private String image_iconLocal;

    private static final String ARG_SECTION_NUMBER = "position_img";
    private static final String ARG_IMG_LIKE = "like_img";
    private static final String ARG_IMG_ACCOUNT_ID = "account_id_img";
    private static final String ARG_IMG_COMMENT = "total_comment_img";
    private static final String ARG_IMG_STATLIKE = "status_like_img";
    private static final String ARG_IMG_USERNAME = "username_img";
    private static final String ARG_IMG_CAPTION = "caption_img";
    private static final String ARG_IMG_IMAGE_POSTED = "image_posted_img";
    private static final String ARG_IMG_IMAGE_ICON = "image_icon_img";
    private static final String ARG_IMG_IMAGE_POSTEDLOCAL = "image_postedLocal_img";
    private static final String ARG_IMG_IMAGE_ICONLOCAL = "image_iconLocal_img";

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.position = args.getInt(ARG_SECTION_NUMBER);
        this.like = args.getString(ARG_IMG_LIKE);
        this.account_id = args.getString(ARG_IMG_ACCOUNT_ID);
        this.total_comment = args.getString(ARG_IMG_COMMENT);
        this.status_like = args.getString(ARG_IMG_STATLIKE);
        this.username = args.getString(ARG_IMG_USERNAME);
        this.caption = args.getString(ARG_IMG_CAPTION);
        this.image_posted = args.getString(ARG_IMG_IMAGE_POSTED);
        this.image_icon = args.getString(ARG_IMG_IMAGE_ICON);
        this.image_postedLocal = args.getString(ARG_IMG_IMAGE_POSTEDLOCAL);
        this.image_iconLocal = args.getString(ARG_IMG_IMAGE_ICONLOCAL);
    }

    public static Fragment newInstance(int position, String id, String like, String account_id, String total_comment, String status_like, String username, String caption, String image_posted, String image_icon, String image_postedLocal, String image_iconLocal) {
        GalleryDetailPagerFragment fragment = new GalleryDetailPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, position);
        args.putString(ARG_IMG_LIKE, like);
        args.putString(ARG_IMG_ACCOUNT_ID, account_id);
        args.putString(ARG_IMG_COMMENT, total_comment);
        args.putString(ARG_IMG_STATLIKE, status_like);
        args.putString(ARG_IMG_USERNAME, username);
        args.putString(ARG_IMG_CAPTION, caption);
        args.putString(ARG_IMG_IMAGE_POSTED, image_posted);
        args.putString(ARG_IMG_IMAGE_ICON, image_icon);
        args.putString(ARG_IMG_IMAGE_POSTEDLOCAL, image_postedLocal);
        args.putString(ARG_IMG_IMAGE_ICONLOCAL, image_iconLocal);
        fragment.setArguments(args);
        return fragment;
    }

    public GalleryDetailPagerFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        final ZoomageView zivDetailGalleryfvbi = (ZoomageView) rootView.findViewById(R.id.zivDetailGallery);
        final LinearLayout llDetailGalleryCommentfvbi = (LinearLayout) rootView.findViewById(R.id.llDetailGalleryComment);
        final LinearLayout llDetailGalleryLikefvbi = (LinearLayout) rootView.findViewById(R.id.llDetailGalleryLike);
        final TextView tvDetailGalleryCommentfvbi = (TextView) rootView.findViewById(R.id.tvDetailGalleryComment);
        final TextView tvDetailGalleryLikefvbi = (TextView) rootView.findViewById(R.id.tvDetailGalleryLike);
        final TextView tvDetailGalleryUsernamefvbi = (TextView) rootView.findViewById(R.id.tvDetailGalleryUsername);
        final TextView tvDetailGalleryCaptionfvbi = (TextView) rootView.findViewById(R.id.tvDetailGalleryCaption);
        final ImageView ivDetailGalleryLikefvbi = (ImageView) rootView.findViewById(R.id.ivDetailGalleryLike);
        final ImageView ivDetailGalleryIconfvbi = (ImageView) rootView.findViewById(R.id.ivDetailGalleryIcon);

        tvDetailGalleryCommentfvbi.setText(total_comment);
        tvDetailGalleryLikefvbi.setText(like);
        tvDetailGalleryUsernamefvbi.setText(username);
        tvDetailGalleryCaptionfvbi.setText(caption);

        Glide.with(getActivity())
                .load(image_posted)
                .thumbnail(0.1f)
                .into(zivDetailGalleryfvbi);

        Glide.with(getActivity())
                .load(image_icon)
                .thumbnail(0.1f)
                .into(ivDetailGalleryIconfvbi);

        if (Integer.parseInt(status_like) != 0) {
            ivDetailGalleryLikefvbi.setImageResource(R.drawable.ic_love_fill);
        } else {
            ivDetailGalleryLikefvbi.setImageResource(R.drawable.ic_love_empty);
        }

        llDetailGalleryLikefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int iLike = Integer.parseInt(like);
                if (Integer.parseInt(status_like) == 0) {
                    tvDetailGalleryLikefvbi.setText(String.valueOf(iLike + 1));
                    ivDetailGalleryLikefvbi.setImageResource(R.drawable.ic_love_fill);
                    status_like = String.valueOf(1);
                    like = String.valueOf(iLike + 1);
                } else {
                    tvDetailGalleryLikefvbi.setText(String.valueOf(iLike - 1));
                    ivDetailGalleryLikefvbi.setImageResource(R.drawable.ic_love_empty);
                    status_like = String.valueOf(0);
                    like = String.valueOf(iLike - 1);
                }
            }
        });

        return rootView;
    }
}
