<%@ include file="jspf/IncludeTop.jspf" %>

<table align="left" bgcolor="#000000" border="0" cellspacing="2" cellpadding="2">
    <tr><td bgcolor="#99FF22">
            <a href="<c:url value="/shop/index.html"/>"><b><font color="BLACK" size="2">&lt;&lt; Main Menu</font></b></a>
        </td></tr>
</table>

<p>
<center>
    <b>Please confirm the information below and then press continue...</b>
</center>
<p>
<table width="60%" align="center" border="0" cellpadding="3" cellspacing="1" bgcolor="#000000">
    <tr bgcolor="#99FF22"><td align="center" colspan="2">
            <font size="4"><b>Order</b></font>
            <br /><font size="3"><b><fmt:formatDate value="${orderForm.order.createTime.time}" pattern="yyyy/MM/dd hh:mm:ss" /></b></font>
        </td></tr>

    <tr bgcolor="#99FF22"><td colspan="2">
            <font color="black" size="4"><b>Billing Address</b></font>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            First name:</td><td><c:out value="${orderForm.order.billToFirstname}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Last name:</td><td><c:out value="${orderForm.order.billToLastname}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Address 1:</td><td><c:out value="${orderForm.order.billAddress.addr1}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Address 2:</td><td><c:out value="${orderForm.order.billAddress.addr2}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            City: </td><td><c:out value="${orderForm.order.billAddress.city}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            State:</td><td><c:out value="${orderForm.order.billAddress.state}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Zip:</td><td><c:out value="${orderForm.order.billAddress.zipcode}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Country: </td><td><c:out value="${orderForm.order.billAddress.country}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td colspan="2">
            <font color="000000" size="4"><b>Shipping Address</b></font>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            First name:</td><td><c:out value="${orderForm.order.shipToFirstname}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Last name:</td><td><c:out value="${orderForm.order.shipToLastname}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Address 1:</td><td><c:out value="${orderForm.order.shipAddress.addr1}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Address 2:</td><td><c:out value="${orderForm.order.shipAddress.addr2}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            City: </td><td><c:out value="${orderForm.order.shipAddress.city}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            State:</td><td><c:out value="${orderForm.order.shipAddress.state}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Zip:</td><td><c:out value="${orderForm.order.shipAddress.zipcode}"/>
        </td></tr>
    <tr bgcolor="#99FF22"><td>
            Country: </td><td><c:out value="${orderForm.order.shipAddress.country}"/>
        </td></tr>

</table>
<p>
    <!-- _finish=true is must present? yes, see
         http://forum.springsource.org/showthread.php?t=23463
    -->
<center><a href="<c:url value="/shop/newOrder.html?_finish=true"/>"><img border="0" src="../images/button_continue.gif" /></a></center>

<%@ include file="jspf/IncludeBottom.jspf" %>
