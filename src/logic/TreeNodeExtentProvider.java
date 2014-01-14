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

import org.abego.treelayout.NodeExtentProvider;

/**
 *
 * @author Dio
 */
public class TreeNodeExtentProvider implements
		NodeExtentProvider<Node> {

	@Override
	public double getWidth(Node treeNode) {
		return treeNode.getWidth();
	}

	@Override
	public double getHeight(Node treeNode) {
		return treeNode.getHeight();
	}

}
