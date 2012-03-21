package posl.editorkit.util;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.CompoundEdit;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

@SuppressWarnings("serial")
public class DefaultUndoManager extends UndoManager {
    /**
     * Delay between consecutive edits in ms. Edits within
     * the time frame are combined together
     */
    public static final int DELAY = 620;

    private long startTime = 0;
    private CompoundEdit comp = null;

    public DefaultUndoManager() {
    	comp = new CompoundEdit();
    }

    @Override
    public synchronized boolean addEdit(UndoableEdit edit) {
        long now = System.currentTimeMillis();
        comp.addEdit(edit);
        if (now - startTime > DELAY) {
            commit();
        }
        startTime = now;
        return true;
    }

    @Override
    public synchronized boolean canRedo() {
        return super.canRedo();
    }

    @Override
    public synchronized boolean canUndo() {
        return super.canUndo();
    }

    @Override
    public synchronized void discardAllEdits() {
        comp = new CompoundEdit();
        super.discardAllEdits();
    }

    @Override
    public synchronized void redo() throws CannotRedoException {
        commit();
        super.redo();
    }

    @Override
    public synchronized void undo() throws CannotUndoException {
        commit();
        super.undo();
    }

    private void commit() {
        comp.end();
        if (comp.isSignificant()){
        	super.addEdit(comp);
        }
        comp = new CompoundEdit();
    }
}
