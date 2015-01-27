<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib prefix="sm" uri="/WEB-INF/sm.tld"%>


<c:set var="javascript">
    <script type="text/javascript">
        sale_assistant._categories_ = [];
        $(function() {
            $(document).ready(function() {            
                sale_assistant.categories = function(force){
                    if(!force && sale_assistant._categories_ && sale_assistant._categories_.length > 0){
                        return sale_assistant._categories_;
                    }else{
                        sale_assistant.get("list", "", function(data, state){
                            sale_assistant._categories_ = data;
                            for(var i = 0; i < data .length; i++){
                                data[i].text = data[i].name;
                            }
                            sale_assistant._categories_.unshift({id:-1, name:"-", text:"-"});
                        }, function(data, state){}, false, true);
                    }
                    return sale_assistant._categories_;
                };
            });
        });
    </script>
</c:set>

<%@include file="OnRecord.jsp" %>

<script type="text/javascript">
    var sale_assistant = window.sale_assistant = window.sale_assistant || {};
    sale_assistant.render_parent = function(record, index, col_index){
        var cv = this.getCellValue(index, col_index);
        var html = "";
        var gs = sale_assistant.categories();
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

    $(function() {
        $(document).ready(function() {  

            w2ui.grid.onSave =  function(event){
                event.onComplete = function () {
                    sa.log("2");
                    var data = sale_assistant.eventData(event);
                    if(!data || data.refresh ){
                        this.reload();
                    }
                    sale_assistant.categories(true);
                    for(var i = 0 ; i < w2ui.grid.columns.length; i++){
                        if(w2ui.grid.columns[i].field === 'parentId'){
                            w2ui.grid.columns[i].editable.items = sale_assistant.categories();
                            break;
                        }
                    }
                }
            };

            w2ui.grid.onLoad = function(event){
                event.onComplete = function () {
                    sale_assistant.initMaxRecId(this,  '${worker.appName}');
                    sale_assistant.categories(true);
                    for(var i = 0 ; i < w2ui.grid.columns.length; i++){
                        if(w2ui.grid.columns[i].field === 'parentId'){
                            w2ui.grid.columns[i].editable.items = sale_assistant.categories();
                            break;
                        }
                    }
                }
            };
        });
    });

</script>