<%@ include file="jspf/IncludeTop.jspf" %>


<table border="0" width="100%" cellspacing="0" cellpadding="0">
<tr><td valign="top" width="20%" align="left">
<table align="left" bgcolor="#000000" border="0" cellspacing="2" cellpadding="2">
<tr><td bgcolor="#99FF22">
<a href="<c:url value="/shop/viewCart.html"/>"><b><font color="BLACK" size="2">&lt;&lt; Shopping Cart</font></b></a>
</td></tr>
</table>
</td>

<td valign="top" align="center">
<h2 align="center">Checkout Summary</h2>

<table align="center" bgcolor="#000000" border="0" cellspacing="2" cellpadding="5">

  <tr bgcolor="#99FF22">
  <td><b>Item ID</b></td>  <td><b>Product ID</b></td>  <td><b>Description</b></td> <td><b>In Stock?</b></td> <td><b>Quantity</b></td>  <td><b>List Price</b></td> <td><b>Total Cost</b></td>
  </tr>

<c:forEach var="cartItem" items="${cart.cartItemList.pageList}">
  <tr bgcolor="#99FF22">
  <td><b>
  <a href="<c:url value="/shop/viewItem.html"><c:param name="itemName" value="${cartItem.item.itemName}"/></c:url>">
    <c:out value="${cartItem.item.itemName}"/>
	</a></b></td>
  <td><c:out value="${cartItem.item.product.productNumber}"/></td>
  <td>
    <c:out value="${cartItem.item.attr1}"/>
    <c:out value="${cartItem.item.attr2}"/>
    <c:out value="${cartItem.item.attr3}"/>
    <c:out value="${cartItem.item.attr4}"/>
    <c:out value="${cartItem.item.attr5}"/>
    <c:out value="${cartItem.item.product.productName}"/>
   </td>
  <td align="center"><c:out value="${cartItem.inStock}"/></td>
  <td align="center">
  <c:out value="${cartItem.quantity}"/>
  </td>
  <td align="right"><fmt:formatNumber value="${cartItem.item.listPrice}" pattern="$#,##0.00" /></td>
  <td align="right"><fmt:formatNumber value="${cartItem.totalPrice}" pattern="$#,##0.00" /></td>
  </tr>
</c:forEach>
<tr bgcolor="#99FF22">
<td colspan="7" align="right">
<b>Sub Total: <fmt:formatNumber value="${cart.subTotal}" pattern="$#,##0.00" /></b><br />

</td>
</tr>
</table>
<center>
  <c:if test="${!cart.cartItemList.firstPage}">
    <a href="checkout.html?page=previousCart"><font color="green"><B>&lt;&lt; Prev</B></font></a>
  </c:if>
  <c:if test="${!cart.cartItemList.lastPage}">
    <a href="checkout.html?page=nextCart"><font color="green"><B>Next &gt;&gt;</B></font></a>
  </c:if>
</center>
<br>
<center><a href="<c:url value="/shop/newOrder.html"/>"><img border="0" src="../images/button_continue.gif" /></a></center>
</td>

<td valign="top" width="20%" align="right">&nbsp;</td>
</tr>
</table>

<%@ include file="jspf/IncludeBottom.jspf" %>
