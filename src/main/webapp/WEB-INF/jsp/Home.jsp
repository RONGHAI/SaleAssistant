<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="com.ecbeta.common.constants.Constants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib prefix="sm" uri="/WEB-INF/sm.tld"%>
<c:set value='<%=request.getAttribute(Constants.REQUEST_WORKER_ATTRIBUTE_NAME)%>' var="worker" />
<c:set value="${worker.servicer}" var="servicer" />
<c:set var='controller' value='${worker}' />
<c:set value='${servicer.navigationBeans}' var='navigationBeans' />
<!DOCTYPE html>
<html>
    <head>
        <title>${worker.appName}</title>
        <sm:Head controller='${worker}' > 
        </sm:Head>
        <style>
          input{
            text-align: right;
          }
          .highlight{
            background-color: yellow;
          }
        </style>
    </head>
    <body>
        
       <!-- 首页 -->
       <form id='mainForm' style='margin-top:20px;'>
         <div style='  margin-left: auto; margin-right: auto; width:850px;'>

            <div id='price-compare-template' style='display:none;  '>
              <div data-row='comparePriceRow' style='   margin-top:10px;'>
                <span> 描述</span>
                <input type='text'  size='4'/>
                <span> 商品价格 $</span>
                <input type='text' data-type='money' name='prices' size='4'/>
                <span> 折扣 </span>
                <input type="text" name="discounts" list="discountList" size='4'/>
                <datalist id="discountList">
                    <option value='0.05' > Target 5%</option>
                    <option value='0.172' > R US Thursday</option>
                    <option value='0.08' > R US Double Rewards</option>
                    <option value='0.02' > CITI CC</option>
                </datalist>

                <span>eBates</span>
                <input type='text' data-type='number' name='ebates'  size='4'/>%
                <span>返现 $</span>
                <input type='text' data-type='money' name='rewards'  size='4'/>
                <span>运费 $</span>
                <input type='text' data-type='money' name='shippings'  size='4'/>
                <span>数量</span>
                <input type='text' data-type='number' name='counts'  size='4'/>
                <span>单价</span>
                <label name="unitPrices"></label>
              </div>
            </div>
            
            <div id='price-compare-zone' style='   '>
            </div>

            <div style=' margin-top:10px; text-align:center;  margin-left: auto; margin-right: auto;'>
              <button type='button'  onclick='javascript:sales_assistant.addCompare();'>添加商品比较 (A)</button>
              <button type='button'  onclick='javascript:sales_assistant.calculateUnitPrice();'>计算单价 (C)</button>
              <button type='button'  onclick='javascript:sales_assistant.resetForm();'>清空表单 (O)</button>
              <br>
              <sub>( 单价 * ( 1 - 折扣 ) * ( 1 - eBates / 100 ) - 返现  + 运费 ) / 数量</b>
            </div>

         </div>
       </form>
        
       <script type="text/javascript">
           if(self === window){
                sales_assistant.log('home.html is not in iframe');
           }else{
                sales_assistant.log('home.html is in iframe');
           }
           sales_assistant.log(window.parent);

           sales_assistant.calculateUnitPrice = function(){
              var prices = $("input[name='prices']");
              var discounts = $("input[name='discounts']");
              var rewards = $("input[name='rewards']");
              var ebates = $("input[name='ebates']");
              var shippings = $("input[name='shippings']");
              var counts = $("input[name='counts']");
              var uplabels = $("label[name='unitPrices']");
              var rows = $("div[data-row='comparePriceRow']");
              var lowestRow = -1;
              var lowerPrice = 0.0;
              for(var i = 1; i <　prices.length; i++){
                try{
                    var unitPrice = $(prices[i]).val() * (1 - $(discounts[i]).val())  * (1 - $(ebates[i]).val() / 100.0) -  $(rewards[i]).val() * 1.0 
                    + $(shippings[i]).val() * 1.0;
                    unitPrice = unitPrice / $(counts[i]).val() * 1.0;
                    if(isNaN(unitPrice)){
                      continue;
                    }
                    if(lowestRow == -1){
                      lowestRow = i;
                      lowerPrice = unitPrice;
                    }else if(lowerPrice > unitPrice){
                      lowerPrice = unitPrice;
                      lowestRow = i;
                    }
                    $(uplabels[i]).text("$"+unitPrice.toFixed(2));
                 }catch(ex){
                    sales_assistant.error(ex);
                 }
                 rows.removeClass("highlight");
                 $(rows[lowestRow]).addClass("highlight");
              }
              //$(rows)
           };

           sales_assistant.resetForm = function(){
              $("#mainForm")[0].reset();
           };

           sales_assistant.addCompare = function(){
              $("#price-compare-zone").append($("#price-compare-template").html());//.append("<br/><br/>");
           };

          $(function() {
            $(document).ready(function() {   
                sales_assistant.addCompare();
            });


            sales_assistant. getChar = function(event) {
                if (event.which == null) {
                  return String.fromCharCode(event.keyCode) // IE
                } else if (event.which!=0 && event.charCode!=0) {
                  return String.fromCharCode(event.which)   // the rest
                } else {
                  return null // special key
                }
            };

            document.onkeypress =  function(event) {
                var chr = sales_assistant.getChar(event || window.event)
                if (!chr) return // special key
                //this.value = chr.toUpperCase();
                if(chr.toUpperCase() === 'A'){
                  sales_assistant.addCompare();
                  return false;
                }else if(chr.toUpperCase() === 'O'){
                  sales_assistant.resetForm();
                  return false;
                }else if(chr.toUpperCase() === 'C'){
                  sales_assistant.calculateUnitPrice();
                  return false;
                }
                return true;
                //alert(chr);
            }
          });
       </script> 
    </body>
</html>

