package com.homework;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final int STOP_ID = 1;
    private static final int STOP_NAME = 3;
    private static final int STOP_DATE = 4;
    private static final int STOP_LAT = 5;
    private static final int STOP_LON = 6;
    private static final int STOP_R_NR = 7;
    private static final int LEG_FROM = 1;
    private static final int LEG_TO = 2;
    private static final int LEG_DURATION = 3;
    private static final int LEG_R_NR = 4;

    private static List<Stop> stops;
    private static List<Leg> legs;


    public static Stop findStopWithIdAndNr(String id, String lineNr) {
        return stops.stream().filter(item -> item.getStopId() == Integer.parseInt(id) &&
                item.getRouteNr() == Integer.parseInt(lineNr.replace("\"", ""))).
                findFirst().get();
    }

    public static void main(String[] args) {
        System.out.println("Hello World");
        System.out.println("YOYO");


        String tramFile = "data/tram_stops.csv";
        String legFile = "data/edges.csv";
        List<String> storedData;


        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(tramFile))) {

            stops = stream.skip(1)
                    .map(line -> line.split(","))
                    .map(snippets ->
                            new Stop(
                                Integer.parseInt(snippets[STOP_ID]),
                                snippets[STOP_NAME],
                                Double.parseDouble(snippets[STOP_LAT]),
                                Double.parseDouble(snippets[STOP_LON]),
                                Integer.parseInt(snippets[STOP_R_NR].replace("\"", "")),
                                new Schedule(new ArrayList<>
                                        (Arrays.asList(snippets[STOP_DATE].replace("\"","").split(" "))).
                                        stream().map(item -> {
                                                String[] array = item.split(":");
                                                return LocalTime.parse(array[0].replace("24","00")+":"+array[1]+":"+array[2]);
                                                })
                                                .collect(Collectors.toList())
                                            )
                                    )
                        )
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }


        try (Stream<String> stream = Files.lines(Paths.get(legFile))) {
            storedData = stream.skip(1).collect(Collectors.toList());
            legs = storedData.stream()
                    .map(line -> line.split(","))
                    .map(snippets -> new Leg(findStopWithIdAndNr(snippets[LEG_FROM], snippets[LEG_R_NR]),
                            findStopWithIdAndNr(snippets[LEG_TO], snippets[LEG_R_NR]),
                            Integer.parseInt(snippets[LEG_DURATION])))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        GraphImpl graph = new GraphImpl();
/*        stops.forEach(item -> graph.addVertex(item));*/
/*        legs.forEach(item -> graph.addEdge(item.getFrom(),item.getTo(),item.getDuration()));*/

/*        for (Leg leg : legs) {
            Stop from = leg.getFrom();
            Stop to = leg.getTo();
            for (Stop stop : stops) {
                if (from.getStopId() == stop.getStopId()) {
                    stop.addLeg(leg);
                }
                if (to.getStopId() == stop.getStopId()) {
                    stop.addLeg(leg);
                }

            }
        }


        for (Stop stop : stops) {

            for (Leg leg : stop.getLegs()) {
                Stop from = leg.getFrom();
                Stop to = leg.getTo();
                if (stop.getStopId() == from.getStopId()) {
                    graph.addEdge(stop, to, leg.getDuration());
                }
                if (stop.getStopId() == to.getStopId()) {
                    graph.addEdge(from, stop, leg.getDuration());
                }

            }

        }*/



        String schedule1 = "05:50:00 06:05:00 06:08:00 06:15:00 06:20:00 06:26:00 06:30:00 06:35:00 06:40:00 06:42:00 06:51:00 06:55:00 06:58:00 07:01:00 07:10:00 07:13:00 07:15:00 07:19:00 07:27:00 07:29:00 07:34:00 07:36:00 07:44:00 07:51:00 07:52:00 07:58:00 07:59:00 08:05:00 08:10:00 08:11:00 08:18:00 08:21:00 08:24:00 08:28:00 08:31:00 08:37:00 08:41:00 08:43:00 08:44:00 08:50:00 08:52:00 08:57:00 08:59:00 09:02:00 09:03:00 09:09:00 09:11:00 09:12:00 09:16:00 09:22:00 09:23:00 09:26:00 09:31:00 09:32:00 09:34:00 09:38:00 09:40:00 09:45:00 09:49:00 09:53:00 09:56:00 09:58:00 10:00:00 10:07:00 10:08:00 10:16:00 10:18:00 10:24:00 10:25:00 10:29:00 10:32:00 10:34:00 10:39:00 10:40:00 10:43:00 10:47:00 10:51:00 10:55:00 10:59:00 11:01:00 11:03:00 11:07:00 11:11:00 11:12:00 11:15:00 11:19:00 11:22:00 11:23:00 11:27:00 11:31:00 11:32:00 11:35:00 11:39:00 11:42:00 11:43:00 11:47:00 11:51:00 11:52:00 11:55:00 11:59:00 12:02:00 12:03:00 12:07:00 12:11:00 12:12:00 12:15:00 12:19:00 12:22:00 12:23:00 12:27:00 12:31:00 12:32:00 12:35:00 12:39:00 12:42:00 12:43:00 12:47:00 12:51:00 12:52:00 12:55:00 12:59:00 13:02:00 13:03:00 13:07:00 13:11:00 13:12:00 13:15:00 13:19:00 13:22:00 13:23:00 13:27:00 13:31:00 13:32:00 13:35:00 13:39:00 13:42:00 13:43:00 13:47:00 13:51:00 13:52:00 13:55:00 13:59:00 14:02:00 14:03:00 14:07:00 14:11:00 14:12:00 14:15:00 14:19:00 14:22:00 14:23:00 14:27:00 14:31:00 14:32:00 14:35:00 14:39:00 14:42:00 14:43:00 14:46:00 14:51:00 14:52:00 14:53:00 14:59:00 15:00:00 15:02:00 15:06:00 15:07:00 15:12:00 15:13:00 15:15:00 15:19:00 15:22:00 15:23:00 15:26:00 15:31:00 15:32:00 15:39:00 15:42:00 15:45:00 15:47:00 15:52:00 15:55:00 15:59:00 16:02:00 16:03:00 16:05:00 16:11:00 16:12:00 16:18:00 16:19:00 16:22:00 16:25:00 16:27:00 16:31:00 16:32:00 16:35:00 16:38:00 16:42:00 16:43:00 16:44:00 16:51:00 16:52:00 16:57:00 17:01:00 17:02:00 17:04:00 17:10:00 17:12:00 17:17:00 17:19:00 17:21:00 17:23:00 17:28:00 17:30:00 17:31:00 17:36:00 17:37:00 17:41:00 17:43:00 17:47:00 17:49:00 17:51:00 17:56:00 18:01:00 18:02:00 18:04:00 18:09:00 18:10:00 18:11:00 18:16:00 18:18:00 18:21:00 18:22:00 18:27:00 18:29:00 18:32:00 18:36:00 18:37:00 18:43:00 18:47:00 18:50:00 18:54:00 18:57:00 19:02:00 19:05:00 19:07:00 19:10:00 19:16:00 19:17:00 19:18:00 19:27:00 19:36:00 19:37:00 19:38:00 19:45:00 19:47:00 19:49:00 19:55:00 19:57:00 20:00:00 20:04:00 20:07:00 20:11:00 20:14:00 20:17:00 20:22:00 20:23:00 20:27:00 20:33:00 20:34:00 20:37:00 20:43:00 20:46:00 20:47:00 20:53:00 20:58:00 21:03:00 21:09:00 21:11:00 21:13:00 21:22:00 21:23:00 21:33:00 21:36:00 21:41:00 21:46:00 21:50:00 21:51:00 22:00:00 22:04:00 22:16:00 22:18:00 22:31:00 22:32:00 22:47:00 23:02:00 23:05:00 23:18:00 23:25:00 23:34:00 23:45:00 23:51:00 24:04:00 24:08:00 24:24:00";

        Stop stop1 = new Stop(1,"Väike",50.0,50.0,1,new Schedule(Arrays.asList(schedule1.replace("\"","").split(" ")).
                stream().map(item -> {
            String[] array = item.split(":");
            return LocalTime.parse(array[0].replace("24","00")+":"+array[1]+":"+array[2]);
        })
                .collect(Collectors.toList())));
        String schedule2 = "05:08:00 05:22:00 05:28:00 05:38:00 05:43:00 05:48:00 05:53:00 06:03:00 06:17:00 06:47:00 07:17:00 07:42:00 07:46:00 07:51:00 07:52:00 07:56:00 08:08:00 08:17:00 08:20:00 08:56:00 09:09:00 09:10:00 09:23:00 09:24:00 13:08:00 13:09:00 13:24:00 13:27:00 14:04:00 14:17:00 16:15:00";

        Stop stop2 = new Stop(2,"Väike2",50.0,50.0,1,new Schedule(Arrays.asList(schedule2.replace("\"","").split(" ")).
                stream().map(item -> {
            String[] array = item.split(":");
            return LocalTime.parse(array[0].replace("24","00")+":"+array[1]+":"+array[2]);
        })
                .collect(Collectors.toList())));



        String schedule3 = "06:01:00 06:13:00 06:25:00 06:38:00 06:51:00 07:02:00 07:15:00 07:28:00 07:41:00 07:53:00 08:00:00 08:01:00 08:08:00 08:20:00 08:25:00 08:29:00 08:42:00 08:49:00 08:51:00 09:05:00 09:13:00 09:15:00 09:19:00 09:29:00 09:31:00 09:32:00 09:44:00 09:49:00 09:54:00 10:03:00 10:06:00 10:09:00 10:21:00 10:23:00 10:24:00 10:34:00 10:39:00 10:40:00 10:49:00 10:53:00 10:56:00 11:04:00 11:11:00 11:12:00 11:14:00 11:28:00 11:29:00 11:43:00 11:44:00 11:54:00 12:00:00 12:01:00 12:09:00 12:16:00 12:19:00 12:24:00 12:32:00 12:33:00 12:34:00 12:48:00 12:49:00 12:51:00 13:04:00 13:09:00 13:14:00 13:18:00 13:21:00 13:28:00 13:29:00 13:31:00 13:37:00 13:41:00 13:43:00 13:46:00 13:51:00 13:52:00 13:59:00 14:01:00 14:05:00 14:08:00 14:11:00 14:17:00 14:21:00 14:25:00 14:26:00 14:31:00 14:37:00 14:39:00 14:41:00 14:48:00 14:51:00 15:00:00 15:01:00 15:02:00 15:08:00 15:11:00 15:16:00 15:20:00 15:21:00 15:28:00 15:29:00 15:31:00 15:41:00 15:43:00 15:51:00 15:53:00 15:54:00 16:01:00 16:02:00 16:07:00 16:11:00 16:20:00 16:21:00 16:33:00 16:39:00 16:46:00 16:55:00 16:57:00 16:59:00 17:12:00 17:13:00 17:15:00 17:25:00 17:31:00 17:33:00 17:39:00 17:47:00 17:51:00 17:54:00 18:05:00 18:09:00 18:10:00 18:21:00 18:23:00 18:26:00 18:39:00 18:41:00 18:44:00 18:55:00 18:59:00 19:02:00 19:12:00 19:13:00 19:16:00 19:28:00 19:31:00 19:34:00 19:44:00 19:49:00 19:52:00 20:02:00 20:05:00 20:06:00 20:18:00 20:19:00 20:24:00 20:36:00 20:37:00 20:41:00 20:53:00 20:56:00 21:09:00 21:11:00 21:25:00 21:26:00 21:41:00";


        Stop stop3 = new Stop(3,"Väike3",50.0,50.0,1,new Schedule(Arrays.asList(schedule3.replace("\"","").split(" ")).
                stream().map(item -> {
            String[] array = item.split(":");
            return LocalTime.parse(array[0].replace("24","00")+":"+array[1]+":"+array[2]);
        })
                .collect(Collectors.toList())));



        String schedule4 = "05:16:00 05:31:00 05:36:00 05:47:00 05:51:00 05:56:00 06:03:00 06:06:00 06:11:00 06:16:00 06:19:00 06:31:00 06:35:00 06:41:00 06:48:00 06:51:00 06:55:00 07:05:00 07:07:00 07:09:00 07:12:00 07:19:00 07:23:00 07:25:00 07:32:00 07:38:00 07:39:00 07:41:00 07:45:00 07:51:00 07:54:00 07:55:00 07:57:00 08:04:00 08:06:00 08:07:00 08:10:00 08:16:00 08:17:00 08:19:00 08:25:00 08:26:00 08:31:00 08:38:00 08:41:00 08:43:00 08:45:00 08:51:00 08:53:00 08:55:00 09:00:00 09:01:00 09:06:00 09:08:00 09:10:00 09:16:00 09:19:00 09:24:00 09:26:00 09:28:00 09:32:00 09:36:00 09:37:00 09:40:00 09:46:00 09:48:00 09:55:00 09:56:00 10:04:00 10:06:00 10:12:00 10:16:00 10:20:00 10:26:00 10:28:00 10:36:00 10:44:00 10:46:00 10:52:00 10:56:00 11:00:00 11:06:00 11:08:00 11:16:00 11:24:00 11:26:00 11:32:00 11:36:00 11:40:00 11:46:00 11:48:00 11:56:00 12:04:00 12:06:00 12:12:00 12:16:00 12:20:00 12:26:00 12:28:00 12:36:00 12:44:00 12:46:00 12:52:00 12:56:00 13:00:00 13:06:00 13:08:00 13:16:00 13:24:00 13:26:00 13:32:00 13:36:00 13:40:00 13:46:00 13:47:00 13:48:00 13:54:00 13:56:00 14:01:00 14:04:00 14:06:00 14:07:00 14:12:00 14:13:00 14:16:00 14:20:00 14:26:00 14:27:00 14:28:00 14:34:00 14:36:00 14:44:00 14:46:00 14:51:00 14:52:00 14:56:00 14:58:00 15:00:00 15:05:00 15:06:00 15:08:00 15:11:00 15:16:00 15:18:00 15:24:00 15:26:00 15:31:00 15:32:00 15:36:00 15:38:00 15:40:00 15:44:00 15:46:00 15:48:00 15:51:00 15:56:00 15:57:00 15:58:00 16:04:00 16:06:00 16:11:00 16:15:00 16:16:00 16:17:00 16:24:00 16:26:00 16:30:00 16:33:00 16:36:00 16:37:00 16:42:00 16:43:00 16:46:00 16:50:00 16:51:00 16:56:00 16:57:00 17:00:00 17:03:00 17:07:00 17:09:00 17:16:00 17:18:00 17:22:00 17:27:00 17:29:00 17:36:00 17:40:00 17:43:00 17:46:00 17:50:00 17:51:00 17:56:00 17:57:00 18:03:00 18:05:00 18:06:00 18:13:00 18:14:00 18:16:00 18:22:00 18:25:00 18:26:00 18:31:00 18:36:00 18:40:00 18:46:00 18:47:00 18:49:00 18:56:00 18:58:00 19:06:00 19:08:00 19:10:00 19:16:00 19:18:00 19:22:00 19:26:00 19:28:00 19:34:00 19:36:00 19:38:00 19:46:00 19:47:00 19:48:00 19:58:00 19:59:00 20:08:00 20:10:00 20:11:00 20:18:00 20:22:00 20:23:00 20:28:00 20:34:00 20:38:00 20:46:00 20:47:00 20:48:00 21:01:00 21:03:00 21:17:00 21:19:00 21:33:00 21:35:00 21:49:00 21:51:00 22:05:00 22:11:00 22:22:00 22:31:00 22:39:00 22:51:00 22:56:00 23:11:00 23:13:00 23:31:00 23:51:00 24:11:00";


        Stop stop4 = new Stop(4,"Väike4",50.0,50.0,2,new Schedule(Arrays.asList(schedule4.replace("\"","").split(" ")).
                stream().map(item -> {
            String[] array = item.split(":");
            return LocalTime.parse(array[0].replace("24","00")+":"+array[1]+":"+array[2]);
        })
                .collect(Collectors.toList())));





        Leg leg1 = new Leg(stop1,stop2,2);
        Leg leg2 = new Leg(stop2,stop4,3);
        Leg leg3 = new Leg(stop1,stop3,3);
        Leg leg4 = new Leg(stop3,stop4,4);

        // 1->2 4min 2->4 7min
        // 1->3 5min 3->4 9min


        stop1.addLeg(leg1);
        stop1.addLeg(leg3);

        stop2.addLeg(leg2);

        stop3.addLeg(leg3);
        stop3.addLeg(leg4);

        stop4.addLeg(leg2);
        stop4.addLeg(leg4);


        graph.addVertex(stop1);
        graph.addVertex(stop2);
        graph.addVertex(stop3);
        graph.addVertex(stop4);

        List<Stop> stopsT = new ArrayList<>(Arrays.asList(stop1,stop2,stop3,stop4));
        List<Leg> legsT = new ArrayList<>(Arrays.asList(leg1,leg2,leg3));


        for (Stop stop : stopsT) {
            for (Leg leg : stop.getLegs()) {
                Stop from = leg.getFrom();
                Stop to = leg.getTo();
                if (stop.getStopId() == from.getStopId()) {
                    graph.addEdge(stop, to, leg.getDuration());
                }
/*                if (stop.getStopId() == to.getStopId()) {
                    graph.addEdge(from, stop, leg.getDuration());
                }*/

            }
        }
        Dijkstra path = new Dijkstra();

        Map<Stop, Path> paths = path.getShortestPathsFromVertex(graph, stop1, LocalTime.parse("14:00:00"));
/*        System.out.println(paths);*/
        System.out.println(paths.get(stop4));



/*        for (int i = 0; i <stops.size(); i++) {
            System.out.println("----------------------");
            System.out.println(stops.get(i).getLegs() + "|||" + stops.get(i));
        }*/

/*        System.out.println(stops.stream().filter(i -> i.getStopId()==1078).collect(Collectors.toList()));*/

/*

        Dijkstra path = new Dijkstra();
 */
/*       System.out.println(stops.get(0));

        Map<Stop, Path> paths = path.getShortestPathsFromVertex(graph, stops.get(0));
        System.out.println(stops.get(60));
        System.out.println(paths.get(stops.get(60)));*//*


        List<LocalTime> times = new ArrayList<>(Arrays.asList(LocalTime.parse("18:15:30"),LocalTime.parse("19:15:30"),LocalTime.parse("20:15:30")));
        LocalTime date5 = LocalTime.parse("20:15:10");
        System.out.println(times.stream().filter(item -> item.isAfter(LocalTime.parse("18:00:30"))).findFirst().get());

        System.out.println("date5: " + date5);


        System.out.println(stops.get(0).getSchedule());

*/

/*        System.out.println(stops);


        System.out.println(paths);*/


/*        System.out.println(legs);*/
    }
}














