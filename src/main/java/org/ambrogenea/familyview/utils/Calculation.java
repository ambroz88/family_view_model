package org.ambrogenea.familyview.utils;

import java.util.Arrays;
import java.util.LinkedList;

import org.ambrogenea.familyview.dto.tree.Position;
import org.ambrogenea.familyview.service.DefaultPageSetup;
import org.ambrogenea.familyview.service.PageSetup;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Calculation {

    public static int DPI = 96;

    public static final double A4_WIDTH = 29.7;
    public static final double A3_WIDTH = 42.0;
    public static final double A2_WIDTH = 59.4;
    public static final double A1_WIDTH = 84.1;
    public static final double A0_WIDTH = 118.9;

    public static LinkedList<Double> PAPER_DIMENSIONS = new LinkedList<>(Arrays.asList(A4_WIDTH, A3_WIDTH, A2_WIDTH, A1_WIDTH, A0_WIDTH));

    public static PageSetup standardizePageSetup(PageSetup setup) {
        int newWidth = getStandardPixelSize(setup.getWidth());
        int newHeight = getStandardPixelSize(setup.getHeight());
        int newX = setup.getRootPosition().getX() + (newWidth - setup.getWidth()) / 2;
        int newY = setup.getRootPosition().getY() + (newHeight - setup.getHeight()) / 2;

        return new DefaultPageSetup(newWidth, newHeight, new Position(newX, newY));
    }

    private static int getStandardPixelSize(int sizeInPixels) {
        double sizeInCm = sizeInPixels * 2.54 / DPI;
        double standardCmSize = sizeInCm;

        for (int i = 0; i < PAPER_DIMENSIONS.size(); i++) {

            if (sizeInCm < PAPER_DIMENSIONS.get(i)) {
                standardCmSize = PAPER_DIMENSIONS.get(i);
                break;
            }
        }

        return (int) (standardCmSize * DPI / 2.54);
    }

}
