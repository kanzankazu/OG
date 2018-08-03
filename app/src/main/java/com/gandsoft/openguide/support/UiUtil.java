package com.gandsoft.openguide.support;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;

public class UiUtil {

    public static void showDialog(Context context, View dialogContentView) {
        if (!(context instanceof Activity)) {
            throw new IllegalStateException("Context should be an activity");
        }
        Dialog dialog = new Dialog((Activity) context);
        dialog.setContentView(dialogContentView);
        dialog.show();
    }

    public static String getIDRadio(LinearLayout view) {
        return view.findViewById(((RadioGroup) ((LinearLayout) view.getChildAt(0)).getChildAt(1)).getCheckedRadioButtonId()).getTag().toString();
    }

    /**
     * Used to scroll to the given view.
     *
     * @param scrollViewParent Parent ScrollView
     * @param view             View to which we need to scroll.
     */
    private void scrollToView(final ScrollView scrollViewParent, final View view) {
        // Get deepChild Offset
        Point childOffset = new Point();
        getDeepChildOffset(scrollViewParent, view.getParent(), view, childOffset);
        // Scroll to child.
        scrollViewParent.smoothScrollTo(0, childOffset.y);
    }

    /**
     * Used to scroll to the given view.
     *
     * @param nestedScrollViewParent Parent ScrollView
     * @param view                   View to which we need to scroll.
     */
    private void scrollToView(final NestedScrollView nestedScrollViewParent, final View view) {
        // Get deepChild Offset
        Point childOffset = new Point();
        getDeepChildOffset(nestedScrollViewParent, view.getParent(), view, childOffset);
        // Scroll to child.
        nestedScrollViewParent.smoothScrollTo(0, childOffset.y);
    }

    /**
     * Used to get deep child offset.
     * <p/>
     * 1. We need to scroll to child in scrollview, but the child may not the direct child to scrollview.
     * 2. So to get correct child position to scroll, we need to iterate through all of its parent views till the main parent.
     *
     * @param mainParent        Main Top parent.
     * @param parent            Parent.
     * @param child             Child.
     * @param accumulatedOffset Accumulated Offset.
     */
    private void getDeepChildOffset(final ViewGroup mainParent, final ViewParent parent, final View child, final Point accumulatedOffset) {
        ViewGroup parentGroup = (ViewGroup) parent;
        accumulatedOffset.x += child.getLeft();
        accumulatedOffset.y += child.getTop();
        if (parentGroup.equals(mainParent)) {
            return;
        }
        getDeepChildOffset(mainParent, parentGroup.getParent(), parentGroup, accumulatedOffset);
    }

}
