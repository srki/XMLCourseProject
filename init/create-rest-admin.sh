curl -v -X POST  --anyauth -u root:root \
  --header "Content-Type:application/json" \
  -d '{"user-name":"rest-admin", "type": "admin", "role": ["rest-admin"]}' \
  http://localhost:8002/manage/v2/users
