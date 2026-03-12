import java.util.ArrayList;

public class Tree {
	private Node root;

	public Tree(){
		root = null;
	}

	public boolean insert(int x){
        if(root == null){
            root = new Node();
            root.keyList.add(x);
			root.subtreeSize = 1;
            root.parent = null;
			return true;
        }else{
            Node leafNode = root.find(x);
            if(!leafNode.keyList.contains(x)){
              leafNode.addKey(x, leafNode.childList);
			  return true;
            }
        }

		return false;
	}
	public int size(){
		return root.subtreeSize;
	}
	public int size(int x){
		Node treeNode = root.find(x);
		if(treeNode.keyList.contains(x)){
			return treeNode.subtreeSize;
		}
		return 0;
	}
	public int get(int x){
		return root.getC(x);
	}

	class Node {
        public Node parent;
        public ArrayList<Integer> keyList;
        public ArrayList<Node> childList;
		int subtreeSize;
		public Node() {
            keyList = new ArrayList<>();
            childList = new ArrayList<>();
			subtreeSize = 0;
		}

        //returns leaf node or node containing x
        private Node find(int x){
            if(isLeaf() || keyList.contains(x)){
                return this;
            }

            int i = 0;
            while(i < keyList.size() && x > keyList.get(i)){
                i++;
            }

            return childList.get(i).find(x);
        }

        private boolean isLeaf(){
            return childList.isEmpty();
        }
		private boolean isFull(){
			return keyList.size() > 2;
		}

		public Integer getC(int x){
			int leftSize = (!childList.isEmpty())
					? childList.get(0).subtreeSize
					: 0;

			if(keyList.size() == 1){
				if (x < leftSize) {
					return childList.get(0).getC(x);
				}
				if (x == leftSize) {
					return keyList.get(0);
				}
				return childList.get(1).getC(x - leftSize -1);
			}

			int middleSize = (childList.size() > 1)
					? childList.get(1).subtreeSize
					: 0;

			if (x < leftSize) {
				return childList.get(0).getC(x);
			}
			if (x == leftSize) {
				return keyList.get(0);
			}
			if (x < leftSize + 1 + middleSize) {
				return childList.get(1).getC(x - leftSize - 1);
			}
			if (x == leftSize + 1 + middleSize) {
				return keyList.get(1);
			}
			return childList.get(2).getC(x - leftSize - 2 - middleSize);
		}

        //adds key to node
		public void addKey(int x, ArrayList<Node> oChildList) {
            int i = 0;
            while(i < keyList.size() && x > keyList.get(i)){
                i++;
            }
            keyList.add(i, x);

			if(!oChildList.isEmpty()){
				childList.remove(i);
				childList.add(i, oChildList.remove(1));
				childList.add(i, oChildList.remove(0));
			}

            if(isFull()){
                splitUp();
				return;
            }
			fixSizesUpward();
		}

        //splits up node
        private void splitUp(){
            Node newNode1 = new Node();
            Node newNode2 = new Node();
            newNode2.keyList.add(keyList.remove(2));
            newNode1.keyList.add(keyList.remove(0));
			if (!childList.isEmpty()) {
				childList.get(0).parent = newNode1;
				childList.get(1).parent = newNode1;
				childList.get(2).parent = newNode2;
				childList.get(3).parent = newNode2;
				newNode1.childList.add(childList.remove(0));
				newNode1.childList.add(childList.remove(0));
				newNode2.childList.add(childList.remove(0));
				newNode2.childList.add(childList.remove(0));
			}
			childList.add(newNode1);
			childList.add(newNode2);
			newNode1.recomputeSize();
			newNode2.recomputeSize();
			this.recomputeSize();
			if(parent == null){
				newNode1.parent = this;
				newNode2.parent = this;

			}else{
				newNode1.parent = this.parent;
				newNode2.parent = this.parent;
				parent.addKey(keyList.get(0), childList);
			}
		}

		private void fixSizesUpward() {
			Node current = this;
			while (current != null) {
				current.recomputeSize();
				current = current.parent;
			}
		}
		private void recomputeSize() {
			int s = keyList.size();
			for (Node c : childList) {
				s += c.subtreeSize;
			}
			subtreeSize = s;
		}
	}
}