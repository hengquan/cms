if (!window.hj) {
	window.hj = {};
}

hj.open=function(url,target,querystring){  
	if (url.indexOf('?') < 0) 
		url+="?";
	else
		url+="&";
	if(querystring)	url+= querystring+'&' ;

	url+= 'rnd=' + Math.random();
	return window.open(url,!target?'_self':target,null);
};