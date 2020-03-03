import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import org.jetbrains.annotations.NotNull;

public class LinesSelectAction extends AnAction {

    public LinesSelectAction() {
        super("LineSelect");
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
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
        int so = document.getLineStartOffset(sn), eo = document.getLineEndOffset(en);
        selectionModel.setSelection(so, eo);
    }

}
