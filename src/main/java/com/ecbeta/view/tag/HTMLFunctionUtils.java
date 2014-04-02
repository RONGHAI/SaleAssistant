/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ecbeta.view.tag;

import com.ecbeta.common.core.AbstractWorker;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public class HTMLFunctionUtils {
      public static String getUrl (AbstractWorker worker, String type, String action) {
          return worker.getUrl(type, action);
      }
    
}
