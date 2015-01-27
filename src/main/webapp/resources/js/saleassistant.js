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
        
        var sale_assistant = window.sale_assistant = window.sale_assistant || {};
        sale_assistant.__gender_list = [{id:'M', text : "Male"}, {id:'F', text : "Female"}, {id:'U', text : "Unknown"}];

        sale_assistant.genders = function(){
            return sale_assistant.__gender_list;
        };

        sale_assistant.render_gender = function(record, index, col_index){
            var cellvalue = this.getCellValue(index, col_index);
            var html = "";
            var gs = sale_assistant.__gender_list;
            for (var p in gs) {
                if (gs[p].id === cellvalue){
                    html = sale_assistant.l(gs[p].text);
                    break;
                } 
            }
            return html;
        };

        sale_assistant.renderLink = function(record, index, col_index){
            var cellvalue = this.getCellValue(index, col_index);
            var html = "";
            //<a href="http://www.w3schools.com" target="_blank">Visit W3Schools.com!</a> 
            return "<a href='"+(cellvalue.indexOf('http') < 0 ? 'http://': '' )+cellvalue+"' target='_blank'>"+cellvalue+"</a>";
        };

        sale_assistant.render_link  = sale_assistant.renderLink ;
        sale_assistant.error = function(d) {
            if (console) {
                console.error(d);
            }
        };
        sale_assistant.log = function(d) {
            if (console) {
                console.log(d);
            }
        };
        sale_assistant.debug = function(d) {
            if (console) {
                console.debug("*********");
                console.debug(d);
            }
        };
        
        sale_assistant.safeEval = function(sr){
            var fu = new Function(sr);
            fu();
        };
        
        sale_assistant.autoUpdate = function(data) {
            if (!data)
                return;
            var zones = data.contentRefreshZone;
            if (zones) {
                for (var z in zones) {
                    try {
                        $j("#" + z).innerHTML = zones[z];
                    } catch (ex) {
                        sale_assistant.log(z);
                    }
                }
            }
            var scripts = data.scriptRefreshZone;
            if (scripts) {
                for (var i = 0; i < scripts.length; i++) {
                    try {
                        sale_assistant.safeEval(scripts[i]);
                    } catch (ex) {
                        sale_assistant.log(ex);
                    }
                }
            }
            var clearZones = data.clearRefreshZone;
            if (clearZones) {
                for (var m = 0; m < clearZones.length; m++) {
                    try {
                        $(clearZones[m]).innerHTML = '';
                    } catch (e) {
                        sale_assistant.log(e);
                    }
                }
            }
            if (data.data) {
                sale_assistant.autoUpdate(data.data);
            }
        };


        sale_assistant.callbackFunction = function(func, transport) {
            if (func && typeof (func) === 'function') {
                var callBack = func;
                callBack(transport);
                return true;
            } else {
                return false;
            }
        };

        sale_assistant.queryString2JSON = function(qry) {
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
        
        sale_assistant.url = function(navUri, action, type){
            return navUri + '&' + sale_assistant.BTN_OPTION +'='+action+"&" + sale_assistant.REFRESH_TYPE +"=" + type;
        };

        sale_assistant.init = function(uri, butName, formName, refreshTypeName) {
            sale_assistant.baseURLWithButton = uri;
            sale_assistant.BTN_OPTION = butName;
            sale_assistant.form = formName;
            sale_assistant.REFRESH_TYPE = refreshTypeName;
        };

        sale_assistant.serialize = function(zone) {
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

        sale_assistant.get = function(action, paraZone, sucessCallback, errorCallback, autoUpdate, sync) {
            var paras = sale_assistant.serialize(paraZone);
            $j.ajax({
                url: sale_assistant.baseURLWithButton + action,
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
                    sale_assistant.callbackFunction(sucessCallback, data, state);
                    if (autoUpdate) {
                        sale_assistant.autoUpdate(data);
                    }
                },
                error: function(data, state) {
                    sale_assistant.callbackFunction(errorCallback, data, state);
                }
            });
        };

        sale_assistant.post = function(action, paraZone, sucessCallback, errorCallback, autoUpdate) {
            var paras = sale_assistant.serialize(paraZone);
            alert(paras);
            $j.ajax({
                url: sale_assistant.baseURLWithButton + action,
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
                    sale_assistant.log(data);
                    sale_assistant.log(state);
                    sale_assistant.callbackFunction(sucessCallback, data, state);
                    if (autoUpdate) {
                        sale_assistant.autoUpdate(data);
                    }
                },
                error: function(data, state) {
                    sale_assistant.error(data);
                    sale_assistant.error(state);
                    sale_assistant.callbackFunction(errorCallback, data, state);
                }
            });
        };

        sale_assistant.sumbit = function(action) {
            $j("#" + sale_assistant.actionButton).value = action;
            $j("#" + sale_assistant.form).submit();
        };
        
        sale_assistant.loadContent = function(url, title, navTier){
            
        };
        
        sale_assistant.runApp = function(et){
            if(!et) return;
            var url = et["data-url"];
            var title = et.text;
            var navTier = et.navTier;
            if(url && url !== '' && (navTier && navTier !== '' && navTier !== sale_assistant.currentNavTier) ){
                sale_assistant.currentNavTier = navTier;
                sale_assistant.log(url);
                document.title = title;
                sale_assistant.loadContent(url, title);
            }
        };
        
        sale_assistant.generateSidebarID = function(prefix, nav){
            if(nav && nav !== "" ){
                return prefix + "-" +  nav.replace(/_/g, '-');  
            }
            return null;
        };
        
        sale_assistant.expandSidebar = function(id, parent){
            if(parent && parent.id && parent.parent){
                  window.w2ui[id].expand(parent.id);
                  if(parent.parent){
                      sale_assistant.expandSidebar(id, parent.parent);
                  }
            }
        };

        sale_assistant.selectSidebar = function (id, item){
            if(item){
                window.w2ui[id].select(item);
                var _sel = window.w2ui[id].get(item);
                if(_sel){
                    sale_assistant.expandSidebar(id ,_sel.parent);
                }
            }
        };

        sale_assistant.max_recid = {};
        sale_assistant.generateRecid = function(grid, app){
            var rs = grid.records;
            sale_assistant.max_recid[app]  = sale_assistant.max_recid[app] || 0;
            for(var i =0; i< rs.length; i++){
                var recid = rs[i].recid;
                if(sale_assistant.max_recid[app] < recid){
                    sale_assistant.max_recid[app] = recid;
                }
            }
            sale_assistant.max_recid[app] += 1;
            return sale_assistant.max_recid[app];
        };

        sale_assistant.initMaxRecId = function(grid, app){
            var rs = grid.records;
            sale_assistant.max_recid[app]  =  0;
            for(var i =0; i< rs.length; i++){
                var recid = rs[i].recid;
                if(sale_assistant.max_recid[app] < recid){
                    sale_assistant.max_recid[app] = recid;
                }
            }
            //sa.log(sale_assistant.max_recid[app]);
        };

        sale_assistant.eventData = function(evt){
            var data;
            try { data = $.parseJSON(evt.xhr.responseText) } catch (e) {}
            sa.log(data);
            return data;
        };
        

        sale_assistant.add_record = function(record){
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