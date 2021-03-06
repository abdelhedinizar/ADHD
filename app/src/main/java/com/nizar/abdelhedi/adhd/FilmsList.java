package com.nizar.abdelhedi.adhd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nizar.abdelhedi.adhd.adapter.AlbumsAdapter;
import com.nizar.abdelhedi.adhd.model.Album;

import java.util.ArrayList;
import java.util.List;


public class FilmsList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_films_list);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            initCollapsingToolbar();

            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

            albumList = new ArrayList<>();
            adapter = new AlbumsAdapter(this, albumList);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);

            prepareAlbums();

            try {
                Glide.with(this).load(R.drawable.film2).into((ImageView) findViewById(R.id.backdrop));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Initializing collapsing toolbar
         * Will show and hide the toolbar title on scroll
         */
        private void initCollapsingToolbar() {
            final CollapsingToolbarLayout collapsingToolbar =
                    (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
            collapsingToolbar.setTitle(" ");
            AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
            appBarLayout.setExpanded(true);

            // hiding & showing the title when toolbar expanded & collapsed
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                boolean isShow = false;
                int scrollRange = -1;

                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout.getTotalScrollRange();
                    }
                    if (scrollRange + verticalOffset == 0) {
                        collapsingToolbar.setTitle(getString(R.string.app_name));
                        isShow = true;
                    } else if (isShow) {
                        collapsingToolbar.setTitle(" ");
                        isShow = false;
                    }
                }
            });
        }

        /**
         * Adding few albums for testing
         */
        private void prepareAlbums() {
            int[] covers = new int[]{
                    R.drawable.image,
                    R.drawable.image1,
                    R.drawable.image2,
                    R.drawable.image3,
                    R.drawable.image4,
                    R.drawable.image5,
                    R.drawable.image6,
                    R.drawable.image7
                    };

            Album a = new Album("Tom and jerry", 1, covers[0],R.raw.tom_jerry);
            albumList.add(a);

            a = new Album("Xscpae", 2, covers[1],R.raw.tom_jerry);
            albumList.add(a);

            a = new Album("Maroon 5", 3, covers[2],R.raw.tom_jerry);
            albumList.add(a);

            a = new Album("Born to Die", 4, covers[3],R.raw.tom_jerry);
            albumList.add(a);

            a = new Album("Honeymoon", 5, covers[4],R.raw.tom_jerry);
            albumList.add(a);

            a = new Album("I Need a Doctor", 6, covers[5],R.raw.tom_jerry);
            albumList.add(a);

            a = new Album("Loud", 7, covers[6],R.raw.tom_jerry);
            albumList.add(a);

            a = new Album("Legend", 8, covers[7],R.raw.tom_jerry);
            albumList.add(a);


            adapter.notifyDataSetChanged();
        }

        /**
         * RecyclerView item decoration - give equal margin around grid item
         */
        public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

            private int spanCount;
            private int spacing;
            private boolean includeEdge;

            public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
                this.spanCount = spanCount;
                this.spacing = spacing;
                this.includeEdge = includeEdge;
            }



            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view); // item position
                int column = position % spanCount; // item column

                if (includeEdge) {
                    outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                    if (position < spanCount) { // top edge
                        outRect.top = spacing;
                    }
                    outRect.bottom = spacing; // item bottom
                } else {
                    outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                    outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                    if (position >= spanCount) {
                        outRect.top = spacing; // item top
                    }
                }
            }
        }

        /**
         * Converting dp to pixel
         */
        private int dpToPx(int dp) {
            Resources r = getResources();
            return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
        }
    }

