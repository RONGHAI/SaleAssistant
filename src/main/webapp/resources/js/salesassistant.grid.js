(function($) {
    var sales_assistant = window.sales_assistant = window.sales_assistant || {};
     sales_assistant.initGrid = function(did, _name, _unique_name , _url, c_options, moreOptions, _max_url){
        var  blank_data = {};
        if(moreOptions.columns){
            for(var ii = 0; ii < moreOptions.columns.length; ii++){
                if(moreOptions.columns[ii] && moreOptions.columns[ii].editable && moreOptions.columns[ii].editable["default"]){
                    blank_data[moreOptions.columns[ii].field] =  moreOptions.columns[ii].editable["default"];
                }
            }
            var changes = {};
            $.extend( true, changes, blank_data );
            blank_data.changes = changes;
        }
        //sa.log(moreOptions.columns);
        //sa.log(blank_data);
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
            var recid = sales_assistant.generateRecid(this , _unique_name, _max_url);
            var _n = { recid: recid };
            if(blank_data){
                $.extend(true, _n,blank_data );
            }
            
            this.add(_n);
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
    };
    sales_assistant.render_cell = function(grid, record, index, col_index, gs){
        var cv = grid.getCellValue(index, col_index);
        var html = "";
        if(gs){
            for (var p in gs) {
                if (gs[p].id === cv || (gs[p].id+"" === cv+"")  || gs[p].name === cv){
                    html = gs[p].name;
                    break;
                } 
            }
        }
        return html;
    };
    
    sales_assistant.render_cell_join = function(grid, record, index, col_index, gs, jo){
        if(jo){
        }else{
            jo = ", ";
        }
        var cv = grid.getCellValue(index, col_index);
        if(cv &&  !$.isArray(cv)){
            cv = [{id:cv}];
        }
        var html = [];
        if(gs){
            for(var i = 0; i < cv.length; i++){
                for (var p in gs) {
                    if (gs[p].id === cv[i].id || (gs[p].id+"" === cv[i].id+"")){
                        html.push(gs[p].name);
                        break;
                    } 
                }
            }            
        }
        return html.join(jo);
    };
    
    sales_assistant.find_data = function(force, exdata, action, paras, render, afterRender){

        if(!force && sales_assistant[exdata] && sales_assistant[exdata] .length > 0){
            return sales_assistant[exdata];
        }else{
            sales_assistant.get(action, paras , function(data, state){
                sales_assistant[exdata]  = data;
                for(var i = 0; i < data .length; i++){
                    if(render){
                        data[i].text = render(data[i]);
                    }else{
                        data[i].text = data[i].name;
                    }
                }
                if(afterRender){
                    afterRender();
                }
            }, function(data, state){}, false, true);
        }
        return sales_assistant[exdata];

    };
})(jQuery);