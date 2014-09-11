<html>
<head>
<title>Activities</title>
</head>
<body>
<table style="width: 600px;">
	<thead>
  	<tr>
           <th>Type</th>
           <th>When</th>
           <th>Amount</th>
           <th>Description</th>
  	</tr>
	</thead>
	<tbody>
  	<g:each in="${activities}" status="i" var="activity">
    	<tr class="\${(i % 2) == 0 ? 'odd' : 'even'}">
              <td>${activity.activityType.name}</td>
              <td><g:formatDate format="MM/dd/yyyy" date="${activity.startDate}" /></td>
              <td>${activity.amount} uom</td>
              <td>${activity.description}</td>
    	</tr>
  	</g:each>
	</tbody>
</table>
</body>
</html>
