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
package logic;

import static gui.MainWindow.returnFontMetrics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.List;
import javax.swing.JComponent;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;

/**
 *
 * @author Dio
 */
public class NodeTreePane extends JComponent {

    private final TreeLayout<Node> treeLayout;

    private TreeForTreeLayout<Node> getTree() {
        return treeLayout.getTree();
    }

    private Iterable<Node> getChildren(Node parent) {
        return getTree().getChildren(parent);
    }

    private Rectangle2D.Double getBoundsOfNode(Node node) {
        return treeLayout.getNodeBounds().get(node);
    }

    /**
     * Specifies the tree to be displayed by passing in a {@link TreeLayout} for
     * that tree.
     *
     * @param treeLayout
     */
    public NodeTreePane(TreeLayout<Node> treeLayout) {
        this.treeLayout = treeLayout;

        Dimension size = treeLayout.getBounds().getBounds().getSize();
        setPreferredSize(size);
    }

    public NodeTreePane(TreeLayout<Node> treeLayout, Color BOX_COLOR, Color BORDER_COLOR, Color TEXT_COLOR) {
        this.treeLayout = treeLayout;

        Dimension size = treeLayout.getBounds().getBounds().getSize();
        setPreferredSize(size);
    }

    // -------------------------------------------------------------------
    // painting
    private final static int ARC_SIZE = 10;

    private void paintEdges(Graphics g, Node parent) {
        if (!getTree().isLeaf(parent)) {
            Rectangle2D.Double b1 = getBoundsOfNode(parent);
            double x1 = b1.getCenterX();
            double y1 = b1.getCenterY();
            for (Node child : getChildren(parent)) {
                Rectangle2D.Double b2 = getBoundsOfNode(child);
                g.drawLine((int) x1, (int) y1, (int) b2.getCenterX(),
                        (int) b2.getCenterY());

                paintEdges(g, child);
            }
        }
    }

    private void paintBox(Graphics g, Node theNode) {
        // draw the box in the background
        if (Colors.isShadowing() == true) {
            if (theNode.isShadowingEnabled() == true) {
                g.setColor(Colors.getBOX_COLOR());
            } else {
                g.setColor(Colors.getSHADOW_COLOR());
            }
        } else {
            g.setColor(Colors.getBOX_COLOR());
        }

        Rectangle2D.Double box = getBoundsOfNode(theNode);
        g.fillRoundRect((int) box.x, (int) box.y, (int) box.width - 1,
                (int) box.height - 1, ARC_SIZE, ARC_SIZE);
        g.setColor(Colors.getBORDER_COLOR());
        g.drawRoundRect((int) box.x, (int) box.y, (int) box.width - 1,
                (int) box.height - 1, ARC_SIZE, ARC_SIZE);

        // draw the text on top of the box (possibly multiple lines)
        g.setColor(Colors.getTEXT_COLOR());

        Tracker t = new Tracker();
        String timestamp = theNode.getTimestamp();
        int id = theNode.getId();
        String key = "#" + Integer.toString(id) + ", " + timestamp;

        List<String> lines = theNode.getFieldTokens();
        FontMetrics m = returnFontMetrics();

        int x = (int) box.x + ARC_SIZE / 2;
        int y = (int) box.y + m.getAscent() + m.getLeading() + 1;
        for (String line : lines) {
            String temp = line.replaceAll("\\s", "");
            if (temp.isEmpty() == true) {
                continue;
            }

            g.drawString(line, x, y);
            y += m.getHeight();
        }
    }

    @Override
    public void paint(Graphics g
    ) {
        super.paint(g);

        paintEdges(g, getTree().getRoot());

        // paint the boxes
        for (Node theNode : treeLayout.getNodeBounds().keySet()) {
            paintBox(g, theNode);
        }
    }

}
