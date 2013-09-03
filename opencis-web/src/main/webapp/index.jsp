<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<title>Hello World</title>
</head>
<body>
<s:form action="HelloWorld">
	<s:textfield name="userName" label="User Name" />
	<s:select  list="templates" listKey="a" listValue="name"
		headerKey="0" headerValue="Template" label="Select a template" />
	<s:submit />
</s:form>
</body>
</html>