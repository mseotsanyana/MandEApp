package com.me.mseotsanyana.treeadapterlibrary;

import java.util.ArrayList;

/**
 * Created by mseotsanyana on 2017/02/24.
 */

public class cNode {
    private String childID;
    private String parentID;
    private boolean isVisited;
    private boolean isExpand;
    private cNode parent = null;
    private ArrayList<cNode> children = new ArrayList<>();
    private Object object; /* for keeping data for this node */

    //private int i = 0;
    public cNode(String childID, String parentID) {
        this.childID  = childID;
        this.parentID = parentID;
        this.isExpand = false;
        this.isVisited = false;
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
        //Log.d("Root Level",""+i++);
        return isRoot() ? 0 : parent.getLevel() + 1;
    }

    public boolean isExpand() {
        return this.isExpand;
    }

    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {
            for (cNode node : children) {
                node.setExpand(false);
            }
        }
    }

    public cNode getParent() {
        return this.parent;
    }

    public void setParent(cNode parent) {
        this.parent = parent;
        //Log.d("Parent -> Chile",""+this.parent.getId()+" -> "+this.parent.getId());
    }

    public ArrayList<cNode> getChildren() {
        return children;
    }

    public int numberOfChildren(){
        ArrayList<cNode> result = getChildren();
        return result.size();
    }

    public void setChildren(ArrayList<cNode> children) {
        this.children = children;
    }

    public Object getObj(){
        return this.object;
    }

    public void setObj(Object object){
        this.object = object ;
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
}
