curl -X PUT "localhost:9200/es-edu-index?pretty" -H 'Content-Type: application/json' -d'
{
  "settings": {
    "index": {
      "number_of_shards": 3,
      "number_of_replicas": 2
    }
  }
}
'

# część z "index" jest opcjonalna, body równie dobrze może wyglądać tak:
#
#{
#  "settings": {
#    "number_of_shards": 3,
#    "number_of_replicas": 2
#  }
#}