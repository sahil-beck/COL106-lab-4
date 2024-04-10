import java.util.*;

public class tree24 {
    public class Node{
        Vector<Integer> keys = new Vector<Integer>();
        Vector<Node> children = new Vector<Node>();
    }

    public static Node deleteRange(Node root, int L, int R){
        // Write your code here
        return root;
    }

    public static void printTree(Node root){
        // Write your code here
    }

    public static void main(String args[]){

        // Test Case 1:
        tree24 tree = new tree24();
        Node root = tree.new Node();
        root.keys.add(10);
        root.keys.add(20);
        Node temp = tree.new Node();
        temp.keys.add(2);
        temp.keys.add(4);
        root.children.add(temp);
        temp = tree.new Node();
        temp.keys.add(15);
        root.children.add(temp);
        temp = tree.new Node();
        temp.keys.add(25);
        temp.keys.add(22);
        root.children.add(temp);
        Node ans = deleteRange(root, 5, 13);
        printTree(ans);
   
    }
}
