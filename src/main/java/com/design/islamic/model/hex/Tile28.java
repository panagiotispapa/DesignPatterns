package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.PointsPath;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.*;
import static java.lang.Math.PI;
import static java.util.Arrays.asList;

public class Tile28 {

    public static double KB = 0.5;
    public static double KC = KB / H;
    private static double ANGLE_1 = PI / 3.0 - PI / 4.0;
    private static double BD = 0.5 * KC * Math.tan(ANGLE_1);
    private static double KD = KB - BD;
    private static double KE = H;
    private static double CE = KC - KE;
    private static double EF = CE;

    public final static FinalPointTransition A = fpt(pt(1.0, RIGHT));
    public final static FinalPointTransition B = fpt(pt(KB, RIGHT));
    public final static FinalPointTransition D = fpt(pt(KD, RIGHT));
    public final static FinalPointTransition C = fpt(pt(KC, DR_V));
    public final static FinalPointTransition E = fpt(pt(KE, DR_V));
    public final static FinalPointTransition F = E.append(pt(CE, UR_H));
    public final static FinalPointTransition E2 = fpt(pt(KE, UR_V));
    public final static FinalPointTransition F2 = E2.append(pt(CE, DR_H));

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple.Builder("hex_tile_28",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsNewFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        List<String> equations = Arrays.asList(
                "KE = h",
                "KB = 0.5 * KA",
                "BD = tan(15) * BC",
                "KD = KA - DB - BA",
                "KC = KB / h",
                "CE = KE - KC",
                "CE = EF"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_28_design")
                .addEquations(equations)
                .addImportantVertexes(Tile28.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K),
                        diagonals(H, VER).apply(K),
                        innerTriangles(1.0, HOR).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(EF, HOR).apply(E)
                )
                .addFullPaths(red, getFullPath())
                ;

    }

    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(F, D, F2)
        );
    }
}