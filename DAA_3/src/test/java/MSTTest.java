import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class MSTTest {

    private Graph createGraph() {
        List<String> nodes = Arrays.asList("A", "B", "C", "D");
        List<Edge> edges = List.of(
                new Edge("A", "B", 1),
                new Edge("B", "C", 2),
                new Edge("C", "D", 3),
                new Edge("A", "C", 4),
                new Edge("B", "D", 5)
        );
        return new Graph(nodes, edges);
    }

    @Test
    public void testSameTotalCost() {
        Graph g = createGraph();
        MSTResult prim = PrimAlgorithm.testPrim(g);
        MSTResult kruskal = KruskalAlgorithm.testKruskal(g);
        assertEquals(prim.totalCost, kruskal.totalCost,
                "Prim and Kruskal MST costs must match");
    }

    @Test
    public void testEdgesEqualVMinus1() {
        Graph g = createGraph();
        MSTResult prim = PrimAlgorithm.testPrim(g);
        assertEquals(g.getNodes().size() - 1, prim.mstEdges.size(),
                "MST must contain V-1 edges");
    }

    @Test
    public void testPositivePerformanceValues() {
        Graph g = createGraph();
        MSTResult prim = PrimAlgorithm.testPrim(g);
        assertTrue(prim.executionTimeMs >= 0, "Execution time must be non-negative");
        assertTrue(prim.operationsCount >= 0, "Operation count must be non-negative");
    }

    @Test
    public void testConnectedMST() {
        Graph g = createGraph();
        MSTResult prim = PrimAlgorithm.testPrim(g);
        assertTrue(isConnected(g.getNodes(), prim.mstEdges),
                "MST must connect all vertices");
    }

    // ===== Helper: проверка связности MST =====
    private boolean isConnected(List<String> nodes, List<Edge> edges) {
        Map<String, Set<String>> adj = new HashMap<>();
        for (String n : nodes) adj.put(n, new HashSet<>());
        for (Edge e : edges) {
            adj.get(e.from).add(e.to);
            adj.get(e.to).add(e.from);
        }
        Set<String> visited = new HashSet<>();
        dfs(nodes.get(0), adj, visited);
        return visited.size() == nodes.size();
    }

    private void dfs(String node, Map<String, Set<String>> adj, Set<String> vis) {
        vis.add(node);
        for (String n : adj.get(node)) {
            if (!vis.contains(n)) dfs(n, adj, vis);
        }
    }
}
