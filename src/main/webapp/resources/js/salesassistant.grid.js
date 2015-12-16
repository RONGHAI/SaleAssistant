(function($) {
    var sales_assistant = window.sales_assistant = window.sales_assistant || {};
     sales_assistant.initGrid = function(did, _name, _unique_name , _url, c_options, moreOptions, _max_url){
        c_options = c_options || {};
        var options = {
        name: _name, 
        show : {
            toolbar : true,
            toolbarColumns  : true,
            toolbarSearch   : true,
            toolbarDelete   : true,
            toolbarAdd      : true,
            toolbarSave     : true,
            //toolbarSort: true,
            footer: true
        },
        url : _url,
        //multiSort: true,
        onAdd: function (target,data) {
            var  recid = sales_assistant.generateRecid(this , _unique_name, _max_url);
            this.add({ recid: recid });
            if(c_options['highlight_new']){
                var tr = $('#grid_'+this.name+"_rec_"+recid);
                tr.css('background-color', tr.hasClass("w2ui-odd") ?  "rgb(235, 241, 222)" : "rgb(216, 228, 188)" );
                var st =  tr.hasClass("w2ui-odd") ?  "background-color:rgb(235, 241, 222)" : "background-color:rgb(216, 228, 188)";
                this.get(recid).style = st;
            }
            $('#grid_'+this.name+"_rec_bottom").css("display", "none"); //grid_addressGrid_rec_bottom
        },
        onSave: function(event){
            event.onComplete = function () {
                //sa.log("1");
                var data = sales_assistant.eventData(event);
                if(!data || data.refresh ){
                    this.reload();
                }
            }
        },
        onLoad: function(event) {
            var data = sales_assistant.eventData(event);
            event.onComplete = function () {
               sales_assistant.initMaxRecId(this, _unique_name, _max_url, data.max);
            }
        },
        onDelete: function(event){
             //event.force = true; // no confirmation
        },
        onError: function(event){
            var data = sales_assistant.eventData(event);
            if(data && !data.refresh && !data.message){
            }
        }
        };
        if(moreOptions){
            $.extend(options, moreOptions);
        }
        if(c_options['clear'] && w2ui[_name]){
            try{ w2ui[_name].clear();}catch(ex){sales_assistant.error(ex);}
        }
        $("#"+did).w2grid(options);
        if(c_options['unshift']){
            w2ui[_name].add = sales_assistant.add_record;
        }

        if(c_options['reload']){
            w2ui[_name].reload();
        }
    };

    sales_assistant.find_search_columns = function(columns, module, locale){
        var cls = [];
        for(var i = 0; i < columns.length; i++){
            if(columns[i].searchable && columns[i].type && columns[i].type != null){
                columns[i].caption = sales_assistant.l(columns[i].caption, module, locale);
                cls.push(columns[i]);
            }
        }
        return cls;
    };
    sales_assistant.find_columns = function(columns, module, locale){
        for(var i = 0; i < columns.length; i++){
            if(columns[i].caption){
                columns[i].caption = sales_assistant.l(columns[i].caption, module, locale);
            }
        }
        return columns;
    }
})(jQuery);