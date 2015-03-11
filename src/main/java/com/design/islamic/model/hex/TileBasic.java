package com.design.islamic.model.hex;

import com.design.common.Polygon;
import com.design.islamic.model.Hex;
import com.design.islamic.model.Payload;
import com.design.islamic.model.Payloads;
import com.design.islamic.model.Tile;
import com.design.islamic.model.tiles.Grid;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public abstract class TileBasic implements Tile {

    protected final Pair<Point2D, Double> initialConditions;
    protected Function<List<Pair<Polygon, Polygon.Vertex>>, List<List<Point2D>>> toMixVertexesFull;
    protected Function<Pair<Polygon, List<List<Polygon.Vertex>>>, List<List<Point2D>>> toLines;
    protected Function<Pair<Polygon, List<List<Polygon.Vertex>>>, List<List<Point2D>>> toLinesFull;
    protected Function<Polygon, List<Point2D>> toVertexes;
    protected Function<Pair<Polygon, Polygon.Vertex>, List<Point2D>> toVertexesFull;
    protected Function<Pair<Polygon, Polygon.Vertex>, Point2D> toVertex;
    protected Function<Polygon, List<Pair<Point2D, Double>>> toCircles;
    protected Function<Triple<Polygon, ? extends Polygon.Vertex, String>, Pair<Point2D, String>> importantPoint;

    protected static Function<Polygon, List<Triple<Polygon, Polygon.Vertex, String>>> allHexVertexesAsImportant =
            polygon -> asList(
                    Triple.of(polygon, Hex.Vertex.ONE, "1"),
                    Triple.of(polygon, Hex.Vertex.TWO, "2"),
                    Triple.of(polygon, Hex.Vertex.THREE, "3"),
                    Triple.of(polygon, Hex.Vertex.FOUR, "4"),
                    Triple.of(polygon, Hex.Vertex.FIVE, "5"),
                    Triple.of(polygon, Hex.Vertex.SIX, "6")
            );

    protected TileBasic(Pair<Point2D, Double> initialConditions) {
        this.initialConditions = initialConditions;

        List<Integer> allVertexIndexes = IntStream.range(0, 6).boxed().collect(toList());
        toLines = Polygon.toLines(0, initialConditions);
        toLinesFull = Polygon.toLines(allVertexIndexes, initialConditions);
        toVertexes = Polygon.vertexes(initialConditions);
        toVertexesFull = Polygon.vertexesFull(allVertexIndexes, initialConditions);
        toMixVertexesFull = Polygon.mixVertexesFull(allVertexIndexes, initialConditions);
        toCircles = Polygon.toCircles(initialConditions);
        toVertex = Polygon.vertex(initialConditions);
        importantPoint = t -> Pair.of(toVertex.apply(Pair.of(t.getLeft(), t.getMiddle())), t.getRight());
    }

    protected Stream<Pair<Polygon, List<List<Polygon.Vertex>>>> getMainLinesFull() {
        return Stream.empty();
    }

    protected Stream<Pair<Polygon, List<List<Polygon.Vertex>>>> getMainLinesSingle() {
        return Stream.empty();
    }

    protected Stream<Pair<Polygon, List<List<Polygon.Vertex>>>> getSecondaryLinesSingle() {
        return Stream.empty();
    }

    protected Stream<List<Pair<Polygon, Polygon.Vertex>>> getMainMixVertexesFull() {
        return Stream.empty();
    }

    protected static Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform(double ratio, Polygon.Type type) {
        return Polygon.centreTransform(ratio, Hex.Vertex.ONE, type);
    }

    protected Grid.Configuration getGridConfiguration() {
        return Grid.Configs.HEX_HOR2.getConfiguration();
    }

    @Override
    public Payload getPayload() {

        return Payloads.payload()
                .lines(
                        Stream.of(
                                getMainLinesFull().map(toLinesFull),
                                getMainLinesSingle().map(toLines),
                                getMainMixVertexesFull().map(toMixVertexesFull)
                        ).flatMap(s -> s).map(Collection::stream).flatMap(s -> s).collect(toList())

                ).secondaryLines(
                        Stream.of(
                                getSecondaryLinesSingle().map(toLines)
                        ).flatMap(s -> s).map(Collection::stream).flatMap(s -> s).collect(Collectors.toList())

                ).gridConfiguration(getGridConfiguration());
    }
}