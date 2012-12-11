# alljSON

A flexible and highly customizable JSON handling library.

## Serializing jSON
To convert an object to JSON simply call:
```java
JsonValue json = jsonMapper.getJson(myObject);
```

Then if you want the JSON as a string, call:
```java
String jsonString = json.toString() 
```

Note that there is a type named ```JsonValue```, so you can handle the output JSON. To do that you must know that there are three direct specializations of ```JsonValue```:

* ```JsonObject```, for JSON objects, using map-like syntax. Example: ```{"name":"John Doe","age":25}```
* ```JsonArray``` for JSON arrays, using array-like syntax. Example: ```["fist","second","third"]```
* ```JsonPrimitive```, which is further specialized in:

	* ```JsonString``` for strings (double quoted)
	* ```JsonBoolean``` for booleans
	* ```JsonNumber``` for numbers
	* ```JsonNull``` for null

