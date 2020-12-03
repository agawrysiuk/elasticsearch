curl -X POST "localhost:9200/es-edu-index/_search?size=0&pretty" -H 'Content-Type: application/json' -d'
{
  "aggs": {
    "avg_numberOfClicks": { "avg": { "field": "numberOfClicks" } }
  }
}
'
