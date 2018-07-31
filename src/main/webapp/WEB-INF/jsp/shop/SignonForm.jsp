<%@ include file="jspf/IncludeTop.jspf" %>

<table cellpadding="10" cellspacing="0" align="center" border="0" bgcolor="#FFFFFF">
    <tr bgcolor="#FFFFFF">
        <td>
            <c:if test="${!empty message}">
                <b><font color="RED"><c:url value="${message}"/></font></b>
            </c:if>
        </td>
    </tr>
</table>

<table cellpadding="10" cellspacing="0" align="center" border="1" bgcolor="#000000">
    <tr>
        <td>
            <form action="<c:url value="/shop/signon.html"/>" method="POST">

                <c:if test="${!empty signonForwardAction}">
                    <input type="hidden" name="forwardAction" value="<c:url value="${signonForwardAction}"/>"/>
                </c:if>

                <table cellpadding="10" cellspacing="0" align="center" border="1" bgcolor="#000000">
                    <tr bgcolor="#99FF22">
                        <td colspan="2">
                            <font color="black"><b>Please enter your username and password.</b></font>
                        </td>
                    </tr>
                    <tr bgcolor="#99FF22">
                        <td>Username:</td>
                        <td><input type="text" name="username" value="j2ee" /></td>
                    </tr>
                    <tr bgcolor="#99FF22">
                        <td>Password:</td>
                        <!-- pre-input password - no finding or guessing for new user-->
                        <td><input type="password" name="password" value="j2ee" /></td>
                    </tr>
                    <!-- kaptcha -->
                    <tr bgcolor="#99FF22">
                        <td colspan="2">
                            <%@ include file="../../jspf/kaptcha.jspf" %>
                        </td>
                    </tr>
                    <tr bgcolor="#99FF22">
                        <td colspan="2" align="right"><input type="submit" value="Submit" name="signon" /></td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
    <tr>
        <td>
            <center>
                <a href="<c:url value="/shop/newUser.html"/>">
                    <font color="yellow"><b>Register Now</b></font>
                </a>
            </center>
        </td>
    </tr>
</table>
<%@ include file="jspf/IncludeBottom.jspf" %>

