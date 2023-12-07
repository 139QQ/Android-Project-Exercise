package com.lzok.essay;

import android.view.View;
import android.widget.Toast;

import com.lzok.essay.interfacex.FormatActions;

import jp.wasabeef.richeditor.RichEditor;

/**
 * @author lok
 * 图标点击事件
 */
public class FormatClick implements View.OnClickListener {
    private int formatAction;
    RichEditor richEditor ;
    FormatClick(RichEditor editor,int action){
        richEditor = editor;
        formatAction  = action;
    }

    @Override
    public void onClick(View v) {
        switch (formatAction) {

            case FormatActions.BOLD:
                richEditor.setBold();
                break;

            case FormatActions.ITALIC:
                richEditor.setSubscript();
                break;
            case FormatActions.SUBSCRIPT:
                richEditor.setSubscript();
                break;

            case FormatActions.SUPERSCRIPT:
                richEditor.setSuperscript();
                break;

            case FormatActions.STRIKETHROUGH:
                richEditor.setStrikeThrough();
                break;

            case FormatActions.UNDERLINE:
                richEditor.setUnderline();
                break;

            case FormatActions.JUSTIFY_LEFT:
                richEditor.setAlignLeft();
                break;

            case FormatActions.JUSTIFY_CENTER:
                richEditor.setAlignCenter();
                break;

            case FormatActions.JUSTIFY_RIGHT:
                richEditor.setAlignCenter();
                break;

            case FormatActions.BLOCKQUOTE:
                richEditor.setBlockquote();
                break;

            case FormatActions.HEADING_1:
                richEditor.setHeading(1);
                break;

            case FormatActions.HEADING_2:
                richEditor.setHeading(2);
                break;
            case FormatActions.HEADING_3:
                richEditor.setHeading(3);
                break;
//            case FormatActions.UNDO:
//                richEditor.setUndo();
//                break;
//            case FormatActions.REDO:
//                richEditor.set();
//                break;
            case FormatActions.INDENT:
                richEditor.setIndent();
                break;
            case FormatActions.OUTDENT:
                richEditor.setOutdent();
                break;



            // 可以继续添加其他格式
            default:
                break;
        }
    }
}
