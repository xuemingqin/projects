package com.itaccess.interphone.widget;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.widget.TextView;

import com.itaccess.interphone.ui.activity.WebActivity;

/**
 * Created by linxi on 2019/01/25.
 */

public class LinkMovementMethod extends android.text.method.LinkMovementMethod {

    public static String BUNDLE_KEY_LINK_URL = "bundle_key_link_url";
    private static LinkMovementMethod sInstance;

    public static LinkMovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new LinkMovementMethod();
        }
        return sInstance;
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    /*
                     * Delete this code
                     */
                    //link[0].onClick(widget);

                    /*
                     * Write your custom logic
                     */
                    if (link[0] instanceof URLSpan) {
                        String url = ((URLSpan) link[0]).getURL();
                        Context context = widget.getContext();
                        Intent intent = new Intent(context, WebActivity.class);
                        intent.putExtra(BUNDLE_KEY_LINK_URL, url);
                        context.startActivity(intent);
                    }
                } else if (action == MotionEvent.ACTION_DOWN) {
                    Selection.setSelection(buffer,
                            buffer.getSpanStart(link[0]),
                            buffer.getSpanEnd(link[0]));
                }

                return true;
            } else {
                Selection.removeSelection(buffer);
            }
        }

        return super.onTouchEvent(widget, buffer, event);
    }
}