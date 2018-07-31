<%@ include file="jspf/IncludeTop.jspf" %>

<center>
  <font size="4"><b>My Orders</b></font>
</center>
<table align="center" bgcolor="#000000" border="0" cellspacing="2" cellpadding="3">
  <tr bgcolor="#99FF22">  <td><b>Order ID</b></td>  <td><b>Date</b></td>  <td><b>Total Price</b></td>  </tr>
<c:forEach var="order" items="${orderList}">
  <tr bgcolor="#99FF22">
  <td><b><a href="<c:url value="/shop/viewOrder.html"><c:param name="orderId" value="${order.id}"/></c:url>">
	  <font color="BLACK"><c:out value="${order.id}"/></font>
  </a></b></td>
  <td><fmt:formatDate value="${order.createTime.time}" pattern="yyyy/MM/dd hh:mm:ss"/></td>
  <td><fmt:formatNumber value="${order.totalPrice}" pattern="$#,##0.00"/></td>
  </tr>
</c:forEach>
</table>

<%@ include file="jspf/IncludeBottom.jspf" %>
