package com.areyes403.practica.data.utils;

public class Utils {
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double lat1rad = Math.toRadians(lat1);
        double lon1rad = Math.toRadians(lon1);
        double lat2rad = Math.toRadians(lat2);
        double lon2rad = Math.toRadians(lon2);

        double difLatitud = lat1rad - lat2rad;
        double difLongitud = lon1rad - lon2rad;

        double a = Math.pow(Math.sin(difLatitud/2), 2) +
                Math.cos(lat1rad) *
                        Math.cos(lat2rad) *
                        Math.pow(Math.sin(difLongitud/2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double radioTierraKm = 6378.0;
        double distancia = radioTierraKm * c;

        return distancia;
    }
}
