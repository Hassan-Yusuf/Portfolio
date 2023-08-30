package com.company;

public class MapCoordinate implements Comparable {
    public final double LATITUDE,LONGITUDE,ALTITUDE;

    public MapCoordinate(double latitude, double longitude, double altitude) {
        LATITUDE = latitude;
        LONGITUDE = longitude;
        ALTITUDE = altitude;
    }

    public double distanceTo(MapCoordinate coordinate){
        final int R = 6371; // this is the radius of the earth
        Double lat1 = this.LATITUDE;
        Double lon1 = this.LONGITUDE;
        Double lat2 = coordinate.LATITUDE;
        Double lon2 = coordinate.LONGITUDE;
        Double latDistance = toRad(lat2-lat1);
        Double lonDistance = toRad(lon2-lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        Double distance = R * c;
        return distance;

    }

    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapCoordinate that = (MapCoordinate) o;
        return Double.compare(that.LATITUDE, LATITUDE) == 0 &&
                Double.compare(that.LONGITUDE, LONGITUDE) == 0 &&
                Double.compare(that.ALTITUDE, ALTITUDE) == 0;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "MapCoordinate{" +
                "LATITUDE=" + LATITUDE +
                ", LONGITUDE=" + LONGITUDE +
                ", ALTITUDE=" + ALTITUDE +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof MapCoordinate){
            MapCoordinate coordinate = (MapCoordinate) o;
            if (this.ALTITUDE ==coordinate.ALTITUDE){
                if (this.LATITUDE ==coordinate.LATITUDE){
                    if (this.LONGITUDE ==coordinate.LONGITUDE){
                        return 0;
                    }
                    else{
                        return (int) (this.LONGITUDE-coordinate.LONGITUDE);
                    }
                }
                else{
                    return (int) (this.LATITUDE-coordinate.LATITUDE);
                }
            }
            else{
                return (int) (this.ALTITUDE-coordinate.ALTITUDE);
            }
        }
        else{
            System.out.println("NOT object");
            return -10000000;
        }
    }
}
