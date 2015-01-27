(function($) {
    var sale_assistant = window.sale_assistant = window.sale_assistant || {};
    sale_assistant.zh = sale_assistant.zh || {};

    $.extend(true, sale_assistant.zh, {
        "ID": "编号",
        "Client Name" : "客户姓名",
        "Birthday" : "出生日期",
        "Client":"客户姓名",
        "Zipcode": "邮编",
        "Phone": "联系电话",
        "Default Address": "默认地址",
        "Unknown": "未知",
        "Male": "男",
        "Female": "女",
        "Address": "地址"
    });
    
    
    sale_assistant.client = sale_assistant.client || {};
    sale_assistant.client.zh = sale_assistant.client.zh || {};

    $.extend(true, sale_assistant.client.zh, {
        "Name" : "客户姓名",
        "Birthday" : "出生日期",
        "Wangwang":"淘宝旺旺",
        "QQ": "QQ",
        "QQ Name": "QQ 名称",
        "Gender": "性别",
        "Phone": "联系电话",
        "Address": "地址"
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