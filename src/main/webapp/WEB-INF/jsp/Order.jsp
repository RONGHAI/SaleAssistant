<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib prefix="sm" uri="/WEB-INF/sm.tld"%>


<c:set var="javascript">
    <script type="text/javascript">
        var sales_assistant = window.sales_assistant = window.sales_assistant || {};
        sales_assistant._currencies_ = [];
        $(function() {
            $(document).ready(function() {            
               sales_assistant.currencies = function(force){
                    if(!force && sales_assistant._currencies_ && sales_assistant._currencies_.length > 0){
                        return sales_assistant._currencies_;
                    }else{
                        sales_assistant.get("listCurrencies", {servicer: 'currencyServicer'} , function(data, state){
                            sales_assistant._currencies_ = data;
                            for(var i = 0; i < data .length; i++){
                                data[i].text = data[i].name;
                            }
                        }, function(data, state){}, false, true);
                    }
                    return sales_assistant._currencies_;
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

     sales_assistant.find_currency = function(cv){
        var gs = sales_assistant.currencies();
        if(gs){
            for (var p in gs) {
                if (gs[p].id == cv || gs[p].name == cv){
                    return gs[p];
                } 
            }
        }
        return null;
    };

    sales_assistant.render_currency = function(record, index, col_index){
        var cv = this.getCellValue(index, col_index);
        if(cv &&  !$.isArray(cv)){
            cv = [{id:cv}];
        }
        var html = [];
        if(cv){
            for(var i = 0; i < cv .length; i++){
                var cat = cv[i];
                if(cat.name ===  undefined){
                    cat = sales_assistant.find_currency(cat.id);
                }
                html.push(cat.name);
            }
        }
        return html.join(", ");
    };
    $(function() {
        $(document).ready(function() {  
            w2ui.grid.onSave =  function(event){
                event.onComplete = function () {
                    var data = sales_assistant.eventData(event);
                    if(!data || data.refresh ){
                        this.reload();
                    }
                    sales_assistant.currencies();
                }
            };
            w2ui.grid.onLoad = function(event){
                event.onComplete = function () {
                    sales_assistant.initMaxRecId(this,  '${worker.appName}');
                    sales_assistant.currencies();
                }
            };
        });
    });


</script>