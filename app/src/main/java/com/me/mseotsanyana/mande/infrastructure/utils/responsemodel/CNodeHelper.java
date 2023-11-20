package com.me.mseotsanyana.treeadapterlibrary;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/24.
 */

class cNodeHelper {
    private static cNode convertData2Node(cTreeModel data) throws IllegalAccessException {
        cNode node;
        //--int childID  = -1;
        //--int parentID = -1;

        String childID  = null;
        String parentID = null;

        Class clazz = data.getClass();

        Field[] fields = clazz.getDeclaredFields();
        //Log.d("Id", ""+fields[0]);
        //Log.d("Id", ""+fields[1]);
       // Log.d("Id", ""+fields[2]);

        for (Field field : fields) {
            if (field.getAnnotation(cNodeChildID.class) != null) {
                //cNodeChildID annotation = field.getAnnotation(cNodeChildID.class);

                /*if (field.getType() == int.class) {
                    Log.e("MARR", "  field.getType():::::int");
                } else if (field.getType() == Integer.class) {
                    Log.e("MARR", "  field.getType()::::::Integer");
                }*/

                field.setAccessible(true);
                //--childID = field.getInt(data);
                childID = (String) field.get(data);

                //Log.d("Id", ""+id);

            }

            if (field.getAnnotation(cNodeParentID.class) != null) {
                field.setAccessible(true);
                //--parentID = field.getInt(data);
                parentID = (String) field.get(data);
                //Log.d("pId", ""+pId);
            }
        }
        //--node = new cNode(childID, parentID);
        node = new cNode(childID, parentID);
        node.setObj(data);
        return node;
    }

    /**
     * each node is assigned a set of children and a parent accordingly - by assigning values
     * to 'parent node' and 'children list of nodes'
     */
    private static void bindHierarchy(List<cNode> nodes) {
        for (cNode n :  nodes ) {
            for (cNode m : nodes) {
                if ((m.getParentID() == n.getChildID()) && (!n.getChildren().contains(m))) {
                    n.getChildren().add(m);
                } else if (m.getChildID() == n.getParentID()) {
                    n.setParent(m);
                }
            }
        }
    }

    /**
     * takes a list of cTreeModel objects and convert them to cNode objects
     */
    static List<cNode> convertDataSet2Nodes(List<cTreeModel> treeModels) throws
            IllegalAccessException, IllegalArgumentException {
        List<cNode> nodes = new ArrayList<>();
        cNode node;
        for (cTreeModel model : treeModels) {
            node = convertData2Node(model);
            nodes.add(node);
        }
        return nodes;
    }

    /**
     * sort by adding parents and then their children
     */
    static List<cNode> getSortedNodes(List<cNode> nodes) throws IllegalArgumentException {
        bindHierarchy(nodes);
        List<cNode> rootNodes = getRootNodes(nodes);

        List<cNode> result = new ArrayList<>();
        for (cNode node : rootNodes) {
            addNode(result, node);
        }
        return result;
    }

    /**
     * add root node and their children to result by recursion
     */
    private static void addNode(List<cNode> result, cNode node ) {
        result.add(node);
        if (node.isLeaf()) {
            return;
        }
        for (cNode child : node.getChildren()) {
            addNode(result, child);
        }
    }

    static void setExpandLevel(List<cNode> list, int expandLevel){
        for(cNode node : list){
            if (expandLevel >= 0) {
                if (node.getLevel() < expandLevel) {
                    node.setExpand(true);
                } else {
                    node.setExpand(false);
                }
            }
        }
    }

    /**
     * create a list of root nodes and/or children of the root node if its 'isExpand'
     * set to true.
     */
    static List<cNode> filterVisibleNodes(List<cNode> nodes) {
        List<cNode> result = new ArrayList<>();
        for (cNode node : nodes) {
            if (node.isRoot() || node.isParentExpand()) {
                result.add(node);
            }
        }
        return result;
    }

    static List<cNode> filterRetainVisibleNodes(List<cNode> allNodes, List<cNode> filteredNodes){
        List<cNode> result = new ArrayList<>();

        /* retain visible nodes */
        for (cNode n : allNodes) {
            for (cNode m : filteredNodes) {
                /*FIXME: if n is a parent and is currently expanded, retain state !!! */
                if (n.getParentID() == m.getParentID() && n.getChildID() == m.getChildID()){
                    n.setExpand(true);
                }
            }
        }

        /* filter visible nodes */
        for (cNode node : allNodes) {
            if (node.isRoot() || node.isParentExpand()) {
                result.add(node);
            }
        }
        return result;
    }

    private static List<cNode> getRootNodes(List<cNode> nodes) {
        List<cNode> root = new ArrayList<>();
        for (cNode node : nodes) {
            if (node.isRoot()) {
                root.add(node);
            }
        }
        return root;
    }
}
