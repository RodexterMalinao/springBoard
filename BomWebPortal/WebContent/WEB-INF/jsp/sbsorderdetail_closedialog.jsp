<%@ include file="/WEB-INF/jsp/header.jsp"%>

<script type="text/javascript">

function closeAndNotify() {
	// Firefox lose window.dialogArugments after post form

	var callback = (window.dialogArguments ? window.dialogArguments : window.opener).reloadcallback;
	if (callback) callback();

	self.close();
};

$(document).ready(function() {
	closeAndNotify();
});

</script>


<%@ include file="/WEB-INF/jsp/footer.jsp"%>