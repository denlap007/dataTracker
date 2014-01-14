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
import java.awt.FontMetrics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Node implements Serializable {

    private String title;
    private String category;
    private String link;
    private String description;
    private final String timestamp;
    private final String key;
    private int width;
    private int height;
    private final int id;
    private final int PADDING;
    private List<String> fieldTokens;
    private final int MAX_LENGTH_OF_TEXT_FIELD;
    private boolean shadowed = false;

    public Node(String key, String title, String category, String link, String description, int id, String timestamp) {
        this.title = title;
        this.category = category;
        this.link = link;
        this.description = description;
        this.id = id;
        this.timestamp = timestamp;
        this.key = key;
        PADDING = 10; //pixels
        MAX_LENGTH_OF_TEXT_FIELD = 50; //characters
        fieldTokens = new ArrayList<>();
        shadowed = false;

        calcNodeDims();
    }

    /**
     * Check if any text field exceeds MAX_LENGTH_OF_TEXT_FIELD characters. If
     * so add, a new line when you find the first space character.
     *
     * @param str
     * @return
     */
    private String checkLength(String str) {

        if (str.length() > MAX_LENGTH_OF_TEXT_FIELD) {
            StringBuilder sb = new StringBuilder(str);

            int i = 0;
            while ((i = sb.indexOf(" ", i + MAX_LENGTH_OF_TEXT_FIELD)) != -1) {
                sb.replace(i, i + 1, "\n");
            }
            return sb.toString();
        } else {
            return str;
        }

    }

    public void calcNodeDims() {
        getFieldTokens().clear();

        String[] theStrings = {checkLength(getKey()), checkLength(getTitle()), checkLength(getCategory()),
            checkLength(getDescription())};

        for (String str : theStrings) {
            if (str.length() > MAX_LENGTH_OF_TEXT_FIELD) {
                String[] temp = str.split("[\n]");

                getFieldTokens().addAll(Arrays.asList(temp));
            } else {
                getFieldTokens().add(str);
            }
        }

        FontMetrics fm = returnFontMetrics();

        int maxLength = 0; //in characters
        int longestStringWidth = 0; //in pixels
        int numOfLines = 0;
        for (String str : getFieldTokens()) {
            if (fm.stringWidth(str) > maxLength) {
                // maxLength = str.length();
                maxLength = fm.stringWidth(str);
                longestStringWidth = fm.stringWidth(str);
            }
            if (str.replaceAll("\\s", "").isEmpty() == false) {
                numOfLines++;
            }
        }

        setWidth(longestStringWidth + PADDING);
        setHeight((fm.getHeight()) * numOfLines + PADDING / 2);
        System.out.println("width: " + getWidth() + ", heigth: " + getHeight());
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the fieldTokens
     */
    public List<String> getFieldTokens() {
        return fieldTokens;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @return the shadowingEnabled
     */
    public boolean isShadowingEnabled() {
        return shadowed;
    }

    /**
     * @param shadowed the shadowingEnabled to set
     */
    public void setShadowed(boolean shadowed) {
        this.shadowed = shadowed;
    }

}
