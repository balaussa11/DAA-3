import java.util.*;

public class KruskalAlgorithm {

    static class UnionFind {
        private final Map<String, String> parent = new HashMap<>();

        public void makeSet(Collection<String> nodes) {
            for (String node : nodes) parent.put(node, node);
        }

        public String find(String node) {
            if (!parent.get(node).equals(node))
                parent.put(node, find(parent.get(node))); // path compression
            return parent.get(node);
        }

        public void union(String a, String b) {
            String rootA = find(a);
            String rootB = find(b);
            if (!rootA.equals(rootB))
                parent.put(rootA, rootB);
        }
    }

    // 🔹 Обычный метод для консольного вывода
    public static void runKruskal(Graph graph, int graphId) {
        long start = System.nanoTime();
        int operations = 0, totalCost = 0;

        List<Edge> edges = new ArrayList<>(graph.getEdges());
        List<Edge> mstEdges = new ArrayList<>();
        edges.sort(Comparator.comparingInt(e -> e.weight));

        UnionFind uf = new UnionFind();
        uf.makeSet(graph.getNodes());

        for (Edge e : edges) {
            operations++;
            String root1 = uf.find(e.from);
            String root2 = uf.find(e.to);
            if (!root1.equals(root2)) {
                mstEdges.add(e);
                totalCost += e.weight;
                uf.union(root1, root2);
            }
            if (mstEdges.size() == graph.getNodes().size() - 1)
                break;
        }

        double execTime = (System.nanoTime() - start) / 1_000_000.0;

        System.out.println("      \"mst_edges\": [");
        for (int i = 0; i < mstEdges.size(); i++) {
            Edge e = mstEdges.get(i);
            String comma = (i < mstEdges.size() - 1) ? "," : "";
            System.out.printf("        {\"from\": \"%s\", \"to\": \"%s\", \"weight\": %d}%s%n",
                    e.from, e.to, e.weight, comma);
        }
        System.out.println("      ],");
        System.out.printf("      \"total_cost\": %d,%n", totalCost);
        System.out.printf("      \"operations_count\": %d,%n", operations);
        System.out.printf("      \"execution_time_ms\": %.2f%n", execTime);
    }

    // ✅ Новый метод — нужен для JUnit тестов (аналог testPrim)
    public static MSTResult testKruskal(Graph graph) {
        long start = System.nanoTime();
        int operations = 0;
        int totalCost = 0;

        List<Edge> edges = new ArrayList<>(graph.getEdges());
        List<Edge> mstEdges = new ArrayList<>();
        edges.sort(Comparator.comparingInt(e -> e.weight));

        UnionFind uf = new UnionFind();
        uf.makeSet(graph.getNodes());

        for (Edge e : edges) {
            operations++;
            String root1 = uf.find(e.from);
            String root2 = uf.find(e.to);
            if (!root1.equals(root2)) {
                mstEdges.add(e);
                totalCost += e.weight;
                uf.union(root1, root2);
            }
            if (mstEdges.size() == graph.getNodes().size() - 1)
                break;
        }

        double execTime = (System.nanoTime() - start) / 1_000_000.0;

        MSTResult result = new MSTResult();
        result.mstEdges = mstEdges;
        result.totalCost = totalCost;
        result.operationsCount = operations;
        result.executionTimeMs = execTime;
        return result;
    }
}
