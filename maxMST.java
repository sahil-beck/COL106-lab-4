import java.util.*;

public class maxMST {
    public class Node {
        Vector<Integer> keys = new Vector<Integer>();
        Vector<Node> children = new Vector<Node>();
    }

    // for a whole subtree it, stores it's max & min values and max sum of all keys of a valid MST subtree
    private class toReturn {
        public boolean bool_ = false; // stores whether the whole subtree is a valid MST or not
        public int int_ = 0, max_ = Integer.MIN_VALUE, min_ = Integer.MAX_VALUE;
    } 

    private toReturn isSorted (Vector<Integer> vec) {
        toReturn ans = new toReturn();
        for (int i = 0; i < vec.size(); i++) {
            // not returning sum, max_ & min_ because these values of an invalid MST subtree has no value for parent nodes 
            if (i != 0 && vec.get(i - 1) >= vec.get(i)) return new toReturn(); // "==" is also invalid, since in a MST we can only have distinct keys 
            ans.int_ += vec.get(i);
            if (vec.get(i) > ans.max_) ans.max_ = vec.get(i);
            if (vec.get(i) < ans.min_) ans.min_ = vec.get(i);
        }
        ans.bool_ = true;
        // if valid MST subtree then returning all useful info, i.e. sum, min_, max_
        return ans;
    }

    // sum, max_, min_, has no useful info for it's parents if current node or it's childrens are not valid
    private toReturn fun (Node node) {
        toReturn ans = isSorted(node.keys);
        int sum = ans.int_, childMax = 0;
        // in a valid MST subtree we can't have empty node, or node with more than 3 keys
        if (node.keys.isEmpty() || node.keys.size() > 3) ans.bool_ = false;
        // in a valid MST internal nodes should have number of keys + 1 children, i.e. 1 key => 2 children, 2 key => 3 children & 3 key => 4 children
        if (node.children.size() != 0 && node.keys.size() + 1 != node.children.size()) ans.bool_ = false;
        for (int i = 0; i < node.children.size(); i++) {
            toReturn childAns = fun(node.children.get(i));
            sum += childAns.int_;
            if (childAns.int_ > childMax) childMax = childAns.int_;
            ans.bool_ &= childAns.bool_;
            // for a valid MST, max_ of left child of key should be strcictly less than the key & min_ of right child of a key should be strictly greater than the key
            if (i < node.keys.size() && childAns.max_ >= node.keys.get(i)) ans.bool_ = false;
            if (i == node.keys.size() && childAns.min_ <= node.keys.get(i - 1)) ans.bool_ = false;
            if (childAns.max_ > ans.max_) ans.max_ = childAns.max_;
            if (childAns.min_ < ans.min_) ans.min_ = childAns.min_;
        }
        ans.int_ = (ans.bool_ == true ? sum : childMax);
        return ans;
    }

    public int getMaxMST(Node node){
        return fun(node).int_;
    }

    // for debugging
    private void outputToReturn (toReturn node) {
        System.out.println(node.bool_ + " " + node.int_ + " " + node.min_ + " " + node.max_);
    }

    private void outputVector (Vector<Integer> vec) {
        for (int i = 0; i < vec.size(); i++) System.out.print(vec.get(i) + " ");
        System.out.println();
    }

    public void outputMST(Node node){
        for (int i = 0; i < node.children.size(); i++) {
            outputVector(node.keys);
            outputVector(node.children.get(i).keys);
            System.out.println();
            outputMST(node.children.get(i));
        }
    }

    public static void main(String args[]){

        // adjList description
        // adjList[i] stores a parent node & all it's children nodes
        // adjList[i][0] stores the parent node && all adjList[i][j] s.t. j != i store the children nodes
        // all adjList[i][j][k] are the key values of a node, for all k
        // adjList[0][0] is the root

        // test case 1
        int adjList[][][] = {{{10, 20}, {2, 4}, {15}, {25, 22}}};

        // test case 3
        // int adjList[][][] ={{{10}, {5}, {12}, {7}}, {{5}, {6}, {8}}, {{7}, {9}}};

        // test case 4
        // int adjList[][][] = {{{8}, {5}, {10}, {15}}, {{5}, {3}, {6}},{{15}, {12}, {18}, {20}}};

        // test case 5
        // int adjList[][][] = {{{10, 20}, {5}, {17}, {22, 24, 29}}}; // valid MST

        // test case 6
        // int adjList[][][] = {{{53}, {27, 38}, {60, 70}}, {{27, 38}, {16, 25}, {33, 36}, {41, 46, 48}}, {{60, 70}, {55, 59}, {65, 68}, {73, 75, 79}}}; // valid MST

        HashMap<List<Integer>, Node> hm = new HashMap<>();
        maxMST mst = new maxMST();
        Node root = null;
        // MST building: easier way to build new MSTs as compared to what was already given
        for (int i = 0; i < adjList.length; i++) {
            Node parentNode = mst.new Node();
            for (int j = 0; j < adjList[i].length; j++) {
                Node node = mst.new Node();
                for (int k = 0; k < adjList[i][j].length; k++) node.keys.add(adjList[i][j][k]);
                if (hm.get(node.keys) == null) {
                    hm.put(node.keys, node);
                    if (root == null) root = node;
                }
                // if node has already been built
                node = hm.get(node.keys);
                if (j == 0) parentNode = node;
                else parentNode.children.add(node);
            }
        }
        System.out.println(mst.getMaxMST(root));
    }
}