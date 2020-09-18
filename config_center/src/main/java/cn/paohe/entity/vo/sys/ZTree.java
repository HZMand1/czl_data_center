package cn.paohe.entity.vo.sys;

import cn.paohe.vo.framework.IfQuery;

import java.util.List;

/**TODO  架构树
 * @author:      黄芝民
 * @date:        2020年10月23日 下午5:38:54
 * @version      V1.0   
 * @copyright    广东跑合中药材有限公司 Copyright (c) 2020
 */
public class ZTree<T> extends IfQuery{
    private static final long serialVersionUID = 1L;
    /**********iview tree属性**************/

    /**
     * 是否展开直子节点
     */
    private boolean expand = false;

    /**
     * 禁掉响应
     */
    private boolean disabled = false;
    /**
     * 禁掉 checkbox
     */
    private boolean disableCheckbox = false;
    /**
     * 是否选中子节点
     */
    private boolean selected = false;
    /**
     * 是否勾选(如果勾选，子节点也会全部勾选)
     */
    private boolean checked = false;

    private boolean leaf = false;
    /**
     * ztree属性
     */

    private List<?> list;

    /**
     * 子节点属性数组
     */
    private List<?> children;
    
    /** 
     * @Fields parent : 父节点 
     */ 
    private T parent;

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisableCheckbox() {
        return disableCheckbox;
    }

    public void setDisableCheckbox(boolean disableCheckbox) {
        this.disableCheckbox = disableCheckbox;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<?> getChildren() {
        return children;
    }

    public void setChildren(List<?> children) {
        this.children = children;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

	public T getParent() {
		return parent;
	}

	public void setParent(T parent) {
		this.parent = parent;
	}
}
