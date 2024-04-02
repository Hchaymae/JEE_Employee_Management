package org.jee.system.model;

public enum Post {



    MANAGER("Manager"),
    DEV("Dev"),
    DEVOPS("Devops"),
    TESTER("Tester"),
    TECH_LEAD("Tech_Lead");
    private final String label;

    Post(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
