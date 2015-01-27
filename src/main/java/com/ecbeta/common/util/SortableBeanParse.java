package com.ecbeta.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Ronghai
 */

public abstract class SortableBeanParse<T> {
  /**
   * 
   * @param o
   * @return list of o's attribute;
   */
  public abstract List<String> parseBeanToList(T o);

  
  public List<Comparable<?>> parseBeanToComparableList(T o) {
      List<String> _list = this.parseBeanToList(o);
      List<Comparable<?>> list = new ArrayList<Comparable<?>>(_list.size());
      for (String x : _list) {
          list.add(x);
      }
      return list;

  }

  public List<Comparable<?>> parse(T o) {
      throw new UnsupportedOperationException();
  }
}
