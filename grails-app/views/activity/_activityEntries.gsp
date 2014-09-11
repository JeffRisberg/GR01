
<div class="activitiesTable">
    <table style="width: 600px;">
        <thead>
           <tr>
           <th>Who</th>
           <th>Type</th>
           <th>When</th>
           <th>Amount</th>
           <th>Description</th>
           </tr>
        </thead>
        <tbody>
          <g:each in="${activities}" status="i" var="activity">
              <tr class="\${(i % 2) == 0 ? 'odd' : 'even'}">
              <td>${activity.user.profile.fullName}</td>
              <td>${activity.activityType.name}</td>
              <td><h:formattedDate date="${activity.startDate}" /></td>
              <td>${activity.amount} ${activity.activityType.unitOfMeasure.name}</td>
              <td>${activity.description}</td>
              </tr>
          </g:each>
        </tbody>
      </table>
</div>
 