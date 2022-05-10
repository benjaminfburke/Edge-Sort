import java.util.ArrayList;

public class GlobalNet
{
    //creates a global network 
    //O : the original graph
    //regions: the regional graphs
    public static Graph run(Graph O, Graph[] regions) 
    {
	    //To be implemented
        Graph graph = new Graph(O.V());
        graph.setCodes(O.getCodes());

        ArrayList<Edge> edges = new ArrayList<>();

        for (int i = 0; i < regions.length - 1; i++) {
            for (int j = i + 1; j < regions.length; j++) {
                edges.addAll(dijkstra(O, regions[i], regions[j]));
            }
        }

        for (Edge e : edges) {
            graph.addEdge(e);
        }

        return graph;
    }

    private static ArrayList<Edge> dijkstra(Graph G, Graph reg1, Graph reg2) {
        Graph graph = new Graph(G.V());
        graph.setCodes(G.getCodes());

        int vertices = G.V();
        DistQueue distQueue = new DistQueue(vertices);

        int[] start = new int[reg1.V()];
        int[] destination = new int[reg2.V()];
        int[] distance = new int[vertices];
        int[] previous = new int[vertices];

        int []path = new int[vertices];
        int n;
        int p = 0;
        int number = Integer.MAX_VALUE;

        for (int i = 0; i < vertices; i++) {
            distance[i] = Integer.MAX_VALUE;
            previous[i] = -1;
            distQueue.insert(i, distance[i]);
        }

        for (int j = 0; j < reg1.V(); j++) {
            start[j] = G.index(reg1.getCode(j));
            distance[start[j]] = 0;
            distQueue.set(start[j], 0);
        }

        for (int x = 0; x < reg2.V(); x++) {
            destination[x] = G.index(reg2.getCode(x));
        }

        while (!(distQueue.isEmpty())) {
            int num = distQueue.delMin();
            ArrayList<Integer> adjacent = G.adj(num);
            for (int y = 0; y < adjacent.size(); y++) {
                if (distQueue.inQueue(adjacent.get(y))) {
                    int alt;
                    if (previous[num] != -1) {
                        alt = distance[num] + G.getEdgeWeight(num, adjacent.get(y));
                    }
                    else {
                        alt = G.getEdgeWeight(num, adjacent.get(y));
                    }
                    if (alt < distance[G.adj(num).get(y)]) {
                        distance[adjacent.get(y)] = alt;
                        previous[adjacent.get(y)] = num;
                        distQueue.set(adjacent.get(y), alt);
                    }
                }
            }
        }

        for (int i = 0; i < destination.length; i++) {
            n = destination[i];
            path[i] = 0;
            while (previous[n] != -1) {
                path[i] += distance[n] - distance[previous[n]];
                n = previous[n];
            }
            if (path[i] < number) {
                p = i;
                number = path[i];
            }
        }

        n = destination[p];

        while (previous[n] != -1) {
            graph.addEdge(previous[n], n, G.getEdgeWeight(previous[n], n));
            n = previous[n];
        }

        ArrayList<Edge> edges = graph.edges();
        edges.addAll(reg1.edges());
        edges.addAll(reg2.edges());
        return edges;
    }
}
    
    
    