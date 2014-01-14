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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

public class Serializer {

    private final static Serializer INSTANCE = new Serializer();

    private Serializer() {
        // Exists only to defeat instantiation.
    }

    private static ArrayList<SerNode> serNodes = new ArrayList<>();
    private static String error = null;

    public static void serialize(String filePath) {

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(serNodes);
            error = null;
        } catch (IOException ex) {
            error = ex.getMessage();
            Logger.getLogger(Tracker.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("INFO: Data successfully saved! " + "\n");

    }

    public static String error() {
        return error;
    }

    public static void deserialize(String filename) throws ClassNotFoundException {
        //System.out.println("INFO: Loading data from file \n");
        System.out.println("INFO: Loading data from file " + "\n");
        serNodes.clear();

        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(filename))) {
            serNodes = (ArrayList<SerNode>) reader.readObject();
            error = null;
        } catch (IOException ex) {
            error = ex.getLocalizedMessage();
            Logger.getLogger(Tracker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void createSerNode(DefaultTreeForTreeLayout<Node> tree, Node node) {
        List<Node> children = tree.getChildrenList(node);
        SerNode newNode = new SerNode(node, children);
        serNodes.add(newNode);
        System.out.println("added node with id: " + node.getId());
        for (Node aNode : children) {
            createSerNode(tree, aNode);
        }
    }

    /**
     *
     * @return
     */
    public static DefaultTreeForTreeLayout<Node> buidTree() {
        DefaultTreeForTreeLayout<Node> tree = null;

        for (SerNode serNode : serNodes) {
            Node newNode = serNode.getNode();
            List<Node> children = serNode.getChildren();
            Node[] childNodes = new Node[children.size()];
            childNodes = children.toArray(childNodes);
            if (serNode.getNode().getId() == 0) {
                tree = new DefaultTreeForTreeLayout<>(newNode);
                tree.addChildren(newNode, childNodes);
            } else {
                if (tree != null) {
                    tree.addChildren(newNode, childNodes);
                }
            }
        }

        return tree;
    }
}
