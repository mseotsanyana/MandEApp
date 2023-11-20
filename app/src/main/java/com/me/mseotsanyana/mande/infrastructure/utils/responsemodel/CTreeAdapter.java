package com.me.mseotsanyana.treeadapterlibrary;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/20.
 */

abstract public class cTreeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static String TAG = cTreeAdapter.class.getSimpleName();
    protected Context context;
    protected List<cTreeModel> treeModels;
    protected List<cNode> visibleNodes;
    protected List<cNode> allNodes;
    //protected Map<Integer, cDataBinder> dataBinders;

    public cTreeAdapter(Context context, List<cTreeModel> treeModels){
        //super(context, data);
        this.context = context;
        this.treeModels = treeModels;
        try {
            this.sort();
        }catch(IllegalAccessException e){
            Log.e(TAG, "Error "+e.getMessage());
        }
    }

    public cTreeAdapter(Context context, List<cTreeModel> treeModels, int expLevel){
        //super(context, data);
        this.context = context;
        this.treeModels = treeModels;
        try {
            this.sort(expLevel);
        }catch(IllegalAccessException e){
            Log.e(TAG, "Error "+e.getMessage());
        }
    }

    public cTreeAdapter(Context context, CIndexedLinkedHashMap<String, cTreeModel> treeModels){
        //super(context, data);
        this.context = context;
        this.treeModels = treeModels;
        try {
            this.sort();
        }catch(IllegalAccessException e){
            Log.e(TAG, "Error "+e.getMessage());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return OnCreateTreeViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        OnBindTreeViewHolder(viewHolder, position);
    }

    @Override
    public int getItemViewType(int position){
        cTreeModel model = (cTreeModel) visibleNodes.get(position).getObj();
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
    //cTreeModel obj, boolean isLeaf, boolean isExpand, int level);
    //public abstract int getItemTreeViewType(int position);
    //public abstract int getTreeItemCount();

    public void sort() throws IllegalAccessException {
        sort(-1);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void sort(int defaultExpandLevel) throws IllegalAccessException, IllegalArgumentException{
        List<cNode> unsortedNodes = cNodeHelper.convertDataSet2Nodes(treeModels);

        allNodes = cNodeHelper.getSortedNodes(unsortedNodes);
        cNodeHelper.setExpandLevel(allNodes, defaultExpandLevel);
        visibleNodes = cNodeHelper.filterVisibleNodes(allNodes);
        notifyDataSetChanged();
    }

//    public int getMaxParentIndex(){
//        int maxParentIndex = -1;
//        for (int i = 0; i < treeModels.size(); i++){
//            int parentID = treeModels.get(i).getParentID();
//            if(maxParentIndex <= parentID){
//                maxParentIndex = parentID + 1;
//            }
//        }
//
//        return maxParentIndex;
//    }

    /*public void addData(cTreeModel model) throws IllegalAccessException {
        treeModels.add(model);

        cNode node = cNodeHelper.convertData2Node(model) ;
        allNodes.add(node);

        allNodes = cNodeHelper.getSortedNodes(allNodes);
        node.getParent().setExpand(true);
        visibleNodes = cNodeHelper.fliterVisibleNodes(allNodes);
        notifyDataSetChanged();
    }*/

    public void addData(cTreeModel model) throws IllegalAccessException {
        treeModels.add(model);

        List<cNode> unsortedNodes = cNodeHelper.convertDataSet2Nodes(treeModels);

        allNodes = cNodeHelper.getSortedNodes(unsortedNodes);
        cNodeHelper.setExpandLevel(allNodes, -1);
        visibleNodes = cNodeHelper.filterVisibleNodes(allNodes);

        notifyDataSetChanged();
    }

    public void setTreeModel(List<cTreeModel> treeModels) throws IllegalAccessException {
        this.treeModels = treeModels;
        try {
            this.sort();
        }catch(IllegalAccessException e){
            Log.e(TAG, "Error "+e.getMessage());
        }
    }

    public List<cTreeModel> getTreeModel() {
        return this.treeModels;
    }

    protected void expandOrCollapse(int position) {
        cNode node = visibleNodes.get(position);
        if (node == null) {
            return;
        }
        if (node.isLeaf()) {
            return;
        }
        //Log.d("All Nodes ",""+allNodes);
        node.setExpand(!node.isExpand());
        visibleNodes = cNodeHelper.filterVisibleNodes(allNodes);
        //Log.d("Visible Nodes ",""+visibleNodes);
        notifyDataSetChanged();
    }

    /**
     * this takes the new tree model to refresh the list, used to filter
     * the list of tree models in an adapter.
     *
     * @param treeModels
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public void notifyTreeModelChanged(List<cTreeModel> treeModels ) throws IllegalAccessException,
            IllegalArgumentException{

        List<cNode> unsortedNodes = cNodeHelper.convertDataSet2Nodes(treeModels);

        allNodes = cNodeHelper.getSortedNodes(unsortedNodes);
        cNodeHelper.setExpandLevel(allNodes, -1);
        visibleNodes = cNodeHelper.filterVisibleNodes(allNodes);

        notifyDataSetChanged();
    }

    public void notifyTreeModelUpdated(cTreeModel treeModel, int position) throws
            IllegalArgumentException{
        /* get the modified tree model */
        cTreeModel currTreeModel = (cTreeModel) visibleNodes.get(position).getObj();
        /* replaces the old tree model with the new one */
        treeModels.remove(currTreeModel);
        currTreeModel.setModelObject(treeModel.getModelObject());
        treeModels.add(currTreeModel);
        /* override the old obj in the visible nodes */
        visibleNodes.get(position).setObj(currTreeModel);
        /* refresh the list */
        notifyDataSetChanged();
    }

    public void notifyTreeModelDeleted(int position) throws IllegalAccessException,
            IllegalArgumentException{
        cTreeModel treeModel = (cTreeModel) visibleNodes.get(position).getObj();
        /* remove in both lists */
        treeModels.remove(treeModel);

        List<cNode> unsortedNodes = cNodeHelper.convertDataSet2Nodes(treeModels);
        allNodes = cNodeHelper.getSortedNodes(unsortedNodes);
        cNodeHelper.setExpandLevel(allNodes, -1);
        visibleNodes = cNodeHelper.filterVisibleNodes(allNodes);

        //visibleNodes.remove(position);
        /* refresh the list */
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void notifyTreeModelChanged(){
        notifyDataSetChanged();
    }
}
