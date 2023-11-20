package com.me.mseotsanyana.mande.infrastructure.utils.responsemodel;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by mseotsanyana on 2017/02/24.
 */

public class CNode {
    private String childID;
    private String parentID;
    private boolean isVisited;
    private boolean isExpand;
    private CNode parent;
    private ArrayList<CNode> children;
    private CTreeModel CTreeModel; /* for keeping data for this node */

    public CNode(String childID, String parentID) {
        this.childID  = childID;
        this.parentID = parentID;
        this.isExpand = false;
        this.isVisited = false;
        this.parent = null;
        this.children = new ArrayList<>();
    }

    public CNode(String childID, String parentID, CNode parent) {
        this.childID  = childID;
        this.parentID = parentID;
        this.isExpand = false;
        this.isVisited = false;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public String getChildID() {
        return childID;
    }

    public void setChildID(String childID) {
        this.childID = childID;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    /**
     * get hierarchy level
     */
    public int getLevel() {
        return isRoot() ? 0 : parent.getLevel() + 1;
    }

    public boolean isExpand() {
        return this.isExpand;
    }

    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {
            for (CNode node : children) {
                node.setExpand(false);
            }
        }
    }

    public CNode getParent() {
        return this.parent;
    }

    public void setParent(CNode parent) {
        this.parent = parent;
    }

    public ArrayList<CNode> getChildren() {
        return children;
    }

    public int numberOfChildren(){
        ArrayList<CNode> result = getChildren();
        return result.size();
    }

    public void setChildren(ArrayList<CNode> children) {
        this.children = children;
    }

    public CTreeModel getTreeModelObject(){
        return this.CTreeModel;
    }

    public void setTreeModelObject(CTreeModel CTreeModel){
        this.CTreeModel = CTreeModel;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public boolean isParentExpand() {
        if (isRoot()){
            return false;
        }
        return parent.isExpand();
    }

    public boolean isLeaf() {
        return children.size() == 0;
    }

    @NonNull
    @Override
    public String toString() {
        return "\n\n ChildID: " + this.childID +
                "\n ParentID: " + this.parentID +
                "\n isVisited: " + this.isVisited +
                "\n isExpand: " + this.isExpand +
                "\n Number of Children: " + this.children.size();
    }
}
