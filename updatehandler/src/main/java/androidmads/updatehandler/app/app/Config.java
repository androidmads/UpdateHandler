package androidmads.updatehandler.app.app;

/**
 * Created by Mushtaq
 * Created on 28-05-2016.
 */
public class Config {

    public static String PLAY_STORE_ROOT_URL = "https://play.google.com/store/apps/details?id=";
    public static final String PLAY_STORE_HTML_TAGS_TO_GET_RIGHT_POSITION = "itemprop=\"softwareVersion\"> ";
    public static final String PLAY_STORE_HTML_TAGS_TO_REMOVE_USELESS_CONTENT = "  </div> </div>";
    public static final String PLAY_STORE_PACKAGE_NOT_PUBLISHED_IDENTIFIER = "We're sorry, the requested URL was not found on this server.";
    public static final String ROOT_PLAY_STORE_DEVICE = "market://details?id=";

    public static final String PLAY_STORE_HTML_TAGS_TO_DIV_WHATS_NEW_START = "<div class=\"recent-change\">";
    public static final String PLAY_STORE_HTML_TAGS_TO_DIV_WHATS_NEW_END = "</div>";
    public static final String PLAY_STORE_VARIES_W_DEVICE = "Varies with device";
    public static final String PLAY_STORE_WHATS_NEW = "WHAT'S NEW";

    public static final String KEY_COUNT = "count";
    public static final String KEY_PREF = "pref";

}
