/*
 * Copyright (C) 2020 util2
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
package org.wingate.cmilkshake;

import com.formdev.flatlaf.FlatDarkLaf;

/**
 *
 * @author util2
 */
public class CaramelMilkshake {
    public static void main(String[] args) {
        FlatDarkLaf.install();
        MainFrame mf = new MainFrame();
        mf.setVisible(true);
    }
}
