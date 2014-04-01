(function($) {
    var sale_assistant = window.sale_assistant = window.sale_assistant || {};
    if (!sale_assistant.zh) {
        sale_assistant.zh = {};
    }

    $.extend(true, sale_assistant.zh, {
        "Client Name" : "客户姓名",
        "Birthday" : "出生日期",
        "":""
    });
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    sale_assistant.l = function(string, module, locale) {
        locale = locale || "zh";
        codes = sale_assistant[locale];
        commonCodes = sale_assistant[locale];
        if (module) {
            if (sale_assistant[module] && sale_assistant[module][locale]) {
                codes = sale_assistant[module][locale];
            }
        } 
        if (codes && codes[string]) {
            return codes[string];
        }
        if (commonCodes && commonCodes[string]) {
            return commonCodes[string];
        }
        return string;
    };
    
})(jQuery);