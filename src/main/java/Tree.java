import java.util.ArrayList;

public class Tree {
	private Node root;
	private int fullTreeSize;

	public Tree(){
		root = null;
		fullTreeSize = 0;
	}

	public boolean insert(int x){
        if(root == null){
            root = new Node();
            root.keyList.add(x);
            root.parent = null;
			return true;
        }else{
            Node leafNode = root.find(x);
            if(!leafNode.keyList.contains(x)){
              leafNode.addKey(x, leafNode.childList);
			  fullTreeSize++;
			  return true;
            }
        }

		return false;
	}
	public int size(){
		return
	}
	public int size(int x){
		return treeSize;
		//do while loop that goes to every parent and increments tree size
		//should be log n
	}
	public int get(int x){ return 0;}

	class Node {
        public Node parent;
        public ArrayList<Integer> keyList;
        public ArrayList<Node> childList;
		int keySize;
		public Node() {
            keyList = new ArrayList<>();
            childList = new ArrayList<>();
			keySize = 0;
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

        //adds key to node
		public void addKey(int x, ArrayList<Node> oChildList) {
            int i = 0;
            while(i < keyList.size() && x > keyList.get(i)){
                i++;
            }
            keyList.add(i, x);
			keySize++;

			if(!oChildList.isEmpty()){
				childList.remove(i);
				childList.add(i, oChildList.remove(1));
				childList.add(i, oChildList.remove(0));
			}

            if(isFull()){
                splitUp();
            }
		}

        //splits up node
        private void splitUp(){
            //makes leftmost and rightmost keys into nodes in childList
            Node newNode1 = new Node();
            Node newNode2 = new Node();
            newNode2.keyList.add(keyList.remove(2));
            newNode1.keyList.add(keyList.remove(0));
			keySize = 1;

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
			if(parent == null){
				newNode1.parent = this;
				newNode2.parent = this;
			}else{
				newNode1.parent = this.parent;
				newNode2.parent = this.parent;
				parent.addKey(keyList.get(0), childList);
			}
		}
	}
}