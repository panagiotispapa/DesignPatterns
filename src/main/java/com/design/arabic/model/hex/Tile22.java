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
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.PointTransition.pt;
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.*;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.numbers.Integers.range;

//p. 101
public class Tile22 {


    private static double RATIO_x = 1.0 / 16.0;
    private static double RATIO_y = RATIO_x / H;

    private static double KA = RATIO_y;
    private static double KB = RATIO_x;

    public final static FinalPointTransition A = fpt(KA, UL_V);
    public final static FinalPointTransition B = fpt(KB, LEFT);
    public final static FinalPointTransition I1 = fpt(2 * KB, RIGHT);
    public final static FinalPointTransition I2 = fpt(3 * KB, UR_H);
    public final static FinalPointTransition I3 = fpt(ur(3), ul(1));
    public final static FinalPointTransition P1 = fpt(ur(3), ul(4));
    public final static FinalPointTransition I4 = fpt(ur(4), ul(1));
    public final static FinalPointTransition P2 = fpt(ur(4), ul(5));
    public final static FinalPointTransition I5 = fpt(ur(5), ul(1));
    public final static FinalPointTransition P4 = fpt(ur(5), ul(8));
    public final static FinalPointTransition I6 = fpt(ur(6), ul(1));
    public final static FinalPointTransition P3 = fpt(ur(6), ul(9));
    public final static FinalPointTransition I7 = fpt(ur(7), ul(3));
    public final static FinalPointTransition I11 = fpt(ur(7), ul(5));
    public final static FinalPointTransition I8 = fpt(ur(8), ul(4));
    public final static FinalPointTransition I12 = fpt(ur(8), ul(5));
    public final static FinalPointTransition I13 = fpt(ur(8), ul(3));
    public final static FinalPointTransition I9 = fpt(ur(9), ul(5));
    public final static FinalPointTransition I10 = fpt(ur(9), ul(3));
    public final static FinalPointTransition L1 = fpt(ur(9), ul(2));
    public final static FinalPointTransition L2 = fpt(ur(9), ul(0.5));
    public final static FinalPointTransition L3 = fpt(ur(10), ul(7));
    public final static FinalPointTransition L4 = fpt(ur(10), ul(2));
    public final static FinalPointTransition L5 = fpt(ur(10), pt(KA, DR_V));
    public final static FinalPointTransition U4 = L5.to(ur(1.5));

    public final static FinalPointTransition P5 = fpt(ur(6), pt(KA, DR_V));
    public final static FinalPointTransition P9 = fpt(ur(9), pt(KA, DR_V));
    public final static FinalPointTransition U5 = P9.to(KA, DOWN);
    public final static FinalPointTransition P6 = fpt(ur(8));
    public final static FinalPointTransition P7 = P6.to(0.5 * KA, DR_V);
    public final static FinalPointTransition P8 = P6.to(0.5 * KA, DL_V);
    public final static FinalPointTransition U2 = P8.to(0.5 * KA, UL_V);
    public final static FinalPointTransition U3 = P6.to(0.5 * KA, UP);
    public final static FinalPointTransition P10 = P7.to(0.5 * KA, DOWN);
    public final static FinalPointTransition L6 = fpt(ur(11), ul(7));
    public final static FinalPointTransition L7 = fpt(ur(11), ul(2));
    public final static FinalPointTransition L8 = fpt(ur(11));
    public final static FinalPointTransition L9 = fpt(ur(12), ul(7));
    public final static FinalPointTransition L10 = fpt(ur(12), ul(3));
    public final static FinalPointTransition L11 = fpt(ur(12), ul(1));
    public final static FinalPointTransition L12 = L11.to(ur(0.5));
    public final static FinalPointTransition L20 = L10.to(ur(1.5));
    public final static FinalPointTransition L13 = fpt(ur(13), ul(7));
    public final static FinalPointTransition L14 = fpt(ur(13), ul(4));
    public final static FinalPointTransition L19 = fpt(ur(13), ul(2));
    public final static FinalPointTransition L15 = fpt(ur(14), ul(7));
    public final static FinalPointTransition L16 = fpt(ur(14), ul(5));
    public final static FinalPointTransition L18 = fpt(ur(14), ul(4));
    public final static FinalPointTransition L17 = L16.to(ur(0.5));
    ;

    private static PointTransition ur(double times) {
        return pt(times * KA, UR_V);
    }

    private static PointTransition down(double times) {
        return pt(times * KA, DOWN);
    }

    private static PointTransition ul(double times) {
        return pt(times * KA, UL_V);
    }


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_22",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
//                .withGridConf(Grid.Configuration.customRect(2*RATIO_w, 2*RATIO_h))
                .withSize(Payload.Size.LARGE)
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();

    }


    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_22_design")
                .addEquations(sequence(
                        "KA = 1/9"
                ))
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .withGridRatio(KA)
                .withGridSize(32)
                .addImportantVertexes(Tile22.class)
                .addImportantVertexes(
                        range(1, 15).flatMap(i -> sequence(
                                DesignHelper.ImportantVertex.of(String.valueOf(i), pt(i * KB, RIGHT)),
                                DesignHelper.ImportantVertex.of(String.valueOf(i), pt(i * KA, UP)),
                                DesignHelper.ImportantVertex.of(String.valueOf(i), pt(i * KA, DOWN)),
                                DesignHelper.ImportantVertex.of(String.valueOf(i), pt(i * KA, UR_V))
                        ))
                )
                .addSinglePathsLines(gray,
//                        IntStream.rangeClosed(1, 15).mapToObj(i ->
//                                perimeter(i * KA, VER).apply(K)).flatMap(s -> s),
                        perimeter(1, HOR).apply(K),
                        diagonals(1, HOR).apply(K),
                        diagonals(H, VER).apply(K)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(12)
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(I1, I3, P1),
                Line.line(I4, P2),
                Line.line(I6, P3),
                Line.line(I5, P4),
                Line.line(fpt(ur(6)), P5, P10, P7, P6, P8, U2, L1, L4),
                Line.line(P6, U3, L2, P9, U5),
                Line.line(I10, I9, I7, I11),
                Line.line(I12, I13),
                Line.line(L3, L5, U4),
                Line.line(L7, L19),
                Line.line(L9, L10, L20),
                Line.line(L13, L14, L18),
                Line.line(L15, L16, L17),
                Line.line(fpt(ur(7), down(2)), fpt(ur(13), down(2))),
                Line.line(fpt(ur(8), down(3)), fpt(ur(13), down(3), ur(0.5))),
                Line.line(fpt(ur(11), down(4)), fpt(ur(14), down(4))),
                Line.line(fpt(ur(12), down(5)), fpt(ur(14), down(5), ur(0.5))),
                Line.line(fpt(ur(13), down(6)), fpt(ur(15), down(6))),
                Line.line(L12, L11, fpt(ur(12)), fpt(ur(11)), L6)
        );
    }


}