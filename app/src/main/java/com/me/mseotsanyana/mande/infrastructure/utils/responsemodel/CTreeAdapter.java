package com.me.mseotsanyana.mande.infrastructure.utils.responsemodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/20.
 */

abstract public class CTreeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = CTreeAdapter.class.getSimpleName();
    protected Context context;
    protected List<CNode> visibleNodes;
    protected List<CNode> allNodes;

    public CTreeAdapter(Context context, int expLevel) {
        this.context = context;
        try {
            this.sort(expLevel);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Error " + e.getMessage());
        }
    }

    public CTreeAdapter(Context context) {
        allNodes = new ArrayList<>();
        this.context = context;
        try {
            this.sort();
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Error " + e.getMessage());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return OnCreateTreeViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        OnBindTreeViewHolder(viewHolder, position);
    }

    @Override
    public int getItemViewType(int position) {
        CTreeModel model = visibleNodes.get(position).getTreeModelObject();
        return model.getType();
    }

    @Override
    public int getItemCount() {
        return visibleNodes.size();
    }

    /**
     * These are abstract methods
     */
    public abstract RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType);

    public abstract void OnBindTreeViewHolder(RecyclerView.ViewHolder viewHolder, int position);

    public void sort() throws IllegalAccessException {
        sort(-1);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void sort(int defaultExpandLevel) throws IllegalAccessException, IllegalArgumentException {
        /* List<CNode> unsortedNodes = CNodeHelper.convertTreeModels2Nodes(treeModels);,allNodes = CNodeHelper.getSortedNodes(unsortedNodes);*/
        CNodeHelper.setExpandLevel(allNodes, defaultExpandLevel);
        visibleNodes = CNodeHelper.filterVisibleNodes(allNodes);
        notifyDataSetChanged();
    }

//    public void setTreeModel(CIndexedLinkedHashMap<String, CTreeModel> treeModels) throws IllegalAccessException {
//        this.treeModels = treeModels;
//        try {
//            this.sort();
//        } catch (IllegalAccessException e) {
//            Log.e(TAG, "Error " + e.getMessage());
//        }
//    }
//
//    public void setTreeModel(CIndexedLinkedHashMap<String, CTreeModel> treeModels, int defaultExpandLevel) throws
//            IllegalAccessException {
//        this.treeModels = treeModels;
//        try {
//            this.sort(defaultExpandLevel);
//        } catch (IllegalAccessException e) {
//            Log.e(TAG, "Error " + e.getMessage());
//        }
//    }

    /**************************************** CRUD methods *****************************************
     *
     * add a tree model to an existing allNodes list without overriding the list
     *
     * @param treeModel tree model
     * @throws IllegalAccessException exception
     */
    public void addTreeModel2TreeAdapter(CTreeModel treeModel) throws IllegalAccessException {
        CNode node = CNodeHelper.convertTreeModel2NodeModel(treeModel);
        addNodeToAllNodes(node);
    }

    public void modifyTreeModelInTreeAdapter(CTreeModel treeModel) throws IllegalAccessException {
        CNode node = CNodeHelper.convertTreeModel2NodeModel(treeModel);
        modifyNodeInAllNodes(node);
    }

    public void deleteTreeModelInTreeAdapter(CTreeModel treeModel) throws IllegalAccessException {
        CNode node = CNodeHelper.convertTreeModel2NodeModel(treeModel);
        deleteNodeInAllNodes(node);
    }

    /************************************* additional methods *************************************/

    @SuppressLint("NotifyDataSetChanged")
    protected void expandOrCollapse(int position) {
        CNode node = visibleNodes.get(position);
        if (node == null) {
            return;
        }
        if (node.isLeaf()) {
            return;
        }
        node.setExpand(!node.isExpand());
        visibleNodes = CNodeHelper.filterVisibleNodes(allNodes);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void addNodeToAllNodes(CNode node) {
        boolean isDuplicate = false;
        for (CNode curr_node : allNodes) {
            if (curr_node.getChildID().equals(node.getChildID())) {
                isDuplicate = true;
                break;
            }
        }
        if (!isDuplicate) {
            allNodes.add(node);

            allNodes = CNodeHelper.getSortedNodes(allNodes);
            CNodeHelper.setExpandLevel(allNodes, -1);

            visibleNodes = CNodeHelper.filterVisibleNodes(allNodes);
            notifyDataSetChanged();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void modifyNodeInAllNodes(CNode node) {
        for (int i = 0; i <= allNodes.size(); i++) {
            if (allNodes.get(i).getChildID().equals(node.getChildID())) {
                // update node in AllNodes - only tree model changes
                allNodes.get(i).setTreeModelObject(node.getTreeModelObject());
                notifyDataSetChanged();
                break;
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void deleteNodeInAllNodes(CNode node) {
        for (int i = 0; i <= allNodes.size(); i++) {
            if (allNodes.get(i).getChildID().equals(node.getChildID())) {
                allNodes.remove(allNodes.get(i));
                visibleNodes = CNodeHelper.filterVisibleNodes(allNodes);
                notifyDataSetChanged();
                break;
            }
        }
    }

//    public void reloadTreeModels(String treeModelID) {
//        for (CNode node : allNodes) {
//            if (node.getChildID().equals(treeModelID)) {
//                CNodeHelper.removeNode(allNodes, node);
//                break;
//            }
//        }
//        visibleNodes = CNodeHelper.filterVisibleNodes(allNodes);
//
//        Log.d(TAG, "ALL NODES ===== " + allNodes.size());
//        Log.d(TAG, "VISIBLE NODES ===== " + visibleNodes.size());
//        //Log.d(TAG, "TREE MODELS ===== " + treeModels.size());
//
//        notifyDataSetChanged();
//    }

    public void printNodeInAllNodes(String nodeID) {
        StringBuilder builder = new StringBuilder("Node : ");
        for (CNode node : allNodes) {
            if (node.getChildID().equals(nodeID)) {
                builder.append(node);
                break;
            }
        }
        Log.d(TAG, " " + builder);
    }

    public void printAllNodes() {
        StringBuilder nodes = new StringBuilder("All Nodes: ");
        for (CNode node : allNodes) {
            nodes.append(node);
        }
        Log.d(TAG, " " + nodes);
    }

    public void printVisibleNodes() {
        StringBuilder nodes = new StringBuilder("Visible Nodes: ");
        for (CNode node : visibleNodes) {
            nodes.append(node);
        }
        Log.d(TAG, " " + nodes);
    }
}
