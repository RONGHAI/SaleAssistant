<%@page contentType="text/html" pageEncoding="UTF-8"%>



<%@include file="OnRecord.jsp" %>



<script type="text/javascript">
    var sale_assistant = window.sale_assistant = window.sale_assistant || {};
    sale_assistant.render_merchant = function(record, index, column_index){
        return "M"+record.merchantId;
    };
</script>>