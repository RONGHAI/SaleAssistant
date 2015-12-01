<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib prefix="sm" uri="/WEB-INF/sm.tld"%>


<c:set var="javascript">
    <script type="text/javascript">
        var sales_assistant = window.sales_assistant = window.sales_assistant || {};
        sales_assistant._categories_ = [];
        $(function() {
            $(document).ready(function() {            
                  sales_assistant.categories = function(force){
                    if(!force && sales_assistant._categories_ && sales_assistant._categories_.length > 0){
                        return sales_assistant._categories_;
                    }else{
                        sales_assistant.get("listCategories", {servicer: 'categoryServicer'} , function(data, state){
                            sales_assistant._categories_ = data;
                            for(var i = 0; i < data .length; i++){
                                data[i].text = sales_assistant.level(data[i].level, "&nbsp;&nbsp;&nbsp;&nbsp;")+data[i].name;
                            }
                            sales_assistant._categories_.unshift({id:-1, name:"-", text:"-"});
                        }, function(data, state){}, false, true);
                    }
                    return sales_assistant._categories_;
                };
            });
        });
    </script>
</c:set>



<%@include file="OnRecord.jsp" %>


<script type="text/javascript">
    var sales_assistant = window.sales_assistant = window.sales_assistant || {};

    sales_assistant.find_cat = function(cv){
        var gs = sales_assistant.categories();
        if(gs){
            for (var p in gs) {
                if (gs[p].id == cv || gs[p].name == cv){
                    return gs[p];
                } 
            }
        }
        return null;
    };

    sales_assistant.render_cats = function(record, index, col_index){
        var cv = this.getCellValue(index, col_index);
        if(cv &&  !$.isArray(cv)){
            cv = [{id:cv}];
        }
        var html = [];
        if(cv){
            for(var i = 0; i < cv .length; i++){
                var cat = cv[i];
                if(cat.name ===  undefined){
                    cat = sales_assistant.find_cat(cat.id);
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
                    sales_assistant.categories();
                    for(var i = 0 ; i < w2ui.grid.columns.length; i++){
                        if(w2ui.grid.columns[i].field === 'categories'){
                            w2ui.grid.columns[i].editable.items = sales_assistant.categories();
                            break;
                        }
                    }
                }
            };

            w2ui.grid.onLoad = function(event){
                event.onComplete = function () {
                    sales_assistant.initMaxRecId(this,  '${worker.appName}');
                    sales_assistant.categories();
                    for(var i = 0 ; i < w2ui.grid.columns.length; i++){
                        if(w2ui.grid.columns[i].field === 'categories'){
                            w2ui.grid.columns[i].editable.items = sales_assistant.categories();
                            break;
                        }
                    }
                }
            };
        });
    });

</script>