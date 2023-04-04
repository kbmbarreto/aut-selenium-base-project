package br.com.lambdateam.core;

public class Properties {

    public static boolean QUIT_BROWSER = false;
    public static Browsers browser = Browsers.CHROME;

    public enum Browsers {
        CHROME,
        FIREFOX,
        EDGE
    }
}
