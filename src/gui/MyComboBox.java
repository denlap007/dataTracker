/*
 * Copyright (C) 2013-2014 Dio
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package gui;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import org.jdesktop.swingx.JXTable;

public class MyComboBox extends JComboBox {

    MyComboBox() {
        super();
        this.addPopupMenuListener(this.getPopupMenuListener());
        this.adjustScrollBar();
    }

    private void adjustPopupWidth() {
        if (getItemCount() == 0) {
            return;
        }
        Object comp = getUI().getAccessibleChild(this, 8);
        if (!(comp instanceof JPopupMenu)) {
            return;
        }
        JPopupMenu popup = (JPopupMenu) comp;
        JScrollPane scrollPane = (JScrollPane) popup.getComponent(0);
        Object value = getItemAt(0);
        Component rendererComp = getRenderer().getListCellRendererComponent(new JList(), value, 8, false, false);
        if (rendererComp instanceof JXTable) {
            scrollPane.setColumnHeaderView(((JTable) rendererComp).getTableHeader());
        }
        Dimension prefSize = rendererComp.getPreferredSize();
        Dimension size = scrollPane.getPreferredSize();
        size.width = Math.max(size.width, prefSize.width);
        scrollPane.setPreferredSize(size);
        scrollPane.setMaximumSize(size);
        scrollPane.revalidate();
    }

    private void adjustScrollBar() {
        if (getItemCount() == 0) {
            return;
        }
        Object comp = getUI().getAccessibleChild(this, 0);
        if (!(comp instanceof JPopupMenu)) {
            return;
        }
        JPopupMenu popup = (JPopupMenu) comp;
        JScrollPane scrollPane = (JScrollPane) popup.getComponent(0);
        scrollPane.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    private PopupMenuListener getPopupMenuListener() {

        return new PopupMenuListener() {

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                adjustScrollBar();
                //adjustPopupWidth();
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        };

    }
}
