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
                      find('input[name],select[name],textarea[name]').serialize();
                params += serialized;
                if (index !== zones.length - 1) {
                    params += "&";
                }
            }
            return params;
        };

        sale_assistant.get = function(action, paraZone, sucessCallback, errorCallback, autoUpdate) {
            var paras = sale_assistant.serialize(paraZone);
            $j.ajax({
                url: sale_assistant.baseURLWithButton + action,
                header: {
                    "Access-Control-Allow-Origin": "*",
                    "Access-Control-Allow-Methods": "GET"
                },
                async: true,
                type: "POST",
                dataType: "GET",
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
        

    })(jQuery);
}