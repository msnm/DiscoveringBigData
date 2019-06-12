# Scala & Spark
In this branch we will play with Spark to analyse some data. 

Main class is SparkRunner.scala where you can run the following jobs:

##CustomerExpenditures
"CUST_EXP" "src/main/resources/data/customer-orders.csv"

##DegreesOfSeparation
"FIND_HERO"  "src/main/resources/data/Marvel-graph.txt"

##MinTemperatures
"MIN_TMP"  "src/main/resources/data/1800.csv"

##MostPopularSuperHero
"POP_HERO"  "src/main/resources/data/Marvel-graph.txt" "src/main/resources/data/Marvel-names.txt"


##WordCount
"WC" "local[*]" "src/main/resources/data/book.txt"

##EEG
Arguments:
EEG src/main/resources/data/EEG/Bart_NounVerb.csv src/main/resources/data/EEG/output/bart

"EEG" "s3://kdgbd52/EEG_Data/Bart_NounVerb.csv" "s3://kdgbd52/output/bart/run[TIMESTAMP]/Bart_NounVerb.csv"


spark-submit --class spark.SparkRunner --master local[*] project_2.12-0.1.jar EEG  Bart_NounVerb.csv /output/bart


