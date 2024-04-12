import java.util.*;

public class tree24 {
    public static class Node {
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

    public static Node findSuccessor (Node node, int value) {
        if (node.children.isEmpty()) return node;
        return findSuccessor(node.children.firstElement(), value);
    }

    // incomplete
    public static Node delete (Node node, int value, Node parent) {
        if (node == null) return node;
        if (node.keys.size() == 1 && parent != null) {
            int index = parent.children.indexOf(node);
            if (index - 1 >= 0 && parent.children.get(index - 1).keys.size() >= 2) {
                node.keys.add(0, parent.keys.get(index - 1));
                parent.keys.remove(index - 1);
                parent.keys.add(index - 1, parent.children.get(index - 1).keys.lastElement());
                node.children.add(0, parent.children.get(index - 1).children.lastElement());
                parent.children.get(index - 1).keys.remove(parent.children.get(index - 1).keys.lastElement());
                parent.children.get(index - 1).children.remove(parent.children.get(index - 1).children.lastElement());
            }
            else if (index + 1 < parent.children.size() && parent.children.get(index + 1).keys.size() >= 2) {
                node.keys.add(parent.keys.get(index));
                parent.keys.remove(index);
                parent.keys.add(index, parent.children.get(index + 1).keys.firstElement());
                node.children.add(parent.children.get(index + 1).children.firstElement());
                parent.children.get(index + 1).keys.remove(parent.children.get(index + 1).keys.firstElement());
                parent.children.get(index + 1).children.remove(parent.children.get(index + 1).children.firstElement());
            }
            else if (parent.keys.size() == 1) {
                Node sibling = parent.children.get(index == 0 ? 1 : 0);
                parent.keys.add(0, parent.children.get(0).keys.firstElement());
                parent.keys.add(parent.children.get(1).keys.firstElement());
                parent.children.clear();
                if (!node.children.isEmpty()) {
                    if (index == 0) {
                        parent.children.add(node.children.firstElement());
                        parent.children.add(node.children.lastElement());
                        parent.children.add(sibling.children.firstElement());
                        parent.children.add(sibling.children.lastElement());
                    }
                    else {
                        parent.children.add(sibling.children.firstElement());
                        parent.children.add(sibling.children.lastElement());
                        parent.children.add(node.children.firstElement());
                        parent.children.add(node.children.lastElement());
                    }
                }
                node = parent;
                parent = null;
            }
            else if (parent.keys.size() > 1) {
                if (index - 1 >= 0) {
                    node.keys.add(0, parent.keys.get(index - 1));
                    node.keys.add(0, parent.children.get(index - 1).keys.lastElement());
                    parent.keys.remove(index - 1);
                    if (!parent.children.get(index - 1).children.isEmpty()) {
                        node.children.add(0, parent.children.get(index - 1).children.lastElement());
                        node.children.add(0, parent.children.get(index - 1).children.firstElement());
                    }
                    parent.children.remove(index - 1);
                }
                else if (index + 1 < parent.children.size()) {
                    node.keys.add(parent.keys.get(index));
                    node.keys.add(parent.children.get(index + 1).keys.firstElement());
                    parent.keys.remove(index);
                    if (!parent.children.get(index + 1).children.isEmpty()) {
                        node.children.add(parent.children.get(index + 1).children.firstElement());
                        node.children.add(parent.children.get(index + 1).children.lastElement());
                    }
                    parent.children.remove(index + 1);
                }
            }
        }
        for (int i = 0; i < node.keys.size(); i++) {
            if (value < node.keys.get(i)) {
                if (!node.children.isEmpty()) delete(node.children.get(i), value, node);
                break;
            }
            else if (value == node.keys.get(i)) {
                if (node.children.isEmpty()) node.keys.remove(i);
                else { // this condition isn't working properly
                    Node successor = findSuccessor(node.children.get(i + 1), value);
                    node.keys.remove(i);
                    node.keys.add(i, successor.keys.firstElement());
                    successor.keys.remove(0);
                    successor.keys.add(0, value);
                    delete(node.children.get(i + 1), value, node);
                }
                break;
            }
        }
        if (!node.keys.isEmpty() && !node.children.isEmpty() && value > node.keys.lastElement()) delete(node.children.lastElement(), value, node);
        return node;
    }

    public static Node deleteRange (Node node, int L, int R){
        Vector<Integer> inorder = new Vector<>();
        buildInorder(node, inorder);
        for (int i = 0; i < inorder.size(); i++) {
            if (inorder.get(i) < L || inorder.get(i) > R) delete(node, inorder.get(i), null);
        }
        return node;
    }

    public static void buildInorder (Node node, Vector<Integer> in) {
        if (node == null) return;
        for (int i = 0; i < node.keys.size(); i++) {
            if (!node.children.isEmpty()) buildInorder(node.children.get(i), in);
            in.add(node.keys.get(i));
        }
        if (!node.children.isEmpty()) buildInorder(node.children.lastElement(), in);
    }

    // for debugging
    public static void printVector(Vector<Integer> vec) {
        for (int i = 0; i < vec.size(); i++) System.out.print(vec.get(i) + " ");
        System.out.println();
    }

    public static void printInorder (Node node) {
        if (node == null) return;
        for (int i = 0; i < node.keys.size(); i++) {
            if (!node.children.isEmpty()) printInorder(node.children.get(i));
            System.out.print(node.keys.get(i) + " ");
        }
        if (!node.children.isEmpty()) printInorder(node.children.lastElement());
    }

    public static void printParentToChildMapping (Node node){
        for (int i = 0; i < node.children.size(); i++) {
            printVector(node.keys);
            printVector(node.children.get(i).keys);
            printParentToChildMapping(node.children.get(i));
        }
    }

    public static void main(String args[]){

        // Test Case 1:
        tree24 tree = new tree24();
        Node root = new Node();
        root.keys.add(10);
        root.keys.add(20);
        Node temp = new Node();
        temp.keys.add(2);
        temp.keys.add(4);
        root.children.add(temp);
        temp = new Node();
        temp.keys.add(15);
        root.children.add(temp);
        temp = new Node();
        temp.keys.add(25);
        temp.keys.add(22);
        root.children.add(temp);
        printParentToChildMapping(root);
        System.out.println();
        Node ans = deleteRange(root, 3, 13);
        printParentToChildMapping(ans);

        // testing
        // int arr[] = {10, 20, 30, 40, 50};
        // int arr[] = {1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 17, 18, 20, 21, 22, 23, 24};
        // for (int i = 0; i < arr.length; i++) {
        //     root = insert(root, null, arr[i]);
        // }
        // // printParentToChildMapping(root);
        // // System.out.println();
        // root = deleteRange(root, 7, 16);
        // printParentToChildMapping(root);
    }
}
