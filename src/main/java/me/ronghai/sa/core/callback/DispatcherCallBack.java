/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.ronghai.sa.core.callback;

import java.io.Serializable;
import me.ronghai.sa.JSONObject;

/**
 *
 * @author L5M
 */
public interface DispatcherCallBack extends Serializable {
       public void callback(String action, JSONObject json);
}
