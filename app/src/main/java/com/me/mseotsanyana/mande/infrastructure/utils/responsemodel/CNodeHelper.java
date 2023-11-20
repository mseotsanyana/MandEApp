package com.me.mseotsanyana.mande.infrastructure.utils.responsemodel;

import com.me.mseotsanyana.mande.application.structures.CIndexedLinkedHashMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2017/02/24.
 */

class CNodeHelper {

    static CNode convertTreeModel2NodeModel(CTreeModel treeModel) throws IllegalAccessException {
        CNode node;
        String childID = null;
        String parentID = null;

        Class<? extends CTreeModel> clazz = treeModel.getClass();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.getAnnotation(INodeChildID.class) != null) {
                field.setAccessible(true);
                childID = (String) field.get(treeModel);
            }

            if (field.getAnnotation(INodeParentID.class) != null) {
                field.setAccessible(true);
                parentID = (String) field.get(treeModel);
            }
        }
        node = new CNode(childID, parentID);
        node.setTreeModelObject(treeModel);
        return node;
    }

    /**
     * takes a list of cTreeModel objects and convert them to cNode objects
     */
    static List<CNode> convertTreeModels2Nodes(CIndexedLinkedHashMap<String, CTreeModel> treeModels)
            throws IllegalAccessException, IllegalArgumentException {
        List<CNode> nodes = new ArrayList<>();
        CNode node;
        for (CTreeModel model : treeModels.values()) {
            node = convertTreeModel2NodeModel(model);
            nodes.add(node);
        }
        return nodes;
    }

    /**
     * each node is assigned a set of children and a parent accordingly - by assigning values
     * to 'parent node' and 'children list of nodes'
     */
    private static void bindHierarchy(List<CNode> nodes) {
        for (CNode parent : nodes) {
            for (CNode child : nodes) {
                if ((Objects.equals(child.getParentID(), parent.getChildID())) &&
                        (!parent.getChildren().contains(child))) {
                    parent.getChildren().add(child);
                } else if (child.getChildID().equals(parent.getParentID())) {
                    parent.setParent(child);
                }
            }
        }
    }

    /**
     * sort by adding parents and then their children
     */
    static List<CNode> getSortedNodes(List<CNode> nodes) throws IllegalArgumentException {
        bindHierarchy(nodes);
        List<CNode> rootNodes = getRootNodes(nodes);

        List<CNode> result = new ArrayList<>();
        for (CNode node : rootNodes) {
            addNode(result, node);
        }
        return result;
    }

    /**
     * add root node and their children to result by recursion
     */
    private static void addNode(List<CNode> result, CNode node) {
        result.add(node);
        if (node.isLeaf()) {
            return;
        }
        for (CNode child : node.getChildren()) {
            addNode(result, child);
        }
    }

    /**
     * remove node and its children to result by recursion
     */
    static void removeNode(List<CNode> result, CNode node) {
        result.remove(node);
        if (node.isLeaf()) {
            return;
        }
        for (CNode child : node.getChildren()) {
            removeNode(result, child);
        }
    }

    static void setExpandLevel(List<CNode> list, int expandLevel) {
        for (CNode node : list) {
            if (expandLevel >= 0) {
                node.setExpand(node.getLevel() < expandLevel);
            }
        }
    }

    /**
     * create a list of root nodes and/or children of the root node if its 'isExpand'
     * set to true.
     */
    static List<CNode> filterVisibleNodes(List<CNode> nodes) {
        List<CNode> result = new ArrayList<>();
        for (CNode node : nodes) {
            if (node.isRoot() || node.isParentExpand()) {
                result.add(node);
            }
        }
        return result;
    }

    public static List<CNode> getRootNodes(List<CNode> nodes) {
        List<CNode> root = new ArrayList<>();
        for (CNode node : nodes) {
            //Log.d("ROOT Nodes ",""+node.isRoot());

            if (node.isRoot()) {
                root.add(node);
            }
        }
        return root;
    }

}
