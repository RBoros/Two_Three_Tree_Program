# 2-3 Tree with Order-Statistics Support
 
**Author:** Raymond Okolo
**Date Started:** March 19, 2026
**Date Modified:** September 25, 2026
 
## Overview
 
This project implements a **2-3 tree** (a balanced multiway search tree where each
node holds 1–2 keys and 0, 2, or 3 children) augmented with **subtree size
counters**. The size augmentation turns the tree into an order-statistics
structure, allowing efficient rank-based queries in addition to standard
search and insert.
 
## Files
 
| File            | Description                                                   |
|-----------------|-----------------------------------------------------------------|
| `Tree.java`      | The 2-3 tree implementation, including the inner `Node` class. |
| `TreeTests.java` | JUnit test suite covering insertion, splitting, duplicates, and order-statistic lookups. |
 
## Public API (`Tree`)
 
- **`boolean insert(int x)`**
  Inserts `x` into the tree. Descends to the appropriate leaf, adds the key
  in sorted order, and recursively splits any node that overflows (more than
  2 keys), promoting the middle key to the parent — the standard 2-3 tree
  insertion algorithm. Returns `true` if the key was inserted.
- **`int size()`**
  Returns the total number of keys stored in the tree (the root's subtree
  size).
- **`int size(int x)`**
  Returns the subtree size rooted at the node containing key `x` (0 if `x`
  is not present). This exposes the augmented count used internally for
  rank calculations.
- **`int get(int x)`**
  Returns the `x`-th smallest key in the tree (0-indexed), computed by
  walking down the tree and using each child's subtree size to decide
  whether the target rank lies in a left subtree, the current node's key,
  or a right subtree.
## Internal Design (`Node`)
 
Each `Node` stores:
- `keyList` — the node's keys, kept in sorted order (max 2 before a split).
- `childList` — child pointers (empty for leaves).
- `parent` — a back-pointer used to propagate splits and size updates upward.
- `subtreeSize` — the total number of keys in the subtree rooted at this node.
Key operations inside `Node`:
- **`find(x)`** — descends the tree to locate the leaf (or node) relevant to `x`.
- **`addKey(x, childList)`** — inserts a key in sorted position and triggers
  `splitUp()` if the node becomes overfull.
- **`splitUp()`** — splits an overfull node into two nodes and promotes the
  middle key to the parent, recursively splitting upward if needed.
- **`recomputeSize()` / `fixSizesUpward()`** — keep `subtreeSize` correct
  after every insertion, which is what makes `size(x)` and `get(x)` work.
- **`getC(x)`** — the order-statistic selection routine used by `get()`.
## Running the Tests
 
The test suite uses **JUnit 4** and exercises:
- Single-node trees
- Left, right, and middle splits
- Duplicate key insertion
- Order-statistic retrieval via `get()`
Example (with JUnit 4 + Hamcrest on the classpath):
 
```bash
javac -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar Tree.java TreeTests.java
java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar:. org.junit.runner.JUnitCore TreeTests
```
 
## Notes
 
- This is a 2-3 tree, not a general B-tree: nodes hold at most 2 keys and
  split into two 1-key nodes when a 3rd key is added.
- `size(x)` and `get(x)` rely on `subtreeSize` staying consistent after
  every insert, so any future modification to `addKey`/`splitUp` should be
  paired with a corresponding update to the size-fixing logic.
