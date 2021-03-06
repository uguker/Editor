package com.uguke.code.editor;

import java.util.HashMap;
import java.util.Map;

public enum ActionType {

    NONE(0),
    // TODO 字体
    FAMILY(1), SIZE(2), LINE_HEIGHT(3), FORE_COLOR(4), BACK_COLOR(5),
    //T ODO 字体格式
    BOLD(6), ITALIC(7), UNDERLINE(8), SUBSCRIPT(9), SUPERSCRIPT(10), STRIKETHROUGH(11),
    // TODO 字体样式
    NORMAL(12), H1(13), H2(14), H3(15), H4(16), H5(17), H6(18),
    // TODO 段落
    JUSTIFY_LEFT(19), JUSTIFY_CENTER(20), JUSTIFY_RIGHT(21), JUSTIFY_FULL(22),
    // TODO 列表
    ORDERED(23), UNORDERED(24),
    // TODO 缩进
    INDENT(25), OUTDENT(26),
    // TODO 插入
    IMAGE(27), LINK(28), TABLE(29), LINE(30),
    // TODO 代码块及引用
    BLOCK_QUOTE(31), BLOCK_CODE(32);

    private int value;

    private static final Map<Integer, ActionType> actionTypeMap = new HashMap<>();

    static {
        for (ActionType actionType : values()) {
            actionTypeMap.put(actionType.getValue(), actionType);
        }
    }

    ActionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static ActionType fromInteger(int key) {
        return actionTypeMap.get(key);
    }
}
