<%@ include file="jspf/IncludeTop.jspf" %>

<table align="left" bgcolor="#000000" border="0" cellspacing="2" cellpadding="2">
<tr><td bgcolor="#99FF22">
<a href="<c:url value="/shop/viewProduct.html"><c:param name="productNumber" value="${product.productNumber}"/></c:url>">
  <b><font color="BLACK" size="2">&lt;&lt; <c:out value="${product.productName}"/></font></b>
</a>
</td></tr>
</table>

<p>

<table align="center" bgcolor="#000000" cellspacing="2" cellpadding="3" border="0" width="60%">
<tr bgcolor="#99FF22">
<td bgcolor="#99FF22">
<img src="../images/<c:out value='${product.image}'/>">
<c:out value="${product.productDesc}"/>
</td>
</tr>
<tr bgcolor="#99FF22">
<td width="100%" bgcolor="#99FF22">
  <b><c:out value="${item.itemName}"/></b>
</td>
</tr><tr bgcolor="#99FF22">
<td>
  <b><font size="4">
    <c:out value="${item.attr1}"/>
    <c:out value="${item.attr2}"/>
    <c:out value="${item.attr3}"/>
    <c:out value="${item.attr4}"/>
    <c:out value="${item.attr5}"/>
    <c:out value="${product.productName}"/>
  </font></b>
</td></tr>
<tr bgcolor="#99FF22"><td>
  <font size="3"><i><c:out value="${product.productName}"/></i></font>
</td></tr>
<tr bgcolor="#99FF22"><td>
  <c:if test="${item.inventory.quantity <= 0}">
      <font color="RED" size="2"><i><fmt:message key="backordered"/></i></font>
  </c:if>
  <c:if test="${item.inventory.quantity > 0}">
    <font size="2"><fmt:formatNumber value="${item.inventory.quantity}"/> in stock.</font>
  </c:if>
</td></tr>
<tr bgcolor="#99FF22"><td>
  <fmt:formatNumber value="${item.listPrice}" pattern="$#,##0.00" />
</td></tr>

<tr bgcolor="#99FF22"><td>
<a href="<c:url value="/shop/addItemToCart.html"><c:param name="workingItemName" value="${item.itemName}"/></c:url>">
  <img border="0" src="../images/button_add_to_cart.gif"/>
</a>
</td></tr>
</table>

<%@ include file="jspf/IncludeBottom.jspf" %>
