import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;

import java.awt.*;


public class NewLineReplaceAction extends AnAction {

    public NewLineReplaceAction() throws AWTException {
        super("NewLineReplace");
    }

    public void actionPerformed(AnActionEvent event) {
        /*if (robot != null) {
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_Y);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_Y);

            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }*/
        Editor editor = event.getData(PlatformDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        CaretModel caretModel = editor.getCaretModel();
        SelectionModel selectionModel = editor.getSelectionModel();
        Document document = editor.getDocument();
        int sn, en;
        if (selectionModel.hasSelection()) {
            sn = document.getLineNumber(selectionModel.getSelectionStart());
            en = document.getLineNumber(selectionModel.getSelectionEnd());
        } else {
            sn = document.getLineNumber(caretModel.getOffset());
            en = sn;
        }
        selectionModel.removeSelection();
        int so = document.getLineStartOffset(sn), eo = document.getLineEndOffset(en);
        String text = document.getText(TextRange.create(so, eo));
        int i = 0;
        while (text.charAt(i) == ' ' && i < eo - so) {
            i++;
        }
        final int sof = so + i;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                document.deleteString(sof, eo);
            }
        };
        Project project = event.getData(PlatformDataKeys.PROJECT);
        WriteCommandAction.runWriteCommandAction(project, runnable);
    }
}
