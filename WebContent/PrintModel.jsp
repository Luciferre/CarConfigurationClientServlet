<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Print Model</title>
</head>
<body>
	<CENTER>
		<H1>Here is what you selected:</H1>
		<%
			Automobile automobile = (Automobile) session.getAttribute("auto");
			ArrayList<String> optsetlist = automobile.getOptionSetList();
			for (int i = 0; i < optsetlist.size(); i++) {
				String optsetname = optsetlist.get(i);
				String optname = request.getParameter(optsetname);
				automobile.setOptionChoice(optsetname, optname);
			}
		%>
		<form method="post">
			<table border="1" cellpadding="8" width="500">
				<tr>
					<td>
						<%
							out.println(automobile.getMake() + " " + automobile.getModel());
						%>
					</td>
					<td>Base Price</td>
					<td>
						<%
							out.println(automobile.getBasePrice());
						%>
					</td>
				</tr>
				<%
					for (int i = 0; i < optsetlist.size(); i++) {
				%>
				<tr>
					<td>
						<%
							String setName = optsetlist.get(i);
								out.println(setName);
						%>
					</td>
					<td>
						<%
							out.println(automobile.getOptionChoiceName(setName));
						%>
					</td>
					<td>
						<%
							out.println(automobile.getOptionChoicePrice(setName));
						%>
					</td>

				</tr>
				<%
					}
				%>

				<tr>
					<td>Total Price</td>
					<td></td>
					<td>
						<%
							out.println(automobile.getTotalPrice());
						%>
					</td>
				</tr>

			</table>
		</form>
	</CENTER>

</body>
</html>