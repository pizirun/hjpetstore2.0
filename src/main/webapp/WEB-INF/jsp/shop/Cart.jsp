<%@ include file="jspf/IncludeTop.jspf" %>

<table border="0" width="100%" cellspacing="0" cellpadding="0">
    <tr><td valign="top" width="20%" align="left">
            <table align="left" bgcolor="#000000" border="0" cellspacing="2" cellpadding="2">
                <tr><td bgcolor="#99FF22">
                        <a href="<c:url value="/shop/index.html"/>"><b><font color="BLACK" size="2">&lt;&lt; Main Menu</font></b></a>
                    </td></tr>
            </table>
        </td><td valign="top" align="center">
            <h2 align="center">Shopping Cart</h2>
            <form action="<c:url value="/shop/updateCartQuantities.html"/>" method="post">
                <table align="center" bgcolor="#000000" border="0" cellspacing="2" cellpadding="5">
                    <tr bgcolor="#99FF22">
                        <td><b>Item ID</b></td>  <td><b>Product ID</b></td>  <td><b>Description</b></td> <td><b>In Stock?</b></td> <td><b>Quantity</b></td>  <td><b>List Price</b></td> <td><b>Total Cost</b></td>  <td>&nbsp;</td>
                    </tr>

                    <c:if test="${cart.numberOfItems == 0}">
                        <tr bgcolor="#99FF22"><td colspan="8"><b>Your cart is empty.</b></td></tr>
                    </c:if>

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
                                <input type="text" size="3" name="<c:out value="${cartItem.item.itemName}"/>" value="<c:out value="${cartItem.quantity}"/>" />
                            </td>
                            <td align="right"><fmt:formatNumber value="${cartItem.item.listPrice}" pattern="$#,##0.00" /></td>
                            <td align="right"><fmt:formatNumber value="${cartItem.totalPrice}" pattern="$#,##0.00" /></td>
                            <td><a href="<c:url value="/shop/removeItemFromCart.html"><c:param name="workingItemName" value="${cartItem.item.itemName}"/></c:url>">
                                    <img border="0" src="../images/button_remove.gif" />
                                </a></td>
                        </tr>
                    </c:forEach>
                    <tr bgcolor="#99FF22">
                        <td colspan="7" align="right">
                            <b>Sub Total: <fmt:formatNumber value="${cart.subTotal}" pattern="$#,##0.00" /></b><br/>
                            <input type="image" border="0" src="../images/button_update_cart.gif" name="update" />
                        </td>
                        <td>&nbsp;</td>
                    </tr>
                </table>
                <center>
                    <c:if test="${!cart.cartItemList.firstPage}">
                        <a href="<c:url value="viewCart.html?page=previousCart"/>"><font color="green"><B>&lt;&lt; Prev</B></font></a>
                    </c:if>
                    <c:if test="${!cart.cartItemList.lastPage}">
                        <a href="<c:url value="viewCart.html?page=nextCart"/>"><font color="green"><B>Next &gt;&gt;</B></font></a>
                    </c:if>
                </center>
            </form>

            <c:if test="${cart.numberOfItems > 0}">
                <br /><center><a href="<c:url value="/shop/checkout.html"/>"><img border="0" src="../images/button_checkout.gif" /></a></center>
                    </c:if>

        </td>

        <td valign="top" width="20%" align="right">
            <c:if test="${!empty userSession.user.username}">
                <c:if test="${userSession.user.displayMylist}">
                    <%@ include file="jspf/IncludeMyFavoriteProductList.jspf" %>
                </c:if>
            </c:if>
        </td>

    </tr>
</table>

<%@ include file="jspf/IncludeBanner.jspf" %>

<%@ include file="jspf/IncludeBottom.jspf" %>
