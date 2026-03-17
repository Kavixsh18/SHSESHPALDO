/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.avl;

/**
 *
 * @author readi
 */
import java.util.Scanner;

class Node {
    int key, height;
    Node left, right;

    Node(int d) {
        key = d;
        height = 1;
    }
}

public class Avl {

    Node root;

    int height(Node n) {
        if (n == null)
            return 0;
        return n.height;
    }

    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    int getBalance(Node n) {
        if (n == null)
            return 0;
        return height(n.left) - height(n.right);
    }

    Node rightRotate(Node y) {
        Node x = y.left;
        Node t2 = x.right;

        x.right = y;
        y.left = t2;

        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        return x;
    }

    Node leftRotate(Node x) {
        Node y = x.right;
        Node t2 = y.left;

        y.left = x;
        x.right = t2;

        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        return y;
    }

    Node insert(Node node, int key) {

        if (node == null)
            return new Node(key);

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node;

        node.height = 1 + max(height(node.left), height(node.right));

        int balance = getBalance(node);

        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    Node minValue(Node node) {
        Node current = node;
        while (current.left != null)
            current = current.left;
        return current;
    }

    Node delete(Node root, int key) {

        if (root == null)
            return root;

        if (key < root.key)
            root.left = delete(root.left, key);

        else if (key > root.key)
            root.right = delete(root.right, key);

        else {

            if (root.left == null || root.right == null) {

                Node temp = null;

                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;

                if (temp == null) {
                    root = null;
                } else
                    root = temp;

            } else {

                Node temp = minValue(root.right);
                root.key = temp.key;
                root.right = delete(root.right, temp.key);
            }
        }

        if (root == null)
            return root;

        root.height = max(height(root.left), height(root.right)) + 1;

        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    void preorder(Node node) {
        if (node != null) {
            System.out.print(node.key + " ");
            preorder(node.left);
            preorder(node.right);
        }
    }

    void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.key + " ");
            inorder(node.right);
        }
    }

    void postorder(Node node) {
        if (node != null) {
            postorder(node.left);
            postorder(node.right);
            System.out.print(node.key + " ");
        }
    }

    public static void main(String[] args) {

        Avl tree = new Avl();
        Scanner sc = new Scanner(System.in);

        int choice;

        do {

            System.out.println("\n=== AVL TREE MENU ===");
            System.out.println("1. Insert node");
            System.out.println("2. Delete node");
            System.out.println("3. Print traversals");
            System.out.println("4. Exit");
            System.out.print("Choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Enter values to insert (ex: 1,2,3,4): ");
                    String insertInput = sc.nextLine();

                    String[] insertValues = insertInput.split(",");

                    for (String val : insertValues) {
                        int num = Integer.parseInt(val.trim());
                        tree.root = tree.insert(tree.root, num);
                    }

                    break;

                case 2:

                    System.out.print("Enter values to delete (ex: 1,2): ");
                    String deleteInput = sc.nextLine();

                    String[] deleteValues = deleteInput.split(",");

                    for (String val : deleteValues) {
                        int num = Integer.parseInt(val.trim());
                        tree.root = tree.delete(tree.root, num);
                    }

                    break;

                case 3:

                    System.out.print("Preorder: ");
                    tree.preorder(tree.root);

                    System.out.print("\nInorder: ");
                    tree.inorder(tree.root);

                    System.out.print("\nPostorder: ");
                    tree.postorder(tree.root);
                    System.out.println();

                    break;

                case 4:
                    System.out.println("Program terminated.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 4);

        sc.close();
    }
}