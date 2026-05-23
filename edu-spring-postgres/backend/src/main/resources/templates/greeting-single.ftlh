<!DOCTYPE HTML>
<html>
<head>
  <title>Getting Started: Serving Web Content</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <style>body { margin: 40px; font-family: Roboto; font-size: 20px; } h1 { font-size: 1.5rem }</style>
</head>
<body>
  <h1>Greeting ${id}</h1>
  <p>Hello from Spring Boot in <b>${name}</b>!</p>
  <#if body??><p>${body}</p></#if>
  <h1>Other pages:</h1>
  <ul>
    <li><a href="/greetings">All greetings</a></li>
    <li><a href="/new">New greeting</a></li>
  </ul>
</body>
