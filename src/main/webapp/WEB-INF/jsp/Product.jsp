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
                    return sales_assistant.find_data(force, "_categories_", "listCategories",  {servicer: 'categoryServicer'},
                        function(da){
                            return sales_assistant.level(da.level, "&nbsp;&nbsp;&nbsp;&nbsp;")+da.name;
                        },
                        function(){
                            sales_assistant._categories_.unshift({id:-1, name:"-", text:"-"});
                        }
                    );
                };
            });
        });
    </script>
</c:set>



<%@include file="OnRecord.jsp" %>


<script type="text/javascript">
    var sales_assistant = window.sales_assistant = window.sales_assistant || {};
    
    sales_assistant.render_cats = function(record, index, col_index){
        return sales_assistant.render_cell_join(this, record, index, col_index,  sales_assistant.categories());    
    };   
</script>