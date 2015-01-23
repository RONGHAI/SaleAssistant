<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib prefix="sm" uri="/WEB-INF/sm.tld"%>

<c:set var="javascript">
    <script type="text/javascript">
        sale_assistant._merchants_ = [];
        $(function() {
            $(document).ready(function() {            
                sale_assistant.merchants = function(){
                    if(sale_assistant._merchants_ && sale_assistant._merchants_.length > 0){
                        return sale_assistant._merchants_;
                    }else{
                        sale_assistant.get("listMerchants", "", function(data, state){
                            sale_assistant._merchants_ = data;
                            for(var i = 0; i < data .length; i++){
                                data[i].text = data[i].name;
                            }
                        }, function(data, state){}, false, true);
                    }
                    return sale_assistant._merchants_;
                };
            });
        });
    </script>
</c:set>

<%@include file="OnRecord.jsp" %>



<script type="text/javascript">
    var sale_assistant = window.sale_assistant = window.sale_assistant || {};
    sale_assistant.render_merchant = function(record, index, col_index){
        var cv = this.getCellValue(index, col_index);
        var html = "";
        var gs = sale_assistant.merchants();
        if(gs){
            for (var p in gs) {
                if (gs[p].id == cv || gs[p].name == cv){
                    html = gs[p].name;
                    break;
                } 
            }
        }
        return html;
    };
</script>