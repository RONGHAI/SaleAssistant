/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.weinyc.sa.common.format;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 *
 * @author Ronghai Wei <ronghai.wei@outlook.com>
 */
public class GenderFormat extends Format{
    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        StringBuffer sb = new StringBuffer();
        if("U".equals(obj)){
            sb.append("Unknown");
        }else if("F".equals(obj)){
            sb.append("Female");
        }else{
            sb.append("Male");
        }
        toAppendTo.append(sb.toString());
        return sb;
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
