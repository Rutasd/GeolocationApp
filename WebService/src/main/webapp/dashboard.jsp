<%@ page import="java.util.ArrayList" %>
<%@ page import="org.bson.Document" %>
<%@ page import="java.util.List" %>
<%--
  Author - Ruta Deshpande
  andrew id - rutasurd
  email id - rutasurd@andrew.cmu.edu
  Dashboard.jsp for displaying analytics and logs
  Reference - https://www.w3schools.com/cssref/pr_border.php
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
<h1>Analytics Dashboard</h1>
<h2>Top Countries of requested IPs</h2>
<%
    ArrayList<String> topCountries = (ArrayList<String>) request.getAttribute("topCountries");
    for (int i = 0; i < topCountries.size(); i++) {
        if (topCountries.get(i) != null) { // check if entry is null
%>
<p><%= (i+1) + ". " + topCountries.get(i) %></p>
<%
        }
    }
%>
<h2>Top Organisations of requested IPs</h2>
<%
    ArrayList<String> topOrganisations = (ArrayList<String>) request.getAttribute("topOrganisations");
    for (int i = 0; i < topOrganisations.size(); i++) {
        if (topOrganisations.get(i) != null) {
%>
<p><%= (i+1) + ". " + topOrganisations.get(i) %></p>
<%
        }
    }
%>

<h2>Top Client Locales of requested IPs</h2>
<%
    ArrayList<String> topClientLocales = (ArrayList<String>) request.getAttribute("topClientLocales");
    for (int i = 0; i < topClientLocales.size(); i++) {
        String locale = topClientLocales.get(i);
        if (locale != null) {
%>
<p><%= (i+1) + ". " + locale %></p>
<%
        }
    }
%>
<h2>Logs</h2>
<table>
    <thead>
    <tr>
        <th style="border: 1px solid black;">Timestamp</th>
        <th style="border: 1px solid black;">Country</th>
        <th style="border: 1px solid black;">Client Locale</th>
        <th style="border: 1px solid black;">Organisation</th>
        <th style="border: 1px solid black;">Search IP</th>
        <th style="border: 1px solid black;">Response Status</th>
    </tr>
    </thead>
    <tbody>
    <% List<Document> logs = (List<Document>) request.getAttribute("allLogs");
        for (Document doc : logs) { %>
    <tr>
        <td style="border: 1px solid black;"><%= doc.getString("timestamp") == null ? "N/A" : doc.getString("timestamp") %></td>
        <td style="border: 1px solid black;"><%= doc.getString("Country") == null ? "N/A" : doc.getString("Country") %></td>
        <td style="border: 1px solid black;"><%= doc.getString("ClientLocale") == null ? "N/A" : doc.getString("ClientLocale") %></td>
        <td style="border: 1px solid black;"><%= doc.getString("Organisation") == null ? "N/A" : doc.getString("Organisation") %></td>
        <td style="border: 1px solid black;"><%= doc.getString("searchIP") == null ? "N/A" : doc.getString("searchIP") %></td>
        <td style="border: 1px solid black;"><%= doc.getString("ResponseStatus") == null ? "N/A" : doc.getString("ResponseStatus") %></td>
    </tr>
    <% } %>
    </tbody>
</table>

</body>
</html>
