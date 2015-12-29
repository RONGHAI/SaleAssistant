if (jQuery) {
    (function($j) {
        
        String.prototype.format = String.prototype.f = function(arg) {
            var s = this,
                i = arg.length;
            while (i--) {
                s = s.replace(new RegExp('\\{' + i + '\\}', 'gm'), arg[i]);
            }
            return s;
        };
        
        var sales_assistant = window.sales_assistant = window.sales_assistant || {};
        //var sale_assistant = window.sale_assistant = sales_assistant;
        sales_assistant.__gender_list = [{id:'M', text : "Male"}, {id:'F', text : "Female"}, {id:'U', text : "Unknown"}];
        
        sales_assistant.__package_types = [{id:'US', text : "美国境内"}, {id:'TX', text : "转运"}, {id:'CN', text : "中国境内"} ];
        
        sales_assistant.render_package_type = function(record, index, col_index){
            sa.log(this);
            sa.log(record);
            sa.log(  this.getCellValue(index, col_index));
            return sales_assistant.render_cell(this, record, index, col_index,  sales_assistant.package_types());
        };
        
        sales_assistant.package_types = function(){
            return sales_assistant.__package_types ;
        }
        
        sales_assistant.genders_i18n = [];
        sales_assistant.genders = function(){
            if(sales_assistant.genders_i18n.length === 0){
                var map = [];
                var gs = sales_assistant.__gender_list;
                for (var p in gs) {
                    var gp = gs[p];
                    gp = $j.extend({}, gp);
                    gp.text = sales_assistant.l(gp.text);
                    map.push(gp);
                }
                sales_assistant.genders_i18n = map;
            }
            return sales_assistant.genders_i18n;
        };

        sales_assistant.render_gender = function(record, index, col_index){
            var cellvalue = this.getCellValue(index, col_index);
            var html = "";
            var gs = sales_assistant.__gender_list;
            for (var p in gs) {
                if (gs[p].id === cellvalue){
                    html = sales_assistant.l(gs[p].text);
                    break;
                } 
            }
            return html;
        };

        sales_assistant.renderLink = function(record, index, col_index){
            var cellvalue = this.getCellValue(index, col_index);
            var html = "";
            //<a href="http://www.w3schools.com" target="_blank">Visit W3Schools.com!</a> 
            return "<a href='"+(cellvalue.indexOf('http') < 0 ? 'http://': '' )+cellvalue+"' target='_blank'>"+cellvalue+"</a>";
        };

        sales_assistant.render_link  = sales_assistant.renderLink ;
        sales_assistant.error = function(d) {
            if (console) {
                console.error(d);
            }
        };
        sales_assistant.log = function(d) {
            if (console) {
                console.log(d);
            }
        };
        sales_assistant.debug = function(d) {
            if (console) {
                console.debug("*********");
                console.debug(d);
            }
        };
        
        sales_assistant.safeEval = function(sr){
            var fu = new Function(sr);
            fu();
        };
        
        sales_assistant.autoUpdate = function(data) {
            if (!data)
                return;
            var zones = data.contentRefreshZone;
            if (zones) {
                for (var z in zones) {
                    try {
                        $j("#" + z).innerHTML = zones[z];
                    } catch (ex) {
                        sales_assistant.log(z);
                    }
                }
            }
            var scripts = data.scriptRefreshZone;
            if (scripts) {
                for (var i = 0; i < scripts.length; i++) {
                    try {
                        sales_assistant.safeEval(scripts[i]);
                    } catch (ex) {
                        sales_assistant.log(ex);
                    }
                }
            }
            var clearZones = data.clearRefreshZone;
            if (clearZones) {
                for (var m = 0; m < clearZones.length; m++) {
                    try {
                        $(clearZones[m]).innerHTML = '';
                    } catch (e) {
                        sales_assistant.log(e);
                    }
                }
            }
            if (data.data) {
                sales_assistant.autoUpdate(data.data);
            }
        };


        sales_assistant.callbackFunction = function(func, transport) {
            if (func && typeof (func) === 'function') {
                var callBack = func;
                callBack(transport);
                return true;
            } else {
                return false;
            }
        };
        
        sales_assistant.level = function(level, ls){
          var x = "";
          for(var i = 1; i < level; i++){
              x += ls ;// "&nbsp;&n"
          }
          return x;
        };

        sales_assistant.queryString2JSON = function(qry) {
            var pairs = qry.split('&');
            var result = {};
            pairs.forEach(function(pair) {
                pair = pair.split('=');
                if (pair[1]) {
                    var va = decodeURIComponent(pair[1]);
                    var curr = result[pair[0]];
                    if (curr) {
                        if (Array.isArray(curr)) {
                            curr.push(va);
                        } else {
                            result[pair[0]] = [curr, va];
                        }
                    } else {
                        result[pair[0]] = decodeURIComponent(va);
                    }
                }
            });
            return JSON.parse(JSON.stringify(result));
        };
        
        sales_assistant.url = function(navUri, action, type){
            return navUri + '&' + sales_assistant.BTN_OPTION +'='+action+"&" + sales_assistant.REFRESH_TYPE +"=" + type;
        };

        sales_assistant.init = function(uri, butName, formName, refreshTypeName) {
            sales_assistant.baseURLWithButton = uri;
            sales_assistant.BTN_OPTION = butName;
            sales_assistant.form = formName;
            sales_assistant.REFRESH_TYPE = refreshTypeName;
        };

        sales_assistant.serialize = function(zone) {
            if(!zone){
                return "";
            }
            if(zone.indexOf("=") >= 0){
                return zone;
            }
            var zones = zone.split(/;|,/);
            var params = "";
            for (var index = 0; index < zones.length; index++) {
                var el = $j("#" + zones[index]);
                var serialized = el.serialize();
                if (!serialized) // not a form
                    serialized = el.
                      find('input,select,textarea').serialize();
                params += serialized;
                if (index !== zones.length - 1) {
                    params += "&";
                }
            }
            return params;
        };

        sales_assistant.get = function(action, paraZone, sucessCallback, errorCallback, autoUpdate, sync) {
            var paras =  $j.isPlainObject(paraZone) ? paraZone :  sales_assistant.serialize(paraZone);
            $j.ajax({
                url: sales_assistant.baseURLWithButton + action,
                header: {
                    "Access-Control-Allow-Origin": "*",
                    "Access-Control-Allow-Methods": "GET"
                },
                async: sync ? false : true,
                type: "GET",
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data: paras,
                success: function(data, state) {
                    sales_assistant.callbackFunction(sucessCallback, data, state);
                    if (autoUpdate) {
                        sales_assistant.autoUpdate(data);
                    }
                },
                error: function(data, state) {
                    sales_assistant.callbackFunction(errorCallback, data, state);
                }
            });
        };

        sales_assistant.post = function(action, paraZone, sucessCallback, errorCallback, autoUpdate) {
            var paras =  $j.isPlainObject(paraZone) ? paraZone :  sales_assistant.serialize(paraZone);
            //alert(paras);
            $j.ajax({
                url: sales_assistant.baseURLWithButton + action,
                header: {
                    "Access-Control-Allow-Origin": "*",
                    "Access-Control-Allow-Methods": "POST"
                },
                async: true,
                type: "POST",
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data: paras,
                success: function(data, state) {
                    sales_assistant.log(data);
                    sales_assistant.log(state);
                    sales_assistant.callbackFunction(sucessCallback, data, state);
                    if (autoUpdate) {
                        sales_assistant.autoUpdate(data);
                    }
                },
                error: function(data, state) {
                    sales_assistant.error(data);
                    sales_assistant.error(state);
                    sales_assistant.callbackFunction(errorCallback, data, state);
                }
            });
        };

        sales_assistant.sumbit = function(action) {
            $j("#" + sales_assistant.actionButton).value = action;
            $j("#" + sales_assistant.form).submit();
        };
        
        sales_assistant.loadContent = function(url, title, navTier){
            
        };
        
        sales_assistant.runApp = function(et){
            if(!et) return;
            var url = et["data-url"];
            var title = et.text;
            var navTier = et.navTier;
            if(url && url !== '' && (navTier && navTier !== '' && navTier !== sales_assistant.currentNavTier) ){
                sales_assistant.currentNavTier = navTier;
                sales_assistant.log(url);
                document.title = title;
                sales_assistant.loadContent(url, title);
            }
        };
        
        sales_assistant.generateSidebarID = function(prefix, nav){
            if(nav && nav !== "" ){
                return prefix + "-" +  nav.replace(/_/g, '-');  
            }
            return null;
        };
        
        sales_assistant.expandSidebar = function(id, parent){
            if(parent && parent.id && parent.parent){
                  window.w2ui[id].expand(parent.id);
                  if(parent.parent){
                      sales_assistant.expandSidebar(id, parent.parent);
                  }
            }
        };

        sales_assistant.selectSidebar = function (id, item){
            if(item){
                window.w2ui[id].select(item);
                var _sel = window.w2ui[id].get(item);
                if(_sel){
                    sales_assistant.expandSidebar(id ,_sel.parent);
                }
            }
        };

        sales_assistant.max_recid = {};
        sales_assistant.generateRecid = function(grid, app, _max_url){
            var rs = grid.records;
            sales_assistant.max_recid[app]  = sales_assistant.max_recid[app] || 0;
            for(var i =0; i< rs.length; i++){
                var recid = rs[i].recid;
                if(sales_assistant.max_recid[app] < recid){
                    sales_assistant.max_recid[app] = recid;
                }
            }
            sales_assistant.max_recid[app] += 1;
            return sales_assistant.max_recid[app];
        };

        sales_assistant.initMaxRecId = function(grid, app, _max_url, max_id){
            var rs = grid.records;
            if(max_id){
                sales_assistant.max_recid[app]  =  max_id;
            }else{
                sales_assistant.max_recid[app]  =  0;
            }
            for(var i =0; i< rs.length; i++){
                var recid = rs[i].recid;
                if(sales_assistant.max_recid[app] < recid){
                    sales_assistant.max_recid[app] = recid;
                }
            }
            //sa.log(sales_assistant.max_recid[app]);
        };

        sales_assistant.eventData = function(evt){
            var data;
            try { data = $.parseJSON(evt.xhr.responseText) } catch (e) {}
            sa.log(data);
            return data;
        };
        

        sales_assistant.add_record = function(record){
            if (!$.isArray(record)) record = [record];
            var added = 0;
            for (var o in record) {
                if (!this.recid && typeof record[o].recid == 'undefined') record[o].recid = record[o][this.recid];
                if (record[o].recid == null || typeof record[o].recid == 'undefined') {
                    console.log('ERROR: Cannot add record without recid. (obj: '+ this.name +')');
                    continue;
                }
                this.records.unshift(record[o]);
                added++;
            }
            var url = (typeof this.url != 'object' ? this.url : this.url.get);
            if (!url) {
                this.total = this.records.length;
                this.localSort();
                this.localSearch();
            }
            this.refresh(); // ??  should it be reload?
            return added;

        }

    })(jQuery);
}