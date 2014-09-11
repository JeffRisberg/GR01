<html>
<head>
	<title>Activity History for ${profile.fullName}</title>
	<meta name="layout" content="main"/>
</head>
<body>
  <h1>Activity Timeline for ${profile.fullName}</h1>
  <div class="allActivities">
  <div class="list" style="margin: 5px 5px 10px 5px;">
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
          <g:each in="${user.activities}" status="i" var="activity">
              <tr class="\${(i % 2) == 0 ? 'odd' : 'even'}">
              <td>${activity.activityType.name}</td>
              <td><h:formattedDate date="${activity.startDate}" /></td>
              <td>${activity.amount} ${activity.activityType.unitOfMeasure.name}</td>
              <td>${activity.description}</td>
              </tr>
          </g:each>
        </tbody>
      </table>
  </div>
  </div>
  <g:link class="buttons" controller="user" action="newActivity">Add New Activity</g:link>
	<g:link class="buttons" controller="user" action="selectActivityTypes">Change Activity Types</g:link>
</body>
</html>