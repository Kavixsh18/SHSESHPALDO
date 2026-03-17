/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package labactivity.avltree_version_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author yj429
 */
public class AVLtree_version_1 {

    static class Node {
        int value;
        int height;
        Node left, right;

        Node(int val) {
            value = val;
            height = 1;
        }
    }

    static Node root = null;
    static ArrayList<Integer> searchedNodes = new ArrayList<>();
    static ArrayList<Integer> searchedIndexes = new ArrayList<>();

    static int maxIndex = 0;

    // =============================
    // AVL UTILITIES
    // =============================

    static int height(Node n) {
        if (n == null)
            return 0;
        return n.height;
    }

    static int getBalance(Node n) {
        if (n == null)
            return 0;
        return height(n.left) - height(n.right);
    }

    static Node rightRotate(Node y) {

        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    static Node leftRotate(Node x) {

        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // =============================
    // INSERT WITH AVL BALANCING
    // =============================

    public static Node insert(Node node, int value) {

        if (node == null)
            return new Node(value);

        if (value < node.value)
            node.left = insert(node.left, value);

        else if (value > node.value)
            node.right = insert(node.right, value);

        else {
            System.out.println("Duplicate node " + value + " discarded.");
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        // LL
        if (balance > 1 && value < node.left.value)
            return rightRotate(node);

        // RR
        if (balance < -1 && value > node.right.value)
            return leftRotate(node);

        // LR
        if (balance > 1 && value > node.left.value) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL
        if (balance < -1 && value < node.right.value) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // =============================
    // DELETE WITH AVL BALANCING
    // =============================

    public static Node delete(Node node, int value) {

        if (node == null) {
            System.out.println("Value " + value + " not found.");
            return null;
        }

        if (value < node.value)
            node.left = delete(node.left, value);

        else if (value > node.value)
            node.right = delete(node.right, value);

        else {

            if (node.left == null || node.right == null) {

                Node temp = null;

                if (temp == node.left)
                    temp = node.right;
                else
                    temp = node.left;

                if (temp == null) {
                    node = null;
                } else
                    node = temp;

            } else {

                Node succ = node.right;

                while (succ.left != null)
                    succ = succ.left;

                node.value = succ.value;
                node.right = delete(node.right, succ.value);
            }
        }

        if (node == null)
            return node;

        node.height = Math.max(height(node.left), height(node.right)) + 1;

        int balance = getBalance(node);

        // LL
        if (balance > 1 && getBalance(node.left) >= 0)
            return rightRotate(node);

        // LR
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RR
        if (balance < -1 && getBalance(node.right) <= 0)
            return leftRotate(node);

        // RL
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // =============================
    // ARRAY REPRESENTATION
    // =============================

    static int[] buildArray(Node node) {

        int size = 100;
        int[] arr = new int[size];

        Arrays.fill(arr, 0);

        maxIndex = 0;

        fillArray(node, arr, 0);

        if (maxIndex == 0 && node == null)
            return new int[0];

        int k = (int) (Math.log(maxIndex + 1) / Math.log(2)) + 1;
        int finalSize = (int) Math.pow(2, k) - 1;

        return Arrays.copyOf(arr, finalSize);
    }

    static void fillArray(Node node, int[] arr, int index) {

        if (node == null || index >= arr.length)
            return;

        arr[index] = node.value;

        if (index > maxIndex)
            maxIndex = index;

        fillArray(node.left, arr, 2 * index + 1);
        fillArray(node.right, arr, 2 * index + 2);
    }

    // =============================
    // TRAVERSALS
    // =============================

    public static void inorder(Node node) {

        if (node == null)
            return;

        inorder(node.left);
        System.out.print(node.value + " ");
        inorder(node.right);
    }

    public static void preorder(Node node) {

        if (node == null)
            return;

        System.out.print(node.value + " ");
        preorder(node.left);
        preorder(node.right);
    }

    public static void postorder(Node node) {

        if (node == null)
            return;

        postorder(node.left);
        postorder(node.right);
        System.out.print(node.value + " ");
    }

    public static void displayTraversals() {

        System.out.println("\n-------------------------------------------");
        System.out.print("PREORDER  : ");
        preorder(root);
        System.out.println();

        System.out.print("INORDER   : ");
        inorder(root);
        System.out.println();

        System.out.print("POSTORDER : ");
        postorder(root);
        System.out.println();
        System.out.println("-------------------------------------------\n");
    }

    // =============================
    // MAIN
    // =============================

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n[1] INPUT NODE");
            System.out.println("[2] DELETE NODE");
            System.out.println("[3] EXIT PROGRAM");

            System.out.print("Enter your choice: ");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {

                case 1:

                    System.out.print("Input node(s) separated by comma: ");
                    String input = sc.nextLine();

                    try {

                        String[] nums = input.split(",");

                        for (String n : nums) {

                            int value = Integer.parseInt(n.trim());
                            root = insert(root, value);
                        }

                        int[] arr = buildArray(root);

                        printTree(arr);
                        displayTraversals();

                    } catch (Exception e) {
                        System.out.println("Invalid format.");
                    }

                    break;

                case 2:

                    System.out.print("Delete node(s) separated by comma: ");
                    String del = sc.nextLine();

                    try {

                        String[] nums = del.split(",");

                        for (String n : nums) {

                            int value = Integer.parseInt(n.trim());
                            root = delete(root, value);
                        }

                        int[] arr = buildArray(root);

                        printTree(arr);
                        displayTraversals();

                    } catch (Exception e) {
                        System.out.println("Invalid format.");
                    }

                    break;

                case 3:

                    System.out.println("Program terminated.");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void printTree(int[] arr) {

        if (arr.length == 0) {
            System.out.println("Tree is empty.");
            return;
        }

        System.out.print("\nAVL TREE = {");

        for (int i = 0; i < arr.length; i++) {

            System.out.print(arr[i]);

            if (i < arr.length - 1)
                System.out.print(",");
        }

        System.out.println("}");
    }
}