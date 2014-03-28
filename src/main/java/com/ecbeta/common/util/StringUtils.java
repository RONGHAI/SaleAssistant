package com.ecbeta.common.util;

public class StringUtils  extends org.apache.commons.lang.StringUtils{
    public static String subString(String str, int end){
        return subString(str, 0, end, "...");
    }
        
    public static String subString(String str, int start, int end, String suffix){
        if(str == null) return str;
        try{
            return str.substring(start, end > str.length() ? str.length() : end) + (end < str.length() ? suffix : "");
        }catch(Exception e){
            return str;
        }
    }
    
    public static int length(String str){
        if(str==null) return 0;
        return str.length();
    }
    
    
    public static String subString(String str,int start,int end ){
        return  subString(str, start, end, "");
    }
    
    
    public static String join_(Object... arr){
        return join(arr, "_");
    }
    
    public static String join(int[] arrays, String separator){
        if(arrays == null ) return null;
        Integer[] i = new Integer[arrays.length];
        for(int k = 0; k < arrays.length; k++){
            i[k] = arrays[k];
        }
        return join(i, separator);
    }
    
       
    public static String join(boolean[] arrays, String separator){
        if(arrays == null ) return null;
        Boolean[] i = new Boolean[arrays.length];
        for(int k = 0; k < arrays.length; k++){
            i[k] = arrays[k];
        }
        return join(i, separator);
    }
    
    public static String join(double[] arrays, String separator){
        if(arrays == null ) return null;
        Double[] i = new Double[arrays.length];
        for(int k = 0; k < arrays.length; k++){
            i[k] = arrays[k];
        }
        return join(i, separator);
    }
    
    public static String join(long[] arrays, String separator){
        if(arrays == null ) return null;
        Long[] i = new Long[arrays.length];
        for(int k = 0; k < arrays.length; k++){
            i[k] = arrays[k];
        }
        return join(i, separator);
    }

    public static String join(float[] arrays, String separator){
        if(arrays == null ) return null;
        Float[] i = new Float[arrays.length];
        for(int k = 0; k < arrays.length; k++){
            i[k] = arrays[k];
        }
        return join(i, separator);
    }
    
    public static String join(short[] arrays, String separator){
        if(arrays == null ) return null;
        Short[] i = new Short[arrays.length];
        for(int k = 0; k < arrays.length; k++){
            i[k] = arrays[k];
        }
        return join(i, separator);
    }
    
    
    public static String join(char[] arrays, String separator){
        if(arrays == null ) return null;
        Character[] i = new Character[arrays.length];
        for(int k = 0; k < arrays.length; k++){
            i[k] = arrays[k];
        }
        return join(i, separator);
    }
}
