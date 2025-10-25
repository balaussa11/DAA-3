import java.util.*;

public class PrimAlgorithm {

    public static void runPrim(Graph graph, int graphId) {
        long start = System.nanoTime();
        int operations = 0;
        int totalCost = 0;

        List<String> nodes = graph.getNodes();
        List<Edge> edges = graph.getEdges();
        Set<String> visited = new HashSet<>();
        List<Edge> mstEdges = new ArrayList<>();

        visited.add(nodes.get(0));

        while (visited.size() < nodes.size()) {
            Edge min = null;
            int minWeight = Integer.MAX_VALUE;

            for (Edge e : edges) {
                operations++;
                boolean cond1 = visited.contains(e.from) && !visited.contains(e.to);
                boolean cond2 = visited.contains(e.to) && !visited.contains(e.from);
                if ((cond1 || cond2) && e.weight < minWeight) {
                    minWeight = e.weight;
                    min = e;
                }
            }

            if (min == null) break;
            mstEdges.add(min);
            totalCost += min.weight;
            visited.add(min.from);
            visited.add(min.to);
        }

        double execTime = (System.nanoTime() - start) / 1000000.0;

        // JSON-формат вывода
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

    // ✅ тестовая версия для JUnit
    public static MSTResult testPrim(Graph graph) {
        long start = System.nanoTime();
        int operations = 0, totalCost = 0;
        List<String> nodes = graph.getNodes();
        List<Edge> edges = graph.getEdges();
        Set<String> visited = new HashSet<>();
        List<Edge> mstEdges = new ArrayList<>();
        visited.add(nodes.get(0));

        while (visited.size() < nodes.size()) {
            Edge min = null;
            int minWeight = Integer.MAX_VALUE;
            for (Edge e : edges) {
                operations++;
                boolean cond1 = visited.contains(e.from) && !visited.contains(e.to);
                boolean cond2 = visited.contains(e.to) && !visited.contains(e.from);
                if ((cond1 || cond2) && e.weight < minWeight) {
                    minWeight = e.weight;
                    min = e;
                }
            }
            if (min == null) break;
            mstEdges.add(min);
            totalCost += min.weight;
            visited.add(min.from);
            visited.add(min.to);
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
