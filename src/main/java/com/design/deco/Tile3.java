package com.design.deco;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.Line;
import com.design.common.model.Style;
import com.design.arabic.model.*;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.arabic.model.Rect.Vertex.*;
import static com.design.arabic.model.Rect.*;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile3 {

    private static final double KA = 1.0;
    private static final double AB = KA / 4.0;
    private static final double KB = KA - AB;

    public final static FinalPointTransition A = fpt(KA, DR);
    public final static FinalPointTransition B = fpt(KB, DR);
    public final static FinalPointTransition C = fpt(KA, UR);
    public final static FinalPointTransition D = fpt(KB, UR);


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();


        return new Payload.Builder("deco_tile_03",
                Rect.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style blue = new Style.Builder(Color.BLUE, 1).build();

        return new DesignHelper(Rect.ALL_VERTEX_INDEXES, "deco_tile_03_design")
                .addFullPaths(red, getFullPath())
                .addEquations(
                        "AB = KA / 4.0"
                )
                .addImportantVertexes(Tile3.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K),
                        perimeter(KB, HOR).apply(K),
                        perimeter(AB, HOR).apply(A)
                )
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(A, B, D, C)
        );
    }


}