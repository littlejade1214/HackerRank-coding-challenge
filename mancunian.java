import java.util.Arrays;
import java.util.Scanner;
 
public class unionfind {
    public static int findParent(int u, int[] parents) {
	if (parents[u] == u) {
	    return u;
	} else {
	    parents[u] = findParent(parents[u], parents);
	    return parents[u];
	}
    }

    public static boolean isCyclic(int u, int v, int[] parents) {
	return findParent(u, parents) == findParent(v, parents);
    }

    public static void union(int u, int v, int[] parents, int[] size) {
	u = findParent(u, parents);
	v = findParent(v, parents);
	/* merge the smaller disjoint set with larger disjoint set */
	if (size[u] > size[v]) {
	    parents[v] = u;
	    size[u] += size[v];
	} else {
	    parents[u] = v;
	    size[v] += size[u];
	}
    }

    public static void unionFind(int numVertices, int numEdges, edge[] edges, int[][] graph) {
	int[][] minimumspan = new int[graph.length][graph.length];
	/* Overriding Comparator */
	Arrays.sort(edges);
	int[] parents = new int[numVertices + 1];
	int[] size = new int[numVertices + 1];
	/* initialize the disjoint sets */
	for (int vertex = 1; vertex < graph.length; vertex++) {
	    parents[vertex] = vertex;
	    size[vertex] = 1;
	}
	int edgeCount = 0;
	int edgeUsed = 1;
	int cost = 0;
	while (edgeUsed <= numVertices - 1) {
	    edge obj = edges[edgeCount];		// edges[edge] = new edge(u, v, w);
	    edgeCount += 1;
	    if (isCyclic(obj.u, obj.v, parents))
		continue;
	    union(findParent(obj.u, parents), findParent(obj.v, parents), parents, size);
	    minimumspan[obj.u][obj.v] = obj.weight;
	    edgeUsed += 1;
	}
	edgeUsed = edgeUsed - 1;
	/* display the minimum spanning tree */
	for (int u = 1; u < minimumspan.length; u++) {
	    for (int v = 0; v < minimumspan.length; v++) {
		if (minimumspan[u][v] != 0) {
		    System.out.println(u + " " + v + " " + minimumspan[u][v]);
		    cost += minimumspan[u][v];
		}
	    }
	}
	System.out.println(cost + " " + edgeUsed);
    }

}
