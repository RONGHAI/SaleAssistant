<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib prefix="sm" uri="/WEB-INF/sm.tld"%>


<c:set var="javascript">
    <script type="text/javascript">
        var sales_assistant = window.sales_assistant = window.sales_assistant || {};
        sales_assistant._currencies_ = [];
        sales_assistant._order_status_ = [];
        $(function() {
            $(document).ready(function() {            
               sales_assistant.currencies = function(force){
                    return sales_assistant.find_data(force, "_currencies_", "listCurrencies", {servicer: 'currencyServicer'}  );
                };

                sales_assistant.order_status = function(force){
                    return sales_assistant.find_data(force, "_order_status_", "listOrderStatuses", "");
                };
                                
                sales_assistant._clients_ = [];
                sales_assistant.clients = function(force){
                    return sales_assistant.find_data(force, "_clients_", "listClients", "");
                };
                
                sales_assistant.subGrids = function(force){
                    return sales_assistant.find_data(force, "_subGrids_", "subGrids",  "" );
                };
                sales_assistant.subGrids();


                sales_assistant.carriers = function(force){
                    return sales_assistant.find_data(force, "_carriers_", "listCarriers", "");
                };
            });
        });
    </script>
</c:set>


<c:set var='html'>
    <div id='sub_grids'>
    </div>
</c:set>



<%@include file="OnRecord.jsp" %>



<script type="text/javascript">
    var sales_assistant = window.sales_assistant = window.sales_assistant || {};
    sales_assistant._orders =  sales_assistant._orders || {};
    sales_assistant.render_buttons = function(record, index, col_index){
        var cv = record.recid;
        var html = "";
        var _grids = sales_assistant.subGrids();
        for(var i = 0 ; i < _grids.length; i++){
            if($("#" +  _grids[i].grid ).length === 0) {
                $("#sub_grids").append('<div id="'+_grids[i].grid+'" style="width:80%; height: 200px; margin-top:20px; overflow: hidden;"></div>'); 
            }
            html +=  "<button class='btn' type='button' onclick='javascript:sales_assistant.edit_("+cv+","+col_index+",\""+i+"\")'>"+sales_assistant.l(_grids[i].label, 'order')+"</button>";
        }
        return html;
    };

    


    sales_assistant.edit_ = function(cid, colindex, index){
        var _grids = sales_assistant.subGrids();
        var grid =  sales_assistant._init_grid( _grids[index].grid , eval( _grids[index].columns ) );
        grid.url = "${sm:url(worker, 'json', 'record')}";
        grid.order = cid;
        grid.postData = {servicer: _grids[index].servicer, order: cid};
        grid.reload();
    };
    sales_assistant._init_grid = function(gid, _cols){
        if(w2ui[gid]){
            w2ui[gid].clear();
        }else{
            sales_assistant.initGrid(gid, gid, '${worker.appName}_'+gid+'v_', "", {
                unshift: false,
                clear: true,
                highlight_new : false
            }, {
                columns: sales_assistant.find_columns(_cols),
                show : {
                    toolbar : true,
                    toolbarColumns  : true,
                    toolbarSearch   : false,
                    toolbarDelete   : true,
                    toolbarAdd      : true,
                    toolbarSave     : true,
                    footer: true
                }
            });
        }
        w2ui[gid].resize();
        return w2ui[gid]; 
    };

    
    sales_assistant.render_currency = function(record, index, col_index){
        return sales_assistant.render_cell_join(this, record, index, col_index,  sales_assistant.currencies());         
    };

    $(function() {
        $(document).ready(function() {  
        });
    });

    sales_assistant.render_order_status = function(record, index, col_index){
        return sales_assistant.render_cell(this,  record, index, col_index,  sales_assistant.order_status());
    };

     

    sales_assistant.render_client = function(record, index, col_index){
        return sales_assistant.render_cell(this, record, index, col_index,  sales_assistant.clients());
    };
    

    sales_assistant.render_carrier = function(record, index, col_index){
        return sales_assistant.render_cell(this, record, index, col_index,  sales_assistant.carriers());
    };

    sales_assistant.render_tracking_buttons = function(record, index, col_index){
        return "<button class='btn' type='button' onclick='javascript:sales_assistant.tracking("+record.carrierId+",\""+record.trackingNumber+"\"); '>"+sales_assistant.l('Tracking', 'order')+"</button>";
    };
    
    sales_assistant.tracking = function(cid, tn){
        var ca;
        var carriers = sales_assistant.carriers();
        for(var i = 0; i < carriers.length; i++){
            if(carriers[i].id == cid){
                ca = carriers[i];
            }
        }
        var url = ca.trackURL.replace("%s", tn);
        window.open(url,'_blank');
    }

</script>