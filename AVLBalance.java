// resources
// https://www.google.com/url?sa=t&source=web&rct=j&opi=89978449&url=https://www.geeksforgeeks.org/introduction-to-avl-tree/&ved=2ahUKEwijm7HDnr6FAxV3umMGHZgSC50QFnoECBsQAQ&usg=AOvVaw08UiZBssk1kA1AuLlD7JpH
// https://www.google.com/url?sa=t&source=web&rct=j&opi=89978449&url=https://en.wikipedia.org/wiki/AVL_tree&ved=2ahUKEwijm7HDnr6FAxV3umMGHZgSC50QmhN6BAhUEAI&usg=AOvVaw3JaiHvdngI_EKc62WdMVLi
import java.util.*;

class Node { 
	int key, height; 
	Node left, right; 

	Node(int d) { 
		key = d; 
		height = 1; 
	} 
} 

class AVLBalance { 

	Node root;

	Node rightRotate(Node node) { 
		Node leftChild = node.left, temp = leftChild.right;
		leftChild.right = node;
		node.left = temp;
		node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
		leftChild.height = 1 + Math.max(getHeight(leftChild.left), getHeight(leftChild.right));
		return leftChild;
	} 

	Node leftRotate(Node node) { 
		Node rightChild = node.right, temp = rightChild.left;
		rightChild.left = node;
		node.right = temp;
		node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
		rightChild.height = 1 + Math.max(getHeight(rightChild.left), getHeight(rightChild.right));
		return rightChild;
	} 

	int getHeight (Node node) {
		if (node == null) return 0;
		return node.height;
	}

	int getBalanceFactor(Node node) { 
        return getHeight(node.right) - getHeight(node.left);
	} 

	Node insert(Node node, int key) { 
        if (node == null) return new Node(key);
		if (key < node.key) node.left = insert(node.left, key);
		else if (key > node.key) node.right = insert(node.right, key);
		else return node;
		node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
		int balanceFactor = getBalanceFactor(node);
		if (balanceFactor < -1) {
			if (key > node.left.key) node.left = leftRotate(node.left);
			return rightRotate(node);
		}
		else if (balanceFactor > 1) {
			if (key < node.right.key) node.right = rightRotate(node.right);
			return leftRotate(node);
		}
		return node; 
	}

	void preOrder(Node node) {
		if (node == null) return;
		preOrder(node.left);
		System.out.println(node.key);
		preOrder(node.right);
	} 

	public static void main(String[] args) { 
		AVLBalance tree = new AVLBalance(); 

		int arr[] = {10, 20, 30, 40, 50, 25};
		for (int i = 0; i < arr.length; i++) tree.root = tree.insert(tree.root, arr[i]); 

		/* The constructed AVL Tree would be 
		  30 
		 / \ 
		20 40 
		/ \	 \ 
		10 25 50 
		*/
		System.out.println("Preorder traversal of constructed tree is: "); 
		tree.preOrder(tree.root); 
	} 
}
