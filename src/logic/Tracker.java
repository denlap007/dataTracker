/*
 * Copyright (C) 2014 Dio
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

public final class Tracker {

    private final int INIT_ID = -1;
    private int id = INIT_ID;
    private final ArrayList<String> categories = new ArrayList<>();
    private Date lastTimeStamp;
    private final ArrayList<SerNode> serNodes = new ArrayList<>();
    private int indxOfDelNode=-1;

    /**
     *
     * @return the next id
     */
    public int createId() {
        id++;
        return getId();
    }

    /**
     * Create serNodes <Node theNode, ArrayList<Node> children> and return the
     * set the index of indxOfDelnode
     *
     * @param tree
     * @param node
     * @param id
     */
    public void createSerNode(DefaultTreeForTreeLayout<Node> tree, Node node, int id) {
        List<Node> children = tree.getChildrenList(node);
        SerNode newNode = new SerNode(node, children);
        serNodes.add(newNode);

        if (node.getId() == id) {
            indxOfDelNode = serNodes.indexOf(newNode);
            System.out.println("Node found with index: " + indxOfDelNode);
        }

        System.out.println("added node with id: " + node.getId());
        for (Node aNode : children) {
            createSerNode(tree, aNode, id);
        }
    }

    public DefaultTreeForTreeLayout<Node> deleteNode(DefaultTreeForTreeLayout<Node> tree, Node node, int id) {
        createSerNode(tree, node, id);

        if (indxOfDelNode == -1) {
            serNodes.clear();
            return null;
        } else {
            //a new tree
            DefaultTreeForTreeLayout<Node> newTree;
            //find the node to delete
            Node delNode = serNodes.get(indxOfDelNode).getNode();
            //find the parent of delNode
            Node parent = tree.getParent(delNode);
            SerNode serParent = null;

            //find the parent of delNode in serNodes
            for (SerNode checkNode : serNodes) {
                if (checkNode.getNode().getId() == parent.getId()) {
                    serParent = checkNode;
                    break;
                }
            }

            //remove delNode from paren'ts children list
            //NO need to worry about nulls as every node has a parent
            serParent.getChildren().remove(delNode);
            //add delNode's children to parent
            for (Node child : serNodes.get(indxOfDelNode).getChildren()) {
                serParent.getChildren().add(child);
            }
            
            //remove serDelNod from serNodes
            serNodes.remove(serNodes.get(indxOfDelNode));

            newTree = buidTree();
            serNodes.clear();
            indxOfDelNode = -1;
            
            return newTree;
        }
    }


    public DefaultTreeForTreeLayout<Node> buidTree() {
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

    /**
     * @return the categories
     */
    public ArrayList<String> getCategories() {
        return categories;
    }

    /**
     *
     * @return the timestamp
     */
    public String createTimestamp() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return dateFormat.format(date);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the lastTimeStamp
     */
    public Date getLastTimeStamp() {
        return lastTimeStamp;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    public void clearCategories() {
        categories.clear();
    }

    public void resetId() {
        id = INIT_ID;
    }

}
