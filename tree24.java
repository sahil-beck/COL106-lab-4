import java.util.*;

public class tree24 {
    public static class Node{
        Vector<Integer> keys = new Vector<Integer>();
        Vector<Node> children = new Vector<Node>();
    }

    public static Node insert (Node node, Node parent, int value){
        for (int i = 0; i < node.keys.size(); i++) {
            if (node.keys.get(i) == value) return node;
        }
        if (node.keys.size() == 3) {
            Node rightNode = new Node();
            boolean left = value < node.keys.get(1) ? true : false;
            rightNode.keys.add(node.keys.lastElement());
            node.keys.remove(node.keys.lastElement());
            if (!node.children.isEmpty()) {
                rightNode.children.add(node.children.get(2));
                rightNode.children.add(node.children.get(3));
                node.children.remove(node.children.lastElement());
                node.children.remove(node.children.lastElement());
            }
            if (parent == null) {
                parent = new Node();
                parent.children.add(node);
                parent.children.add(rightNode);
            }
            else parent.children.add(parent.children.indexOf(node) + 1, rightNode);
            parent.keys.add(parent.children.indexOf(node), node.keys.lastElement());
            node.keys.remove(node.keys.lastElement());
            if (left) insert(node, parent, value);
            else insert(rightNode, parent, value);
            return parent;
        }
        if (node.children.isEmpty()) {
            node.keys.add(value);
            Collections.sort(node.keys);
            return node;
        }
        if (value < node.keys.firstElement()) insert(node.children.firstElement(), node, value);
        else if (value > node.keys.lastElement()) insert(node.children.lastElement(), node, value);
        else insert(node.children.get(1), node, value);
        return node;
    }

    public static int findSuccessor (Node node, int value) {
        if (node.children.isEmpty()) return node.keys.firstElement();
        return findSuccessor(node.children.firstElement(), value);
    }

    // incomplete
    public static Node delete (Node node, int value, Node parent) {
        if (node == null) return node;
        if (node.keys.size() == 1) {
            // if (parent.keys.size() == 1) {
            //     for (int j = 0; j < parent.children.size(); j++) {
            //         for (int k = 0; k < parent.children.get(j).keys.size(); k++) {
            //             parent.keys.add(parent.children.get(j).keys.get(k));
            //         }
            //     }
            //     parent.children.clear();
            // }
            // else {

            // }
        }
        for (int i = 0; i < node.keys.size(); i++) {
            if (value < node.keys.get(i)) {
                if (!node.children.isEmpty()) delete(node.children.get(i), value, node);
                break;
            }
            else if (value == node.keys.get(i)) {
                if (node.children.isEmpty()) node.keys.remove(value);
                else {
                    int successor = findSuccessor(node.children.get(i + 1), value);
                    node.keys.remove(i);
                    node.keys.add(i, successor);
                    delete(node.children.get(i + 1), successor, node);
                }
                break;
            }
        }
        if (value > node.keys.lastElement()) delete(node.children.lastElement(), value, node);
        return node;
    }

    public static Node deleteRange (Node node, int L, int R){
        while (!node.keys.isEmpty() && ((node.keys.firstElement() - 1 < L && !node.children.isEmpty()) || node.keys.firstElement() < L)) {
            if (node.keys.firstElement() - 1 < L) deleteRange(node.children.firstElement(), L, R);
            if (node.keys.firstElement() < L) delete(node, node.keys.firstElement(), node);
        }
        while (!node.keys.isEmpty() && ((node.keys.lastElement() + 1 > R && !node.children.isEmpty()) || node.keys.firstElement() > R)) {
            if (node.keys.lastElement() + 1 > R) deleteRange(node.children.lastElement(), L, R);
            if (node.keys.lastElement() > R) delete(node, node.keys.lastElement(), node);
        }
        return node;
    }

    public static void printVector(Vector<Integer> vec) {
        for (int i = 0; i < vec.size(); i++) System.out.print(vec.get(i) + " ");
        System.out.println();
    }

    public static void printTree(Node node){
        // if (node == null) return;
        // for (int i = 0; i < node.keys.size(); i++) {
        //     if (!node.children.isEmpty()) printTree(node.children.get(i));
        //     System.out.print(node.keys.get(i) + " ");
        // }
        // if (!node.children.isEmpty()) printTree(node.children.lastElement());
        for (int i = 0; i < node.children.size(); i++) {
            printVector(node.keys);
            printVector(node.children.get(i).keys);
            printTree(node.children.get(i));
        }
    }

    public static void main(String args[]){

        // Test Case 1:
        // tree24 tree = new tree24();
        Node root = new Node();
        // root.keys.add(10);
        // root.keys.add(20);
        // Node temp = tree.new Node();
        // temp.keys.add(2);
        // temp.keys.add(4);
        // root.children.add(temp);
        // temp = tree.new Node();
        // temp.keys.add(15);
        // root.children.add(temp);
        // temp = tree.new Node();
        // temp.keys.add(25);
        // temp.keys.add(22);
        // root.children.add(temp);
        // Node ans = deleteRange(root, 5, 13);

        int arr[] = {10, 20, 30, 40, 50};
        for (int i = 0; i < arr.length; i++) {
            root = insert(root, null, arr[i]);
            // printTree(root);
            // System.out.println();
        }
        printTree(root);
    }
}
