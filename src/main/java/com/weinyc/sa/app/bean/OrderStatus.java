/*
 * Copyright (C) 2015 L5M
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.weinyc.sa.app.bean;

import net.sf.json.JSONObject;

/**
 *
 * @author L5M
 */
public enum OrderStatus {
    INIT("新订单"),
    BUYING("购买中"),
    SHIPPING("转运中"),
    PAID("已付款"),
    RECEIVED("已收货"),
    DONE("订单完成")
    
    
    ;
    private final String label;
    private final int level;
    private OrderStatus(int level){
        this.label = this.name();
        this.level = level;
    }
    private OrderStatus(String label){
        this.label = label;
        this.level = this.ordinal();
    }
    public String getLabel(){
        return this.label;
    }
    public int getLevel(){
        return level;
    }

    public JSONObject toJSON() {
        OrderStatus r = this;
        JSONObject map = new JSONObject();
        map.put("id", r.name());
        map.put("name", r.getLabel());
        map.put("text", r.getLabel());
        return map;
    }
}
