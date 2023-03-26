package com.bw.reference.pojo;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
public class TwoParamGroupedCount<T, S> {
    private final T GroupId;
    private final S subGroupId;
    private final Long itemCount;

    public TwoParamGroupedCount(T groupId, S subGroupId, Long itemCount) {
        GroupId = groupId;
        this.subGroupId = subGroupId;
        this.itemCount = itemCount;
    }

    public T getGroupId() {
        return GroupId;
    }

    public S getSubGroupId() {
        return subGroupId;
    }

    public Long getItemCount() {
        return itemCount;
    }
}
