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
    <div id="addressGrid" style="width:80%; height: 200px; margin-top:20px; overflow: hidden;"></div>
    <div id="addressPopup" style="display:none; width: 650px; height: 350px; overflow: hidden;">
        <div rel="title">
        </div>
        <div rel="body" style="padding: 10px;  width: 650px; height:90%; width:90%">
            
        </div>
        <div rel="buttons">
            <button class="btn" type='button' onclick="w2popup.close()">Close</button>
        </div>
    </div>
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
        /*
        var addressGrid = sales_assistant.init_address_grid();
        addressGrid.url = "${sm:url(worker, 'json', 'record')}";
        addressGrid.client = cid;
        addressGrid.postData = {servicer: "addressServicer", client: cid};
        addressGrid.reload();
        
        */
    };

    sales_assistant.init_address_grid = function(){
        <%--
        if(w2ui['addressGrid']){
            w2ui['addressGrid'].clear();
        }else{
            sales_assistant.initGrid("addressGrid", "addressGrid", '${worker.appName}_a_', "", {
                unshift: false,
                clear: true,
                highlight_new : false
            }, {
                columns: sales_assistant.find_columns(${worker.addressColumns}),
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
        w2ui['addressGrid'].resize();
        return w2ui['addressGrid']; --%>
    };

    /*
    sales_assistant.render_client = function(record, index, col_index){
        var cv = this.getCellValue(index, col_index);
        var records = w2ui['grid'].records;
        for(var i = 0; i < records.length; i++){
            if(cv == records[i].recid){
                return records[i].name;
            }
        }
        
        for(var i = 0; i < records.length; i++){
            if( w2ui['addressGrid'].client == records[i].recid){
                return records[i].name;
            }
        }
        return "";
    };
    */


    sales_assistant.render_currency = function(record, index, col_index){
        return sales_assistant.render_cell_join(this, record, index, col_index,  sales_assistant.currencies());         
    };
    $(function() {
        $(document).ready(function() {  
            w2ui.grid.onSave =  function(event){
                event.onComplete = function () {
                    var data = sales_assistant.eventData(event);
                    if(!data || data.refresh ){
                        this.reload();
                    }
                }
            };
            w2ui.grid.onLoad = function(event){
                event.onComplete = function () {
                    sales_assistant.initMaxRecId(this,  '${worker.appName}');
                }
            };
        });
    });

    sales_assistant.render_order_status = function(record, index, col_index){
        return sales_assistant.render_cell(this,  record, index, col_index,  sales_assistant.order_status());
    };

     

    sales_assistant.render_client = function(record, index, col_index){
        return sales_assistant.render_cell(this, record, index, col_index,  sales_assistant.clients());
    };

</script>