import java.util.ArrayList;

public class Tree {
	private Node root;
	private int treeSize;

	public Tree(){
		root = null;
		treeSize = 0;
	}

	public boolean insert(int x){
        if(root == null){
            root = new Node();
            root.keyList.add(x);
            root.parent = null;
        }else{
            Node leafNode = root.find(x);
            if(!leafNode.keyList.contains(x)){
              leafNode.addKey(x);
            }else{
                return false;
            }
        }

		return true;
	}
	public int size(){
		return treeSize;
	}
	public int size(int x){
		return treeSize;
	}
	public int get(int x){ return 0;}

	class Node {
        public Node parent;
        public ArrayList<Integer> keyList;
        public ArrayList<Node> childList;
		public Node() {
            keyList = new ArrayList<>();
            childList = new ArrayList<>();
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

        //adds key to leaf node
		public void addKey(int x) {
            int i = 0;
            while(i < keyList.size() && x > keyList.get(i)){
                i++;
            }
            keyList.add(i, x);
            if(isFull()){
                splitUp();
            }

		}
        /*

         */
        //splits up leaf node
        private void splitUp(){
            //makes leftmost and rightmost keys into nodes in childList
            Node newNode1 = new Node();
            Node newNode2 = new Node();
            newNode2.keyList.add(keyList.remove(2));
            newNode1.keyList.add(keyList.remove(0));
            childList.add(newNode1);
            childList.add(newNode2);

            if(parent == null){
                newNode1.parent = this;
                newNode2.parent = this;
            } else{
                newNode1.parent = this.parent;
                newNode2.parent = this.parent;

                parent.addChildren(keyList.get(0), childList);
                parent = null;
            }
        }

        /**
         * Adds key and children to parent node
         * Removes previous child in childList
         * @param x
         * @param oChildList
         */
        public void addChildren(int x, ArrayList<Node> oChildList){
            int i = 0;
            while(i < keyList.size() && x > keyList.get(i)){
                i++;
            }
            keyList.add(i, x);
            childList.remove(i);
            childList.add(i, oChildList.remove(1));
            childList.add(i, oChildList.remove(0));
            if(isFull()){
                splitParentUp();
            }
        }
        //splits up node that currently has 4 children
        public void splitParentUp(){
            Node newNode1 = new Node();
            Node newNode2 = new Node();
            newNode2.keyList.add(keyList.remove(2));
            newNode1.keyList.add(keyList.remove(0));
            newNode1.childList.add(childList.remove(0));
            newNode1.childList.add(childList.remove(0));
            newNode2.childList.add(childList.remove(0));
            newNode2.childList.add(childList.remove(0));
            childList.add(newNode1);
            childList.add(newNode2);

        }
        private boolean isFull(){
            return keyList.size() > 2;
        }

	}
}