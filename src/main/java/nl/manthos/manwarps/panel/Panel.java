package nl.manthos.manwarps.panel;

public enum Panel {
    USER_HOME_PANEL("user-home-panel");

    private String key;

    private Panel(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
