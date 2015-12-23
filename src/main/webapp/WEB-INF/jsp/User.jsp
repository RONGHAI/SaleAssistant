<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib prefix="sm" uri="/WEB-INF/sm.tld"%>

<c:set var="javascript">
    <script type="text/javascript">
        sales_assistant._roles_ = [];
        $(function() {
            $(document).ready(function() {            
                sales_assistant.roles = function(force){
                    if(!force && sales_assistant._roles_ && sales_assistant._roles_.length > 0){
                        return sales_assistant._roles_;
                    }else{
                        sales_assistant.get("loadRoles", "", function(data, state){
                            sales_assistant._roles_ = data;
                            for(var i = 0; i < data .length; i++){
                                data[i].text = data[i].name;
                            }
                        }, function(data, state){}, false, true);
                    }
                    return sales_assistant._roles_;
                };
            });
        });
    </script>
</c:set>

<%@include file="OnRecord.jsp" %>



<script type="text/javascript">
    var sales_assistant = window.sales_assistant = window.sales_assistant || {};
    sales_assistant.render_role = function(record, index, col_index){
        var cv = this.getCellValue(index, col_index);
        var html = "";
        var gs = sales_assistant.roles();
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