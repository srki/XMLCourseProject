curl -v -X POST  --anyauth -u root:root \
  --header "Content-Type:application/json" \
  -d '{"rest-api": { "name": "xml-project-rest", "port": "8011", "database": "xml-project-database", "modules-database": "xml-project-database-modules" } }' \
  http://localhost:8002/v1/rest-apis
