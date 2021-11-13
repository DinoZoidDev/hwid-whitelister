package me.dinozoid.whitelister;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class Start {

    public static void main(String[] args) throws InterruptedException {
        StringSelection selection = new StringSelection(LicenceUtil.licence());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        System.out.println("Your HWID has been copied to your clipboard.");
        Thread.sleep(10000);
    }

}
