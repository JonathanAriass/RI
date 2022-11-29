import json 

from elasticsearch import Elasticsearch, helpers

def main():

    READONLY_PASSWORD = "abretesesamo"

    global es

    es = Elasticsearch(
        "https://localhost:9200",
        ca_certs="./http_ca.crt",
        basic_auth=("lectura", READONLY_PASSWORD)
    )


    # Busqueda de resultados con search (limite de 10000 hits)
    results = es.search(
        index="tweets-20090624-20090626-en_es-10percent",
        q="michael jackson"
    )

    print(str(results["hits"]["total"]) + " resultados para la query q=\"michael jackson\" con search")


    # Busqueda de resultados con count (no hay limite de hits)
    results = es.count(
        index="tweets-20090624-20090626-en_es-10percent",
        q="michael jackson"
    )

    print(str(results["count"])+ " resultados para la query q=\"michael jackson\" con count")


    # Busqueda de resultados con scan (hay limite de hits pero se puede iterar sobre los resultados)
    results = helpers.scan(
        es,
        index="tweets-20090624-20090626-en_es-10percent",
        query={
            "query": {
                "match": {
                    "text": "michael jackson"
                }
            }
        },
        scroll="1m", # no hacen falta
        size=10000, # es practico para indices muy grandes
    )

    # print (len(list(results)), "resultados para la query q=\"michael jackson\" con scan")
    
    # Codigo para iterar sobre los resultados (mostrar cada documento)
    for result in results:
        print(result)
    

if __name__ == '__main__':
    main()
