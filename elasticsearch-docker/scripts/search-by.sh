curl -X GET "localhost:9200/es-edu-index/_search?pretty" -H 'Content-Type: application/json' -d'
{
  "query": { "match": { "name": "1_MAIN" } }
}
'
