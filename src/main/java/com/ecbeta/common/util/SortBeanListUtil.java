package com.ecbeta.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortBeanListUtil{

  public final static int SMALLER = -1;
  public final static int GREATER = 1;
  public final static int EQUAL = 0;
  
  public final static  String NA = "N/A";
  public final static int NA_NONE = 0 , NA_FIRST = 1, NA_LAST = 2;
  public static boolean isNullOrEmptyString(String str){
    return ((str == null) || (("").equals(str)));
  }

  public static boolean isNumerical(String str){
    if (isNullOrEmptyString(str)) return false;
    try{
      Double.parseDouble(str);
      return true;
    }
    catch (NumberFormatException nfe){
      return false;
    }
  }
  
  private static boolean isNA(Object na){
	  return na == null || NA.equalsIgnoreCase(na.toString());
  }
  
  public static <T extends Object> int compareBeanList(List<String> beanList1,List<String> beanList2,final int[] sortKeys,
          final boolean[] sortAscending){
      return compareBeanList(beanList1, beanList2, sortKeys, sortAscending, false, NA_NONE);
  }
  
  
  
  
  public static <T extends Object> int compareBeanList(List<String> beanList1, List<String> beanList2, final int[] sortKeys, final boolean[] sortAscending, final boolean nullAlwaysFirst, final int naOrder){
    int comp = EQUAL;
    for (int i = 0; i < sortKeys.length; i++){
      if (sortKeys[i] < 0){
        continue;
      }
      boolean asc = true;
      try{
        asc = sortAscending[i];
      }
      catch (Exception e){}
      Object field1 = null;
      Object field2 = null;
      if(sortKeys[i] >= beanList1.size() ) return 0;
      String temp1 = beanList1.get(sortKeys[i]);
      String temp2 = beanList2.get(sortKeys[i]);
      if ((SortBeanListUtil.isNumerical(temp1)) && (SortBeanListUtil.isNumerical(temp2))){
        field1 = new Double(temp1);
        field2 = new Double(temp2);
      }
      else{
        field1 = temp1;
        field2 = temp2;
      }
      
      //TODO .... 
      if(naOrder !=  NA_NONE){
    	  if(isNA(field1) &&  !isNA(field2) ){
    		  comp = naOrder == NA_FIRST ? SMALLER : GREATER;
    		  return comp;
    	  }else  if(!isNA(field1) &&  isNA(field2) ){
    		  comp = naOrder == NA_FIRST ? GREATER : SMALLER;
    		  return comp;
    	  }
      }
      
      //TODO ....
      
      
      if (field1 == null){
          if (field2 != null){
            comp = SMALLER; 
            if(nullAlwaysFirst){
          	  return comp;
            }
          }
        } else if (field2 == null){
          comp = GREATER; 
          if(nullAlwaysFirst){
        	  return comp;
          }
        } else if (!field1.equals(field2)){
          try{
            if (asc){
              @SuppressWarnings("unchecked")
              Comparable<Object> comparable = ((Comparable<Object>)field1);
              return comparable.compareTo(field2);
            }
            else{
              @SuppressWarnings("unchecked")
              Comparable<Object> comparable = ((Comparable<Object>)field2);
              return comparable.compareTo(field1);
            }
          }
          catch (Exception e){
            return EQUAL;
          }
        }
      
 
      

 
      if (comp != EQUAL){
        if (asc){
          return ((comp == SMALLER) ? SMALLER : GREATER);
        }
        else{
          return ((comp == SMALLER) ? GREATER : SMALLER);
        }
      }
    }
    return EQUAL;
  }

  public static <T> Comparator<? super T> getBeanComparator(final int[] sortKeys, final boolean[] sortAscending, final SortableBeanParse<T> parseBean){
     return  getBeanComparator(sortKeys, sortAscending, false, parseBean);
  }
  
  public static <T> Comparator<? super T> getBeanComparator(final int[] sortKeys, final boolean[] sortAscending, final boolean nullAlwaysFirst, final SortableBeanParse<T> parseBean){
		  return  getBeanComparator(sortKeys, sortAscending, nullAlwaysFirst, parseBean, NA_NONE);
	}
  
  public static <T> Comparator<? super T> getBeanComparator(final int[] sortKeys, final boolean[] sortAscending, final boolean nullAlwaysFirst, final SortableBeanParse<T> parseBean, final int naOrder){
	    return new Comparator<T>(){
	      public int compare(T o1, T o2){
	        List<String> beanList1 = parseBean.parseBeanToList(o1);
	        List<String> beanList2 = parseBean.parseBeanToList(o2);
	        return SortBeanListUtil.compareBeanList(beanList1, beanList2, sortKeys, sortAscending , nullAlwaysFirst , naOrder);
	      }
	    };
	}
  
  private static   Comparator<Integer> getBeanComparator2(final List<String> [] beanParsers,final int[] sortKeys, final boolean[] sortAscending, final boolean nullAlwaysFirst,  final int naOrder){
      return new Comparator<Integer>(){
        public int compare(Integer o1, Integer o2){
          List<String> beanList1 = beanParsers[o1];
          List<String> beanList2 = beanParsers[o2];
          return SortBeanListUtil.compareBeanList(beanList1, beanList2, sortKeys, sortAscending , nullAlwaysFirst , naOrder);
        }
      };
  }
  
  
  public static <T> void sort(List<T> beanList, final int[] sortKeys, final boolean[] sortAscending, final SortableBeanParse<T> parseBean){
	    	sort(beanList, sortKeys, sortAscending, parseBean, NA_NONE);
  }
  
  public static <T> void sort(List<T> beanList, final int[] sortKeys, final boolean[] sortAscending, final SortableBeanParse<T> parseBean, int naOrder ){
	     sort(beanList, sortKeys, sortAscending, false, parseBean, naOrder);
  }
  
  public static <T> void sort(List<T> beanList, final int[] sortKeys, final boolean[] sortAscending, final boolean nullAlwaysFirst, final SortableBeanParse<T> parseBean , final int naOrder){
	    if (beanList == null || beanList.isEmpty()) return; 
	    if (sortKeys == null || sortKeys.length <= 0) return; 
	    if (parseBean == null) return;
	    
	    
	    
	    Collections.sort(beanList, SortBeanListUtil.getBeanComparator(sortKeys, sortAscending , nullAlwaysFirst , parseBean , naOrder));
	  }

  
  public static <T> void sort2(List<T> beanList, final int[] sortKeys, final boolean[] sortAscending, final SortableBeanParse<T> parseBean){
            sort2(beanList, sortKeys, sortAscending, parseBean, NA_NONE);
  }
  
  public static <T> void sort2(List<T> beanList, final int[] sortKeys, final boolean[] sortAscending, final SortableBeanParse<T> parseBean, int naOrder ){
         sort2(beanList, sortKeys, sortAscending, false, parseBean, naOrder);
  }
  
  
  public static <T> void sort2(List<T> beanList, final int[] sortKeys, final boolean[] sortAscending, final boolean nullAlwaysFirst, final SortableBeanParse<T> parseBean){
       sort2(beanList, sortKeys, sortAscending, nullAlwaysFirst, parseBean, NA_NONE);
  }
 
  public static <T> void sort2(List<T> beanList, final int[] sortKeys, final boolean[] sortAscending, final boolean nullAlwaysFirst, final SortableBeanParse<T> parseBean , final int naOrder){
      if (beanList == null || beanList.isEmpty()) return; 
      if (sortKeys == null || sortKeys.length <= 0) return; 
      if (parseBean == null) return;
      @SuppressWarnings("unchecked")
      List<String>[] beanParsers = new List[beanList.size()];
      List<Integer> indices = new ArrayList<Integer>();
      for(int i = 0 ; i < beanList.size() ; i++){
          beanParsers[i] = parseBean.parseBeanToList(beanList.get(i));
          indices.add(i);
      } 
      Collections.sort(indices, SortBeanListUtil.getBeanComparator2(beanParsers, sortKeys, sortAscending , nullAlwaysFirst , naOrder)); 
      List<T> _beanList = new ArrayList<T>(beanList);
      beanList.clear();
      for(Integer i : indices){
          beanList.add(_beanList.get(i.intValue()));
      }
    }


  public static <T> void sort(List<T> beanList, final int[] sortKeys, final boolean[] sortAscending, final boolean nullAlwaysFirst, final SortableBeanParse<T> parseBean){
      sort(beanList, sortKeys, sortAscending, nullAlwaysFirst, parseBean, NA_NONE);
  }



  public static <T> void sort3(List<T> beanList, final int[] sortKeys, final boolean[] sortAscending, final SortableBeanParse<T> parseBean){
      sort3(beanList, sortKeys, sortAscending, parseBean, NA_NONE);
  }

  public static <T> void sort3(List<T> beanList, final int[] sortKeys, final boolean[] sortAscending, final SortableBeanParse<T> parseBean, int naOrder ){
      sort3(beanList, sortKeys, sortAscending, false, parseBean, naOrder);
  }


  public static <T> void sort3(List<T> beanList, final int[] sortKeys, final boolean[] sortAscending, final boolean nullAlwaysFirst, final SortableBeanParse<T> parseBean){
      sort3(beanList, sortKeys, sortAscending, nullAlwaysFirst, parseBean, NA_NONE);
  }

  public static <T> void sort3(List<T> beanList, final int[] sortKeys, final boolean[] sortAscending, final boolean nullAlwaysFirst, final SortableBeanParse<T> parseBean , final int naOrder){
      if (beanList == null || beanList.isEmpty()) return; 
      if (sortKeys == null || sortKeys.length <= 0) return; 
      if (parseBean == null) return;
      @SuppressWarnings("unchecked")
      List<Comparable<?>>[]beanParsers = new List[beanList.size()];
      List<Integer> indices = new ArrayList<Integer>();
      for(int i = 0 ; i < beanList.size() ; i++){
          beanParsers[i] = parseBean.parse(beanList.get(i));
          indices.add(i);
      } 
      Collections.sort(indices, SortBeanListUtil.getBeanComparator3(beanParsers, sortKeys, sortAscending , nullAlwaysFirst , naOrder)); 
      List<T> _beanList = new ArrayList<T>(beanList);
      beanList.clear();
      for(Integer i : indices){
          beanList.add(_beanList.get(i.intValue()));
      }
  }


  private static  Comparator<Integer> getBeanComparator3(final List<Comparable<?>> [] beanParsers,final int[] sortKeys, final boolean[] sortAscending, final boolean nullAlwaysFirst,  final int naOrder){
      return new Comparator<Integer>(){
          public int compare(Integer o1, Integer o2){
              List<Comparable<?>> beanList1 = beanParsers[o1];
              List<Comparable<?>> beanList2 = beanParsers[o2];
              return SortBeanListUtil.compare(beanList1, beanList2, sortKeys, sortAscending , nullAlwaysFirst , naOrder);
          }
      };
  }

  private static boolean isNA(   Comparable<?> na){
      return na == null;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static <T extends Object  > int compare(List< Comparable<?>> beanList1, List<Comparable<?>> beanList2, final int[] sortKeys, final boolean[] sortAscending, final boolean nullAlwaysFirst, final int naOrder){
      int comp = EQUAL;
      for (int i = 0; i < sortKeys.length; i++){
          if (sortKeys[i] < 0){
              continue;
          }
          boolean asc = true;
          try{
              asc = sortAscending[i];
          }
          catch (Exception e){}

          if(sortKeys[i] >= beanList1.size() ) return 0;
          Comparable<?> field1 = beanList1.get(sortKeys[i]);
          Comparable<?> field2 = beanList2.get(sortKeys[i]);



          //TODO .... 
          if(naOrder !=  NA_NONE){
              if(isNA(field1) &&  !isNA(field2) ){
                  comp = naOrder == NA_FIRST ? SMALLER : GREATER;
                  return comp;
              }else  if(!isNA(field1) &&  isNA(field2) ){
                  comp = naOrder == NA_FIRST ? GREATER : SMALLER;
                  return comp;
              }
          }

          if (field1 == null){
              if (field2 != null){
                  comp = SMALLER; 
                  if(nullAlwaysFirst){
                      return comp;
                  }
              }
          } else if (field2 == null){
              comp = GREATER; 
              if(nullAlwaysFirst){
                  return comp;
              }
          } else if (!field1.equals(field2)){
              try{
                  if(asc){
                      return ((Comparable)field1).compareTo(field2);
                  }
                  else{
                      return ((Comparable)field2).compareTo(field1);
                  }
              }
              catch (Exception e){
                  return EQUAL;
              }
          }





          if (comp != EQUAL){
              if (asc){
                  return ((comp == SMALLER) ? SMALLER : GREATER);
              }
              else{
                  return ((comp == SMALLER) ? GREATER : SMALLER);
              }
          }
      }
      return EQUAL;
  }




  
  public static final void main(String are[]){
    List<TestBean> list = new ArrayList<TestBean>();
    TestBean bean = new TestBean();
    bean.setKey(10);
    list.add(bean);
    bean = new TestBean();
    bean.setKey(14);
    list.add(bean);
    bean = new TestBean();
    bean.setKey(25);
    list.add(bean);
    bean = new TestBean();
    bean.setKey(9);
    list.add(bean);

    SortBeanListUtil.sort2(list, new int[]{0}, new boolean[]{false}, new SortableBeanParse<TestBean>(){
      @Override
      public List<String> parseBeanToList(TestBean o){
        List<String> ar = new ArrayList<String>();
        ar.add(o.getKey() + "");
        return ar;
      }

    });

    System.out.println(list);
  }

}

class TestBean{
  private int key;

  public int getKey(){
    return key;
  }

  public void setKey(int key){
    this.key = key;
  }

  public String toString(){
    return this.key + "";
  }
}
