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

import java.util.ArrayList;

public class Struct {

    private String category;
    private ArrayList<Node> listOfNodes;

    public Struct(String category, ArrayList<Node> listOfNodes) {
        this.category = category;
        this.listOfNodes = listOfNodes;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @return the listOfNodes
     */
    public ArrayList<Node> getListOfNodes() {
        return listOfNodes;
    }

}
