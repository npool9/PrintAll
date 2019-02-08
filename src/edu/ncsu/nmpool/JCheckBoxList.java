package edu.ncsu.nmpool;

import java.awt.Component;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class JCheckBoxList extends JList<JCheckBox> {
  protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

  public JCheckBoxList() {
    setCellRenderer(new CellRenderer());
    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        int index = locationToIndex(e.getPoint());
        if (index != -1) {
          JCheckBox JCheckBox = (JCheckBox) getModel().getElementAt(index);
          JCheckBox.setSelected(!JCheckBox.isSelected());
          repaint();
        }
      }
    });
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }

  public JCheckBoxList(ListModel<JCheckBox> model){
    this();
    setModel(model);
  }

  protected class CellRenderer implements ListCellRenderer<JCheckBox> {
    public Component getListCellRendererComponent(
        JList<? extends JCheckBox> list, JCheckBox value, int index,
        boolean isSelected, boolean cellHasFocus) {
      JCheckBox JCheckBox = value;

      //Drawing JCheckBox, change the appearance here
      JCheckBox.setBackground(isSelected ? getSelectionBackground()
          : getBackground());
      JCheckBox.setForeground(isSelected ? getSelectionForeground()
          : getForeground());
      JCheckBox.setEnabled(isEnabled());
      JCheckBox.setFont(getFont());
      JCheckBox.setFocusPainted(false);
      JCheckBox.setBorderPainted(true);
      JCheckBox.setBorder(isSelected ? UIManager
          .getBorder("List.focusCellHighlightBorder") : noFocusBorder);
      return JCheckBox;
    }
  }
}