public class Tree {
	private Node root;
	private int treeSize;
	public static void main(String[] args) {
		System.out.println("hello");
	}

	public Tree(){
		root = null;
		treeSize = 0;
	}

	public boolean insert(int x){
		if(root == null){
			root = new Node(x);
			root.parent = null;
		}else {
			root.addKey(x);
		}
		return true;
	}
	public int size(){
		return treeSize;
	}
	public int size(int x){
		return treeSize;
	}
	public int get(int x){}

	class Node {
		public int key1;
		public int key2;
		public int moveUpKey;

		public Node left;
		public Node middle;
		public Node right;
		public Node parent;

		public Node(int x) {
			key1 = x;
			key2 = -1;
			left = null;
			middle = null;
			right = null;
		}
		/**
		 * Inserts a new node as a descendant of this node.
		 @param x the value to insert
		 */
		public void addKey(int x {
			if(x < key1){
				if(left == null){
					if(key2 == -1){
						key2 = key1;
						key1 = x;
					}
					else{
						moveUpKey = key1;
						key2 = key1;
						key1 = x;
						moveUp(key1, key2, moveUpKey, parent);
					}
				}
				else{
					if(parent == null) {
						left.addKey(x);
					}else{
						left.addKey(x, )
					}
				}
			}
			else if(x > key1){
				if(left == null){
					if(key2 == -1){
						key2 = x;
					}
					else if(x > key2){
						moveUpKey = key2;
						key2 = x;
						moveUp(key1, key2, moveUpKey, parent);
					}
					else if(x < key2){
						moveUpKey = x;
						moveUp(key1, key2, moveUpKey, parent);
					}
				}
			}
		}
		private void moveUp(int key1, int key2, int moveUpKey, Node parent){
			if(parent == null){
				left = new Node(key1);
				right = new Node(key2);
				this.key1 = moveUpKey;
				this.key2 = -1;
			}else{
				left = new Node(key1);
				right = new Node(key2);
				parent.addKey(moveUpKey, );
			}
		}

	}
}