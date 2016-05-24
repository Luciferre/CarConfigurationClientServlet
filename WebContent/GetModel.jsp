<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Get Model</title>
</head>
<body>
	<CENTER>
		<H1>Basic Car Choice</H1>
		<form method="post" action="PrintModel.jsp">
			<%
				Automobile automobile = (Automobile) session.getAttribute("auto");
				ArrayList<String> optsetlist = automobile.getOptionSetList();
			%>
			<table border="1" cellpadding="5" width="500">
				<tr>
					<td>Make/Model</td>
					<td>
						<%
							out.println(automobile.getMake() + " " + automobile.getModel());
						%>
					</td>
				</tr>
				<%
					for (int i = 0; i < optsetlist.size(); i++) {
				%>
				<tr>
					<td>
						<%
							String optsetname = optsetlist.get(i);
								out.println(optsetname);
						%>
					</td>
					<td><select name="<%=optsetname%>">
							<%
								ArrayList<String> optlist = automobile.getOptionList(optsetname);
									for (int j = 0; j < optlist.size(); j++) {
										String optname = optlist.get(j);
							%>
							<option value="<%=optname%>"><%=optname%></option>
							<%
								}
							%>
					</select>
				</tr>
				<%
					}
				%>
			</table>
			<br></br>
			<td><input type="submit" value="Done"></td>

		</form>
	</CENTER>
</body>
</html>