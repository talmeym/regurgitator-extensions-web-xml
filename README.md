# regurgitator-extensions-web-xml

regurgitator is a modular, light-weight, extendable java-based processing framework designed to 'regurgitate' canned or clever responses to incoming requests; useful for mocking or prototyping services.

start your reading here: [regurgitator-all](http://github.com/talmeym/regurgitator-all#regurgitator)

read more about regurgitator in xml here: [regurgitator-core-xml](http://github.com/talmeym/regurgitator-core-xml#xml-configuration-of-regurgitator)

## extension web steps in xml

### http-call 

an http-call step make an outward http call

```xml
<rgw:http-call host="http://otherservice.com" port="80" username="username" password="password">
	<rg:create-response source="response-metadata:status-code">
		<rge:freemarker-processor>http call returned ${value}</rge:freemarker-processor>
	</rg:create-response>
</rgw:http-call>
```

while host, port and (optional) user credentials are specified in the xml, all other attributes of the call are set from parameters within the message object, as listed below:

|context|parameter|call attribute|default (if not specified)|
|:---|:---|:---|:---|
|``request-metadata``|``method``|method|GET|
|``request-metadata``|``path-info``|path|/|
|``request-headers``|``[header name]`` | http header |not set|
|``request-payload``|``text`` (for POST calls) |payload|not set|
|``request-metadata``|``content-type`` (for POST calls) |content type|not set|
|``request-metadata``|``character-encoding`` (for POST calls) |character encoding|not set|

#### http proxy

if using the ``RegurgitatorServlet`` to mock http requests, an http-call step not placed within an isolated sequence will, upon execution, be given a message object that already contains the ``request-metadata`` context from the incoming http call, and will therefore act as an http proxy, making an http call identical to the one received by the ``RegurgitatorServlet``, except redirected to a new endpoint. this is useful for forwarding on any calls you wish not to mock / implement.

If instead you wish the http-call step to make an independant call, then the step should be placed within an isolated sequence, and should be preceeded by ``create-request`` steps to set the necessary method, path and header metadata.

```xml
<rg:sequence isolate="true">
	<rg:create-parameter name="request-metadata:method" value="GET"/>
	<rg:create-parameter name="request-metadata:path-info" value="/service-api"/>
	<rgw:http-call host="http://otherservice.com" port="80" username="username" password="password">
		<rg:create-response source="response-metadata:status-code">
			<rge:freemarker-processor>http call returned ${value}</rge:freemarker-processor>
		</rg:create-response>
	</rgw:http-call>
</rg:sequence>
```

the body of the http-call step can contain zero or more "response processing" steps, to be run when a response is received from the outgoing http call. these steps are given a new message object containing all the metadata associated with the http response (contexts such as ``response-metadata``, ``response-payload`` etc.) as well as the ``parameters`` and ``session`` contexts of the original message. 

### create-http-response

a create-http-response step returns a response from regurgitator, allowing ``response-metadata`` parameters to be set for ``content-type`` and ``status-code``.

```xml
<rgw:create-http-response source="response" content-type="application/json" status-code="200"/>
```

this step is primarily a convenience step, agregating the following core config:

```xml
<rg:create-parameter name="response-metadata:status-code" value="200"/>
<rg:create-parameter name="response-metadata:content-type" value="application/json"/>
<rg:create-response source="response"/>
```

### create-file-response

a create-file-response step returns a response from regurgitator, with it's contents read from a file, the name of which is sourced from a parameter value. the main use case for this step is when you have an http request for a file, and you wish to simply return the contents of that file.

```xml
<rgw:create-file-response source="request-metadata:path-info" path-prefix="/assets"/>
```

the optional ``path-prefix`` attribute specifies a path fragment to be prefixed to the value found in the parameter, i.e. ``/file.xml`` becomes ``/assets/file.xml`` before the file is then read

## extension web constructs in xml

### query-param-processor

a query-param-processor processes a parameter value, extracting a value from within an http query string.

```xml
<rg:create-parameter name="id" value="something=this&amp;id=that&amp;third=another">
	<rgw:query-param-processor key="id"/>
</rg:create-parameter>
```

if the key appears in the query string more than once, the processor will aggregate the values into a collection.
