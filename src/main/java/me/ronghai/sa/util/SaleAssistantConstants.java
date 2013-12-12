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
public class SaleAssistantConstants {
    public static final java.awt.Dimension MIN_DIMENSION = new java.awt.Dimension(800, 600), 
            MAX_DIMENSION = new java.awt.Dimension(2147483647, 2147483647);
    public static final String[] ContextFiles = new String[]{"spring-context.xml", "spring-bean.xml"};
    public static final String ResourceBundleFile = "i18n/sa";
    
    public static final boolean MAC_OS_X = System.getProperty("os.name").toLowerCase().indexOf("mac os x") >= 0;
}
