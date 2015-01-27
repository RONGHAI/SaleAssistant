<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib prefix="sm" uri="/WEB-INF/sm.tld"%>


<c:set var="javascript">
    <script type="text/javascript">
        $(function() {
            $(document).ready(function() {            
               
            });
        });
    </script>
</c:set>

<c:set var='html'>
    <div id="addressGrid" style="width:500px; height: 200px; margin-top:20px; overflow: hidden;"></div>
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
    var sale_assistant = window.sale_assistant = window.sale_assistant || {};
    sale_assistant.render_address = function(record, index, col_index){
        var cv = this.getCellValue(index, col_index);
        var html = "<button class='btn' type='button' onclick='javascript:sale_assistant.edit_address("+cv+")'>住址</button>";
        return html;
    };
    sale_assistant.edit_address = function(cid){
        /*$("#addressPopup").w2popup({
            title: '地址信息',
            onOpen : function (event) {

            };

        });*/
        var addressGrid = sale_assistant.init_address_grid();
        addressGrid.url = "${sm:url(worker, 'json', 'record')}";
        addressGrid.client = cid;
        addressGrid.postData = {servicer: "addressServicer", client: cid};
        addressGrid.reload();
    };

    sale_assistant.init_address_grid = function(){
        if(w2ui['addressGrid']){
            w2ui['addressGrid'].clear();
        }else{
            sale_assistant.initGrid("addressGrid", "addressGrid", '${worker.appName}_a_', "", {
                unshift: false,
                clear: true,
                highlight_new : false
            }, {
                columns: sale_assistant.find_columns(${worker.addressColumns}),
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
        return w2ui['addressGrid'];
    };


    sale_assistant.render_client = function(record, index, col_index){
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
       

        //return records[0].name;
    };

    //"${sm:url(worker, 'json', 'record')}&servicer=addressServicer"
    $(function() {
            $(document).ready(function() {               
                
            });
        });

</script>