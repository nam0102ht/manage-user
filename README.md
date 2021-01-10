# manage-user
RUN file SQL in folder sql
RUN manage-user.sql to create table
THEN run all file to create data

All project use:
LIB: Vertx
Languages: Java 1.8
Database use: MYSQL


RUN project via IDE
Then use command curl to get data
```CURL *curl;
CURLcode res;
curl = curl_easy_init();
if(curl) {
  curl_easy_setopt(curl, CURLOPT_CUSTOMREQUEST, "POST");
  curl_easy_setopt(curl, CURLOPT_URL, "127.0.0.1:8081/api/v2/employees");
  curl_easy_setopt(curl, CURLOPT_FOLLOWLOCATION, 1L);
  curl_easy_setopt(curl, CURLOPT_DEFAULT_PROTOCOL, "https");
  struct curl_slist *headers = NULL;
  headers = curl_slist_append(headers, "Content-Type: application/json");
  curl_easy_setopt(curl, CURLOPT_HTTPHEADER, headers);
  const char *data = "{\r\n    \"resultCode\": 0,\r\n    \"result\": true,\r\n    \"queue\": \"SELECT\",\r\n    \"userId\": null,\r\n    \"data\": {\r\n    }\r\n}";
  curl_easy_setopt(curl, CURLOPT_POSTFIELDS, data);
  res = curl_easy_perform(curl);
}
curl_easy_cleanup(curl);
```
