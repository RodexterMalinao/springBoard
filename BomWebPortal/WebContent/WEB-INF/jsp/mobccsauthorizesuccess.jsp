<!doctype html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<title>Authorize Success</title>
<script type="text/javascript">
window.onload = function() {
	// Firefox lose window.dialogArugments after post form
	(window.dialogArguments ? window.dialogArguments : parent).authorized();
	self.close();
};
</script>
</head>
<body>
</body>
</html>