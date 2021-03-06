package com.design.arabic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.Line;
import com.design.common.model.Style;
import com.design.arabic.model.DesignSupplier;
import com.design.arabic.model.Hex;
import com.design.arabic.model.Payload;
import com.design.arabic.model.TileSupplier;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Line.line;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.diagonals;
import static com.design.arabic.model.Hex.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile37 {

//    http://us.123rf.com/450wm/mehdialigol/mehdialigol1508/mehdialigol150800043/43852601-original-islamic-galaxy-geometric-pattern.jpg?ver=6

    public static double KA = 1;
    public static double KB = H;
    public static double KC = KB / 3;
    public static double KD = KC * H;
    public static double KE = 2 * KD;

    public final static FinalPointTransition A = fpt(1, UP);
    public final static FinalPointTransition A2 = fpt(1, UR_V);
    public final static FinalPointTransition B = fpt(KB, RIGHT);
    public final static FinalPointTransition C = fpt(KC, RIGHT);
    public final static FinalPointTransition C2 = fpt(KC, UR_H);
    public final static FinalPointTransition D = fpt(KD, UR_V);
    public final static FinalPointTransition E = fpt(KE, UR_V);
    public final static FinalPointTransition F = E.to(2 * KC, DR_H);
    public final static FinalPointTransition G = F.to(2 * KC, DL_H);

    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_37",
                Hex.ALL_VERTEX_INDEXES
        )
                .withSize(Payload.Size.SMALL)
                .withPathsFull(whiteBold, getFullPath())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_37_design")
                .withGrid(Grid.Configs.HEX_HOR.getConfiguration())
                .withGridRatio(KC)
                .withGridSize(16)
                .addEquations(sequence(
                        "KA = 1",
                        "KB = H",
                        "KC = KB / 3",
                        "KD = KC * H",
                        "KE = 2 * KD"
                ))
                .addImportantVertexes(Tile37.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, VER).apply(K),
                        diagonals(KA, VER).apply(K),
                        diagonals(KB, HOR).apply(K),
                        perimeter(KC, HOR).apply(K)
//                        perimeter(KD, HOR).apply(K),
//                        perimeter(KE, HOR).apply(K)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15);

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                line(C, E, C2),
                line(E, F, G)
//                line(N, L, J, G, F, I, M, P, N)
        );
    }

}