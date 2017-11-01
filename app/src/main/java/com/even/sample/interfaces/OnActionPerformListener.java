package com.even.sample.interfaces;

import com.uguke.code.editor.ActionType;

/**
 * OnActionPerformListener
 * Created by even.wu on 17/8/17.
 */

public interface OnActionPerformListener {
    void onActionPerform(ActionType type, Object... values);
}
