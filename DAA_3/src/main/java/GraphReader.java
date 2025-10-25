import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class GraphReader {

    public static List<Graph> loadGraphs(String filePath) {
        List<Graph> graphs = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            JsonArray graphsArray = jsonObject.getAsJsonArray("graphs");
            for (JsonElement element : graphsArray) {
                JsonObject g = element.getAsJsonObject();

                // Nodes
                List<String> nodes = new ArrayList<>();
                for (JsonElement n : g.getAsJsonArray("nodes")) {
                    nodes.add(n.getAsString());
                }

                // Edges
                List<Edge> edges = new ArrayList<>();
                for (JsonElement e : g.getAsJsonArray("edges")) {
                    JsonObject obj = e.getAsJsonObject();
                    String from = obj.get("from").getAsString();
                    String to = obj.get("to").getAsString();
                    int weight = obj.get("weight").getAsInt();
                    edges.add(new Edge(from, to, weight));
                }

                graphs.add(new Graph(nodes, edges));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return graphs;
    }
}
