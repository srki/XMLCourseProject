#XMLCourseProject Rest Api Specification

###POST /api/login 

Request example
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<user>
    <username>c</username>
    <password>c</password>
</user>
```
Response example
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?> 
<user> 
    <type>president</type> 
    <username>c</username> 
</user> 
```

###GET /api/login

Response example

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?> 
<user> 
    <type>president</type> 
    <username>c</username> 
</user>
```

###POST /api/register 

Request example
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<user>
    <username>d</username>
    <password>d</password>
</user>
```
