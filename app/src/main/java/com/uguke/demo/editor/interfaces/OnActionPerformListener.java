package com.uguke.demo.editor.interfaces;

import com.uguke.code.editor.ActionType;

public interface OnActionPerformListener {
    void onActionPerform(ActionType type, Object... values);
}
