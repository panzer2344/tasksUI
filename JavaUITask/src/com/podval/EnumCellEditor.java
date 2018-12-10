package com.podval;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EnumCellEditor extends AbstractCellEditor implements TableCellEditor {

    private JComboBox editor;

    EnumCellEditor(){
        editor = new JComboBox<>(Genre.values());
        editor.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    EnumCellEditor.super.stopCellEditing();
                }

                switch (e.getKeyCode()){
                    case KeyEvent.VK_ENTER:
                        EnumCellEditor.super.stopCellEditing();

                    case KeyEvent.VK_ESCAPE:
                        EnumCellEditor.super.cancelCellEditing();
                }
            }
        });
    }

    @Override
    public Object getCellEditorValue() {

        return editor.getSelectedItem().toString();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return editor;
    }


}
