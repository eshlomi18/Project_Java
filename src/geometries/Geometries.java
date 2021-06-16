package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/* composite class for all geometries
 */
public class Geometries extends Intersectable {
    private List<Intersectable> intersectables = new LinkedList<>();

    public Geometries() {
        //Initial the max/min values in order that the first geometries
        //will give the right value
        this.ResetBox();
    }

    /**
     * constructor
     * @param list  of Intersectable gemotries
     */
    public  Geometries(Intersectable...list){
        this();
        add(list);
    }

    /**
     * add geometries to the list
     * @param list The updated new list
     */
    public void add(Intersectable... list) {
        Collections.addAll(intersectables, list);
        for (Intersectable geo : list) {
            intersectables.add(geo);
            if (geo.box._max_X > this.box._max_X)
                this.box._max_X = geo.box._max_X;
            if (geo.box._min_X < this.box._min_X)
                this.box._min_X = geo.box._min_X;
            if (geo.box._max_Y > this.box._max_Y)
                this.box._max_Y = geo.box._max_Y;
            if (geo.box._min_Y < this.box._min_Y)
                this.box._min_Y = geo.box._min_Y;
            if (geo.box._max_Z > this.box._max_Z)
                this.box._max_Z = geo.box._max_Z;
            if (geo.box._min_Z < this.box._min_Z)
                this.box._min_Z = geo.box._min_Z;
        }
    }

    /**
     * find geo points intersections of ray and geometries
     * @param ray The cutting ray
     * @param maxDistance the distance from the light
     * @return list of GeoPoint intersections
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        List<GeoPoint> result = null;
        for (Intersectable geo : intersectables) {
            List<GeoPoint> geoPoints = geo.findGeoIntersections(ray, maxDistance);
            if (geoPoints != null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(geoPoints);
            }
        }
        return result;
    }

    /**
     * main function for bounding tree building .
     * Act the recursion depthOf TreeRec.
     * The infinity geometries is not include in the recursion and go
     * by the end to the root of the tree
     *
     * @param depthOfTree the depth of recursion
     */
    public void createTree(int depthOfTree) {

        Intersectable infinityGeometries = new Geometries();
        Intersectable finiteGeometries = new Geometries();

        //Remove all the infinity geometries before sending for recursion
        for (Intersectable geo : this.intersectables) {
            if (geo.getBox()._max_X == Double.POSITIVE_INFINITY ||
                    geo.getBox()._max_Y == Double.POSITIVE_INFINITY ||
                    geo.getBox()._max_Z == Double.POSITIVE_INFINITY ||
                    geo.getBox()._min_X == Double.NEGATIVE_INFINITY ||
                    geo.getBox()._min_Y == Double.NEGATIVE_INFINITY ||
                    geo.getBox()._min_Z == Double.NEGATIVE_INFINITY)
                ((Geometries) infinityGeometries).add(geo);
            else
                ((Geometries) finiteGeometries).add(geo);
        }

        this.intersectables.clear();
        this.ResetBox();

        for (Intersectable geo : ((Geometries) finiteGeometries).intersectables)
            this.add(geo);

        this.createTreeRec(depthOfTree);

        for (Intersectable geo : ((Geometries) infinityGeometries).intersectables)
            this.add(geo);
    }

