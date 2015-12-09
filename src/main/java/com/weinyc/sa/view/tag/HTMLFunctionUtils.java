/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weinyc.sa.view.tag;

import com.weinyc.sa.core.engine.AbstractController;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public class HTMLFunctionUtils {
      public static String getUrl (AbstractController worker, String type, String action) {
          return worker.getUrl(type, action);
      }
    
}
