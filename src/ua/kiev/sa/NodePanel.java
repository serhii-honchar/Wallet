package ua.kiev.sa;

import java.util.ArrayList;

import java.util.Enumeration;
import java.util.HashMap;

import java.util.Hashtable;

import java.util.TreeMap;

import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class NodePanel extends DefaultMutableTreeNode {

    public NodePanel() {
        XMLAnalize xmlAnalize = new XMLAnalize();
        rootMap = xmlAnalize.getTm();
        rootTree("Категории");
        tree = new JTree(model);
        System.out.println(tree);
        System.out.println("++++++");
        System.out.println(model.toString());
        tree.setShowsRootHandles(true);
//        tree.setRootVisible(false);
        tree.setRowHeight(50);
//        tree.
        tree.setEditable(true);
    }


    private void rootTree(String string) {

        root = new DefaultMutableTreeNode(string); //катего
        model = new DefaultTreeModel(root);

        al1 = new ArrayList<String>();
        //      root.removeAllChildren();
        al1 = rootMap.get("Категории"); //алк+гиг+....
        node0 = new DefaultMutableTreeNode(al1.get(0)); // алк
        addNewNodes(root, al1); //кат, алк+гиг
        al2 = new ArrayList<String>();
        al2 = rootMap.get(node0.toString());
        addNewNodes(node0, al2);
    }

    public static void addNewNodes(DefaultMutableTreeNode currNode,
                                   ArrayList arr) {
        //кьтегор   new
        //катег      алког +гигиена
        // алк        вино...
        //      System.out.println("currNode " + currNode);

        for (int i = 0; i < arr.size(); i++) {
            DefaultMutableTreeNode childNode =
                    new DefaultMutableTreeNode(arr.get(i)); //алк     |вино
            //          System.out.println(arr.get(i).toString());
            model.insertNodeInto(childNode, currNode,
                    currNode.getChildCount());
            if (rootMap.get(childNode.toString()) != null) { //алк=вино...
                //              System.out.println("++++++");
                al2 = rootMap.get(childNode.toString()); // вино...
                addNewNodes(childNode, al2);
            }
        }

    }

    public static void addNewNode(DefaultMutableTreeNode _currNode,
                                  DefaultMutableTreeNode _subNode) {
        currNode = _currNode;
        subNode = _subNode;
        model.insertNodeInto(subNode, (MutableTreeNode)currNode,
                currNode.getChildCount());

        //            // Отображение нового узла.
        TreeNode[] nodes = model.getPathToRoot(subNode);
        TreePath path = new TreePath(nodes);
        tree.scrollPathToVisible(path);

    }

    public static void addNewList(DefaultMutableTreeNode _currNode,
                                  DefaultMutableTreeNode _subNode) {
        currNode = _currNode;
        subNode = _subNode;
        DefaultMutableTreeNode child = new DefaultMutableTreeNode();
        int count = 0;
        for (int i = 0; i < root.getChildCount(); i++) {
            if (root.getChildAt(i).toString().equals(currNode.toString())) {
                child = (DefaultMutableTreeNode)root.getChildAt(i);
                count = child.getChildCount();
            }

        }
        model.insertNodeInto(subNode, child, count);

        TreeNode[] nodes = model.getPathToRoot(subNode);
        TreePath path = new TreePath(nodes);
        tree.scrollPathToVisible(path);

    }

    public static void deleteNode(DefaultMutableTreeNode dmtn) {

    }

    public static void deleteItem(java.awt.event.ActionEvent evt, Object obj) {
        DefaultMutableTreeNode selectedNode =
                (DefaultMutableTreeNode)obj;

        if (selectedNode != null && selectedNode.getParent() != null) {
            model.removeNodeFromParent(selectedNode);


        }
    }

    private static DefaultMutableTreeNode currNode;
    private static DefaultMutableTreeNode subNode;
    static ArrayList<String> al2 = new ArrayList<String>();
    static DefaultMutableTreeNode node0;
    static ArrayList<String> al1 = new ArrayList<String>();
    static TreeMap<String, ArrayList<String>> rootMap =
            new TreeMap<String, ArrayList<String>>();
    ArrayList<String> value = new ArrayList<String>();
    static DefaultMutableTreeNode root;
    private static DefaultTreeModel model;
    private static JTree tree;

    public void setTree(JTree tree) {
        this.tree = tree;
    }

    public JTree getTree() {
        return tree;
    }

    public void update() {

    }


    public void setModel(DefaultTreeModel model) {
        this.model = model;
    }

    public DefaultTreeModel getModel() {
        return model;
    }

    public void setRoot(DefaultMutableTreeNode root) {
        this.root = root;
    }

    public DefaultMutableTreeNode getRoot() {
        return root;
    }

    public void setRootMap(TreeMap<String, ArrayList<String>> rootMap) {
        NodePanel.rootMap = rootMap;
    }

    public TreeMap<String, ArrayList<String>> getRootMap() {
        return rootMap;
    }

    public void updateTree() {
        new NodePanel();
        System.out.println("OK");
    }
}
