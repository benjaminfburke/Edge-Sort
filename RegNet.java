import java.util.ArrayList;

public class RegNet
{

    private static Graph MST;
    //creates a regional network
    //G: the original graph
    //max: the budget
    public static Graph run(Graph G, int max) 
    {
	    //To be implemented
        kruskal(G);
        ArrayList<Edge> sortedEdges = G.sortedEdges();
        int totalWeight = 0;
        for (Edge edge : sortedEdges) {
            totalWeight += edge.w;
        }
        int i = sortedEdges.size() - 1;
        while (totalWeight > max && i > 0) {
            MST.removeEdge(sortedEdges.get(i));
            totalWeight -= sortedEdges.get(i).w;
            i--;
        }
        MST = MST.connGraph();
        return MST;

    }

    private static void kruskal(Graph g) {
        MST = new Graph(g.V());
        MST.setCodes(g.getCodes());
        ArrayList<Edge> sortedEdges = g.sortedEdges();
        UnionFind unionFind = new UnionFind(MST.V());
        for (Edge temp : sortedEdges) {
            if (!unionFind.connected(MST.index(temp.u), MST.index(temp.v))) {
                unionFind.union(MST.index(temp.u), MST.index(temp.v));
                MST.addEdge(MST.index(temp.u), MST.index(temp.v), temp.w);
            }
        }
    }
}