# DAA-3
# 🧩 Assignment 3: Minimum Spanning Tree (Prim’s & Kruskal’s Algorithms)

## 📘 Project Overview
This project implements and compares **Prim’s** and **Kruskal’s** algorithms for finding the **Minimum Spanning Tree (MST)** of a weighted undirected graph.  
The task models the problem of connecting all city districts with the minimum possible total cost.

---

## 🧱 Features
- Reads graph data from a JSON file (`input.json`).
- Implements both **Prim’s** and **Kruskal’s** algorithms.
- Measures:
  - Total MST cost  
  - Number of operations (comparisons, unions, etc.)  
  - Execution time in milliseconds  
- Writes full results into:
  - `output.json` (detailed structure)
  - `results.csv` (summary for performance comparison)
- Includes **automated JUnit tests** for correctness and performance.

---

## 📂 Project Structure
DAA_Assignment3_MST/
├── pom.xml # Maven project file
├── input.json # Input datasets
├── output.json # Algorithm results
├── results.csv # Summary comparison
└── src/
├── main/java/
│ ├── Graph.java
│ ├── Edge.java
│ ├── PrimAlgorithm.java
│ ├── KruskalAlgorithm.java
│ ├── GraphReader.java
│ └── Main.java
└── test/java/
└── MSTTests.java

---

## ⚙️ How to Run
### 🧩 Option 1 — Using IntelliJ IDEA
1. Open the project as a **Maven project**.
2. Ensure `pom.xml` dependencies are downloaded (GSON & JUnit).
3. Run the `Main.java` file.
4. Results will be generated in:
   - `output.json`
   - `results.csv`

### 🧪 Option 2 — Run from terminal
```bash
mvn compile
mvn exec:java -Dexec.mainClass="Main"
| Graph | Vertices | Edges | Prim Cost | Kruskal Cost | Prim Ops | Kruskal Ops | Prim Time (ms) | Kruskal Time (ms) |
| ----- | -------- | ----- | --------- | ------------ | -------- | ----------- | -------------- | ----------------- |
| 1     | 5        | 7     | 16        | 16           | 28       | 5           | 0.089          | 3.644             |
| 2     | 10       | 13    | 32        | 32           | 117      | 9           | 0.086          | 0.047             |
| 3     | 20       | 22    | 96        | 96           | 418      | 21          | 0.191          | 0.072             |
| 4     | 6        | 8     | 17        | 17           | 40       | 5           | 0.029          | 0.022             |
| 5     | 13       | 15    | 46        | 46           | 180      | 13          | 0.123          | 0.048             |
✅ Both algorithms produce identical MST total costs.
⚡ Kruskal’s algorithm is slightly faster on sparse graphs,
while Prim’s performs better on denser graphs.

📊 Testing

Automated tests verify:

MST total cost is identical for both algorithms

Number of MST edges = V − 1

No cycles are formed (acyclic property)

Graph remains connected

Execution time ≥ 0 ms

Reproducibility of results for the same dataset
