<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*,tum.in.dbpra.bean.BandBean,tum.in.dbpra.bean.StageBean,tum.in.dbpra.bean.TimeslotBean,tum.in.dbpra.bean.FestivalBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language="JavaScript" type="text/javascript">
	
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Part</title>
</head>
<body>

	<%
		if (request.getAttribute("error") != null) {
	%>
	<h1>Band not found!</h1>
	<%=request.getAttribute("error")%>

	<%
		} else if (pageContext.findAttribute("bands") != null
				&& ((List) pageContext.findAttribute("bands")).size() != 0
				&& pageContext.findAttribute("stages") != null
				&& ((List) pageContext.findAttribute("stages")).size() != 0
				&& pageContext.findAttribute("bookedTimeslots") != null
				&& ((List) pageContext.findAttribute("bookedTimeslots"))
						.size() != 0) {//&&pageContext.findAttribute("festival")!= null){
			List<BandBean> bands = (List<BandBean>) pageContext
					.findAttribute("bands");
			List<StageBean> stages = (List<StageBean>) pageContext
					.findAttribute("stages");
			List<TimeslotBean> bookedTimeslots = (List<TimeslotBean>) pageContext
					.findAttribute("bookedTimeslots");
			boolean booked[][] = new boolean[100][100];
			for (TimeslotBean timeslotBean : bookedTimeslots) {
				booked[timeslotBean.getTimeBuildUp().getHours()][timeslotBean
						.getTimeBuildUp().getDate()] = true;
				//System.out.println(timeslotBean.getTimeBuildUp().getHours()+" "+timeslotBean.getTimeBuildUp().getDate()+" "+booked[timeslotBean.getTimeBuildUp().getHours()][timeslotBean.getTimeBuildUp().getDate()]);
			}
			FestivalBean festival = (FestivalBean) pageContext
					.findAttribute("festival");

			String prevStage = "";
			if (pageContext.findAttribute("prevStage") != null
					&& ((String) pageContext.findAttribute("prevStage"))
							.length() != 0)
				prevStage = (String) pageContext.findAttribute("prevStage");
	%>


	<form name="timeslotform" method="post">

		<table>

			<tr>
				<td>Bands:</td>
				<td>Stages:</td>
				<td>Timeslots:</td>
			</tr>

			<tr>
				<td><select name="bands">
						<%
							for (BandBean band : bands) {
						%>
						<option value="<%=band.getName()%>"><%=band.getName()%></option>
						<%
							}
						%>
				</select></td>
				<td><select name="stages" onChange="timeslotform.submit();">
						<%
							for (StageBean stage : stages) {
						%>
						<option <%if (prevStage.equals(stage.getName())) {%> selected
							<%}%> value="<%=stage.getName()%>"><%=stage.getName()%></option>
						<%
							}
						%>
				</select></td>
				<td>
					<table border="1">
						<tr>
							<td></td>
							<%
								for (int i = festival.getStartDate().getDate(); i <= festival
											.getEndDate().getDate(); i++) {
							%>
							<td><%=i%> <%
 	switch (festival.getStartDate().getMonth()) {
 			case 0:
 %> January<%
 	;
 				break;
 			case 1:
 %> February<%
 	;
 				break;
 			case 2:
 %> March<%
 	;
 				break;
 			case 3:
 %> April<%
 	;
 				break;
 			case 4:
 %> May<%
 	;
 				break;
 			case 5:
 %> June<%
 	;
 				break;
 			case 6:
 %> July<%
 	;
 				break;
 			case 7:
 %> August<%
 	;
 				break;
 			case 8:
 %> September<%
 	;
 				break;
 			case 9:
 %> October<%
 	;
 				break;
 			case 10:
 %> November<%
 	;
 				break;
 			case 11:
 %> December<%
 	;
 				break;
 			}
 %></td>
							<%
								}
							%>

						</tr>
						<%
							for (int i = festival.getStartTime().getHours(); i <= festival
										.getEndTime().getHours() - 1; i++) {
						%>
						<tr>
							<td><%=i%>:00</td>
							<%
								for (int j = festival.getStartDate().getDate(); j <= festival
												.getEndDate().getDate(); j++) {
							%>

							<td><input type="checkbox" <%if (booked[i][j]) {%> disabled
								<%}%> name="timecell" value="<%=i%>_<%=j%>"></td>
							<%
								}
							%>
						</tr>
						<%
							}
						%>
					</table>
				</td>
			</tr>

			<%
				}
			%>

		</table>

		<input type="submit" value="Assign" name="Check" />

	</form>



</body>
</html>