package androidmads.updatehandler.app;

public interface UpdateListener {
    void onUpdateFound(boolean newVersion, String whatsNew);
}
