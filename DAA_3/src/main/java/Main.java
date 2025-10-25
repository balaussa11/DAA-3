import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Загружаем графы
        List<Graph> graphs = GraphReader.loadGraphs("assign_3_input.json");
        System.out.println("Graphs loaded: " + graphs.size());

        List<Map<String, Object>> resultsList = new ArrayList<>();

        int id = 1;
        for (Graph graph : graphs) {
            System.out.println("\nProcessing Graph " + id + "...");

            MSTResult prim = PrimAlgorithm.testPrim(graph);
            MSTResult kruskal = KruskalAlgorithm.testKruskal(graph);

            Map<String, Object> result = new LinkedHashMap<>();
            result.put("graph_id", id);

            Map<String, Object> stats = new LinkedHashMap<>();
            stats.put("vertices", graph.getNodes().size());
            stats.put("edges", graph.getEdges().size());
            result.put("input_stats", stats);

            result.put("prim", toMap(prim));
            result.put("kruskal", toMap(kruskal));

            resultsList.add(result);
            id++;
        }

        Map<String, Object> fullOutput = new LinkedHashMap<>();
        fullOutput.put("results", resultsList);

        // Пишем JSON
        try (FileWriter writer = new FileWriter("output.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(fullOutput, writer);
            System.out.println("\n✅ Results saved to output.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Пишем CSV
        try (FileWriter csv = new FileWriter("results.csv")) {
            csv.write("graph_id,vertices,edges,prim_cost,kruskal_cost,prim_ops,kruskal_ops,prim_time,kruskal_time\n");
            id = 1;
            for (Map<String, Object> g : resultsList) {
                Map<String, Object> inputStats = (Map<String, Object>) g.get("input_stats");
                Map<String, Object> prim = (Map<String, Object>) g.get("prim");
                Map<String, Object> kruskal = (Map<String, Object>) g.get("kruskal");
                csv.write(String.format(
                        "%d,%d,%d,%d,%d,%d,%d,%.3f,%.3f\n",
                        id,
                        inputStats.get("vertices"),
                        inputStats.get("edges"),
                        prim.get("total_cost"),
                        kruskal.get("total_cost"),
                        prim.get("operations_count"),
                        kruskal.get("operations_count"),
                        prim.get("execution_time_ms"),
                        kruskal.get("execution_time_ms")
                ));
                id++;
            }
            System.out.println("✅ Results also saved to results.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Преобразует результат в Map для JSON
    private static Map<String, Object> toMap(MSTResult r) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("mst_edges", r.mstEdges);
        map.put("total_cost", r.totalCost);
        map.put("operations_count", r.operationsCount);
        map.put("execution_time_ms", r.executionTimeMs);
        return map;
    }
}
