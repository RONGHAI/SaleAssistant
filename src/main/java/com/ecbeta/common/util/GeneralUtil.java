package com.ecbeta.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralUtil {
    public static DateFormat df = new SimpleDateFormat("mm:ss");
    public static Pattern RegexExpressionPattern = Pattern.compile("/(.+)/([dixmus]*)");
     public static boolean isRegexExpression(String regexExpress){
         if(regexExpress == null) 
             return false;
         regexExpress = regexExpress.trim();
         return RegexExpressionPattern.matcher(regexExpress).find();
     }
     public static String[] getRegexAndFlag(String regexExpress){
         if(regexExpress == null ) return null;
         regexExpress = regexExpress.trim();
         Matcher  matcher=  RegexExpressionPattern.matcher(regexExpress); 
         if(!matcher.find()){
             return null;
         }
         String regex = matcher.group(1);
         String flags = matcher.group(2);
         return new String[]{regex, flags.trim()};
     }
     
     public static String buildRegexSQL(String column, String expression){
         String[] regexAndFlag = getRegexAndFlag(expression);
         if(regexAndFlag == null){
             return "";
         }
         String regex = regexAndFlag[0];
         regex = regex.replaceAll("'", "''");
         int small = 0; int middle = 0; int large = 0;
         for(int i = 0; i < regex.length(); i++){
             char s = regex.charAt(i);
             
             if(i != 0 ){
                 if(regex.charAt(i - 1) == '\\'){
                     continue;
                 }
             }
             if(s == '('){
                 small ++;
             }else if(s == ')'){
                 small --;
             }else if(s == '['){
                 middle ++;
             }else if(s == ']'){
                 middle --;
             }else if(s == '{'){
                 large ++;
             }else if(s == '}'){
                 large --;
             }
         }
         if(small != 0 || middle != 0 || large != 0){
             return "";
         }
         
         String flags = regexAndFlag[1];
         String flag = "";
         if(flags.contains("i")){
             flag += "i";
         } 
         if(flags.contains("x")){
             flag += "x";
         }
         if(flags.contains("m")){
             flag += "m";
         } 
         if(flags.equals("")){
             return "REGEXP_LIKE( " + column + ", '"+regex+"')";
         }else{
             return "REGEXP_LIKE( " + column + ", '"+regex+"', '" + flags + "')";
         } 
        // REGEXP_LIKE (first_name, '^Ste(v|ph)en$', 'i');
     }
     
     public static Pattern getPattern(String regexExpress/*javascript format*/){ 
         if(regexExpress == null ) return null;
         regexExpress = regexExpress.trim();
         String[] regexAndFlag = getRegexAndFlag(regexExpress);
         if(regexAndFlag == null){
             try{
                 return Pattern.compile( regexExpress);
             }catch(Exception e){
               //  e.printStackTrace();
                 return Pattern.compile( Pattern.quote(regexExpress));
             }
         }
    
         String regex = regexAndFlag[0];
         String flags = regexAndFlag[1];
         int flag = 0;
         if(flags.contains("i")){
             flag = flag | Pattern.CASE_INSENSITIVE;
         }
         if(flags.contains("d")){
             flag = flag | Pattern.UNIX_LINES;
         }
         if(flags.contains("x")){
             flag = flag | Pattern.COMMENTS;
         }
         if(flags.contains("m")){
             flag = flag | Pattern.MULTILINE;
         }
         if(flags.contains("s")){
             flag = flag | Pattern.DOTALL;
         }
         if(flags.contains("u")){
             flag = flag | Pattern.UNICODE_CASE;
         }
         try{
             return Pattern.compile(regex, flag);
         }catch(Exception e){
             return Pattern.compile( Pattern.quote(regex), flag);
         }
         // ?d   * expression&nbsp;<tt>(?d)</tt>. UNIX_LINES= 0x01; 
         // ?i  CASE_INSENSITIVE = 0x02;
         // ?x COMMENTS = 0x04;
         //?m MULTILINE = 0x08;
         //?s  DOTALL = 0x20;
         //?u UNICODE_CASE
         // {@link #CASE_INSENSITIVE}, {@link #MULTILINE}, {@link #DOTALL},
        // *         {@link #UNICODE_CASE}, and {@link #CANON_EQ}
         
          
        
     }
    /* public static void main(String[] s){
         String expression = "/^Yahoo\\[/";
         boolean isRegex = QHTCommonUtil.isRegexExpression(expression);
         System.out.println(isRegex);
         Pattern pattern = getPattern(expression);
         System.out.println(pattern.toString());
         
         Matcher  matcher= pattern.matcher("Yahoo[baidu.com]");
         System.out.println(matcher.find());
         
          // Matcher  matcher= Pattern.compile(pa.toString() ,(caseSensitive ? 0 : Pattern.CASE_INSENSITIVE )).matcher(content); 
      }
     */
   
    public static final int SQL_ERROR_CODE_TABLE_OR_VIEW_NOT_EXIST = 942, SQL_ERROR_CODE_INVALID_IDENTIFIER = 904;
    
    public static String generateIndicator(int index){
        StringBuilder sb = new StringBuilder();
        int div = 0;
        
        do{
            div = index % 26;
            index = index / 26;
            
            sb.insert(0, (char)('A'+ div));
            
        }while( index > 0);
        
        return sb.toString();
        
        
    }
}
