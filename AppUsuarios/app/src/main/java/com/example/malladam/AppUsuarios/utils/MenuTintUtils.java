package com.example.malladam.AppUsuarios.utils;

import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by malladam on 14/06/2016.
 */
public class MenuTintUtils {
    public static void tintAllIcons(Menu menu, final int color) {
        for (int i = 0; i < menu.size(); ++i) {
            final MenuItem item = menu.getItem(i);
            tintMenuItemIcon(color, item);
            tintShareIconIfPresent(color, item);
        }
    }

    private static void tintMenuItemIcon(int color, MenuItem item) {
        final Drawable drawable = item.getIcon();
        if (drawable != null) {
            final Drawable wrapped = DrawableCompat.wrap(drawable);
            drawable.mutate();
            DrawableCompat.setTint(wrapped, color);
            item.setIcon(drawable);
        }
    }

    private static void tintShareIconIfPresent(int color, MenuItem item) {
        if (item.getActionView() != null) {
            final View actionView = item.getActionView();
            final View expandActivitiesButton = actionView.findViewById(android.support.design.R.id.expand_activities_button);
            if (expandActivitiesButton != null) {
                final ImageView image = (ImageView) expandActivitiesButton.findViewById(android.support.design.R.id.image);
                if (image != null) {
                    final Drawable drawable = image.getDrawable();
                    final Drawable wrapped = DrawableCompat.wrap(drawable);
                    drawable.mutate();
                    DrawableCompat.setTint(wrapped, color);
                    image.setImageDrawable(drawable);
                }
            }
        }
    }
}
