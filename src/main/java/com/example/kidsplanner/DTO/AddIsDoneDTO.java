package com.example.kidsplanner.DTO;


public class AddIsDoneDTO {
    private Long periodActivityId;
    private Boolean isDoneParent;
    private Boolean isDoneChild;

    public AddIsDoneDTO(Long periodActivityId, Boolean isDoneParent, Boolean isDoneChild) {
        this.periodActivityId = periodActivityId;
        this.isDoneParent = isDoneParent;
        this.isDoneChild = isDoneChild;
    }

    public AddIsDoneDTO() {
    }

    public Long getPeriodActivityId() {
        return periodActivityId;
    }

    public void setPeriodActivityId(Long periodActivityId) {
        this.periodActivityId = periodActivityId;
    }

    public Boolean getDoneParent() {
        return isDoneParent;
    }

    public void setDoneParent(Boolean doneParent) {
        isDoneParent = doneParent;
    }

    public Boolean getDoneChild() {
        return isDoneChild;
    }

    public void setDoneChild(Boolean doneChild) {
        isDoneChild = doneChild;
    }
}
