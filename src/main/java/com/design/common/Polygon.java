package com.design.common;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.lang.Math.cos;
import static java.util.stream.Collectors.toList;

public abstract class Polygon {

    public static final Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> IDENTITY = p -> p;

    public static Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform(double ratio, Polygon.Vertex vertex, Polygon.Type type) {
        return p -> Triple.of(Points.translateAndScale(p.getLeft(), p.getMiddle() * ratio).apply(vertex.getPoint(p.getRight(), type)), p.getMiddle(), p.getRight());
    }

    public static Function<Polygon, List<Point2D>> vertexes(Pair<Point2D, Double> ic) {
        return p -> p.getVertexes().stream().map(p.toPoint(Triple.of(ic.getLeft(), ic.getRight(), 0))).collect(toList());
    }

    public static Function<Pair<Polygon, List<List<Vertex>>>, List<List<Point2D>>> toLines(IntStream offsets, Pair<Point2D, Double> ic) {
        return p -> offsets.mapToObj(i -> toLines(i, ic).apply(p)).flatMap(s -> s.stream()).collect(toList());
    }

    public static Function<Pair<Polygon, List<List<Vertex>>>, List<List<Point2D>>> toLines(int offset, Pair<Point2D, Double> ic) {
        return pair -> Mappings.fromListOfLists(pair.getLeft().toPoint(Triple.of(ic.getLeft(), ic.getRight(), offset))).apply(pair.getRight());
    }

    public Function<Vertex, Point2D> toPoint(Triple<Point2D, Double, Integer> ic) {
        final Point2D centre = centreTransform.apply(ic).getLeft();
        return mapVertexToPoint(centre, ic.getMiddle(), ic.getRight());
    }

    public Function<Vertex, Point2D> mapVertexToPoint(Point2D centre, double r, int offset) {
        return v -> Points.translateAndScale(centre, r * ratio).apply(v.getPoint(offset, type));
    }

    protected abstract double getHeightRatio();

    protected abstract List<Vertex> getVertexes();

    protected abstract Polygon newInstance(double ratio, Type type, Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform);

    public Polygon getMirror() {
        return newInstance(ratio, type.revert(), centreTransform);
    }

    public Polygon getInternal() {
        return newInstance(ratio * getHeightRatio(), type.revert(), centreTransform);
    }

    public Polygon getExternal() {
        return newInstance(ratio / getHeightRatio(), type.revert(), centreTransform);
    }

    public static enum Type {
        HOR,
        VER;

        public Type revert() {
            return this == VER ? HOR : VER;
        }
    }

    public interface Vertex {
        Point2D getPoint(int offset, Polygon.Type type);

    }

    private final double ratio;
    private final Polygon.Type type;

    private final Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform;

//    public static Polygon newPolygon(double ratio, Type type) {
//        return new Polygon(ratio, type, IDENTITY);
//    }
//
//    public static Polygon newPolygon(double ratio, Type type, Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform) {
//        return new Polygon(ratio, type, centreTransform);
//    }

    public Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> getCentreTransform() {
        return centreTransform;
    }

    public double getRatio() {
        return ratio;
    }

    public Type getType() {
        return type;
    }

    public Polygon(double ratio, Type type) {
        this(ratio, type, IDENTITY);
    }

    public Polygon(double ratio, Type type, Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform) {
        this.ratio = ratio;
        this.type = type;
        this.centreTransform = centreTransform;
    }
}
