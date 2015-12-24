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
            });
        });
    </script>
</c:set>


<c:set var='html'>
    <div id="productGrid" style="width:80%; height: 200px; margin-top:20px; overflow: hidden;"></div>
    <div id="usaTrackingGrid" style="width:80%; height: 200px; margin-top:20px; overflow: hidden;"></div>
    <div id="trackingGrid" style="width:80%; height: 200px; margin-top:20px; overflow: hidden;"></div>
    
</c:set>



<%@include file="OnRecord.jsp" %>



<script type="text/javascript">
    var sales_assistant = window.sales_assistant = window.sales_assistant || {};
    sales_assistant.render_button = function(record, index, col_index){
        var cv = this.getCellValue(index, col_index);
        var html = "<button class='btn' type='button' onclick='javascript:sales_assistant.edit_("+cv+","+col_index+",\""+this.columns[col_index].caption+"\")'>"+this.columns[col_index].caption+"</button>";
        return html;
    };

    


    sales_assistant.edit_ = function(cid, colindex, caption){
        <%--
        var grid =  sales_assistant._init_grid('trackingGrid', '');
        grid.url = "${sm:url(worker, 'json', 'record')}";
        grid.order = cid;
        grid.postData = {servicer: "trackingServicer", order: cid};
        grid.reload();
        --%>
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

</script>