    /**
     * Recursive function for bounding tree building. Build an octree of voxels
     * and send to the next stage of recursion any voxel contains more than
     * ong geometries.
     * The idea of octree hierarchy was taken from:
     * https://www.scratchapixel.com/lessons/advanced-rendering/introduction-acceleration-structure/bounding-volume-hierarchy-BVH-part2
     *
     * @param depthOfTree the depth of recursion
     */
    public void createTreeRec(int depthOfTree) {

        Intersectable topRightCloseVoxel = null;
        Intersectable topLeftCloseVoxel = null;
        Intersectable downRightCloseVoxel = null;
        Intersectable downLeftCloseVoxel = null;
        Intersectable topRightFarVoxel = null;
        Intersectable topLeftFarVoxel = null;
        Intersectable downRightFarVoxel = null;
        Intersectable downLeftFarVoxel = null;

        //Insert any Geometries in the Geometries t the right voxel
        for (int i = 0; i < intersectables.size(); i++) {
            if (intersectables.get(i).getMiddleZ() < this.getMiddleZ()) {
                if (intersectables.get(i).getMiddleY() < this.getMiddleY()) {
                    if (intersectables.get(i).getMiddleX() > this.getMiddleX()) {
                        if (topRightCloseVoxel == null)
                            topRightCloseVoxel = new Geometries();
                        ((Geometries) topRightCloseVoxel).add(intersectables.get(i));
                    } else {
                        if (topLeftCloseVoxel == null)
                            topLeftCloseVoxel = new Geometries();
                        ((Geometries) topLeftCloseVoxel).add(intersectables.get(i));
                    }
                } else {
                    if (intersectables.get(i).getMiddleX() > this.getMiddleX()) {
                        if (downRightCloseVoxel == null)
                            downRightCloseVoxel = new Geometries();
                        ((Geometries) downRightCloseVoxel).add(intersectables.get(i));
                    } else {
                        if (downLeftCloseVoxel == null)
                            downLeftCloseVoxel = new Geometries();
                        ((Geometries) downLeftCloseVoxel).add(intersectables.get(i));
                    }
                }
            } else {
                if (intersectables.get(i).getMiddleY() < this.getMiddleY()) {
                    if (intersectables.get(i).getMiddleX() > this.getMiddleX()) {
                        if (topRightFarVoxel == null)
                            topRightFarVoxel = new Geometries();
                        ((Geometries) topRightFarVoxel).add(intersectables.get(i));
                    } else {
                        if (topLeftFarVoxel == null)
                            topLeftFarVoxel = new Geometries();
                        ((Geometries) topLeftFarVoxel).add(intersectables.get(i));
                    }
                } else {
                    if (intersectables.get(i).getMiddleX() > this.getMiddleX()) {
                        if (downRightFarVoxel == null)
                            downRightFarVoxel = new Geometries();
                        ((Geometries) downRightFarVoxel).add(intersectables.get(i));
                    } else {
                        if (downLeftFarVoxel == null)
                            downLeftFarVoxel = new Geometries();
                        ((Geometries) downLeftFarVoxel).add(intersectables.get(i));
                    }
                }
            }
        }

        intersectables.clear();

        //check for each voxel if it contain more than one geometries
        if (topRightCloseVoxel != null) {
            if (((Geometries) topRightCloseVoxel).intersectables.size() == 1)
                intersectables.add(((Geometries) topRightCloseVoxel).intersectables.get(0));
            else {
                if (depthOfTree > 1)
                    ((Geometries) topRightCloseVoxel).createTreeRec(depthOfTree - 1);
                intersectables.add(topRightCloseVoxel);
            }
        }

        if (topLeftCloseVoxel != null) {
            if (((Geometries) topLeftCloseVoxel).intersectables.size() == 1)
                intersectables.add(((Geometries) topLeftCloseVoxel).intersectables.get(0));
            else {
                if (depthOfTree > 1)
                    ((Geometries) topLeftCloseVoxel).createTreeRec(depthOfTree - 1);
                intersectables.add(topLeftCloseVoxel);
            }
        }

        if (downRightCloseVoxel != null) {
            if (((Geometries) downRightCloseVoxel).intersectables.size() == 1)
                intersectables.add(((Geometries) downRightCloseVoxel).intersectables.get(0));
            else {
                if (depthOfTree > 1)
                    ((Geometries) downRightCloseVoxel).createTreeRec(depthOfTree - 1);
                intersectables.add(downRightCloseVoxel);
            }
        }

        if (downLeftCloseVoxel != null) {
            if (((Geometries) downLeftCloseVoxel).intersectables.size() == 1)
                intersectables.add(((Geometries) downLeftCloseVoxel).intersectables.get(0));
            else {
                if (depthOfTree > 1)
                    ((Geometries) downLeftCloseVoxel).createTreeRec(depthOfTree - 1);
                intersectables.add(downLeftCloseVoxel);
            }
        }

        if (topRightFarVoxel != null) {
            if (((Geometries) topRightFarVoxel).intersectables.size() == 1)
                intersectables.add(((Geometries) topRightFarVoxel).intersectables.get(0));
            else {
                if (depthOfTree > 1)
                    ((Geometries) topRightFarVoxel).createTreeRec(depthOfTree - 1);
                intersectables.add(topRightFarVoxel);
            }
        }

        if (topLeftFarVoxel != null) {
            if (((Geometries) topLeftFarVoxel).intersectables.size() == 1)
                intersectables.add(((Geometries) topLeftFarVoxel).intersectables.get(0));
            else {
                if (depthOfTree > 1)
                    ((Geometries) topLeftFarVoxel).createTreeRec(depthOfTree - 1);
                intersectables.add(topLeftFarVoxel);
            }
        }

        if (downRightFarVoxel != null) {
            if (((Geometries) downRightFarVoxel).intersectables.size() == 1)
                intersectables.add(((Geometries) downRightFarVoxel).intersectables.get(0));
            else {
                if (depthOfTree > 1)
                    ((Geometries) downRightFarVoxel).createTreeRec(depthOfTree - 1);
                intersectables.add(downRightFarVoxel);
            }
        }

        if (downLeftFarVoxel != null) {
            if (((Geometries) downLeftFarVoxel).intersectables.size() == 1)
                intersectables.add(((Geometries) downLeftFarVoxel).intersectables.get(0));
            else {
                if (depthOfTree > 1)
                    ((Geometries) downLeftFarVoxel).createTreeRec(depthOfTree - 1);
                intersectables.add(downLeftFarVoxel);
            }
        }
    }
}


