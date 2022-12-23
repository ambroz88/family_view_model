package cz.ambrogenea.familyvision.utils;

import cz.ambrogenea.familyvision.dto.tree.PageSetup;
import cz.ambrogenea.familyvision.dto.tree.Position;

import java.util.Arrays;
import java.util.LinkedList;

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
        int newWidth = getStandardPixelSize(setup.pictureWidth());
        int newHeight = getStandardPixelSize(setup.pictureHeight());
        int deltaY = (newHeight - setup.pictureHeight()) / 2;
        int newX = setup.startPosition().x() + (newWidth - setup.pictureWidth()) / 2;
        int newY = setup.startPosition().y() + deltaY;

        return new PageSetup(new Position(newX, newY), newWidth, newHeight);
    }

    private static int getStandardPixelSize(int sizeInPixels) {
        double sizeInCm = sizeInPixels * 2.54 / DPI;
        double standardCmSize = sizeInCm;

        for (Double paperDimension : PAPER_DIMENSIONS) {
            if (sizeInCm < paperDimension) {
                standardCmSize = paperDimension;
                break;
            }
        }
        System.out.println("paper size: " + standardCmSize);
        return (int) (standardCmSize * DPI / 2.54);
    }

}
