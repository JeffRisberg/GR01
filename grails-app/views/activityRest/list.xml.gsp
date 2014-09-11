<%@ page contentType="application/xml; charset=utf-8" %>
<activities>
  <g:each in="${activities}" var="activity">
    <activity>
      <description>${activity.description}</description>
      <amount>${activity.amount}</amount>
      <activityType>${activity.activityType.name}</activityType>
      <startDate><g:formatDate date="${activity.startDate}" format="yyyy-MM-dd"/></startDate>
      <user>
        <userId>${activity.user.userId}</userId>
        <fullName>${activity.user.profile.fullName}</fullName>
      </user>
    </activity>
  </g:each>
</activities>
