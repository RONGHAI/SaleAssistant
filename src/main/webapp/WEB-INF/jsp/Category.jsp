<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib prefix="sm" uri="/WEB-INF/sm.tld"%>


<c:set var="javascript">
    <script type="text/javascript">
        sales_assistant._categories_ = [];
        $(function() {
            $(document).ready(function() {            
                sales_assistant.categories = function(force){
                    return sales_assistant.find_data(force, "_categories_", "list",  "", 
                        function(da){
                            return sales_assistant.level(da.level, "&nbsp;&nbsp;&nbsp;&nbsp;")+da.name;
                        },
                        function(){
                            sales_assistant._categories_.unshift({id:-1, name:"-", text:"-"});
                        }
                    );
                };



                sales_assistant.parents = function(current){
                    var list = sales_assistant.categories();
                    if(!current){
                        return list;
                    }
                    var cats = {};
                    for(var i = 0 ; i < list.length; i++){
                        cats[list[i].id] = list[i];
                    }
                    for(var i = 0 ; i < list.length; i++){
                        list[i].parent = cats[list[i].parentId];
                    }
                    var newlist = [];
                    for(var i = 0 ; i < list.length; i++){
                        var cat = list[i];
                        var p = false;
                        while(cat != null){
                            if(cat.id == current){
                                p = true;
                                break;
                            }else{
                                cat = cat.parent;
                            }
                        }
                        if(!p){
                            newlist.push(list[i]);
                        }
                    }
                    return newlist;
                };
            });
        });
    </script>
</c:set>

<c:set var="html">
</c:set>

<%@include file="OnRecord.jsp" %>

<script type="text/javascript">
    var sales_assistant = window.sales_assistant = window.sales_assistant || {};
    sales_assistant.render_parent = function(record, index, col_index){
        return sales_assistant.render_cell(this, record, index, col_index,  sales_assistant.categories());   
    };  

    sales_assistant.renderDrop = function(item, options){
        return  item.text;
    };

    $(function() {
        $(document).ready(function() {  
            w2ui.grid.onSave =  function(event){
                event.onComplete = function () {
                    var data = sales_assistant.eventData(event);
                    if(!data || data.refresh ){
                        this.reload();
                    }
                    sales_assistant.categories(true);
                    for(var i = 0 ; i < w2ui.grid.columns.length; i++){
                        if(w2ui.grid.columns[i].field === 'parentId'){
                            w2ui.grid.columns[i].editable.items = sales_assistant.parents();
                            break;
                        }
                    }
                }

            };

            w2ui.grid.onLoad = function(event){
                event.onComplete = function () {
                    sales_assistant.initMaxRecId(this,  '${worker.appName}');
                    sales_assistant.categories(true);
                    for(var i = 0 ; i < w2ui.grid.columns.length; i++){
                        if(w2ui.grid.columns[i].field === 'parentId'){
                            w2ui.grid.columns[i].editable.items = sales_assistant.parents();
                            break;
                        }
                    }
                }
            };


            w2ui.grid.on('click', function(event) {
               if(event.execute === 'before' && event.type === 'click' &&  w2ui.grid.columns[event.column].field === 'parentId' ){
                    var row = w2ui.grid.get(event.recid);
                   w2ui.grid.columns[event.column].editable.items = sales_assistant.parents(row.id);
               }

            });
        });
    });

</script>