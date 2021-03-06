package com.design.arabic.model.hex;

import com.design.common.*;
import com.design.common.model.Style;
import com.design.arabic.model.DesignSupplier;
import com.design.arabic.model.Hex;
import com.design.arabic.model.Payload;
import com.design.arabic.model.TileSupplier;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.PointTransition.pt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.diagonals;
import static com.design.arabic.model.Hex.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.numbers.Integers.range;

//p. 100
public class Tile21 {

    private static double RATIO_m = 1.0 / 6.0;
    private static double RATIO_n = 1.0 / 16.0;
    private static double KA = RATIO_m;
    private static double KB = RATIO_n;

    public final static FinalPointTransition A = fpt(KA, UL_H);
    public final static FinalPointTransition B = fpt(KB, UL_H);
    public final static FinalPointTransition I1 = fpt(KA, RIGHT);
    public final static FinalPointTransition I2 = fpt(3 * KA, RIGHT);
    public final static FinalPointTransition I3 = fpt(KA, UR_H);
    public final static FinalPointTransition I4 = fpt(3 * KA, UR_H);
    public final static FinalPointTransition I5 = I1.to(2 * KA, UR_H);
    public final static FinalPointTransition I6 = I3.to(2 * KA, RIGHT);
    public final static FinalPointTransition I7 = I6.to(KA, UR_H);
    public final static FinalPointTransition L1 = I4.to(5 * KB, RIGHT);
    public final static FinalPointTransition L2 = L1.to(2 * KB, UL_H);
    public final static FinalPointTransition L3 = L2.to(2 * KB, LEFT);
    public final static FinalPointTransition L4 = L3.to(5 * KB, UR_H);
    public final static FinalPointTransition L5 = L2.to(2 * KB, UR_H);
    public final static FinalPointTransition L6 = L5.to(9 * KB, DR_H);
    public final static FinalPointTransition L7 = L6.to(2 * KB, LEFT);
    public final static FinalPointTransition L8 = L7.to(2 * KB, UL_H);
    public final static FinalPointTransition L9 = L7.to(2 * KB, DL_H);
    public final static FinalPointTransition L10 = L9.to(5 * KB, RIGHT);


    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_21",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .withSize(Payload.Size.MEDIUM)
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();

    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_21_design")
                .addEquations(sequence(
                        "KA = 1 / 6",
                        "KB = 1 / 16"
                ))
                .withGrid(Grid.Configs.HEX_HOR.getConfiguration())
                .withGridRatio(KA)
                .withGridSize(12)
                .addImportantVertexes(Tile21.class)
                .addImportantVertexes(
                        range(1, 6).flatMap(i -> sequence(
                                DesignHelper.ImportantVertex.of(String.valueOf(i), pt(i * KA, RIGHT)),
                                DesignHelper.ImportantVertex.of(String.valueOf(i), pt(i * KA, UR_H)),
                                DesignHelper.ImportantVertex.of(String.valueOf(i), pt(i * KA, DR_H))
                        ))
                )
                .addSinglePathsLines(blue,
                        range(1, 6).flatMap(i ->
                                perimeter(i * KA, HOR).apply(K))
                )
                .addSinglePathsLines(gray,
                        range(1, 16).flatMap(i ->
                                perimeter(i * KB, HOR).apply(K))
                )
                .addSinglePathsLines(gray,
                        perimeter(1, HOR).apply(K),
                        diagonals(1, HOR).apply(K),
                        diagonals(RatioHelper.P6.H, VER).apply(K)

                )
                .addFullPaths(red, getFullPath())
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(I1, I5, I7, I6, I3),
                Line.line(I6, I2, L8, L7, L6, L5, L2, L3, L4),
                Line.line(I5, I4, L1, L2),
                Line.line(L7, L9, L10)
        );
    }


}