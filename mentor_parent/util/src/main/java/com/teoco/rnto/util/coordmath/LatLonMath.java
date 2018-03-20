package com.teoco.rnto.util.coordmath;

/**
 * Created by guptaam on 10/10/2014.
 */
public class LatLonMath {
    /***
     * return distance in meters
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2) {

        return Math.round(Math.acos(Math.cos(radians(90-lat1)) * Math.cos(radians(90-lat2)) +
                Math.sin(radians(90-lat1)) *Math.sin(radians(90-lat2)) *Math.cos(radians(lon1-lon2))) *6371000);

    }

    /***
     * This function converts decimal degrees to radians
     * @param deg
     * @return
     */
    private static double radians(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /***
     * his function converts radians to decimal degrees
     * @param rad
     * @return
     */
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    /***
     * Returns distance in miles
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public static double distanceInMiles(double lat1, double lon1, double lat2, double lon2) {
        return 0.000621371 * distance(lat1, lon1, lat2, lon2);
    }

    /**
     * Java implementation of Ray-casting algorithm which determines whether a point is inside a polygon.
     *
     * @see <a href="http://rosettacode.org/wiki/Ray-casting_algorithm">http://rosettacode.org/wiki/Ray-casting_algorithm</a>
     * @see <a href="http://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html">http://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html</a>
     *
     * @param latitude  Latitude of the point
     * @param longitude Longitude of the point
     * @param lats      Array of latitudes of the vertices of the polygon
     * @param longs     Array of longitudes of the vertices of the polygon
     * @return true if the point is inside polygon, false otherwise
     */
    public static boolean isCoordinateInsidePolygon(double latitude, double longitude,
                                                    double[] lats, double[] longs) {
        int i, j, noVertices = lats.length;
        boolean c = false;

        for (i = 0, j = noVertices - 1; i < noVertices; j = i++) {
            if (((longs[i] >= longitude) != (longs[j] >= longitude)) &&
                    (latitude <= (lats[j] - lats[i]) * (longitude - longs[i]) / (longs[j] - longs[i]) + lats[i])
                    )
                c = !c;
        }

        return c;
    }
}
