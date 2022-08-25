/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trainsandtowns;

/**
 *
 * @author Paul
 */
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.LinkedList;
import java.util.List;

public class Graph {

    private List<Route>[] link;
    private List<String> listRoutes;
    private int routesCount;
    private int tripsCount;
    private int to;
    private int maxDistance;
    private int stops;

    public Graph(String inputArrOk) {
        fillLinked(GlobalValues.CHAR_LIST_INPUT.length());
        fillRoutes(inputArrOk);
    }

    private void fillRoutes(String inputArrOk) {
        String[] split = inputArrOk.split(",");
        for (String str : split) {
            str = str.trim();
            int from = getIndex(str.substring(0, 1));
            int to = getIndex(str.substring(1, 2));
            int distance = Integer.parseInt(str.substring(2, str.length()));
            Route route = new Route(from, to, distance);
            link[from].add(route);
        }
    }

    private void fillLinked(int n) {
        link = (List<Route>[]) new LinkedList[n];
        for (int i = 0; i < n; ++i) {
            link[i] = new LinkedList<>();
        }
    }

    public String getDistance(String route) {
        int distance = this.calculateDistance(route);
        return (distance != -1) ? String.valueOf(distance) : "NO SUCH ROUTE";
    }

    private int calculateDistance(String route) {
        int distance = 0;
        String[] arrRoute = route.trim().split("");
        int i = 0;
        while (i < arrRoute.length - 1) {
            boolean okRoute = false;
            int from = getIndex(arrRoute[i++]);
            int to = getIndex(arrRoute[i]);
            List<Route> routeList = link[from];
            for (Route r : routeList) {
                if (r.to() == to) {
                    distance += r.distance();
                    okRoute = true;
                    break;
                }
            }
            if (!okRoute) {
                return -1;
            }
        }
        return distance;
    }

    public int calculateTripsCount(String from, String to, Predicate<Integer> predicate, int stops) {
        this.to = getIndex(to);
        this.stops = stops;
        this.tripsCount = 0;
        int startIndex = getIndex(from);
        this.calculateTripsCount(startIndex, String.valueOf(startIndex), predicate);
        return this.tripsCount;
    }

    private void calculateTripsCount(int from, String strFrom, Predicate<Integer> predicate) {
        List<Route> routes = link[from];
        for (Route route : routes) {
            String next = strFrom + route.to();
            int stopCount = next.length() - 1;
            if (this.to == route.to() && predicate.test(stopCount)) {
                ++this.tripsCount;
            }
            if (stopCount <= this.stops) {
                this.calculateTripsCount(route.to(), next, predicate);
            }
        }
    }

    public int calculateShortRoute(String from, String to) {
        listRoutes = new ArrayList<>();
        this.to = getIndex(to);
        int startIndex = getIndex(from);
        calculateShortRoute(startIndex, String.valueOf(startIndex));
        int shortestDistance = Integer.MAX_VALUE;
        for (String str : this.listRoutes) {
            int currentDistance = calculateDistance(str);
            if (shortestDistance > currentDistance) {
                shortestDistance = currentDistance;
            }
        }
        if (shortestDistance == Integer.MAX_VALUE) {
            return 0;
        }
        return shortestDistance;
    }

    private void calculateShortRoute(int from, String strFrom) {
        List<Route> routes = link[from];
        for (Route route : routes) {
            if (strFrom.length() <= 1 || !strFrom.substring(1).contains(String.valueOf(route.to()))) {
                String next = strFrom + route.to();
                if (this.to == route.to()) {
                    this.listRoutes.add(this.getRouteString(next));
                }
                this.calculateShortRoute(route.to(), next);
            }
        }
    }

    public int calculateRoutesCount(String from, String to, int maxDistance) {
        this.to = getIndex(to);
        this.maxDistance = maxDistance;
        this.routesCount = 0;
        int startIndex = getIndex(from);
        this.calculateRoutesCount(startIndex, String.valueOf(startIndex));
        return this.routesCount;
    }

    private void calculateRoutesCount(int from, String path) {
        List<Route> routes = link[from];
        for (Route e : routes) {
            String next = path + e.to();
            int distance = this.calculateDistance(this.getRouteString(next));
            if (this.to == e.to() && distance < this.maxDistance) {
                ++this.routesCount;
            }
            if (distance < this.maxDistance) {
                this.calculateRoutesCount(e.to(), next);
            }
        }
    }

    private static int getIndex(String val) {
        return GlobalValues.CHAR_LIST_INPUT.indexOf(val);
    }

    private String getRouteString(String strFrom) {
        String[] arr = strFrom.trim().split("");
        String str = "";
        for (String ind : arr) {
            str = str + GlobalValues.CHAR_LIST_INPUT.substring(Integer.parseInt(ind), Integer.parseInt(ind) + 1);
        }
        return str;
    }
}
