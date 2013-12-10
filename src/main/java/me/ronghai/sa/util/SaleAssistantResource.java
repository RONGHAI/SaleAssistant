/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ronghai.sa.util;

/**
 *
 * @author L5M
 */
public class SaleAssistantResource {

    private static java.util.ResourceBundle resources = java.util.ResourceBundle.getBundle("i18n/sa"); // NOI18N

    public final static String getString(String key) {
        return resources.getString(key);
    }

    public final static String[] getStringArray(String key) {
        return resources.getStringArray(key);
    }
}
