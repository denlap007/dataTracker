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

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.List;
import java.util.Arrays;
import javax.imageio.*;
import javax.swing.*;

public class ScreenImage {

    private List<String> types = Arrays.asList(ImageIO.getWriterFileSuffixes());

    /*
     *  Create a BufferedImage for Swing components.
     *  The entire component will be captured to an image.
     *
     *  @param  component Swing component to create image from
     *  @return	image the image for the given region
     */
    public BufferedImage createImage(JComponent component) {
        Dimension d = component.getSize();

        if (d.width == 0 || d.height == 0) {
            d = component.getPreferredSize();
            component.setSize(d);
        }

        Rectangle region = new Rectangle(0, 0, d.width, d.height);
        return createImage(component, region);
    }

    /*
     *  Create a BufferedImage for Swing components.
     *  All or part of the component can be captured to an image.
     *
     *  @param  component Swing component to create image from
     *  @param  region The region of the component to be captured to an image
     *  @return	image the image for the given region
     */
    public BufferedImage createImage(JComponent component, Rectangle region) {
        //  Make sure the component has a size and has been layed out.
        //  (necessary check for components not added to a realized frame)

        if (!component.isDisplayable()) {
            Dimension d = component.getSize();

            if (d.width == 0 || d.height == 0) {
                d = component.getPreferredSize();
                component.setSize(d);
            }

            layoutComponent(component);
        }

        BufferedImage image = new BufferedImage(region.width, region.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

		//  Paint a background for non-opaque components,
        //  otherwise the background will be black
        if (!component.isOpaque()) {
            g2d.setColor(component.getBackground());
            g2d.fillRect(region.x, region.y, region.width, region.height);
        }

        g2d.translate(-region.x, -region.y);
        component.paint(g2d);
        g2d.dispose();
        return image;
    }

    /**
     * Write a BufferedImage to a File.
     *
     * @param	image image to be written
     * @param	fileName name of file to be created
     * @exception IOException if an error occurs during writing
     */
    public void writeImage(BufferedImage image, String fileName)
            throws IOException {
        if (fileName == null) {
            return;
        }

        int offset = fileName.lastIndexOf(".");

        if (offset == -1) {
            String message = "file suffix was not specified";
            throw new IOException(message);
        }

        String type = fileName.substring(offset + 1);

        if (types.contains(type)) {
            ImageIO.write(image, type, new File(fileName));
        } else {
            String message = "unknown writer file suffix (" + type + ")";
            throw new IOException(message);
        }
    }

    void layoutComponent(Component component) {
        synchronized (component.getTreeLock()) {
            component.doLayout();

            if (component instanceof Container) {
                for (Component child : ((Container) component).getComponents()) {
                    layoutComponent(child);
                }
            }
        }
    }

}
