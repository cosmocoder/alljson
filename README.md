# alljSON

A flexible and highly customizable JSON handling library.

## Serializing JSON
To convert an object to JSON simply call:
```java
JsonValue json = jsonMapper.getJson(myObject);
```

Then if you want the JSON as a string, call:
```java
String jsonString = json.toString() 
```

Note that there is a type named ```JsonValue```, so you can handle the output JSON. To do that you must know that there are three direct specializations of ```JsonValue```, based on [json.org specification](http://json.org/):

* ```JsonObject``` for JSON objects, using map-like syntax. Example: ```{"name":"John Doe","age":25}```
* ```JsonArray``` for JSON arrays, using array-like syntax. Example: ```["fist","second","third"]```
* ```JsonPrimitive``` which is further specialized in:

	* ```JsonString``` for strings (double quoted)
	* ```JsonBoolean``` for booleans
	* ```JsonNumber``` for numbers
	* ```JsonNull``` for null

![JsonObject, JsonArray and JsonPrimitive extend JsonValue. JsonString, JsonBoolean, JsonNumber and JsonNull extend JsonPrimitive.](http://yuml.me/92c84e1e)

