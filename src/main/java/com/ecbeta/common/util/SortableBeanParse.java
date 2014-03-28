package com.ecbeta.common.util;

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

}
