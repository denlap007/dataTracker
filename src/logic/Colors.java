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

import java.awt.Color;

public final class Colors {

    private static Color BOX_COLOR = Color.orange;
    private static Color BORDER_COLOR = Color.darkGray;
    private static Color TEXT_COLOR = Color.black;

    private static Color LAST_BOX_COLOR = Color.orange;
    private static Color LAST_BORDER_COLOR = Color.darkGray;
    private static Color LAST_TEXT_COLOR = Color.black;
    private final static Color SHADOW_COLOR = Color.lightGray;
    private static Color[] profile = null;
    private static boolean shadowing = false;

    public final static Colors INSTANCE = new Colors();

    /**
     * @return the profile
     */
    public static Color[] getProfile() {
        return profile;
    }

    /**
     * @return the SHADOW_COLOR
     */
    public static Color getSHADOW_COLOR() {
        return SHADOW_COLOR;
    }

    /**
     * @return the shadowing
     */
    public static boolean isShadowing() {
        return shadowing;
    }

    /**
     * @param aShadowing the shadowing to set
     */
    public static void setShadowing(boolean aShadowing) {
        shadowing = aShadowing;
    }

    private Colors() {
        // Exists only to defeat instantiation.
    }

    public static void saveProfile() {
        profile = new Color[3];
        profile[0] = BOX_COLOR;
        profile[1] = BORDER_COLOR;
        profile[2] = TEXT_COLOR;
    }

    public static void loadProfile() {
        BOX_COLOR = getProfile()[0];
        BORDER_COLOR = getProfile()[1];
        TEXT_COLOR = getProfile()[2];
    }

    /* Other methods protected by singleton-ness */
    /**
     * @return the BOX_COLOR
     */
    public static Color getBOX_COLOR() {
        return BOX_COLOR;
    }

    /**
     * @param aBOX_COLOR the BOX_COLOR to set
     */
    public static void setBOX_COLOR(Color aBOX_COLOR) {
        BOX_COLOR = aBOX_COLOR;
    }

    /**
     * @return the BORDER_COLOR
     */
    public static Color getBORDER_COLOR() {
        return BORDER_COLOR;
    }

    /**
     * @param aBORDER_COLOR the BORDER_COLOR to set
     */
    public static void setBORDER_COLOR(Color aBORDER_COLOR) {
        BORDER_COLOR = aBORDER_COLOR;
    }

    /**
     * @return the TEXT_COLOR
     */
    public static Color getTEXT_COLOR() {
        return TEXT_COLOR;
    }

    /**
     * @param aTEXT_COLOR the TEXT_COLOR to set
     */
    public static void setTEXT_COLOR(Color aTEXT_COLOR) {
        TEXT_COLOR = aTEXT_COLOR;
    }

    public static void setdefaultColors() {
        BOX_COLOR = Color.orange;
        BORDER_COLOR = Color.darkGray;
        TEXT_COLOR = Color.black;
    }

    public static void setCurColorsAsLast() {
        LAST_BOX_COLOR = BOX_COLOR;
        LAST_BORDER_COLOR = BORDER_COLOR;
        LAST_TEXT_COLOR = TEXT_COLOR;
    }

    public static void getLastColors() {
        BOX_COLOR = LAST_BOX_COLOR;
        BORDER_COLOR = LAST_BORDER_COLOR;
        TEXT_COLOR = LAST_TEXT_COLOR;
    }

}
