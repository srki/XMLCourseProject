curl -v -X PUT --anyauth -u root:root \
  --header "Content-Type:application/json" \
  -d '{"collection-lexicon":true}' \
  http://localhost:8002/manage/v2/databases/xml-project-database/properties
