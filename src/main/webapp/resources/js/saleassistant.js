if (jQuery) {
    (function($j) {
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
        
        sale_assistant.callbackFunction = function(func, transport) {
            if (func && typeof(func) === 'function') {
                var callBack = func;
                callBack(transport);
                return true;
            } else {
                return false;
            }
        };
        
        sale_assistant.queryString2JSON = function(qry ){
            var pairs = qry.split('&');
            var result = {};
            pairs.forEach(function(pair) {
                pair = pair.split('=');
                if(pair[1]){
                    var va = decodeURIComponent(pair[1]);
                    var curr = result[pair[0]];
                    if(curr){
                        if(Array.isArray(curr)){
                            curr.push(va); 
                        }else{
                            result[pair[0]] = [curr, va];
                        }
                    }else{
                        result[pair[0]] = decodeURIComponent(va);
                    }
                }
            });
            return JSON.parse(JSON.stringify(result));
        };
         

        sale_assistant.init = function(uri, butName, formName) {
            sale_assistant.baseURLWithButton = uri;
            sale_assistant.actionButton = butName;
            sale_assistant.form = formName;
        };
        
        sale_assistant.serialize = function(zone) {
            var zones = zone.split(/;|,/);
            var params = "";
            for (var index = 0; index < zones.length; index++) {
                params += $j( "#"+zones[index] ).serialize();
                if(index !== zones.length - 1){
                    params += "&";
                }
            }
            return params;
        };
        
        sale_assistant.get = function(action, paraZone, sucessCallback, errorCallback) {
            var paras = sale_assistant.serialize(paraZone);
            $j.ajax({
                url:sale_assistant.baseURLWithButton+action,
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
                },
                error: function(data, state) {
                    sale_assistant.callbackFunction(errorCallback, data, state);
                }
            });
        };
        
        sale_assistant.post = function(action, paraZone, sucessCallback, errorCallback) {
            var paras = sale_assistant.serialize(paraZone);
            $j.ajax({
                url:sale_assistant.baseURLWithButton+action,
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
                    sale_assistant.callbackFunction(sucessCallback, data, state);
                },
                error: function(data, state) {
                    sale_assistant.callbackFunction(errorCallback, data, state);
                }
            });
        };
        
        sale_assistant.sumbit = function(action){
            $j("#"+sale_assistant.actionButton).value = action;
            $j("#"+sale_assistant.form).submit();
        };




    })(jQuery);
}