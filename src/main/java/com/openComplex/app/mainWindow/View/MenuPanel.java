package com.openComplex.app.mainWindow.View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MenuPanel extends JPanel {

    private final Border noFocusBorder = new EmptyBorder(3, 3, 3, 3);
    private final Border focusBorder = new LineBorder(Color.black);
    private final Font defaultFont = new Font(new JLabel().getFont().getFontName(), new JLabel().getFont().getStyle(), 15);
    private JList list;

    public MenuPanel(final MainView parent) {

        this.setBorder(noFocusBorder);

        String[] test = new String[TopicCollection.menuItems.size()];

        for (int i = 0; i < TopicCollection.menuItems.size(); i++) {
            test[i] = TopicCollection.menuItems.get(i);
        }

        JScrollPane scrollPane = new JScrollPane();
        list = new JList(test);
        list.setVisibleRowCount(20);
        list.setCellRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                String s = String.valueOf(value);
                s = s.replaceAll("\t", "    ");
                setText(s);
                setFont(defaultFont);
                setBorder(cellHasFocus ? focusBorder : noFocusBorder);
                setBackground(cellHasFocus ? Color.lightGray : Color.white);
                return this;
            }
        });

        list.setSelectedIndex(0);
        scrollPane.setViewportView(list);
        scrollPane.setSize(this.getWidth() / 3, this.getHeight());
        this.add(scrollPane);

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String selectedItem = (String) list.getSelectedValue();
                System.out.println(selectedItem);

                parent.updateGUI(selectedItem);

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
            }
        };
        list.addMouseListener(mouseListener);
    }
}