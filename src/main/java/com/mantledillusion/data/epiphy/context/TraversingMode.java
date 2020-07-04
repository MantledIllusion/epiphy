package com.mantledillusion.data.epiphy.context;

/**
 * The mode of how to traverse over {@link com.mantledillusion.data.epiphy.ModelPropertyNode}s in a path when contexting.
 */
public enum TraversingMode {

    /**
     * Only context the parents at the base context when contextualizing.
     */
    PARENT(true, false),

    /**
     * Only context the children of the parents at the base context when contextualizing.
     */
    CHILD(false, true),

    /**
     * Context the parents, the children and the children's children recursively from the base context when contextualizing.
     */
    RECURSIVE(true, true);

    private final boolean includeParent;
    private final boolean includeChildren;

    TraversingMode(boolean includeParent, boolean includeChildren) {
        this.includeParent = includeParent;
        this.includeChildren = includeChildren;
    }

    /**
     * Returns whether parents are included in this mode.
     *
     * @return True if parents are included, false otherwise.
     */
    public boolean isIncludeParent() {
        return includeParent;
    }

    /**
     * Returns whether children are included in this mode.
     *
     * @return True if children are included, false otherwise.
     */
    public boolean isIncludeChildren() {
        return includeChildren;
    }
}
