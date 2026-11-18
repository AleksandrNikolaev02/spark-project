#!/bin/bash

set -e

docker compose exec spark-master \
  /opt/spark/bin/spark-submit \
  --class com.senla.core.ArtistJob \
  --master spark://spark-master:7077 \
  --deploy-mode cluster \
  --conf spark.executor.instances=2 \
  --conf spark.executor.cores=1 \
  --conf spark.executor.memory=1g \
  /app/lyrics-spark-job.jar \
  /data/ru_lyrics_info.csv
